package com.hometask.task_security;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(proxyBeanMethods = false)
@EnableScheduling
public class TaskSecurityApplication {

    public static void main(String[] args) {
        run(TaskSecurityApplication.class, args);
    }

}
