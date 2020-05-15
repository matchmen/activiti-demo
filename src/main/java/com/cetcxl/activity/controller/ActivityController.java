package com.cetcxl.activity.controller;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/activiti")
@Slf4j
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private static String PROCESS_LEVEL = "myProcess_level";

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;


    /**
     * 查询流程定义（根据流程定义KEY）
     * @param processDefinitionKey
     * @return
     */
    @PostMapping(value="/getProcess")
    public String getProcess(@RequestParam(name = "processDefinitionKey",required = true) String processDefinitionKey){

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).list();

        logger.info("processDefinitionKey:{},processDefinitions Count:{},processDefinitions:{}", processDefinitionKey, processDefinitions.size(),processDefinitions);

        processDefinitions.stream().forEach(processDefinition -> {
            logger.info("processDefinition:{}", processDefinition);
            logger.info("processDefinition name:{}",processDefinition.getName());
            logger.info("processDefinition category:{}",processDefinition.getCategory());
            logger.info("processDefinition version:{}",processDefinition.getVersion());
            logger.info("processDefinition category:{}",processDefinition.getResourceName());
        });

        return "";
    }

    /**
     * 查询流程最新定义（根据流程定义ID）
     * @param processDefinitionId
     * @return
     */
    @PostMapping(value="/getLastProcess")
    public String getLastProcess(@RequestParam(name = "processDefinitionId",required = true) String processDefinitionId){

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionId).orderByProcessDefinitionVersion().desc().singleResult();

        logger.info("processDefinitionId:{},processDefinition:{}.", processDefinitionId, processDefinition);

        if(Objects.isNull(processDefinition)){
            return "为找到对应实例";
        }
        return processDefinition.getName();
    }

 /*   *//**   TODO 刪除流程定義
     *
     * @param processDefinitionId
     * @return
     *//*
    @PostMapping(value="/deleteProcess")
    public String deleteProcess(@RequestParam(name = "processDefinitionId",required = true) String processDefinitionId){

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).dele

        logger.info("processDefinitionId:{},processDefinition:{}.", processDefinitionId, processDefinition);

        if(Objects.isNull(processDefinition)){
            return "为找到对应实例";
        }
        return processDefinition.getName();
    }
*/

    /**
     * 基于流程定义开始一个流程
     * @param processKey
     * @return
     */
    @PostMapping(value="/startProcess")
    public String startProcess(@RequestParam(name = "processKey",required = true) String processKey){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey);

        logger.info("processKey:{},processInstance:{}.", processKey, processInstance);

        if(Objects.isNull(processInstance)){
            return "为找到对应实例";
        }
        return processInstance.getDeploymentId();
    }

    /**
     * 查询开始的流程实例
     * @param processInstanceId
     * @return
     */
    @PostMapping(value="/getProcessInstance")
    @ResponseBody
    public String getProcessInstance(@RequestParam(name = "processInstanceId") String processInstanceId){

        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        logger.info("processInstanceId:{},instance:{}.", processInstanceId, instance);
        logger.info("instance name:{}.", instance.getName());
        logger.info("instance desc:{}.", instance.getDescription());
        logger.info("instance variables:{}.", instance.getProcessVariables());
        if(Objects.isNull(instance)){
            return "为找到对应实例";
        }
        return instance.getName();
    }

    /**
     * 执行流程(根据流程实例ID)
     * @param processInstanceId
     * @return
     */
    @PostMapping(value = "/execute")
    public String execute(String processInstanceId) {

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();

        //任务内容(设置审批内容)
        Map<String, Object> map = new HashMap<>();

        taskService.complete(task.getId());

        logger.info("task:{}", task);

        return "OK";
    }


    /**
     * 根据流程参与人获取本人Task list
     * @param userId
     * @return
     */
    @PostMapping(value = "/listTasks")
    @ResponseBody
    public String listTasks(String userId) {

        logger.info("user:{}", userId);

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
        logger.info("tasks:{}", tasks);
        tasks.stream().forEach(task -> {
            logger.info("task state:{}",task.getDelegationState().name());
            logger.info("task name:{}",task.getName());
            logger.info("task desc:{}",task.getDescription());
            logger.info("task state:{}",task.getCategory());
        });

        return "";
    }

    /**
     * 获取所有
     * @return
     */
    @PostMapping(value = "/listAllTasks")
    @ResponseBody
    public String listAllTasks() {

        List<Task> tasks = taskService.createTaskQuery().list();

        logger.info("tasks:{}", tasks);
        tasks.stream().forEach(task -> {
            logger.info("task state:{}",task.getDelegationState().name());
            logger.info("task name:{}",task.getName());
            logger.info("task desc:{}",task.getDescription());
            logger.info("task state:{}",task.getCategory());
            logger.info("task assignee:{}",task.getAssignee());
        });

        return "";
    }

    /**
     * 获取流程实例当前task
     * @param processInstanceId
     * @return
     */
    @PostMapping(value = "/getCurrTask")
    public String getTaskById(String processInstanceId) {

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();

        logger.info("task:{}", task);

        return task.getId();
    }



    /**
     *
     * @param processInstanceId
     * @return
     */
    @PostMapping(value = "/count")
    @ResponseBody
    public String count(String processInstanceId) {

        long count = repositoryService.createDeploymentQuery().count();

        logger.info("count:{}", count);

        return "count is " + count;
    }


    /**
     * 新增审批流程
     * @return
     */
    @PostMapping(value = "/addProcess")
    public String addProcess() {

        return "OK";
    }

    /**
     * 申请定密
     */
    @PostMapping(value = "/apply")
    public String apply(String type,String fileName,String level,String msg,String userName) {
        try {
            //开始流程（创建流程）
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_LEVEL);
            //当前任务(申请人提交申请)
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
            //任务内容(设置审批内容)
            Map<String, Object> map = new HashMap<>();
            map.put("userName",userName);
            map.put("type",type);
            map.put("fileName",fileName);
            map.put("level",level);
            map.put("msg",msg);

            taskService.complete(task.getId(), map);

        }catch (Exception e){
            logger.error("Exception:{}", e);
            return "FAIL";
        }
        return "SUCCESS";
    }

    /**
     * 审批
     */
    @PostMapping(value = "/approve")
    public String approve(Boolean isPass,String taskId,String msg) {
        try {
            logger.info("taskId:{}", taskId);
            List<Task> list = taskService.createTaskQuery().taskId(taskId).list();
            logger.info("count by task id:{}", list.size());
            if (CollectionUtils.isEmpty(list)) {
                return "task not exist!";
            }
            Task task = list.get(0);
            //任务内容
            Map<String, Object> map = new HashMap<>();

            map.put("msg",msg);
            if(isPass){
                taskService.complete(task.getId(), map);
            }else {
                //TODO
            }

        }catch (Exception e){
            logger.error("Exception:{}", e);
            return "FAIL";
        }
        return "SUCCESS";
    }



}
