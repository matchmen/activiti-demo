package com.cetcxl.activity.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeploymentController {

    private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("/listDeployment")
    public ModelAndView listDeployment(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("deployment");
        logger.info("查询已部署流程列表");
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        logger.info("已部署流程定义数量为:{}", deployments.size());
        modelAndView.addObject("list", deployments);
        return modelAndView;
    }

}
