package com.phor.ngac.core.epp.publisher;

import com.google.common.eventbus.EventBus;
import com.phor.ngac.core.epp.events.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class EventPublisher {
    @Autowired
    private EventBus eventBus;

    @PostConstruct
    @ConditionalOnBean(EventBus.class)
    public void init() {
        log.info("EventPublisher init");
    }

    public void publish(BaseEvent event) {
        eventBus.post(event);
    }
}
