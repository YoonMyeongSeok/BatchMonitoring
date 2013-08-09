package com.boneis.support.batchlauncher;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.boneis.batchjob.base.BaseJob;
import com.boneis.domain.tool.batchjob.BatchJob;
import com.boneis.domain.tool.batchjob.BatchJobRepository;
import com.boneis.support.util.BatchServerConnection;
import com.boneis.support.util.Util;

public class BatchJobLauncher {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		execBatch(Util.parseLong(args[0]));
	}
	
	public static void execBatch(long seq) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		try{
		
			ApplicationContext ac = new GenericXmlApplicationContext(new String[] {"com/boneis/batchjob/base/config/base-context.xml"});
			BatchJobRepository batchjobRepository = (BatchJobRepository)ac.getBean("batchjobRepository");
			BatchJob batchjob = new BatchJob(batchjobRepository);
			BatchJob execjob = null;
			batchjob.setSeq(seq);
			execjob = batchjob.getInfo();
			BaseJob basejob = (BaseJob) Class.forName(execjob.getClazz()).newInstance();
			basejob.exec();
			
			String serverIp = execjob.getServerip();
			String batchName = execjob.getName();
			
			BatchServerConnection connection = new BatchServerConnection(serverIp, batchName, "1", basejob);
			connection.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}