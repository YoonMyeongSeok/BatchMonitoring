package com.boneis.contoller.batchjob;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.boneis.domain.base.paging.Paging;
import com.boneis.domain.base.root.Repository;
import com.boneis.domain.tool.batchjob.BatchJob;
import com.boneis.domain.tool.batchjob.joblog.BatchJobLog;
import com.boneis.support.util.Util;

@Controller
public class BatchJobLogController {

	@Autowired
	private Repository<BatchJobLog> batchjoblogRepository;

	@Autowired
	private Repository<BatchJob> batchjobRepository;

	@RequestMapping(value = {"/batchjoblog/board.b1"})
	public ModelAndView getBatchJobLog(HttpServletRequest request){

		ModelAndView mav = new ModelAndView("batchjob/joblog/jobLog");
		
		BatchJobLog batchJobLog = new BatchJobLog(batchjoblogRepository, new Paging(request));
		BatchJob batchJob = new BatchJob(batchjobRepository);

		long totalrow = batchJobLog.getListCount();

		mav.addObject("list", batchJobLog.getList());
		mav.addObject("jobInfo", batchJob.getList());
		mav.addObject("cnt", totalrow);
		mav.addObject("paging", batchJobLog.getPaging().getList(totalrow, "JobLog"));
		mav.addObject("tgp", request.getParameter("tgp"));
		
		return mav;
	}

	@RequestMapping(value = {"/batchjoblog/indivLog.b1"})
	public ModelAndView getBatchJobLogIndiviual(HttpServletRequest request){

		ModelAndView mav = new ModelAndView("batchjob/joblog/jobLog");

		long seq = 0;
		if(request.getParameter("seq")!=null && request.getParameter("seq").length() > 0){
			seq = Long.parseLong(request.getParameter("seq"));
		}		
		
		//로그페이지 상단 배치명(Header-Table)부분 사용 유무
		String headerYn = "Y";
		if(request.getParameter("headerYn")!=null){
			headerYn = request.getParameter("headerYn");
		}

		BatchJobLog batchJobLog = new BatchJobLog(batchjoblogRepository, new BatchJob(), new Paging(request));
		batchJobLog.getBatchjob().setSeq(seq);

		long totalrow = batchJobLog.getListCount();

		mav.addObject("list", batchJobLog.getList());
		mav.addObject("cnt", totalrow);
		mav.addObject("headerYn", headerYn);
		mav.addObject("paging", batchJobLog.getPaging().getList(totalrow, "JobLog"+seq));
		mav.addObject("tgp", request.getParameter("tgp"));
		mav.addObject("seq", seq);
		
		return mav;
	}

	@RequestMapping(value = {"/batchjoblog/fileDown.b1"})
	public ModelAndView errorFileDown(HttpServletRequest request) throws Exception{
		
		ModelAndView mav = new ModelAndView("/batchjob/popupLayer");
		
		String logPath = request.getParameter("logPath");
		String log = "";
		
		if(!Util.isNull(logPath)){
			File file = new File(logPath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			//BufferedWriter writer = new BufferedWriter(new FileWriter("c:\\result.txt"));
			
			String line;			
			
			while ( (line = reader.readLine()) != null ){
				StringTokenizer tk = new StringTokenizer(line, " ");
				String token;
				while ( tk.hasMoreTokens() ) {
					token = tk.nextToken() + "\r\n";
					//writer.append(token);
					log += token;
				}
			}
			reader.close();
			//writer.close();
		}
		
		mav.addObject("log", log);
		
		return mav;    
	}
}