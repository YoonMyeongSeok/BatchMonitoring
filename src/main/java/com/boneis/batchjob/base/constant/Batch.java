package com.boneis.batchjob.base.constant;

import com.boneis.support.config.Config;

public class Batch {
	
	public static final String JAVA_BATCH_JOB_SHELL_WINDOWS = Config.get("batch.shell.windows");
	public static final String JAVA_BATCH_JOB_SHELL_LINUX = Config.get("batch.shell.linux");
	public static final String WINDOWS = "Windows";		//Server OS name - windows
	public static final String LINUX = "Linux";			//Server OS name - linux
	public static final long USE_Y = 1; 				//사용
	public static final long USE_N = 2; 				//미사용
	public static final long USE_EXEC = 3; 				//실행
	
	public static final long EXEC_START = 1;			//배치시작
	public static final long EXEC_END = 2;				//배치종료
	
	public static final long RESULT_SUCCESS = 1;		//정상종료
	public static final long RESULT_FAIL = 2;			//실패
	public static final long RESULT_ING = 3;			//실행중
	public static final long RESULT_STOP = 4;			//중지
	public static final long RESULT_CONNECTFAIL = 5;	//서버연결실패

	//배치서버 Connection관련
	public static final long BATCH_START = 1;		//배치실행
	public static final long BATCH_STOP = 2;		//배치중지
	public static final long SERVER_MONITOR = 3;	//서버모니터링
	public static final long CONNECT_KEEP = 4;		//서버연결유지
	
}