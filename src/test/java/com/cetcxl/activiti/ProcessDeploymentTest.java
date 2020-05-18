package com.cetcxl.activiti;

import com.cetcxl.activity.ActivityDemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityDemoApplication.class)
@Slf4j
public class ProcessDeploymentTest {
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    RepositoryService repositoryService;

    /**
     * 部署流程定义
     * 表:
     *  流程定义表：ACT_RE_PROCDEF
     *  流程部署表:act_re_deployment
     *  流程文件信息表:act_ge_bytearray
     */
    @Test
    public void deploy() {

        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/a.bpmn").deploy();
        System.out.println("ID："+deployment.getId());
        System.out.println("name:"+deployment.getName());
        System.out.println("key:"+deployment.getKey());
    }

    /**
     * 部署流程查询
     */
    @Test
    public void queryDeployment(){

        List<Deployment> deployments = repositoryService.createDeploymentQuery().processDefinitionKey("leave").list();

        deployments.stream().forEach(deployment -> {
            System.out.println("deployment name:"+deployment.getName());
            System.out.println("deployment id:"+deployment.getId());
        });
    }

    /**
     * 删除流程定义
     */
    @Test
    public void deleteDeployment(){
        repositoryService.deleteDeployment("2501");
    }
}
