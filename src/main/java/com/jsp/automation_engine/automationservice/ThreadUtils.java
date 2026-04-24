package com.jsp.automation_engine.automationservice;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadUtils {
    //  @Bean
//    public ThreadPoolExecutor automationExecutor(){
//        return new ThreadPoolExecutor(
//                10,                     // corePoolSize
//                20,                     // maxPoolSize
//                60L, TimeUnit.SECONDS, // keepAliveTime
//                new LinkedBlockingQueue<>(50), // queueCapacity
//                new ThreadFactory() {
//                ,
//                new ThreadPoolExecutor.AbortPolicy() // rejection policy
//        );
//
//    }
//
}
