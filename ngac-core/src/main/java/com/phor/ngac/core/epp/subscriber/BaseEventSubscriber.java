package com.phor.ngac.core.epp.subscriber;


import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.phor.ngac.consts.FtlEnum;
import com.phor.ngac.core.epp.events.*;
import com.phor.ngac.core.pap.PolicyAdministrationPoint;
import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.po.relation.CommonRelation;
import com.phor.ngac.utils.FreemarkerUtils;
import com.phor.ngac.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Slf4j
@Component
public class BaseEventSubscriber {

    private static ConcurrentHashMap<Class<?>, List<Method>> handlerRouteMap;
    @Resource
    private PolicyAdministrationPoint neo4jPap;

    /**
     * 初始化handlerRouteMap
     *
     * @see AddNodeEvent
     * @see DeleteNodeEvent
     * @see AddRelationEvent
     * @see DeleteRelationEvent
     */
    @PostConstruct
    @SuppressWarnings("java:S2696")
    public void init() {
        log.info("BaseEventSubscriber init");
        Method[] methods = this.getClass().getDeclaredMethods();
        handlerRouteMap = Arrays.stream(methods)
                .collect(Collectors.groupingBy(
                        method -> Arrays.stream(method.getParameterTypes()).findFirst().orElse(UnknownEvent.class),
                        ConcurrentHashMap::new,
                        Collectors.toList()));
        log.info("HANDLER_ROUTE_MAP: {}", handlerRouteMap);
    }

    protected void handleEventDispatcher(BaseEvent event) {
        Class<? extends BaseEvent> childEventClass = event.getType();
        BaseEventSubscriber.handlerRouteMap.get(childEventClass).forEach(method -> {
            try {
                // 获取代理对象执行方法
                BaseEventSubscriber instance = SpringContextUtils.getBean(this.getClass());
                method.invoke(instance, event);
            } catch (Exception e) {
                log.error("handleEventDispatcher error", e);
            }
        });
    }

    private void handleAddNodeEvent(AddNodeEvent event) {
        log.debug("handleAddNodeEvent: {}", event);

        List<String> labelList = event.getLabels();
        Map<String, Object> propertyMap = event.getPropertyMap();

        // 目前项目只有neo4j单数据源，所以直接提交cypher给pap执行
        Map<String, Object> params = MapUtil.ofEntries(
                MapUtil.entry("labels", labelList),
                MapUtil.entry("properties", JSON.toJSONString(propertyMap, JSONWriter.Feature.UnquoteFieldName))
        );
        log.info("params: {}", params);
        String cypher = FreemarkerUtils.getContentByTemplatePath(FtlEnum.ADD_NODE.getFullFtlPath(),
                params); // neo4j的property不支持引号

        log.debug("cypher: {}", cypher);
        neo4jPap.addNode(cypher);
    }

    private void handleDeleteNode(DeleteNodeEvent event) {
        log.info("handleDeleteNodeEvent: {}", event);

        List<String> label = event.getLabels();
        Long id = event.getId();
        String name = event.getName();

        if (Objects.isNull(id) && StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("id和name不能同时为空");
        }

        String cypher = FreemarkerUtils.getContentByTemplatePath(FtlEnum.DELETE_NODE.getFullFtlPath(),
                MapUtil.ofEntries(
                        MapUtil.entry("labels", label),
                        MapUtil.entry("id", id),
                        MapUtil.entry("name", name)
                ));

        neo4jPap.deleteNode(cypher);
    }

    public void handleAddRelation(AddRelationEvent event) {
        CommonNode source = event.getSource();
        CommonNode target = event.getTarget();
        CommonRelation relation = event.getRelation();
        Map<String, Object> propertyMap = relation.getPropertyMap();

        String cypher = FreemarkerUtils.getContentByTemplatePath(FtlEnum.ADD_RELATION.getFullFtlPath(),
                MapUtil.ofEntries(
                        MapUtil.entry("source", source),
                        MapUtil.entry("target", target),
                        MapUtil.entry("relationLabel", relation.getType()),
                        MapUtil.entry("propertyMap", JSON.toJSONString(propertyMap, JSONWriter.Feature.UnquoteFieldName))
                ));
        log.info("add rel cypher: {}", cypher);
        neo4jPap.addRelation(cypher);
    }

    public void handleDelRelation(DeleteRelationEvent event) {
        CommonNode source = event.getSource();
        CommonNode target = event.getTarget();
        CommonRelation relation = event.getRelation();

        Long id = relation.getId();

        String cypher = FreemarkerUtils.getContentByTemplatePath(FtlEnum.DEL_RELATION.getFullFtlPath(),
                MapUtil.ofEntries(
                        MapUtil.entry("source", source),
                        MapUtil.entry("target", target),
                        MapUtil.entry("relation", relation)
                ));
        log.info("del rel cypher: {}", cypher);
        neo4jPap.deleteRelation(cypher);
    }

    private void handleUnknownEvent(UnknownEvent event) {
        log.info("handleUnknownEvent: {}", event);
    }
}
