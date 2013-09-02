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
	
	/**
	 * 배치 잡 로그의 스텝로그를 볼 수 있는 페이지 관련 메서드
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/batchsteplog/board.b1"})
	public ModelAndView getBatchStepLog(HttpServletRequest request){		
				
		ModelAndView mav = new ModelAndView("batchjob/steplog/stepLog");
		
		//BatchStepLog 객체 생성 - 페이징 처리 추가
		BatchStepLog batchStepLog = new BatchStepLog(batchsteplogRepository, new Paging(request));
		BatchJob batchJob = new BatchJob(batchjobRepository);
		
		//BatchStepLog의 총 카운트
		long totalrow = batchStepLog.getListCount();
		
		/*
		 * list : 배치 잡 로그의 리스트
		 * jobInfo : 잡의 정보
		 * cnt : 잡의 총 개수
		 * paging : 잡의 페이징 처리
		 */
		mav.addObject("list", batchStepLog.getList());
		mav.addObject("jobInfo", batchJob.getList());
		mav.addObject("cnt", totalrow);
		mav.addObject("paging", batchStepLog.getPaging().getList(totalrow, "StepLog"));
		
		return mav;
	}
	
	/**
	 * 특정 배치의 스텝 로그 페이지를 보는 메서드
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/batchsteplog/indivLog.b1"})
	public ModelAndView getBatchStepLogIndiviual(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("batchjob/steplog/stepLog");
		
		//seq 초기화 및 Parameter로 넘어온 seq의 정보를 세팅
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
		
		//배치스텝의 총 카운트
		long totalrow = batchStepLog.getListCount();

		/*
		 * list : 배치 잡 로그의 리스트
		 * cnt : 잡의 총 개수
		 * headerYn : 헤더사용의 유무
		 * paging : 잡의 페이징 처리
		 */
		mav.addObject("list", batchStepLog.getList());
		mav.addObject("cnt", totalrow);
		mav.addObject("headerYn", headerYn);
		mav.addObject("paging", batchStepLog.getPaging().getList(totalrow, "StepLog"));
		
		return mav;
	}
}
