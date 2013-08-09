package com.boneis.domain.tool.batchjob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boneis.domain.base.root.Repository;
import com.boneis.domain.base.sequence.Sequence;

@Component("batchjobRepository")
public class BatchJobRepository extends Repository<BatchJob> {
	
	@Autowired
	private Repository<Sequence> sequenceRepository;
	
	public void setSequenceRepository(Repository<Sequence> sequenceRepository) {
		this.sequenceRepository = sequenceRepository;
	}

	@Override
	public void beforeAdd(BatchJob t) {
		Sequence sequence = new Sequence(this.sequenceRepository, "ma_batchjob");
		t.setSeq(sequence.getNext());
	}
}
