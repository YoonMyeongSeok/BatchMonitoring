package com.boneis.batchjob.testjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boneis.batchjob.base.BaseJob;
import com.boneis.batchjob.testjob.step.TestJobStep1;
import com.boneis.batchjob.testjob.step.TestJobStep2;

public class TestJob extends BaseJob {
	
	private static Logger logger = LoggerFactory.getLogger(TestJob.class);
	
	public TestJob(){
		for(int i=0;i<100;i++){
			this.setBatchno(1);
			this.getBatchstepList().add(new TestJobStep1());
			this.getBatchstepList().add(new TestJobStep2());
		}
	}

}
