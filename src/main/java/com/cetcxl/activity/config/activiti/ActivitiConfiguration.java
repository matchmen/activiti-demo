package com.cetcxl.activity.config.activiti;

import com.cetcxl.activity.controller.DeploymentController;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ActivitiConfiguration implements ProcessEngineConfigurationConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);
    @Autowired
    private CustomGroupEntityManagerFactory customGroupEntityManagerFactory;

    @Autowired
    private CustomUserEntityManagerFactory customUserEntityManagerFactory;

    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        logger.debug("装配自定义用户、群组实例........");
        List<SessionFactory> customSessionFactories= new ArrayList<>();
        customSessionFactories.add(customGroupEntityManagerFactory);
        customSessionFactories.add(customUserEntityManagerFactory);
        processEngineConfiguration.setCustomSessionFactories(customSessionFactories);
    }
}
