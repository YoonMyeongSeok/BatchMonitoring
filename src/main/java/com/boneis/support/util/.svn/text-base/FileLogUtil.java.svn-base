package com.boneis.support.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.boneis.support.config.Config;

public class FileLogUtil {
	private static final Logger logger = Logger.getLogger(FileLogUtil.class);
	
	public static final String FILELOG_ROOT_DIR = Config.get("batch.log.path");	/* 로그파일 경로 */
	private static final String FILELOG_POSTFIX = ".fileLog"; /* 로그파일 확장자 */
	public static final String FILELOG_NEWLINE = "\n"; /* 로그 줄바꿈 처리 */
    
	/**
	 * 파일로그 작성 및 DB에 로그경로 저장
	 */
    public static String saveFileLog(Long batchNo, String batchName, StringBuilder sbLog) throws Exception {
    	//파일로그 작성
    	String fileLogFullPath = writeFileLog(batchNo, batchName, sbLog);
    	//DB에 파일로그 경로 저장
    	//saveFileLogPath(batchNo, fileLogFullPath);
    	logger.debug("---------------------> 로그파일 경로 : " + fileLogFullPath);
    	return fileLogFullPath;
    }
    
    /**
	 * 파일로그 작성
	 * @throws Exception
	 */
    public static String writeFileLog(Long batchNo, String batchName, StringBuilder sbLog) throws Exception {
    	
    	String fileLogFullPath = "";
		File fileLogPath = new File(FILELOG_ROOT_DIR  + File.separator + Util.currentFormatDate("yyyyMMdd"));
		
		if (!fileLogPath.exists()){
			FileUtils.forceMkdir(fileLogPath);
		}
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fileLogFullPath = fileLogPath + File.separator + "BN" + batchNo + "_" + Util.currentFormatDate("yyyyMMdd_HHmmss") + FILELOG_POSTFIX;
			fw = new FileWriter(fileLogFullPath);
			bw = new BufferedWriter(fw);

			bw.append("---------- " + batchName + "_" + Util.currentFormatDate("yyyyMMdd_HHmmss") + FILELOG_POSTFIX + " ----------" + FILELOG_NEWLINE);
			bw.write(sbLog.toString());
			bw.flush();	
			
		} catch (Exception ex){
			logger.error(ex);
			throw ex;
		} finally {
			if ( bw != null ) bw.close();
			if ( fw != null ) fw.close();
		}
		
		return fileLogFullPath;
	}
	
}
