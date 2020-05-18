package com.cetcxl.activity;


import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class ActivityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityDemoApplication.class,args);
    }
    
}
