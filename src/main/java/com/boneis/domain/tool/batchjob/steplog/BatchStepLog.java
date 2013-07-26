package com.boneis.domain.tool.batchjob.steplog;

import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.boneis.domain.base.paging.Paging;
import com.boneis.domain.base.root.Entity;
import com.boneis.domain.base.root.Repository;
import com.boneis.domain.tool.batchjob.joblog.BatchJobLog;

public class BatchStepLog extends Entity<BatchStepLog> {

	private final String NAMESPACE = "batchsteplog";
	private long seq;
	private BatchJobLog batchjoblog;
	private Timestamp starttime;
	private Timestamp endtime;
	private long processtime;
	private long resultyn;
	private String resultmsg;
	private String logpath;
	
	// Constructor Behavior start.....................................
	public BatchStepLog(){}
	public BatchStepLog(Repository<BatchStepLog> repository){
		this.setRepository(repository);
	}
	public BatchStepLog(Repository<BatchStepLog> repository, Paging paging){
		this.setRepository(repository);
		this.setPaging(paging);
	}
	public BatchStepLog(Repository<BatchStepLog> repository, BatchJobLog batchjoblog, Paging paging){
		this.setRepository(repository);
		this.batchjoblog = batchjoblog;
		this.setPaging(paging);
	}
	// Constructor Behavior end.......................................
	
	// Abstractor Behavior start.....................................
	@Override
	@JsonIgnore
	public BatchStepLog getInfo() {
		return this.getRepository().getInfo(this);
	}
	@Override
	@JsonIgnore
	public List<BatchStepLog> getList() {
		return this.getRepository().getList(this);
	}
	@Override
	@JsonIgnore
	public List<BatchStepLog> getList(String findmode) {
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
	// Abstractor Behavior end.......................................
	
	// Behavior .....................................
	public void update(String findmode) {
		this.setFindmode(findmode);
		this.getRepository().update(this);
	}
	
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public BatchJobLog getBatchjoblog() {
		return batchjoblog;
	}
	public void setBatchjoblog(BatchJobLog batchjoblog) {
		this.batchjoblog = batchjoblog;
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
	public long getResultyn() {
		return resultyn;
	}
	public void setResultyn(long resultyn) {
		this.resultyn = resultyn;
	}
	public String getResultmsg() {
		return resultmsg;
	}
	public void setResultmsg(String resultmsg) {
		this.resultmsg = resultmsg;
	}
	public String getNAMESPACE() {
		return NAMESPACE;
	}
	public String getLogpath() {
		return logpath;
	}
	public void setLogpath(String logpath) {
		this.logpath = logpath;
	}
	
}
