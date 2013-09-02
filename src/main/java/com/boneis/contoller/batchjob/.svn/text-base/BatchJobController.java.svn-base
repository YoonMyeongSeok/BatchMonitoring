package com.boneis.contoller.batchjob;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.boneis.batchjob.base.constant.Batch;
import com.boneis.domain.base.paging.Paging;
import com.boneis.domain.base.root.Repository;
import com.boneis.domain.tool.batchjob.BatchJob;
import com.boneis.domain.tool.batchjob.status.BatchJobStatus;
import com.boneis.support.batchlauncher.BatchJobLauncher;
import com.boneis.support.connection.ServerConnection;
import com.boneis.support.util.Util;

@Controller
public class BatchJobController {

	@Autowired
	private Repository<BatchJob> batchjobRepository;
	
	@Autowired
	private Repository<BatchJobStatus> batchjobstatusRepository;
	
	/**
	 * 배치정보 리스트를 가져오는 메서드
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/state/batchjobList.b1"})
	public ModelAndView batchjobList(HttpServletRequest request)throws Exception {
		
		ModelAndView mav = new ModelAndView("/batchjob/state/BatchJobList");

		//BatchJob 객체 생성 - 페이징 처리 포함
		BatchJob batchjob = new BatchJob(batchjobRepository, new Paging(request));
		
		//배치의 총 개수
		long totalrow = batchjob.getListCount();
		
		//찾을 배치 이름 설정 - 배치검색에 사용됨
		batchjob.setName(request.getParameter("search_name"));
		
		/*
		 * list - BatchJob의 리스트
		 * search_name - 찾을 배치명
		 * paging - 페이징 처리
		 */
		mav.addObject("list", batchjob.getList());
		mav.addObject("search_name", request.getParameter("search_name"));
		mav.addObject("paging", batchjob.getPaging().getList(totalrow, "batchjob"));
		
		return mav;
	}
	
	/**
	 * 배치정보수정 및 등록 페이지 호출하는 메서드
	 * @param batchjob
	 * @throws Exception
	 */
	@RequestMapping(value = {"/state/getBatchJobInfo.b1"})
	public ModelAndView getBatchJobInfo(HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView("/batchjob/state/batchJobInfoPop");
		
		BatchJob batchjob = new BatchJob(batchjobRepository);
		if(request.getParameter("seq") != null && !request.getParameter("seq").equals("")){
			batchjob.setSeq(Util.parseLong(request.getParameter("seq")));
			mav.addObject("list", batchjob.getInfo() );
		}else{
			mav.addObject("list",null);
		}	
		return mav;
	}
	
	/**
	 * 기존배치 수정 및 생성 관련 메서드
	 * @param batchjob
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = {"/state/updateBatchJob.b1"})
	public void updateBatchJob(BatchJob batchjob, HttpServletRequest request) throws Exception  {
		
		//BatchJob Repository 생성
		batchjob.setRepository(batchjobRepository);
		
		//기존배치의 Seq가 존재한다면 해당 Seq번호로 BatchJob을 설정해준다.
		if(request.getParameter("setseq") != null && !request.getParameter("setseq").equals("")){
			batchjob.setSeq(Long.parseLong(request.getParameter("setseq")));
			batchjob.update();
		}else{
			//BatchJob Repository 생성후 추가메서드 호출
			batchjob.add();
		}
	}
	
	/**
	 * 기존배치 삭제 관련 메서드
	 * @param batchjob
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = {"/state/deleteBatchJob.b1"})
	public void deleteBatchJob(BatchJob batchjob, HttpServletRequest request) throws Exception  {
		
		//BatchJob Repository 생성
		batchjob.setRepository(batchjobRepository);
		
		//기존배치의 Seq가 존재한다면 해당 Seq번호로 BatchJob을 설정해준다.
		if(request.getParameter("setseq") != null && !request.getParameter("setseq").equals("")){
			System.out.println(request.getParameter("setseq"));
			batchjob.setSeq(Long.parseLong(request.getParameter("setseq")));
			batchjob.remove();
		}
	}
	
	/**
	 * 배치실행 관련 메서드
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = {"/state/exec.b1"})
	public void exec(HttpServletRequest request) throws Exception  {
		
		BatchJob batchjob = new BatchJob(batchjobRepository);		
		batchjob.setSeq(Util.parseLong(request.getParameter("seq")));
		
		//배치 실행상태:실행중으로 변경
		batchjob.update("start");
		
		//런쳐 객체 생성 후 run
		BatchJobLauncher launcher = new BatchJobLauncher(batchjob.getSeq());
		launcher.run();			
	
		//배치 실행상태:대기중
		batchjob.update("end");		
	}
	
	/**
	 * 배치 강제종료 관련 메서드
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = {"/state/stop.b1"})
	public void stop(HttpServletRequest request) throws Exception {
 		
		BatchJob batchjob = new BatchJob(batchjobRepository);
		batchjob.setSeq(Util.parseLong(request.getParameter("seq")));
		
		//해당배치의 stopYn:Y update
		batchjob.setStopyn("Y");
		batchjob.update("stopyn");
		
		//해당배치의 PID번호를 얻는다
		String pid = batchjob.getInfo().getPid();
		//배치가 돌아가는 서버의 IP주소를 얻는다.
		String serverIp = batchjob.getInfo().getServerip();
		
		//처음 실행시 저장된 PID를 배치서버측으로 넘김
		ServerConnection connection = new ServerConnection(serverIp, pid, Batch.BATCH_STOP, null);
		connection.run();
    }
	
	/**
	 * 배치서버 CPU&Memory정보 취득 관련 메서드
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/state/monitoringPop.b1"})
	public ModelAndView monitoringPop(HttpServletRequest request)throws Exception {
		
		ModelAndView mav = new ModelAndView("/batchjob/state/monitoringPop");
		
		BatchJob batchjob = new BatchJob(batchjobRepository);
		batchjob.setSeq(Util.parseLong(request.getParameter("seq")));
		
		String serverIp = batchjob.getInfo().getServerip();
		String pid = batchjob.getInfo().getPid();
		
		//배치 실행시 저장된 PID를 server측으로 넘김
		ServerConnection connection = new ServerConnection(serverIp, pid, Batch.SERVER_MONITOR, null);
		connection.run();

		/*
		 * seq : 해당 배치의 시퀀스번호
		 * batchName : 배치명
		 * serverIp : 배치서버IP
		 * serverUseMem : 서버의 사용중인 메모리
		 * serverUseCpu : 서버의 사용중인 CPU
		 * batchUseMem : 배치가 서버에서 사용중인 메모리
		 * batchUseCpu : 배치가 서버에서 사용중인 CPU
		 */
		mav.addObject("seq", batchjob.getInfo().getSeq());
		mav.addObject("batchName", batchjob.getInfo().getName());
		mav.addObject("serverIp", serverIp);
		mav.addObject("serverUseMem", connection.getMsg("serverMem"));
		mav.addObject("serverUseCpu", connection.getMsg("serverCpu"));
		mav.addObject("batchUseMem", connection.getMsg("batchMem"));
		mav.addObject("batchUseCpu", connection.getMsg("batchCpu"));

		return mav;
	}
}