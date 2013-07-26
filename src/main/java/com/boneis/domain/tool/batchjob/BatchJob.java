package com.boneis.domain.tool.batchjob;

import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.boneis.domain.base.paging.Paging;
import com.boneis.domain.base.root.Entity;
import com.boneis.domain.base.root.Repository;

public class BatchJob extends Entity<BatchJob> {
	
	private final String NAMESPACE = "batchjob";
	private long seq;
	private String name;
	private String clazz;
	private String cronexpression;
	private String serverip;
	private String serveros;
	private long execplace;
	private long useyn;
	private long execyn;
	private long lastresultyn;
	private Timestamp starttime;
	private Timestamp endtime;
	private long processtime;
	private String smslist; // ,로 구분
	private String emaillist; // ,로 구분
	private String stopyn; //Y:중지, N:실행가능
	
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
	@Override
	@JsonIgnore
	public BatchJob getInfo() {
		return this.getRepository().getInfo(this);
	}
	@Override
	@JsonIgnore
	public List<BatchJob> getList() {
		return this.getRepository().getList(this);
	}
	@Override
	@JsonIgnore
	public List<BatchJob> getList(String findmode) {
		this.setFindmode(findmode);
		return this.getRepository().getList(this);
	}
	@Override
	@JsonIgnore
	public long getListCount() {
		return this.getRepository().getListCount(this);
	}
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
	@Override
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
	public long getExecplace() {
		return execplace;
	}
	public void setExecplace(long execplace) {
		this.execplace = execplace;
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
	public String getNAMESPACE() {
		return NAMESPACE;
	}
	
}