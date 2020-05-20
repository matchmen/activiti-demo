package com.cetcxl.activity.config.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessStartListener implements ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(ProcessStartListener.class);
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        logger.info("【{}】启动了流程【{}】.", delegateExecution.getCurrentActivityName());
    }
}
