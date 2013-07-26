package com.boneis.domain.tool.batchjob.joblog;

import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.boneis.domain.base.paging.Paging;
import com.boneis.domain.base.root.Entity;
import com.boneis.domain.base.root.Repository;
import com.boneis.domain.tool.batchjob.BatchJob;

public class BatchJobLog extends Entity<BatchJobLog> {

	private final String NAMESPACE = "batchjoblog";
	private long seq;
	private BatchJob batchjob;
	private Timestamp starttime;
	private Timestamp endtime;
	private long processtime;
	private long resultyn;
	private String resultmsg;
	private String logpath;
	
	// Constructor start...........................................
	public BatchJobLog(){}
	public BatchJobLog(Repository<BatchJobLog> repository){
		this.setRepository(repository);
	}
	public BatchJobLog(Repository<BatchJobLog> repository, Paging paging){
		this.setRepository(repository);
		this.setPaging(paging);
	}
	public BatchJobLog(Repository<BatchJobLog> repository, BatchJob batchjob, Paging paging){
		this.setRepository(repository);
		this.batchjob = batchjob;
		this.setPaging(paging);
	}
	// Constructor end.............................................
	
	// Abstractor Behavior start...................................
	@Override
	@JsonIgnore
	public BatchJobLog getInfo() {
		return this.getRepository().getInfo(this);
	}
	@Override
	@JsonIgnore
	public List<BatchJobLog> getList() {
		return this.getRepository().getList(this);
	}
	@Override
	@JsonIgnore
	public List<BatchJobLog> getList(String findmode) {
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
	// Abstractor Behavior end...................................
	
	// Behavior .....................................
	public void update(String findmode) {
		this.setFindmode(findmode);
		this.getRepository().update(this);
	}
	
	// Get & Set ......................
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public BatchJob getBatchjob() {
		return batchjob;
	}
	public void setBatchjob(BatchJob batchjob) {
		this.batchjob = batchjob;
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
