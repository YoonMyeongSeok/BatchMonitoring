package com.boneis.support.batchlauncher;

import java.util.List;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailBean;

import com.boneis.batchjob.base.constant.Batch;
import com.boneis.domain.tool.batchjob.BatchJob;
import com.boneis.domain.tool.batchjob.BatchJobRepository;

public class BatchJobScheduler {

	private static Logger logger = LoggerFactory.getLogger(BatchJobScheduler.class);
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//ApplicationContext ac = new GenericXmlApplicationContext(ClassUtils.classPackageAsResourcePath(BatchJobScheduler.class) + "/config/application-context.xml");
		ApplicationContext ac = new GenericXmlApplicationContext(new String[] {
				"com/boneis/support/batchlauncher/config/scheduler-context.xml",
				"com/boneis/batchjob/base/config/base-context.xml"
				});
		Scheduler scheduler = (Scheduler) ac.getBean("schedulerFactoryBean");
		BatchJobRepository batchjobRepository = (BatchJobRepository)ac.getBean("batchjobRepository");
		BatchJob batchjob = new BatchJob();
		
		batchjob.setRepository(batchjobRepository);
		batchjob.setUseyn(Batch.USE_Y);
		List<BatchJob> list = batchjob.getList();
		logger.info("name?"+batchjob.getName());
		for(int i=0;i<list.size();i++){
			BatchJob temp = list.get(i);
			JobDetailBean jobDetail = new JobDetailBean(); 
			jobDetail.setName(temp.getName()+"Job");
			jobDetail.setJobClass(Class.forName(temp.getClazz()));
			
			CronTriggerBean cronTrigger = new CronTriggerBean();
			cronTrigger.setName(temp.getName()+"Trigger");
			cronTrigger.setCronExpression(temp.getCronexpression());
			cronTrigger.afterPropertiesSet();
			
			scheduler.scheduleJob(jobDetail, cronTrigger);
		}
		scheduler.start();
		
		logger.info("list size : "+ list.size());
		
		/*// 하나의 배치처리 프로세스 시작
		JobDetailBean jobDetail = new JobDetailBean(); 
		jobDetail.setName("batchnameJob");
		jobDetail.setJobClass(com.codeinven.support.batch.test.TestJob.class);
		
		CronTriggerBean cronTrigger = new CronTriggerBean();
		cronTrigger.setName("batchnameTrigger");
		cronTrigger.setCronExpression("0 * * * * ?");
		cronTrigger.afterPropertiesSet();
		
		scheduler.scheduleJob(jobDetail, cronTrigger);
		
		JobDetailBean jobDetail2 = new JobDetailBean(); 
		jobDetail2.setName("batchnameJob2");
		jobDetail2.setJobClass(com.codeinven.support.batch.test.TestJob2.class);
		
		CronTriggerBean cronTrigger2 = new CronTriggerBean();
		cronTrigger2.setName("batchnameTrigger2");
		cronTrigger2.setCronExpression("20 * * * * ?");
		cronTrigger2.afterPropertiesSet();
		
		scheduler.scheduleJob(jobDetail2, cronTrigger2);
		// 하나의 배치처리 프로세스 종료
		scheduler.start();*/
		
		logger.info("scheduler started!!");
	}

}
