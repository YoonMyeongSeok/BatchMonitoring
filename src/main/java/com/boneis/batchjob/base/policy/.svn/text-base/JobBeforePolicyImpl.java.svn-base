package com.boneis.batchjob.base.policy;

import com.boneis.batchjob.base.BaseJob;

public class JobBeforePolicyImpl implements JobPolicy<BaseJob> {

	@Override
	public boolean isNext(BaseJob t) {
		boolean result = false;
		if(t.getBatchno()>0){
			result = true;
		}
		return result;
	}

}
