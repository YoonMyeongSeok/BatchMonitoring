package com.boneis.batchjob.base;

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
import com.boneis.support.util.FileLogUtil;

public class BaseJob extends QuartzJobBean {

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
	
	// Constructor start..............................................
	public BaseJob() {
		super();
		this.contextList.add("com/boneis/batchjob/base/config/base-context.xml");
	}
	// Constructor end..............................................
	
	/*
	 *초기화작업
	 *생성자 Parameter로 XML 파일의 class path를 지정해준다.
	 *배치잡과 잡로그, 스텝로그 객체를 생성한다.
	 */
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
	
	/*
	 * 배치를 실행하기 전에 실행코드와 메세지를 세팅해준다.
	 */
	private void before(long result, String msg) {		
		if(this.beforepolicy.isNext(this)){			
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
	
	/*
	 * 배치실행 도중 또는 배치종료 후에 결과코드와 메세지를 세팅해준다.
	 */
	private void after(long result, String msg) {
		makeLogFile();
		this.batchjob.setLastresultyn(result);
		this.batchjoblog.setResultyn(result);
		this.batchjoblog.setResultmsg(msg);
		this.batchjoblog.update();
	}

	public void jobStart(long result, String msg) {
		baseinit();
		before(result, msg);
	}
	
	public void jobProcess(){
		BaseJobStep baseJobStep = new BaseJobStep();
		baseJobStep.setBasejob(this);
		baseJobStep.execStep();
	}
	
	public void jobEnd(long result, String msg){
		after(result, msg);
	}

	public void put(String key, Object obj) {
		this.params.put(key, obj);
	}
	
	public Object get(String key) {
		return this.params.get(key);
	}
	
	/*
	 * 배치의 PID를 DB에 업데이트
	 */
	public void updatePid(String pid){		
		BatchJob batchjob = new BatchJob(batchjobRepository);
		batchjob.setSeq(this.batchno);
		batchjob.setPid(pid);
		batchjob.update("pid");
	}
	
	/*
	 * 배치의 실행 종료 후 결과 값을 DB에 업데이트.
	 */
	public void updateResult(){		
		BatchJob batchjob = new BatchJob(batchjobRepository);
		batchjob.setSeq(this.batchno);
		batchjob.setLastresultyn(4);
		batchjob.update("resultyn");
	}
	
	/*
	 * 배치의 실행 종료 후 결과 값을 DB에 업데이트(Parameter - 포함).
	 */
	public void updateResult(long resultCode){		
		BatchJob batchjob = new BatchJob(batchjobRepository);
		batchjob.setSeq(this.batchno);
		batchjob.setLastresultyn(resultCode);
		batchjob.update("resultyn");
	}
	
	/*
	 * DB에 저장되어 있는 배치의 PID번호를 가져온다.
	 */
	public String getPid(){
		String pid;
		BatchJob batchjob = new BatchJob(batchjobRepository);
		batchjob.setSeq(this.batchno);
		pid = batchjob.getInfo().getPid();
		
		return pid;
	}
	
	/*
	 * 서버 및 배치의 Memory & CPU DB에 업데이트
	 */
	public void updateServerInfo(String args){
		String[] serverInfo = args.split("@##");
		BatchJob batchjob = new BatchJob(batchjobRepository);
		batchjob.setSeq(this.batchno);
		batchjob.setServerUseMem(serverInfo[0].substring(5));
		batchjob.setServerUseCpu(serverInfo[1].substring(5));
		batchjob.setBatchUseMem(serverInfo[2].substring(4));
		batchjob.setBatchUseCpu(serverInfo[3].substring(4));
		batchjob.update("serverInfo");
		
	}
	
	/*
	 * 강제종료 arg 확인 후 리턴
	 */
	public String getStopYn(){
		BatchJob batchjob = new BatchJob(batchjobRepository);
		batchjob.setSeq(this.batchno);

		return batchjob.getInfo().getStopyn(); 
	}
	
	public void makeLogFile(){
		
    	try { 
    		//배치번호
        	long batchNo = this.getExecjobinfo().getSeq();
        	//배치명
        	String batchName = this.getExecjobinfo().getName();
        	//배치로그 내용
        	StringBuilder sbLog = new StringBuilder();
        	sbLog.append("-> 로그테스트 ").append(FileLogUtil.FILELOG_NEWLINE);
        	sbLog.append("-> jobLog test ").append(FileLogUtil.FILELOG_NEWLINE);
        	sbLog.append("로그 파일 입니다. ").append(FileLogUtil.FILELOG_NEWLINE);
        	
    		//파일로그 작성매서드 호출
        	String logpath = FileLogUtil.saveFileLog(batchNo, batchName, sbLog);
        	this.getBatchjoblog().setLogpath(logpath);
    		
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
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

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}	
}