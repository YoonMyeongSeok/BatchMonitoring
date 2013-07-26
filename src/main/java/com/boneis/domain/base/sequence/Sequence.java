package com.boneis.domain.base.sequence;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.boneis.domain.base.root.Entity;
import com.boneis.domain.base.root.Repository;

public class Sequence extends Entity<Sequence> {
	
	private final String NAMESPACE = "sequence";
	private String id;
	private long max;
	
	// Constructor
	public Sequence(){}
	public Sequence(Repository<Sequence> repository, String id){
		this.setRepository(repository);
		this.id = id;
	}
	
	// Abstract Behavior
	@Override
	@JsonIgnore
	public Sequence getInfo(){
		return this.getRepository().getInfo(this);
	}
	@Override
	@JsonIgnore
	public List<Sequence> getList() {
		return null;
	}
	@Override
	@JsonIgnore
	public List<Sequence> getList(String findmode) {
		return null;
	}
	@Override
	@JsonIgnore
	public long getListCount() {
		return 0;
	}
	@Override
	@JsonIgnore
	public long getListCount(String findmode) {
		return 0;
	}
	@Override
	public void add(){
		this.getRepository().add(this);
	}
	@Override
	public void update(){
		this.getRepository().update(this);
	}
	@Override
	public void remove(){
		this.getRepository().remove(this);
	}
	
	// Behavior
	public void update(Sequence sequence){
		this.getRepository().update(sequence);
	}
	public long getNext(){
		Sequence sequence = getInfo();
		if(sequence!=null){
			this.max = sequence.getMax()+1;
			this.update();
		}else{
			this.max = 1;
			this.add();
		}
		return this.max;
	}
	
	// Get & Set
	public String getNAMESPACE() {
		return NAMESPACE;
	}
	public String getId() {
		return id;
	}public void setId(String id) {
		this.id = id;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	
}
