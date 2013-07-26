package com.boneis.domain.tool.batchjob.joblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boneis.domain.base.root.Repository;
import com.boneis.domain.base.sequence.Sequence;

@Component("batchjoblogRepository")
public class BatchJobLogRepository extends Repository<BatchJobLog> {
	
	@Autowired
	private Repository<Sequence> sequenceRepository;
	
	public void setSequenceRepository(Repository<Sequence> sequenceRepository) {
		this.sequenceRepository = sequenceRepository;
	}

	@Override
	public void beforeAdd(BatchJobLog t) {
		Sequence sequence = new Sequence(this.sequenceRepository, "tr_batchjoblog");
		t.setSeq(sequence.getNext());
	}
}
