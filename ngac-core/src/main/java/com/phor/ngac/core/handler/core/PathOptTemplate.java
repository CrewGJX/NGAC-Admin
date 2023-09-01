package com.phor.ngac.core.handler.core;

import cn.hutool.core.stream.StreamUtil;
import com.phor.ngac.consts.CommonAccess;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.RelationEnum;
import com.phor.ngac.entity.po.node.pc.PolicyClass;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Path;

import java.util.List;
import java.util.stream.Collectors;

/**
 * neo4j路径操作模板类
 *
 * @date 2023/8/11
 */
public class PathOptTemplate implements PathOptHandler {
    @Override
    public List<PolicyClass> getPolicyClassNodeFromPath(List<Path> path) {
        return path.stream().flatMap(p -> StreamUtil.of(p.nodes()))
                .filter(node -> StreamUtil.of(node.labels()).anyMatch(NodeEnum.PC.getLabel().get(0)::equals))
                .map(node -> {
                    PolicyClass policyClass = new PolicyClass();
                    policyClass.setId(node.id());
                    policyClass.setName(node.get("name").asString());
                    return policyClass;
                }).collect(Collectors.toList());
    }

    @Override
    public List<CommonAccess> getAccessListFromPath(List<Path> path) {
        return getAccessSegmentFromPath(path, RelationEnum.ACCESS);
    }

    @Override
    public List<CommonAccess> getPhbListFromPath(List<Path> path) {
        return getAccessSegmentFromPath(path, RelationEnum.PROHIBITION);
    }

    private List<CommonAccess> getAccessSegmentFromPath(List<Path> path, RelationEnum relationEnum) {
        return path.stream().flatMap(p -> StreamUtil.of(p.relationships().iterator()))
                .filter(relationship -> relationEnum.getType().equals(relationship.type()))
                .flatMap(relationship -> {
                    List<String> typeList = relationship.get("type").asList(Value::asString);
                    return typeList.stream().map(CommonAccess::getAccess);
                }).distinct()
                .collect(Collectors.toList());
    }
}
