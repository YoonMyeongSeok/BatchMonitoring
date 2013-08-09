package com.boneis.batchjob.statusjob.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boneis.batchjob.base.BaseJobStep;
import com.boneis.domain.base.root.Repository;
import com.boneis.domain.tool.batchjob.status.BatchJobStatus;
import com.boneis.support.util.FileLogUtil;

public class CheckStep extends BaseJobStep {

	private static Logger logger = LoggerFactory.getLogger(CheckStep.class);
	
	@Override
	protected void exec() {
		@SuppressWarnings("unchecked")
		BatchJobStatus batchjobstatus = new BatchJobStatus((Repository<BatchJobStatus>) this.get("batchjobstatusRepository"));
		batchjobstatus.setServerip(this.getBasejob().getBatchjob().getServerip());
		this.put("batchjobstatus", batchjobstatus.getInfo());
		
		try { 
    		//배치번호
        	long batchNo = this.getBasejob().getExecjobinfo().getSeq();
        	//배치명
        	String batchName = this.getBasejob().getExecjobinfo().getName();
        	//배치로그 내용
        	StringBuilder sbLog = new StringBuilder();
        	sbLog.append("-> 로그테스트 ").append(FileLogUtil.FILELOG_NEWLINE);
        	sbLog.append("-> CheckStep.exec() ").append(FileLogUtil.FILELOG_NEWLINE);
        	sbLog.append("로그 파일 입니다. ").append(FileLogUtil.FILELOG_NEWLINE);
        	
    		//파일로그 작성매서드 호출
        	String logpath = FileLogUtil.saveFileLog(batchNo, batchName, sbLog);
        	this.getBasejob().getBatchjoblog().setLogpath(logpath);
        	Thread.sleep(5000);
    		
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
	}

}
