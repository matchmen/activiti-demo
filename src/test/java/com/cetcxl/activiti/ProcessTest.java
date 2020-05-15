package com.cetcxl.activiti;

import com.cetcxl.activity.ActivityDemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityDemoApplication.class)
@Slf4j
public class ProcessTest {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Test
    public void getFlow() {

        String processDefinitionId = "1000";

        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);

        System.out.println("processDefinition:" + processDefinition);
    }

    @Test
    public void startProcess() {
        /**
         * 启动流程
         * 1.
         */

        //定执行工作流程
        String instanceKey = "1000";



        //启动实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(instanceKey);



    }



}
