package com.phor.ngac.core.epp;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.core.epp.events.BaseEvent;
import com.phor.ngac.core.epp.events.UnknownEvent;
import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.po.relation.CommonRelation;
import com.phor.ngac.exception.EventFactoryException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Supplier;

/**
 * 事件工厂，用于创建事件
 *
 * @see com.phor.ngac.core.epp.events.BaseEvent
 * @since 0.1
 */
@Slf4j
public class EventFactory {

    private EventFactory() {
    }

    /**
     * 无类型，直接构建{@link UnknownEvent}
     *
     * @return UnknownEvent事件构建器
     */
    public static Builder<UnknownEvent> builder() {
        return new Builder<>(UnknownEvent.class);
    }

    /**
     * 构建指定类型的事件
     *
     * @param eventClass 事件类型
     * @param <T>        事件类型
     * @return 指定类型事件构建器
     */
    public static <T extends BaseEvent> Builder<T> builder(Class<T> eventClass) {
        return new Builder<>(eventClass);
    }

    /**
     * 事件构建器
     *
     * @param <T> 事件类型
     */
    public static class Builder<T extends BaseEvent> {
        private final Class<T> eventClass;

        Builder(Class<T> eventClass) {
            this.eventClass = eventClass;
        }

        public NodeBuilder<T> node() {
            return new NodeBuilder<>(this.eventClass);
        }

        public RelationBuilder<T> relation() {
            return new RelationBuilder<>(this.eventClass);
        }

        public T build() {
            try {
                return eventClass.newInstance();
            } catch (Exception e) {
                throw new EventFactoryException("构建事件失败", e);
            }
        }
    }

    @SuppressWarnings({"all"})
    public static class NodeBuilder<T extends BaseEvent> extends Builder<T> {
        private List<String> labels;
        private String name;
        private Long id;
        private Map<String, Object> propertyMap;

        NodeBuilder(Class<T> eventClass) {
            super(eventClass);
        }

        public NodeBuilder<T> properties(Map<String, Object> properties) {
            if (Objects.isNull(properties)) {
                return this;
            }

            if (Objects.isNull(this.propertyMap)) {
                this.propertyMap = properties;
                return this;
            }

            this.propertyMap.putAll(properties);
            return this;
        }

        public NodeBuilder<T> label(String... label) {
            this.labels = Arrays.asList(label);
            return this;
        }

        public NodeBuilder<T> label(Iterable<String> label) {
            ArrayList<String> labelList = new ArrayList<>();
            label.forEach(labelList::add);
            this.labels = labelList;
            return this;
        }

        /**
         * 设置label
         *
         * @param label 节点类型
         * @return this
         */
        public NodeBuilder<T> label(List<String> label) {
            this.labels = label;
            return this;
        }

        /**
         * 如果nodeEnum为UNKNOWN，则使用callback返回的label
         *
         * @param nodeEnum 节点类型
         * @param callback 回调函数
         * @return this
         */
        public NodeBuilder<T> label(NodeEnum nodeEnum, Supplier<List<String>> callback) {
            if (nodeEnum == NodeEnum.UNKNOWN) {
                this.labels = callback.get();
                return this;
            }
            this.labels = nodeEnum.getLabel();
            return this;
        }

        public NodeBuilder<T> label(NodeEnum nodeEnum) {
            this.labels = nodeEnum.getLabel();
            return this;
        }

        public NodeBuilder<T> name(String name) {
            this.name = name;
            if (Objects.isNull(this.propertyMap)) {
                this.propertyMap = MapUtil.of("name", name);
                return this;
            }
            this.propertyMap.put("name", name);
            return this;
        }

        public NodeBuilder<T> id(Long id) {
            this.id = id;
            return this;
        }

        @Override
        public T build() {
            T build = super.build();
            // 找到泛型T所有的属性和对应的set方法
            Arrays.stream(ReflectUtil.getFields(build.getClass()))
                    .peek(ReflectUtil::setAccessible)
                    .filter(field -> !field.getName().equals("log") && !field.getName().equals("type"))
                    .forEach(field -> {
                        log.info("field: {}", field);
                        String fieldName = field.getName();
                        String setMethodName = "set" + StringUtils.capitalize(fieldName);
                        log.info("setMethodName: {}", setMethodName);
                        Method setMethod = ReflectUtil.getMethodByName(build.getClass(), setMethodName);
                        Object fieldValue = ReflectUtil.getFieldValue(this, fieldName);
                        try {
                            setMethod.invoke(build, fieldValue);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new EventFactoryException("设置baseEvent变量失败", e);
                        }
                    });
            log.info("build: {}", build);
            return build;
        }
    }

    public static class RelationBuilder<T extends BaseEvent> extends Builder<T> {
        private CommonNode source;
        private CommonNode target;
        private CommonRelation relation;

        RelationBuilder(Class<T> eventClass) {
            super(eventClass);
        }

        public RelationBuilder<T> sourceNode(CommonNode source) {
            this.source = source;
            return this;
        }

        public RelationBuilder<T> targetNode(CommonNode target) {
            this.target = target;
            return this;
        }

        public RelationBuilder<T> relationData(CommonRelation relation) {
            this.relation = relation;
            return this;
        }

        @Override
        public T build() {
            T build = super.build();
            // 找到泛型T所有的属性和对应的set方法
            Arrays.stream(ReflectUtil.getFields(build.getClass()))
                    .peek(ReflectUtil::setAccessible)
                    .filter(field -> !field.getName().equals("log") && !field.getName().equals("type"))
                    .forEach(field -> {
                        log.info("field: {}", field);
                        String fieldName = field.getName();
                        String setMethodName = "set" + StringUtils.capitalize(fieldName);
                        log.info("setMethodName: {}", setMethodName);
                        Method setMethod = ReflectUtil.getMethodByName(build.getClass(), setMethodName);
                        Object fieldValue = ReflectUtil.getFieldValue(this, fieldName);
                        try {
                            setMethod.invoke(build, fieldValue);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new EventFactoryException("设置baseEvent变量失败", e);
                        }
                    });
            log.info("build: {}", build);
            return build;
        }
    }
}
