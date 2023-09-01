package com.phor.ngac.core.epp.subscriber;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.phor.ngac.core.epp.events.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "ngac.eventbus", name = "async", havingValue = "false")
@ConditionalOnBean(EventBus.class)
public class BlockingEventSubscriber extends BaseEventSubscriber {
    @Autowired
    private EventBus eventBus;

    @PostConstruct
    public void init() {
        log.info("BlockingEventSubscriber init");
        eventBus.register(this);
    }

    @Subscribe
    public void handleEvent(BaseEvent event) {
        log.info("handleEvent: {}", event);
        super.handleEventDispatcher(event);
    }

    @Subscribe
    public void audit(BaseEvent event) {
        log.info("预留审计: {}", event);
    }
}
