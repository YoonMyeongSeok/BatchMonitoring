package com.boneis.batchjob.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boneis.batchjob.base.constant.Batch;

public abstract class BaseJobStep {

	private static Logger logger = LoggerFactory.getLogger(BaseJobStep.class);
	private BaseJob basejob;
	
	private void before(long result, String msg) {
		logger.info("step start................................resultyn:"+result+", msg:"+msg);
		this.basejob.getBatchsteplog().setBatchjoblog(this.basejob.getBatchjoblog());
		this.basejob.getBatchsteplog().setResultyn(result);
		this.basejob.getBatchsteplog().setResultmsg(msg);
		this.basejob.getBatchsteplog().add();
	}
	private void after(long result, String msg) {
		this.basejob.getBatchsteplog().setResultyn(result);
		this.basejob.getBatchsteplog().setResultmsg(msg);
		this.basejob.getBatchsteplog().update();
		logger.info("step end..................................resultyn:"+result+", msg:"+msg);
	}
	protected void start(BaseJob basejob) {
		this.basejob = basejob;
		long result = Batch.RESULT_ING;
		String resultmsg = "스텝처리시작";
		
		before(result, resultmsg);
		try{
			exec();
			result = Batch.RESULT_SUCCESS;
			resultmsg = "배치스텝완료";
		}catch(Exception e){
			result = Batch.RESULT_FAIL;
			resultmsg = "배치스텝실패:"+e.toString();
		}finally{
			after(result, resultmsg);
		}
	}
	protected abstract void exec();
	
	public void put(String key, Object obj) {
		this.basejob.put(key, obj);
	}
	
	public Object get(String key) {
		return this.basejob.get(key);
	}
	
	// Get & Set .................................................
	public BaseJob getBasejob() {
		return basejob;
	}
	public void setBasejob(BaseJob basejob) {
		this.basejob = basejob;
	}
}
