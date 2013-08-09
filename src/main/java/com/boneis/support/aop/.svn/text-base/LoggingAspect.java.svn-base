package com.boneis.support.aop;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class LoggingAspect {
	
	private Log logger = LogFactory.getLog(getClass());

	public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
		
		logger.info(" * @시작 - "
					+ joinPoint.getSignature().getName() + "() 시작@ ---- "
					+ joinPoint.getSignature().getDeclaringTypeName());
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object retValue = null;
		try {
			retValue = joinPoint.proceed();
		} catch (Exception e) {
			e.printStackTrace();
		}

		stopWatch.stop();
		logger.info(" * @종료 - "
					+ joinPoint.getSignature().getName() + "()"
					+ "<" + stopWatch.getTotalTimeMillis() + "ms> 종료@ ----"
					+ joinPoint.getSignature().getDeclaringTypeName());
		return retValue;
	}
	
}