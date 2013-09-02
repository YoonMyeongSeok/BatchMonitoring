package com.boneis.support.batchlauncher;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.boneis.batchjob.base.BaseJob;
import com.boneis.batchjob.base.constant.Batch;
import com.boneis.domain.tool.batchjob.BatchJob;
import com.boneis.domain.tool.batchjob.BatchJobRepository;
import com.boneis.support.connection.ServerConnection;

public class BatchJobLauncher extends Thread{
	
	private long seq;
	
	public BatchJobLauncher(long seq){
		this.seq = seq;
	}
	/**
	 * 실행
	 */
	public void run(){
		try {
			execBatch(seq);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		yield();
	}
	/**
	 * 배치서버와의 연결 시작
	 * @param seq
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static void execBatch(long seq) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		try{
		
			ApplicationContext ac = new GenericXmlApplicationContext(new String[] {"com/boneis/batchjob/base/config/base-context.xml"});
			BatchJobRepository batchjobRepository = (BatchJobRepository)ac.getBean("batchjobRepository");		// ac에서 batchjobRepository의 설정 가져오기
			BatchJob batchjob = new BatchJob(batchjobRepository);
			batchjob.setSeq(seq);
			
			//해당 배치의 잡 생성
			BaseJob basejob = new BaseJob();
			basejob.setBatchno(seq);
			
			String serverIp = batchjob.getInfo().getServerip();
			String batchName = batchjob.getInfo().getName();
			
			//실행 시킬 배치의 이름과 함께 서버에 연결
			ServerConnection connection = new ServerConnection(serverIp, batchName, Batch.BATCH_START, basejob);		// 배치서버와 컨넥션 생성
			connection.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}