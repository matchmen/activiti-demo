package com.cetcxl.activity.config.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class AssignmentTaskListener implements TaskListener {
    private static final Logger logger = LoggerFactory.getLogger(CompleteTaskListener.class);
    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info("触发监听,任务【{}】分配已经给【】", delegateTask.getName(), delegateTask.getOwner());
        logger.info("任务变量:{}", delegateTask.getVariables());
        logger.info("AssignmentTaskListener：{}" ,delegateTask.getEventName());

    }
}
