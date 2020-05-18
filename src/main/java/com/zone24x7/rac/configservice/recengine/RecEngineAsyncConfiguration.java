package com.zone24x7.rac.configservice.recengine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class RecEngineAsyncConfiguration {

    @Bean(name = "bundleTaskExecutor")
    public TaskExecutor bundleTaskExecutor() {
        return getTaskExecutor();
    }


    @Bean(name = "ruleTaskExecutor")
    public TaskExecutor ruleTaskExecutor() {
        return getTaskExecutor();
    }


    @Bean(name = "recTaskExecutor")
    public TaskExecutor recTaskExecutor() {
        return getTaskExecutor();
    }


    @Bean(name = "recSlotTaskExecutor")
    public TaskExecutor recSlotTaskExecutor() {
        return getTaskExecutor();
    }




    /**
     * Return task executor.
     */
    private static TaskExecutor getTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        return executor;
    }
}
