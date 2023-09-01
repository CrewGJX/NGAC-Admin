package com.phor.ngac.core.epp.bus;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import(AsyncEventBusConfig.class)
public class EventBusConfig {
    @Bean
    @ConditionalOnMissingBean
    public EventBus eventBus() {
        log.info("eventbus: BlockingEventBus");
        return new EventBus();
    }
}
