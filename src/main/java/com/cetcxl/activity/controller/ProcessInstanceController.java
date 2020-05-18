package com.cetcxl.activity.controller;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ProcessInstanceController {

    private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/listInstance")
    public ModelAndView listDeployment(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("instance");
        logger.info("查询所有流程实例");
        List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery().list();
        logger.info("流程实例数量为:{}", instances.size());
        modelAndView.addObject("list", instances);
        return modelAndView;
    }


    @GetMapping("/startProcess")
        public void startProcess(HttpServletResponse response, String processDefinitionKey,String userId,Integer days,String startDate,String endDate){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("instance");
        logger.info("发起流程。。。。。。。。。。。。");
        //设置发起人
        identityService.setAuthenticatedUserId(userId);
        //参数设置
        HashMap<String, Object> map = new HashMap<>();
        //设置下一步操作人(下一步还是自己)
        map.put("userId", userId);
        map.put("days", days);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey,map);
        logger.info("流程实例ID:{}", processInstance.getId());
        try {
            response.sendRedirect("/listInstance");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/instanceDetail")
    public ModelAndView instanceDetail(String instanceId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("instanceDetail");
        return modelAndView;
    }

    @GetMapping("/listTodoTask")
    public ModelAndView listTodoTask(String userId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task");
        logger.info("查询个人待办");
        //根据分组查询（角色）
        List<Task> tasks1 = taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
        //节点指定人员完成，根据用户查询代办
        //List<Task> task2 = taskService.createTaskQuery().taskAssignee(userId).active().list();

        List<Task> allTask = new ArrayList<>();
        //allTask.addAll(task2);
        allTask.addAll(tasks1);

        logger.info("待办数量为:{}", allTask.size());
        modelAndView.addObject("list", allTask);
        return modelAndView;
    }

    @GetMapping("/completeTask")
    @ResponseBody
    public String completeTask(String taskId,String userId,boolean isPass,String comment){
        logger.info("完成任务");
        //参数设置
        HashMap<String, Object> map = new HashMap<>();
        //是否通过
        map.put("pass", isPass);
        taskService.claim(taskId,userId);
        //添加批注
        taskService.addComment(taskId, null, comment);

        taskService.complete(taskId,map);

        return "SUCCESS";
    }



}
