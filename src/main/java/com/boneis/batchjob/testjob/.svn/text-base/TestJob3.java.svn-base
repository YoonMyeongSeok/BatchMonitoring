package com.boneis.batchjob.testjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boneis.batchjob.base.BaseJob;
import com.boneis.batchjob.testjob.step.TestJobStep1;
import com.boneis.batchjob.testjob.step.TestJobStep2;

public class TestJob3 extends BaseJob {
	
	private static Logger logger = LoggerFactory.getLogger(TestJob3.class);
	
	public TestJob3(){
		this.setBatchno(3);
		this.getBatchstepList().add(new TestJobStep1());
		this.getBatchstepList().add(new TestJobStep2());
		logger.info("TestJob3's added Step class!!");
	}

}
