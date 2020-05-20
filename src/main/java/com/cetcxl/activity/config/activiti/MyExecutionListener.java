package com.cetcxl.activity.config.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行监听器
 */
public class MyExecutionListener implements ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(CompleteTaskListener.class);
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        if (delegateExecution.getEventName().equals(EVENTNAME_START)) {
            logger.info("【{}】执行开始", delegateExecution.getEventName());
        }
        if (delegateExecution.getEventName().equals(EVENTNAME_TAKE)) {
            logger.info("【{}】领取");
        }
        if (delegateExecution.getEventName().equals(EVENTNAME_END)) {
            logger.info("【{}】执行完成", delegateExecution.getEventName());
        }
    }
}
