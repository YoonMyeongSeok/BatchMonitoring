package com.boneis.batchjob.testjob.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boneis.batchjob.base.BaseJobStep;

public class TestJobStep2 extends BaseJobStep {

	private static Logger logger = LoggerFactory.getLogger(TestJobStep2.class);

	@Override
	protected void exec() {
		logger.info("TestJobStep2.exec(), map"+this.get("paramtest"));
	}
	
}
