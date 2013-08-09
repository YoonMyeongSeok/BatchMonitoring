package com.boneis.domain.base.root;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public abstract class Entity<T> extends Domain {

	private Repository<T> repository;
	
	// Abstract Behavior
	@JsonIgnore
	public abstract T getInfo();
	@JsonIgnore
	public abstract List<T> getList();
	@JsonIgnore
	public abstract List<T> getList(String findmode);
	@JsonIgnore
	public abstract long getListCount();
	@JsonIgnore
	public abstract long getListCount(String findmode);
	public abstract void add();
	public abstract void update();
	public abstract void remove();
	
	// Get & Set
	@JsonIgnore
	public Repository<T> getRepository() {
		return repository;
	}
	public void setRepository(Repository<T> repository) {
		this.repository = repository;
	}
	
}
