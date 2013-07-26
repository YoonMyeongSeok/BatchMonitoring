package com.boneis.batchjob.testjob.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boneis.batchjob.base.BaseJobStep;
import com.boneis.support.config.Config;
import com.boneis.support.util.FileLogUtil;

public class TestJobStep1 extends BaseJobStep {

	private static Logger logger = LoggerFactory.getLogger(TestJobStep1.class);

	@Override
	protected void exec() {
		this.put("paramtest", "in step1.......!!!!!");
		logger.info("TestJobStep1.exec()");
		logger.info("batch.log.path: "+Config.get("batch.log.path"));
    	
		try { 
    		//배치번호
        	long batchNo = this.getBasejob().getExecjobinfo().getSeq();
        	//배치명
        	String batchName = this.getBasejob().getExecjobinfo().getName();
        	//배치로그 내용
        	StringBuilder sbLog = new StringBuilder();
        	sbLog.append("-> 로그테스트 ").append(FileLogUtil.FILELOG_NEWLINE);
        	sbLog.append("-> log test ").append(FileLogUtil.FILELOG_NEWLINE);
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
