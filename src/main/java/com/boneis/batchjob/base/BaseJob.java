package com.boneis.batchjob.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.boneis.batchjob.base.constant.Batch;
import com.boneis.batchjob.base.policy.JobBeforePolicyImpl;
import com.boneis.batchjob.base.policy.JobPolicy;
import com.boneis.domain.base.root.Repository;
import com.boneis.domain.tool.batchjob.BatchJob;
import com.boneis.domain.tool.batchjob.joblog.BatchJobLog;
import com.boneis.domain.tool.batchjob.steplog.BatchStepLog;
import com.boneis.support.util.Util;

public abstract class BaseJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(BaseJob.class);
	private List<BaseJobStep> batchstepList = new ArrayList<BaseJobStep>();
	private List<String> contextList = new ArrayList<String>();
	private long batchno;
	//....................................................
	private JobPolicy<BaseJob> beforepolicy;
	private ApplicationContext basecontext;
	private Repository<BatchJob> batchjobRepository;
	private BatchJob batchjob;
	private Repository<BatchJobLog> batchjoblogRepository;
	private BatchJobLog batchjoblog;
	private Repository<BatchStepLog> batchsteplogRepository;
	private BatchStepLog batchsteplog;
	private BatchJob execjobinfo;
	private HashMap<String,Object> params = new HashMap<String,Object>();
	private JobExecutionContext context;
	
	// Constructor start..............................................
	public BaseJob() {
		super();
		this.contextList.add("com/boneis/batchjob/base/config/base-context.xml");
	}
	// Constructor end..............................................

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		String ip = Util.getServerIp();
		baseinit();
		init();
		
		if(ip.equals(this.execjobinfo.getServerip())){
			if(this.execjobinfo.getExecplace()==Batch.INPROCESS){
				// 내부 프로세스로 배치처리....
				innerexec();
			}else if(this.execjobinfo.getExecplace()==Batch.OUTPROCESS){
				// 외부 프로세스로 배치처리....
				try {
					String[] command = new String[4];
					command[0] = "cmd";
					command[1] = "/c";
					command[2] = Batch.JAVA_BATCH_JOB_SHELL_LINUX;
					if(Batch.WINDOWS.equals(this.execjobinfo.getServeros())){
						command[2] = Batch.JAVA_BATCH_JOB_SHELL_WINDOWS;
					}
					command[3] = this.batchno+"";
					Runtime.getRuntime().exec(command);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void baseinit() {
		this.basecontext = new GenericXmlApplicationContext(this.contextList.toArray(new String[0]));
		this.batchjobRepository = (Repository<BatchJob>)this.basecontext.getBean("batchjobRepository");
		this.batchjob = new BatchJob(this.batchjobRepository);
		this.batchjob.setSeq(this.batchno);
		this.execjobinfo = this.batchjob.getInfo();
		this.batchjoblogRepository = (Repository<BatchJobLog>)this.basecontext.getBean("batchjoblogRepository");
		this.batchjoblog = new BatchJobLog(this.batchjoblogRepository);
		this.batchsteplogRepository = (Repository<BatchStepLog>)this.basecontext.getBean("batchsteplogRepository");
		this.batchsteplog = new BatchStepLog(this.batchsteplogRepository);
		this.beforepolicy = new JobBeforePolicyImpl();
	}
	
	private void before(long result, String msg) {
		if(this.beforepolicy.isNext(this)){
			logger.info("Batch Started.......................resultyn:"+result+", msg:"+msg);
			this.batchjob.setSeq(this.batchno);
			this.execjobinfo = this.batchjob.getInfo();
			this.batchjob.update("start");
			this.batchjoblog.setBatchjob(this.batchjob);
			this.batchjoblog.setResultyn(result);
			this.batchjoblog.setResultmsg(msg);
			this.batchjoblog.add();
		}else{
			logger.info("Batch Start beforepolicy error!!.................");
		}
	}
	
	private void after(long result, String msg) {
		logger.info("Batch Ended............................resultyn:"+result+", msg:"+msg);
		this.batchjob.setLastresultyn(result);
		this.batchjob.update("end");
		this.batchjoblog.setResultyn(result);
		this.batchjoblog.setResultmsg(msg);
		this.batchjoblog.update();
	}
	
	private void start() {
		for(int i=0;i<batchstepList.size();i++) {
			BaseJobStep batchstep = batchstepList.get(i);
			this.batchjob.setSeq(this.batchno);
			this.execjobinfo = this.batchjob.getInfo();
			if(!execjobinfo.getStopyn().equals("Y")){
				batchstep.start(this);
			}else{
				break;
			}
		}
	}
	
	public void innerexec() {
		long result = Batch.RESULT_ING;
		String resultmsg = "배치처리시작";
		
		before(result, resultmsg);
		try{
			start();
			if(!this.execjobinfo.getStopyn().equals("Y")){
				result = Batch.RESULT_SUCCESS;
				resultmsg = "배치처리완료";
			}else{
				result = Batch.RESULT_STOP;
				resultmsg = "배치실행중지";
			}
		}catch(Exception e){
			result = Batch.RESULT_FAIL;
			resultmsg = "배치처리실패:"+e.toString();
		}finally{
			after(result, resultmsg);
		}
	}
	public void exec() {
		baseinit();
		init();
		innerexec();
		
/*		try{
			executeInternal(context);
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}
	protected void init(){};
	
	public void put(String key, Object obj) {
		this.params.put(key, obj);
	}
	
	public Object get(String key) {
		return this.params.get(key);
	}
	
	// Get & Set start................................
	public long getBatchno() {
		return batchno;
	}
	public void setBatchno(long batchno) {
		this.batchno = batchno;
	}
	public List<BaseJobStep> getBatchstepList() {
		return batchstepList;
	}
	public void setBatchstepList(List<BaseJobStep> batchstepList) {
		this.batchstepList = batchstepList;
	}
	public JobPolicy<BaseJob> getBeforepolicy() {
		return beforepolicy;
	}
	public void setBeforepolicy(JobPolicy<BaseJob> beforepolicy) {
		this.beforepolicy = beforepolicy;
	}
	public ApplicationContext getBasecontext() {
		return basecontext;
	}
	public void setBasecontext(ApplicationContext basecontext) {
		this.basecontext = basecontext;
	}
	public Repository<BatchJob> getBatchjobRepository() {
		return batchjobRepository;
	}
	public void setBatchjobRepository(Repository<BatchJob> batchjobRepository) {
		this.batchjobRepository = batchjobRepository;
	}
	public BatchJob getBatchjob() {
		return batchjob;
	}
	public void setBatchjob(BatchJob batchjob) {
		this.batchjob = batchjob;
	}
	public Repository<BatchJobLog> getBatchjoblogRepository() {
		return batchjoblogRepository;
	}
	public void setBatchjoblogRepository(
			Repository<BatchJobLog> batchjoblogRepository) {
		this.batchjoblogRepository = batchjoblogRepository;
	}
	public BatchJobLog getBatchjoblog() {
		return batchjoblog;
	}
	public void setBatchjoblog(BatchJobLog batchjoblog) {
		this.batchjoblog = batchjoblog;
	}
	public Repository<BatchStepLog> getBatchsteplogRepository() {
		return batchsteplogRepository;
	}
	public void setBatchsteplogRepository(
			Repository<BatchStepLog> batchsteplogRepository) {
		this.batchsteplogRepository = batchsteplogRepository;
	}
	public BatchStepLog getBatchsteplog() {
		return batchsteplog;
	}
	public void setBatchsteplog(BatchStepLog batchsteplog) {
		this.batchsteplog = batchsteplog;
	}
	public List<String> getContextList() {
		return contextList;
	}
	public void setContextList(List<String> contextList) {
		this.contextList = contextList;
	}
	public BatchJob getExecjobinfo() {
		return execjobinfo;
	}
	public void setExecjobinfo(BatchJob execjobinfo) {
		this.execjobinfo = execjobinfo;
	}
	// Get & Set end................................
	
}