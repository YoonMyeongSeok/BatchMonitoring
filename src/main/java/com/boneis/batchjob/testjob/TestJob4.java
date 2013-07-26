package com.boneis.batchjob.testjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boneis.batchjob.base.BaseJob;
import com.boneis.batchjob.testjob.step.TestJobStep1;
import com.boneis.batchjob.testjob.step.TestJobStep2;

public class TestJob4 extends BaseJob {
	
	private static Logger logger = LoggerFactory.getLogger(TestJob4.class);
	
	public TestJob4(){
		this.setBatchno(29);
		this.getBatchstepList().add(new TestJobStep1());
		this.getBatchstepList().add(new TestJobStep2());
		logger.info("TestJob4's added Step class!!");
	}
}