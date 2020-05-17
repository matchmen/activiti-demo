package com.cetcxl.activiti;

import com.cetcxl.activity.ActivityDemoApplication;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityDemoApplication.class)
public class ProcessInstanceTest {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    /**
     * 发起流程
     * 发起人：张三
     *
     */
    @Test
    public void startProcess(){
        //流程发起人（申请人）
        identityService.setAuthenticatedUserId("zhangsan");
        Map<String, Object> map = new HashMap<>();
        map.put("userId", "zhangsan");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1",map);
        runtimeService.setProcessInstanceName(processInstance.getId(),"请假申请");


        System.out.println(processInstance.getName());
    }

    /**
     * 获取实例信息
     */
    @Test
    public void queryProcessInstanceDetail(){

        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionKey("leave").active().list();

        processInstances.stream().forEach(processInstance -> {

            System.out.println("instance name:"+processInstance.getName());
            System.out.println("instance start user id:"+processInstance.getStartUserId());

        });
    }

    @Test
    public void queryInstance() {
        List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery().processDefinitionKey("myProcess_1").active().list();

        System.out.println("activity size:" + instances.size());
    }

    /**
     * 我的待办
     *
     */
    @Test
    public void queryTodo() {
        //节点指定角色完成，根据用户查询代办
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey("myProcess_1").taskCandidateOrAssigned("lisi").list();
        //节点指定人员完成，根据用户查询代办
        //List<Task> tasks = taskService.createTaskQuery().processDefinitionKey("myProcess_1").taskAssignee("zhangsan").list();
        tasks.stream().forEach(task -> {
            System.out.println("task name:"+task.getName());
            System.out.println("task:"+task);
        });

    }

    @Test
    public void apply() {

        identityService.setAuthenticatedUserId("zhangsan");

        Map<String, Object> map = new HashMap<>();
        map.put("pass", true);
        Task task = taskService.createTaskQuery().processDefinitionKey("myProcess_1").taskAssignee("zhangsan").singleResult();
        taskService.complete(task.getId(),map);
    }

    @Test
    public void complete(){
        identityService.setAuthenticatedUserId("lisi");
        Map<String, Object> map = new HashMap<>();
        map.put("pass", false);
        taskService.claim("35005","lisi");
        taskService.complete("35005",map);
    }

    /**
     * 查询历史记录
     */
    @Test
    public void queryHistory(){
        HistoricTaskInstanceQuery instanceQuery = historyService.createHistoricTaskInstanceQuery().processInstanceId("32501");

        List<HistoricTaskInstance> list = instanceQuery.list();

        list.stream().forEach(historicTaskInstance -> {
            System.out.println("任务名称："+historicTaskInstance.getName());
            System.out.println("经办人："+historicTaskInstance.getAssignee());
        });

    }


}
