package com.phor.ngac.core.epp.bus;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 异步事件总线线程池
 *
 * @see AsyncEventBusConfig
 * @since 0.1
 */
@Configuration
@ConditionalOnProperty(prefix = "ngac.eventbus", name = "async", havingValue = "true")
public class EventThreadPoolConfig {
    // 事件队列
    protected static final ArrayBlockingQueue<Runnable> EVENT_QUEUE = new ArrayBlockingQueue<>(1000);

    @Bean("eventThreadPool")
    public ThreadPoolExecutor eventThreadPool() {
        return new ThreadPoolExecutor(5,
                10,
                10,
                TimeUnit.SECONDS,
                EVENT_QUEUE);
    }
}
