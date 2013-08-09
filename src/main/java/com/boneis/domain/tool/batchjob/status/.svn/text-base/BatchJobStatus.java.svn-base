package com.boneis.domain.tool.batchjob.status;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.boneis.domain.base.root.Entity;
import com.boneis.domain.base.root.Repository;

public class BatchJobStatus extends Entity<BatchJobStatus> {

	private final String NAMESPACE = "batchjobstatus";
	private String serverip;
	private long execyn;
	
	// Constructer ........................................
	public BatchJobStatus(){}
	public BatchJobStatus(Repository<BatchJobStatus> repository){
		this.setRepository(repository);
	}
	
	// Abstractor Behavior ................................
	@Override
	@JsonIgnore
	public BatchJobStatus getInfo() {
		return this.getRepository().getInfo(this);
	}
	@Override
	@JsonIgnore
	public List<BatchJobStatus> getList() {
		return this.getRepository().getList(this);
	}
	@Override
	@JsonIgnore
	public List<BatchJobStatus> getList(String findmode) {
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
	
	// Get & Set ...........................................
	public String getServerip() {
		return serverip;
	}
	public void setServerip(String serverip) {
		this.serverip = serverip;
	}
	public long getExecyn() {
		return execyn;
	}
	public void setExecyn(long execyn) {
		this.execyn = execyn;
	}
	public String getNAMESPACE() {
		return NAMESPACE;
	}
	
}
