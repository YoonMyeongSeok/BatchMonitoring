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
import com.boneis.support.util.Util;

@Controller
public class BatchJobController {
	
	@Autowired
	private Repository<BatchJob> batchjobRepository;
	
	@Autowired
	private Repository<BatchJobStatus> batchjobstatusRepository;
	
	@RequestMapping(value = {"/state/batchjobList.b1"})
	public ModelAndView batchjobList(HttpServletRequest request)throws Exception {
		ModelAndView mav = new ModelAndView("/batchjob/state/BatchJobList");
		
		BatchJob batchjob = new BatchJob(batchjobRepository, new Paging(request));
		
		long totalrow = batchjob.getListCount();
		
		batchjob.setName(request.getParameter("search_name"));
		
		mav.addObject("list", batchjob.getList());
		mav.addObject("search_name", request.getParameter("search_name"));
		mav.addObject("paging", batchjob.getPaging().getList(totalrow, "batchjob"));
		
		return mav;
	}
	
	@RequestMapping(value = {"/state/saveBatchJob.b1"})
	public ModelAndView saveBatchJob(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/batchjob/state/saveBatchJob");
		
		BatchJob batchjob = new BatchJob(batchjobRepository);
		
		// seq값이 없으면 신규등록창 있으면 기존등록창
		if(request.getParameter("seq") != null && !request.getParameter("seq").equals("")){
			batchjob.setSeq(Long.parseLong(request.getParameter("seq")));
			mav.addObject("list", batchjob.getInfo());
		}else{
			mav.addObject("list", null);
		}
		
		return mav;
	}
	
	@RequestMapping(value = {"/state/createBatchJob.b1"})
	public void createBatchJob(BatchJob batchjob)throws Exception {
		batchjob.setRepository(batchjobRepository);
		
		batchjob.add();
	}
	
	@RequestMapping(value = {"/state/updateBatchJob.b1"})
	public void updateBatchJob(BatchJob batchjob, HttpServletRequest request) throws Exception  {
		batchjob.setRepository(batchjobRepository);
		
		if(request.getParameter("setseq") != null && !request.getParameter("setseq").equals("")){
			batchjob.setSeq(Long.parseLong(request.getParameter("setseq")));
		}
		
		batchjob.update();
	}
	
	@RequestMapping(value = {"/state/exec.b1"})
	public void exec(HttpServletRequest request) throws Exception  {
		BatchJob batchjob = new BatchJob(batchjobRepository);
		BatchJobStatus batchjobstatus = new BatchJobStatus(batchjobstatusRepository);
		
		batchjob.setSeq(Util.parseLong(request.getParameter("seq")));
		String batchIP = batchjob.getInfo().getServerip();
		String localIP = Util.getServerIp();
		batchjobstatus.setServerip(localIP);
		String permitIP = "Not Permittion";
		
		if(batchjobstatus.getListCount() > 0){
			permitIP = batchjobstatus.getInfo().getServerip();
		}

		if(localIP.equals(batchIP) || localIP.equals(permitIP)){
			BatchJobLauncher.execBatch(batchjob.getSeq());
		}else{
			batchjob.setFindmode("useyn");
			batchjob.setUseyn(Batch.USE_EXEC);
			batchjob.update();
			
			batchjobstatus.setServerip(localIP);
			batchjobstatus.setExecyn(Batch.EXEC_START);
			batchjobstatus.update();
		}
	}
	
	@RequestMapping(value = {"/state/stop.b1"})
	public void stop(HttpServletRequest request) throws Exception {
		BatchJob batchjob = new BatchJob(batchjobRepository);
		batchjob.setSeq(Util.parseLong(request.getParameter("seq")));
		batchjob.setStopyn("Y");
		batchjob.update("stopyn");
	}
}
