package com.boneis.domain.base.root;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class Repository<T> {
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	// Behavior ////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	public T getInfo(T t){
		Domain d = (Domain) t;
		return (T) sqlMapClientTemplate.queryForObject(d.getNAMESPACE()+".info", t);
	}
	@SuppressWarnings("unchecked")
	public List<T> getList(T t){
		Domain d = (Domain) t;
		String id = d.getNAMESPACE()+".list";
		if(d.getFindmode()!=null&&!"".equals(d.getFindmode())){
			id = d.getNAMESPACE()+"."+d.getFindmode();
		}
		return sqlMapClientTemplate.queryForList(id, t);
	}
	public long getListCount(T t){
		Domain d = (Domain) t;
		String id = d.getNAMESPACE()+".list.count";
		if(d.getFindmode()!=null&&!"".equals(d.getFindmode())){
			id = d.getNAMESPACE()+"."+d.getFindmode()+".count";
		}
		return (Long) sqlMapClientTemplate.queryForObject(id, t);
	}
	public void beforeAdd(T t){}
	public void afterAdd(T t){}
	public void add(T t){
		Domain d = (Domain) t;
		beforeAdd(t);
		sqlMapClientTemplate.insert(d.getNAMESPACE()+".insert", t);
		afterAdd(t);
	}
	public void beforeUpdate(T t){}
	public void afterUpdate(T t){}
	public void update(T t){
		Domain d = (Domain) t;
		String f = "";
		if(d.getFindmode()!=null&&!"".equals(d.getFindmode())){
			f = "."+d.getFindmode();
		}
		beforeUpdate(t);
		sqlMapClientTemplate.update(d.getNAMESPACE()+".update"+f, t);
		afterUpdate(t);
	}
	public void beforeRemove(T t){}
	public void afterRemove(T t){}
	public void remove(T t){
		Domain d = (Domain) t;
		beforeRemove(t);
		sqlMapClientTemplate.delete(d.getNAMESPACE()+".delete", t);
		afterRemove(t);
	}
	
}
