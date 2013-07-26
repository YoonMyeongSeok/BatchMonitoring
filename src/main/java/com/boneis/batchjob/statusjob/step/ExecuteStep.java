package com.boneis.batchjob.statusjob.step;

import java.util.List;

import com.boneis.batchjob.base.BaseJobStep;
import com.boneis.batchjob.base.constant.Batch;
import com.boneis.domain.tool.batchjob.BatchJob;
import com.boneis.domain.tool.batchjob.status.BatchJobStatus;
import com.boneis.support.batchlauncher.BatchJobLauncher;
import com.boneis.support.util.Util;

public class ExecuteStep extends BaseJobStep {

	@Override
	protected void exec() {
		BatchJobStatus batchjobstatus = (BatchJobStatus) this.get("batchjobstatus");
		if(batchjobstatus!=null&&batchjobstatus.getExecyn()==Batch.EXEC_START){
			BatchJob batchjob = this.getBasejob().getBatchjob();
			batchjob.setUseyn(Batch.USE_EXEC);
			batchjob.setServerip(Util.getServerIp());
			List<BatchJob> list = batchjob.getList("");
			for(int i=0;i<list.size();i++){
				BatchJob temp = list.get(i);
				if(temp.getServerip().equals(batchjob.getServerip())){
					try {
						BatchJobLauncher.execBatch(temp.getSeq());
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}	
				}
			}
		}
	}

}
