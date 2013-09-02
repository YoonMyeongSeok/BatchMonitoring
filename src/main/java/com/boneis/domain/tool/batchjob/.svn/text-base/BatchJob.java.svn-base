package com.boneis.domain.tool.batchjob;

import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.boneis.domain.base.paging.Paging;
import com.boneis.domain.base.root.Entity;
import com.boneis.domain.base.root.Repository;

public class BatchJob extends Entity<BatchJob> {
	
	private final String NAMESPACE = "batchjob";
	private long seq;				// 시퀀스 번호
	private String name;			// 배치 이름
	private String clazz;			// 배치 클래스
	private String cronexpression;	// 스케쥴러 실행시간
	private String serverip;		// 배치서버 IP
	private String serveros;		// 배치서버 OS
	private long useyn;				// 사용 여부
	private long execyn;			// 실행 여부
	private long lastresultyn;		// 종료 여부
	private Timestamp starttime;	// 시작 시간
	private Timestamp endtime;		// 종료 시간
	private long processtime;		// 구동 시간
	private String smslist; 		// ,로 구분
	private String emaillist; 		// ,로 구분
	private String stopyn; 			// Y:중지, N:실행가능
	private String pid;				// 배치서버에서 받아온 PID
	private String ServerUseMem;	// 배치서버에서 사용 중인 전체 메모리
	private String ServerUseCpu;	// 배치서버에서 사용 중인 전체 CPU
	private String batchUseMem;		// 배치서버에서 사용 중인 전체 메모리 중 해당 배치가 사용 중인 메모리
	private String batchUseCpu;		// 배치서버에서 사용 중인 전체 CPU 중 해당 배치가 사용 중인 CPU
	
	// Constructor startd.............................
	public BatchJob(){}
	public BatchJob(Repository<BatchJob> repository){
		this.setRepository(repository);
	}
	public BatchJob(Repository<BatchJob> repository, Paging paging){
		this.setRepository(repository);
		this.setPaging(paging);
	}
	// Constructor end................................
	
	// Abstract Behavior ///////////////////////////
	/**
	 * 배치 정보 GET
	 */
	@Override
	@JsonIgnore
	public BatchJob getInfo() {
		return this.getRepository().getInfo(this);
	}
	
	/**
	 * 배치 리스트 GET
	 */
	@Override
	@JsonIgnore
	public List<BatchJob> getList() {
		return this.getRepository().getList(this);
	}
	
	/**
	 * 검색 시 배치 리스트 GET
	 */
	@Override
	@JsonIgnore
	public List<BatchJob> getList(String findmode) {
		this.setFindmode(findmode);
		return this.getRepository().getList(this);
	}
	/**
	 * 배치의 총 개수 GET
	 */
	@Override
	@JsonIgnore
	public long getListCount() {
		return this.getRepository().getListCount(this);
	}
	
	/**
	 * 검색 시 배치의 총 개수 GET
	 */
	@Override
	@JsonIgnore
	public long getListCount(String findmode) {
		this.setFindmode(findmode);
		return this.getRepository().getListCount(this);
	}
	
	@Override
	public void add() {
		this.getRepository().add(this);
	}

	@Override
	public void update() {
		this.getRepository().update(this);
	}

	public void remove() {
		this.getRepository().remove(this);
	}
	
	// Behavior .....................................
	public void update(String findmode) {
		this.setFindmode(findmode);
		this.getRepository().update(this);
	}
	
	// Get & Set ///////////////////////////////////
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getCronexpression() {
		return cronexpression;
	}
	public void setCronexpression(String cronexpression) {
		this.cronexpression = cronexpression;
	}
	public String getServerip() {
		return serverip;
	}
	public void setServerip(String serverip) {
		this.serverip = serverip;
	}
	public String getServeros() {
		return serveros;
	}
	public void setServeros(String serveros) {
		this.serveros = serveros;
	}
	public long getUseyn() {
		return useyn;
	}
	public void setUseyn(long useyn) {
		this.useyn = useyn;
	}
	public long getExecyn() {
		return execyn;
	}
	public void setExecyn(long execyn) {
		this.execyn = execyn;
	}
	public long getLastresultyn() {
		return lastresultyn;
	}
	public void setLastresultyn(long lastresultyn) {
		this.lastresultyn = lastresultyn;
	}
	public Timestamp getStarttime() {
		return starttime;
	}
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public long getProcesstime() {
		return processtime;
	}
	public void setProcesstime(long processtime) {
		this.processtime = processtime;
	}
	public String getSmslist() {
		return smslist;
	}
	public void setSmslist(String smslist) {
		this.smslist = smslist;
	}
	public String getEmaillist() {
		return emaillist;
	}
	public void setEmaillist(String emaillist) {
		this.emaillist = emaillist;
	}
	public String getStopyn(){
		return stopyn;
	}
	public void setStopyn(String stopyn){
		this.stopyn = stopyn;
	}
	public String getPid(){
		return pid;
	}
	public void setPid(String pid){
		this.pid = pid;
	}
	public String getNAMESPACE() {
		return NAMESPACE;
	}
	public String getServerUseMem() {
		return ServerUseMem;
	}
	public void setServerUseMem(String serverUseMem) {
		ServerUseMem = serverUseMem;
	}
	public String getServerUseCpu() {
		return ServerUseCpu;
	}
	public void setServerUseCpu(String serverUseCpu) {
		ServerUseCpu = serverUseCpu;
	}
	public String getBatchUseMem() {
		return batchUseMem;
	}
	public void setBatchUseMem(String batchUseMem) {
		this.batchUseMem = batchUseMem;
	}
	public String getBatchUseCpu() {
		return batchUseCpu;
	}
	public void setBatchUseCpu(String batchUseCpu) {
		this.batchUseCpu = batchUseCpu;
	}
	
}
