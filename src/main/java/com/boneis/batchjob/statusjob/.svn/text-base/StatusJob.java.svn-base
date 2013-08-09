package com.boneis.batchjob.statusjob;

import com.boneis.batchjob.base.BaseJob;
import com.boneis.batchjob.statusjob.step.CheckStep;
import com.boneis.batchjob.statusjob.step.CompleteStep;
import com.boneis.batchjob.statusjob.step.ExecuteStep;
import com.boneis.domain.base.root.Repository;
import com.boneis.domain.tool.batchjob.status.BatchJobStatus;

public class StatusJob extends BaseJob {

	public StatusJob() {
		this.getContextList().add("com/boneis/batchjob/statusjob/config/status-context.xml");
		//.......................................................................................................................
		this.setBatchno(4);
		this.getBatchstepList().add(new CheckStep());
		this.getBatchstepList().add(new ExecuteStep());
		this.getBatchstepList().add(new CompleteStep());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void init() {
		this.put("batchjobstatusRepository", (Repository<BatchJobStatus>)this.getBasecontext().getBean("batchjobstatusRepository"));
	}

}
