package com.cetcxl.activity.config.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessEndListener implements ExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(ProcessEndListener.class);
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        logger.info("【{}】完成了【{}】.", delegateExecution.getCurrentActivityName());
    }
}
