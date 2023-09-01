package com.phor.ngac.core.epp.bus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步事件总线
 *
 * @version 0.1
 * @since 0.1
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "ngac.eventbus", name = "async", havingValue = "true")
public class AsyncEventBusConfig {

    @Resource
    private ThreadPoolExecutor eventThreadPool;

    @Bean("eventBus")
    public EventBus asyncEventBus() {
        log.info("eventbus: AsyncEventBus");
        return new AsyncEventBus(eventThreadPool);
    }
}
