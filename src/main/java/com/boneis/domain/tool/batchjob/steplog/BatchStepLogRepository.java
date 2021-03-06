package com.boneis.domain.tool.batchjob.steplog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boneis.domain.base.root.Repository;
import com.boneis.domain.base.sequence.Sequence;

@Component("batchsteplogRepository")
public class BatchStepLogRepository extends Repository<BatchStepLog> {
	
	@Autowired
	private Repository<Sequence> sequenceRepository;
	
	public void setSequenceRepository(Repository<Sequence> sequenceRepository) {
		this.sequenceRepository = sequenceRepository;
	}

	@Override
	public void beforeAdd(BatchStepLog t) {
		Sequence sequence = new Sequence(this.sequenceRepository, "tr_batchsteplog");
		t.setSeq(sequence.getNext());
	}
}
