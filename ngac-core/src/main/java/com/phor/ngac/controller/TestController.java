package com.phor.ngac.controller;

import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.RelationEnum;
import com.phor.ngac.core.epp.EventFactory;
import com.phor.ngac.core.epp.events.AddNodeEvent;
import com.phor.ngac.core.epp.events.AddRelationEvent;
import com.phor.ngac.core.epp.events.DelRelationEvent;
import com.phor.ngac.core.epp.events.DeleteNodeEvent;
import com.phor.ngac.core.epp.publisher.EventPublisher;
import com.phor.ngac.core.pap.PolicyAdministrationPoint;
import com.phor.ngac.entity.po.node.BaseNode;
import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.po.relation.BaseRelation;
import com.phor.ngac.entity.vo.requests.NodeOptVo;
import com.phor.ngac.entity.vo.requests.RelationOptVo;
import com.phor.ngac.entity.vo.responses.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 仅作为功能性验证，后续删除
 *
 * @author Phor
 * @version 0.1
 * @since 0.1
 */
@Slf4j
@RestController
@ConditionalOnBean(EventPublisher.class)
@ConditionalOnProperty(prefix = "test-api", name = "enable", havingValue = "true")
@RequestMapping("/test")
@Api(tags = "测试接口")
@Deprecated
public class TestController {
    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private PolicyAdministrationPoint neo4jPap;

    @PostMapping("/node/add")
    @ApiOperation("添加节点")
    public CommonResponse<String> test(@RequestBody NodeOptVo nodeOptVo) {
        AddNodeEvent nodeEvent = EventFactory.builder(AddNodeEvent.class)
                .node()
                .label(NodeEnum.getByLabel(nodeOptVo.getLabel()),
                        nodeOptVo::getLabel)
                .name(nodeOptVo.getName())
                .properties(nodeOptVo.getProperty())
                .build();
        eventPublisher.publish(nodeEvent);
        log.info("event published");
        return CommonResponse.success("添加节点成功");
    }

    @PostMapping("/node/delete")
    @ApiOperation("删除节点")
    public CommonResponse<String> delete(@RequestBody NodeOptVo nodeOptVo) {
        DeleteNodeEvent nodeEvent = EventFactory.builder(DeleteNodeEvent.class)
                .node()
                .name(nodeOptVo.getName())
                .id(nodeOptVo.getId())
                .label(NodeEnum.getByLabel(nodeOptVo.getLabel()),
                        nodeOptVo::getLabel)
                .build();
        eventPublisher.publish(nodeEvent);
        log.info("event published");
        return CommonResponse.success("delete");
    }

    @PostMapping("/node/list")
    @ApiOperation("查询节点")
    public CommonResponse<BaseNode> list(@RequestBody NodeOptVo nodeOptVo) {
        BaseNode node = neo4jPap.findNode(NodeEnum.getByLabel(nodeOptVo.getLabel()), nodeOptVo.getId(), nodeOptVo.getName());
        return CommonResponse.success(node);
    }

    @PostMapping("/rel/add")
    @ApiOperation("添加关系")
    public CommonResponse<String> addRel(@RequestBody RelationOptVo relationOptVo) {
        AddRelationEvent addRelationEvent = new AddRelationEvent();
        CommonNode source = relationOptVo.getSource();
        addRelationEvent.setSource(source);

        CommonNode target = relationOptVo.getTarget();
        addRelationEvent.setTarget(target);

        addRelationEvent.setRelationLabel(relationOptVo.getCommonRelation().getType());
        addRelationEvent.setPropertyMap(relationOptVo.getCommonRelation().getPropertyMap());

        eventPublisher.publish(addRelationEvent);
        return CommonResponse.success("addRel");
    }

    @PostMapping("/rel/del")
    @ApiOperation("删除关系")
    public CommonResponse<String> delRel(@RequestBody RelationOptVo relationOptVo) {
        DelRelationEvent delRelationEvent = new DelRelationEvent();
        CommonNode source = relationOptVo.getSource();
        delRelationEvent.setSource(source);

        CommonNode target = relationOptVo.getTarget();
        delRelationEvent.setTarget(target);

        delRelationEvent.setRelation(relationOptVo.getCommonRelation());

        eventPublisher.publish(delRelationEvent);
        return CommonResponse.success("delRel");
    }

    @GetMapping("/rel/list/byType")
    @ApiOperation("通过关系属性查询")
    public CommonResponse<BaseRelation> listRelByType() {
        BaseRelation relation = neo4jPap.findRelation(RelationEnum.INHERIT, 44L, "");
        return CommonResponse.success(relation);
    }

    @GetMapping("/rel/list/byNodes")
    @ApiOperation("通过节点查询关系")
    public CommonResponse<BaseRelation> listRelByNodes() {
        CommonNode source = new CommonNode();
        CommonNode target = new CommonNode();

        source.setLabels(NodeEnum.USER.getLabel());
        source.setName("testUser");

        target.setLabels(NodeEnum.USER_GROUP.getLabel());
        target.setName("用户组1:专网1");

        BaseRelation relation = neo4jPap.findRelation(source, target);
        return CommonResponse.success(relation);
    }

    @PostMapping("/permission/add/direct")
    @ApiOperation("添加权限")
    public CommonResponse<String> addPermission() {
        // 直接添加新的用户组and资源组，最小化权限
        return CommonResponse.success("addPermission");
    }

    @PostMapping("/permission/phb/direct")
    @ApiOperation("禁止权限")
    public CommonResponse<String> phbPermission() {
        // 直接在用户和资源之间添加禁止关系

        return CommonResponse.success("addPermission");
    }
}
