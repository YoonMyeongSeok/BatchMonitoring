package com.boneis.batchjob.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boneis.batchjob.base.constant.Batch;
import com.boneis.support.util.FileLogUtil;

public class BaseJobStep {

	private static Logger logger = LoggerFactory.getLogger(BaseJobStep.class);
	private BaseJob basejob;
	
	/*
	 * 배치 실행하기 전에 실행코드와 메세지를 세팅해준다. - 배치스텝로그
	 */
	private void before(long result, String msg) {
		this.basejob.getBatchsteplog().setBatchjoblog(this.basejob.getBatchjoblog());
		this.basejob.getBatchsteplog().setResultyn(result);
		this.basejob.getBatchsteplog().setResultmsg(msg);
		this.basejob.getBatchsteplog().add();
	}
	
	/*
	 * 배치실행 도중 또는 배치종료 후에 결과코드와 메세지를 세팅해준다. - 배치스텝로그
	 */
	private void after(long result, String msg) {
		this.basejob.getBatchsteplog().setResultyn(result);
		this.basejob.getBatchsteplog().setResultmsg(msg);
		this.basejob.getBatchsteplog().update();
	}
	
	public void execStep(){
		makeLogFile();
		before(Batch.RESULT_ING, "스텝처리시작");		
		after(Batch.RESULT_SUCCESS, "배치스텝완료");		
	}
	
	public void put(String key, Object obj) {
		this.basejob.put(key, obj);
	}
	
	public Object get(String key) {
		return this.basejob.get(key);
	}
	
	public void makeLogFile(){
		
    	try { 
    		//배치번호
        	long batchNo = this.getBasejob().getExecjobinfo().getSeq();
        	//배치명
        	String batchName = this.getBasejob().getExecjobinfo().getName();
        	//배치로그 내용
        	StringBuilder sbLog = new StringBuilder();
        	sbLog.append("-> 로그테스트 ").append(FileLogUtil.FILELOG_NEWLINE);
        	sbLog.append("-> StepLog test ").append(FileLogUtil.FILELOG_NEWLINE);
        	sbLog.append("로그 파일 입니다. ").append(FileLogUtil.FILELOG_NEWLINE);
        	
    		//파일로그 작성매서드 호출
        	String logpath = FileLogUtil.saveFileLog(batchNo, batchName, sbLog);
        	this.getBasejob().getBatchsteplog().setLogpath(logpath);
    		
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
	}
	
	// Get & Set .................................................
	public BaseJob getBasejob() {
		return basejob;
	}
	public void setBasejob(BaseJob basejob) {
		this.basejob = basejob;
	}
}
