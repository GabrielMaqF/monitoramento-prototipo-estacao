//package com.maqfiltros.sensors_contract.config;
//
//import java.util.concurrent.Executor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//@Configuration
//@EnableAsync
//public class AsyncConfig {
//
//    @Bean(name = "taskExecutor")
//    public Executor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(4); // mínimo de threads
//        executor.setMaxPoolSize(4); // máximo de threads
//        executor.setQueueCapacity(100); // fila de tarefas aguardando
//        executor.setThreadNamePrefix("AsyncExecutor-");
//        executor.initialize();
//        return executor;
//    }
//}
