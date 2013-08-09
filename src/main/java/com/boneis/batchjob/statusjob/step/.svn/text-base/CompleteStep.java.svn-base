package com.boneis.batchjob.statusjob.step;

import com.boneis.batchjob.base.BaseJobStep;
import com.boneis.batchjob.base.constant.Batch;
import com.boneis.domain.base.root.Repository;
import com.boneis.domain.tool.batchjob.BatchJob;
import com.boneis.domain.tool.batchjob.status.BatchJobStatus;
import com.boneis.support.util.Util;

public class CompleteStep extends BaseJobStep {

	@Override
	protected void exec() {
		
		@SuppressWarnings("unchecked")
		BatchJobStatus batchjobstatus = new BatchJobStatus((Repository<BatchJobStatus>) this.get("batchjobstatusRepository"));
		batchjobstatus.setServerip(this.getBasejob().getBatchjob().getServerip());
		batchjobstatus.setExecyn(Batch.EXEC_END);
		batchjobstatus.update();
		
		BatchJob batchjob = this.getBasejob().getBatchjob();
		batchjob.setServerip(Util.getServerIp());
		batchjob.update("useyn.s");
		
	}

}
