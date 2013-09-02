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
	
	/**
	 * 배치 잡 로그를 볼 수 있는 페이지 호출
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/batchjoblog/board.b1"})
	public ModelAndView getBatchJobLog(HttpServletRequest request){

		ModelAndView mav = new ModelAndView("batchjob/joblog/jobLog");
		
		//BatchJobLog객체 생성 - 페이징처리 추가
		BatchJobLog batchJobLog = new BatchJobLog(batchjoblogRepository, new Paging(request));
		BatchJob batchJob = new BatchJob(batchjobRepository);
		
		//BatchJobLog의 총 개수
		long totalrow = batchJobLog.getListCount();

		/*
		 * list : 배치 잡 로그의 리스트
		 * jobInfo : 잡의 정보
		 * cnt : 잡의 총 개수
		 * paging : 잡의 페이징 처리
		 * tgp : Target Point - joblog.jsp의 html을 뿌려줄 Target
		 */
		mav.addObject("list", batchJobLog.getList());
		mav.addObject("jobInfo", batchJob.getList());
		mav.addObject("cnt", totalrow);
		mav.addObject("paging", batchJobLog.getPaging().getList(totalrow, "JobLog"));
		mav.addObject("tgp", request.getParameter("tgp"));
		
		return mav;
	}

	/**
	 * 특정배치의 잡 로그 페이지를 보는 메서드
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/batchjoblog/indivLog.b1"})
	public ModelAndView getBatchJobLogIndiviual(HttpServletRequest request){

		ModelAndView mav = new ModelAndView("batchjob/joblog/jobLog");

		//seq 초기화 및 Parameter로 넘어온 seq의 정보를 세팅
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

		//배치잡의 총 카운트
		long totalrow = batchJobLog.getListCount();

		/*
		 * list : 배치 잡 로그의 리스트 
		 * cnt : 잡의 총 개수
		 * headerYn : 헤더사용의 유무
		 * paging : 잡의 페이징 처리
		 * tgp : Target Point - joblog.jsp의 html을 뿌려줄 Target
		 * seq : 특정 배치의 Seq번호
		 */
		mav.addObject("list", batchJobLog.getList());
		mav.addObject("cnt", totalrow);
		mav.addObject("headerYn", headerYn);
		mav.addObject("paging", batchJobLog.getPaging().getList(totalrow, "JobLog"+seq));
		mav.addObject("tgp", request.getParameter("tgp"));
		mav.addObject("seq", seq);
		
		return mav;
	}
	
	/**
	 * 로그파일을 볼 수 있는 메서드
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/batchjoblog/viewLogFile.b1"})
	public ModelAndView viewLogFile(HttpServletRequest request) throws Exception{
		
		ModelAndView mav = new ModelAndView("/batchjob/logPop");
		
		String logPath = request.getParameter("logPath");
		String log = "";
		
		if(!Util.isNull(logPath)){
			File file = new File(logPath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));			
			
			String line;			
			
			while ( (line = reader.readLine()) != null ){
				StringTokenizer tk = new StringTokenizer(line, " ");
				String token;
				while ( tk.hasMoreTokens() ) {
					token = tk.nextToken() + "\r\n";
					log += token;
				}
			}
			reader.close();
		}
		
		mav.addObject("log", log);
		
		return mav;    
	}
}