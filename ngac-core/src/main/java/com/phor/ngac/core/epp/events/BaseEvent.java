package com.phor.ngac.core.epp.events;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 所有自定义事件需要继承此基础事件
 *
 * @date 2023/8/21
 * @since 0.1
 */
@Data
@Slf4j
public class BaseEvent {
    private Class<? extends BaseEvent> type;

    protected BaseEvent() {
        this.type = this.getClass();
        log.info("BaseEvent init: {}", this.type);
    }
}