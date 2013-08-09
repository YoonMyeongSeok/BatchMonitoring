package com.boneis.contoller.batchjob;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.boneis.domain.base.paging.Paging;
import com.boneis.domain.base.root.Repository;
import com.boneis.domain.tool.batchjob.BatchJob;
import com.boneis.domain.tool.batchjob.joblog.BatchJobLog;
import com.boneis.domain.tool.batchjob.steplog.BatchStepLog;

@Controller
public class BatchStepLogController {
	
	@Autowired
	private Repository<BatchStepLog> batchsteplogRepository;
	
	@Autowired
	private Repository<BatchJob> batchjobRepository;
	
	@RequestMapping(value = {"/batchsteplog/board.b1"})
	public ModelAndView getBatchStepLog(HttpServletRequest request){		
				
		ModelAndView mav = new ModelAndView("batchjob/steplog/stepLog");
		
		BatchStepLog batchStepLog = new BatchStepLog(batchsteplogRepository, new Paging(request));
		BatchJob batchJob = new BatchJob(batchjobRepository);
		
		long totalrow = batchStepLog.getListCount();
		
		mav.addObject("list", batchStepLog.getList());
		mav.addObject("jobInfo", batchJob.getList());
		mav.addObject("cnt", totalrow);
		mav.addObject("paging", batchStepLog.getPaging().getList(totalrow, "StepLog"));
		
		return mav;
	}
	
	@RequestMapping(value = {"/batchsteplog/indivLog.b1"})
	public ModelAndView getBatchStepLogIndiviual(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("batchjob/steplog/stepLog");
		
		long seq = 0;
		if(request.getParameter("seq")!=null){
			seq = Long.parseLong(request.getParameter("seq"));
		}

		//로그페이지 상단 배치명(Header-Table)부분 사용 유무
		String headerYn = "Y";
		if(request.getParameter("headerYn")!=null){
			headerYn = request.getParameter("headerYn");
		}
		
		BatchStepLog batchStepLog = new BatchStepLog(batchsteplogRepository, new BatchJobLog(), new Paging(request));
		batchStepLog.getBatchjoblog().setSeq(seq);
		
		long totalrow = batchStepLog.getListCount();

		mav.addObject("list", batchStepLog.getList());
		mav.addObject("cnt", totalrow);
		mav.addObject("headerYn", headerYn);
		mav.addObject("paging", batchStepLog.getPaging().getList(totalrow, "StepLog"));
		
		return mav;
	}
}
