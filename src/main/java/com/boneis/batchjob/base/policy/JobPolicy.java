package com.boneis.batchjob.base.policy;

public interface JobPolicy<T> {
	public boolean isNext(T t);
}
