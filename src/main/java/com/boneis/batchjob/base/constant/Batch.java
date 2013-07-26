package com.boneis.batchjob.base.constant;

import com.boneis.support.config.Config;

public class Batch {
	
	public static final String JAVA_BATCH_JOB_SHELL_WINDOWS = Config.get("batch.shell.windows");
	public static final String JAVA_BATCH_JOB_SHELL_LINUX = Config.get("batch.shell.linux");
	public static final String WINDOWS = "Windows";
	public static final String LINUX = "Linux";
	public static final long USE_Y = 1;
	public static final long USE_N = 2;
	public static final long USE_EXEC = 3;
	public static final long EXEC_START = 1;
	public static final long EXEC_END = 2;
	public static final long RESULT_SUCCESS = 1;
	public static final long RESULT_FAIL = 2;
	public static final long RESULT_ING = 3;
	public static final long RESULT_STOP = 4;
	public static final long INPROCESS = 1;
	public static final long OUTPROCESS = 2;
	
}
