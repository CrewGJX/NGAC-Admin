package com.phor.ngac.core.pap;

import cn.hutool.core.map.MapUtil;
import com.phor.ngac.consts.FtlEnum;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.RelationEnum;
import com.phor.ngac.core.pip.Neo4jPip;
import com.phor.ngac.entity.dto.AccessRight;
import com.phor.ngac.entity.dto.UserPermission;
import com.phor.ngac.entity.po.node.BaseNode;
import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.po.node.u.User;
import com.phor.ngac.entity.po.relation.BaseRelation;
import com.phor.ngac.entity.po.relation.CommonRelation;
import com.phor.ngac.exception.NodeNotFountException;
import com.phor.ngac.exception.PolicyClassException;
import com.phor.ngac.utils.FreemarkerUtils;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Result;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Supplier;

@Slf4j
@Component
public class Neo4jPap implements PolicyAdministrationPoint {

    @Resource
    private Neo4jPip pip;

    @Override
    public UserPermission findUserAndPermissions(String name) {
        // 查询用户
        Optional<User> node = pip.findUserAndRelations(name);
        UserPermission userPermission = new UserPermission();
        userPermission.setUser(node.orElse(null));
        return userPermission;
    }

    /**
     * 获取subject对resource的操作权限集合
     *
     * @param user   访问主体
     * @param object 资源主体
     * @return subject对resource的操作权限集合
     * pc节点连接的oa。需要过滤，accessRights需要走policyRelatedResources进行过滤。获得的结果的与集才是最后的ar。
     * e.g. db_ar = [{u1, rw, oa1, o1}, {u1, r, oa2, o1}] db_pc = [{pc1: o1, oa1}, {pc2: o1, oa2}]
     * final_ar = [{u1 ,r, o1}]
     */
    @Override
    public AccessRight getAccessRights(String user, String object) {
        return getAccessRights(user, object, NodeEnum.RESOURCE);
    }

    @Override
    public AccessRight getProhibitions(String user, String object) {
        // 没有admin操作，可以直接调用pip进行查询和返回
        return pip.getProhibitions(user, object).stream().findFirst().orElse(AccessRight.emptyAccess());
    }

    @Override
    public void addNode(String cypher) {
        simpleTxRunCypher(cypher);
    }

    @Override
    public void deleteNode(String cypher) {
        simpleTxRunCypher(cypher);
    }

    @Override
    public BaseNode findNode(NodeEnum nodeEnum, Long id, String name) {
        String cypher = FreemarkerUtils.getContentByTemplatePath(FtlEnum.FIND_NODE.getFullFtlPath(),
                MapUtil.ofEntries(
                        MapUtil.entry("label", nodeEnum.getLabel().stream().findFirst().orElse(null)),
                        MapUtil.entry("id", id),
                        MapUtil.entry("name", name)
                ));

        Result result = simpleRunCypher(cypher);

        if (!result.hasNext()) {
            return null;
        }

        return result.single().values().stream().map(value -> {
            // 构建commonNode
            Node node = value.asNode();

            List<String> nodeList = new ArrayList<>();
            CommonNode commonNode = new CommonNode();

            // 设置属性
            commonNode.setId(node.id());
            commonNode.setName(node.get("name").asString());

            node.labels().iterator().forEachRemaining(nodeList::add);
            commonNode.setLabels(nodeList);
            return commonNode;
        }).findFirst().orElse(null);
    }

    @Override
    public void addRelation(String cypher) {
        simpleTxRunCypher(cypher);
    }

    @Override
    public void deleteRelation(String cypher) {
        simpleTxRunCypher(cypher);
    }

    @Override
    public BaseRelation findRelation(CommonNode source, CommonNode target) {
        return findRelationInternal(() -> MapUtil.ofEntries(
                MapUtil.entry("source", source),
                MapUtil.entry("target", target)
        ));
    }

    @Override
    public BaseRelation findRelation(CommonNode source, CommonNode target, RelationEnum relationEnum) {
        return findRelationInternal(() -> MapUtil.ofEntries(
                MapUtil.entry("source", source),
                MapUtil.entry("target", target),
                MapUtil.entry("type", relationEnum.getType())
        ));
    }

    @Override
    public AccessRight getAccessRights(String user, String object, NodeEnum nodeEnum) {
        try {
            // 获取user信息
            BaseNode node = findNode(NodeEnum.USER, null, user);
            // 获取resource信息
            BaseNode resource = findNode(nodeEnum, null, object);

            // 没有节点存在，返回空权限集合
            if (Objects.isNull(node) || Objects.isNull(resource)) {
                log.error("获取权限集合失败，节点不存在，node: {}, resource: {}, operateNodeEnum: {}, object: {}",
                        node, resource, nodeEnum, object);
                throw new NodeNotFountException();
            }

            // 没有admin操作，可以直接调用pip进行查询和返回
            return pip.getAccessRights(user, object).stream().findFirst().orElse(AccessRight.emptyAccess());
        } catch (PolicyClassException e) {
            log.error("获取权限集合失败", e);
            // 返回空权限集合
            return AccessRight.emptyAccess();
        }
    }

    @Override
    public BaseRelation findRelation(RelationEnum relationEnum, Long id, String name) {
        return findRelationInternal(() -> MapUtil.ofEntries(
                MapUtil.entry("type", relationEnum.getType()),
                MapUtil.entry("id", id),
                MapUtil.entry("name", name)
        ));
    }

    public CommonRelation findRelationInternal(Supplier<Map<String, Object>> paramsSupplier) {
        String cypher = FreemarkerUtils.getContentByTemplatePath(FtlEnum.FIND_RELATION.getFullFtlPath(),
                paramsSupplier.get());

        log.info("findRelationInternal cypher: {}", cypher);

        Result result = simpleRunCypher(cypher);

        if (!result.hasNext()) {
            return null;
        }

        return result.single().values().stream().map(value -> {
            Relationship relationship = value.asRelationship();
            CommonRelation commonRelation = new CommonRelation();
            commonRelation.setId(relationship.id());
            commonRelation.setType(relationship.type());
            commonRelation.setPropertyMap(relationship.asMap());

            return commonRelation;
        }).findFirst().orElse(null);
    }

    public Result simpleRunCypher(String cypher) {
        Result result = pip.driverRunCypherWithoutTx(cypher);
        // debug日志记录result内部元素
        log.debug("cypher result: {}", result.keys());
        return result;
    }

    public void simpleTxRunCypher(String cypher) {
        Result result = pip.beginTx()
                .driverRunCypherWithTx(cypher)
                .commit()
                .result();
        log.debug("cypher result: {}", result.keys());
    }
}
