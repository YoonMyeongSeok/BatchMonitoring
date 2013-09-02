package com.boneis.server;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SupportServerImpl {
	
	private Process process;
	private BufferedReader bufferedReader;
	
	private String pid;	
	private String tmp;
	private String processList;			
	private String serverInfo;
	private String resultMsg;
		
	/**
	 * 넘어온 batchName으로 배치 실행 후 배치의 PID를 리턴
	 * @param batchName
	 * @return
	 */
	public String startBatch(String batchName) {

		try {
			
			//windows
			if (System.getProperty("os.name").contains("Windows")) {
			/*  검토필요
			 	 
				// start.bat 생성 후 각 배치명에 맞는 배치파일 호출
				fileWriter = new FileWriter("C:\\start.bat");
				stringBuffer = new StringBuffer();
				stringBuffer.append("start c:\\" + batchName + ".bat");
				fileWriter.write(stringBuffer.toString());
				fileWriter.close();
				process = new ProcessBuilder("cmd", "/c", "C:\\start.bat").start();
				Thread.sleep(2000);
				process = new ProcessBuilder("cmd", "/c", "del /f /q C:\\start.bat").start();
				pid = getPid(batchName);
			 */
				
			//linux
			} else {
				//startBatch.sh 생성 후 각 배치명에 맞는 쉘 호출
				FileWriter fileWriter = new FileWriter("/usr/develop/startBatch.sh");
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("#!/bin/bash");
				stringBuffer.append("\n");
				stringBuffer.append("gnome-terminal -x /usr/develop/" + batchName + ".sh");
				fileWriter.write(stringBuffer.toString());
				fileWriter.close();
				
				//생성한 startBatch.sh 실행 후 2초뒤 생성한 쉘파일 삭제 : 나중에 파일이 중복될 경우 방지
				process = new ProcessBuilder("/bin/bash", "-c", "sh /usr/develop/startBatch.sh").start();
				Thread.sleep(2000);
				process = new ProcessBuilder("/bin/bash", "-c", "rm /usr/develop/startBatch.sh").start();
				
				//pid변수에 해당 batchName으로 실행되고 있는 프로세스의 PID값을 대입
				pid = getPid(batchName);
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pid;
	}
	
	
	
	/**
	 * 프로세스 상에서 진행 중인 batchName의 PID 가져오기
	 * @param batchName
	 * @return
	 */
	public String getPid(String batchName) {

		try {

			// Window
			if (System.getProperty("os.name").contains("Windows")) {
			/*	검토필요

				batchName = "관리자:  " + batchName;
				process = new ProcessBuilder("cmd.exe", "/C", "tasklist /FI \"WINDOWTITLE eq " + batchName + "\" /FO CSV /NH").start();
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((tmp = bufferedReader.readLine()) != null) {
					processList = tmp;
				}
				String processArr[] = processList.split("\n");
				String procSplit[] = processArr[0].split(",");
				pid = procSplit[1];
				pid = pid.substring(1, pid.length() - 1);
			*/
				
			// Linux
			} else {
				
				//배치명으로 현재 실행중인 배치의 PID를 얻어오는 명령어실행
				String[] cmd = { "/bin/sh", "-c", "ps -ef | grep " + batchName +".sh | grep -v grep"};
				process = Runtime.getRuntime().exec(cmd);
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				
				//processList변수에 상기 명령어에 해당하는 결과물을 대입
				tmp = "";
				processList = "";
				while ((tmp = bufferedReader.readLine()) != null) {
					processList = tmp;
				}
				
				//processList의 내용을 공백을 ','로 replace 후 ','를 기준으로 split처리
				processList = processList.replaceAll(" ", ",");
				String str[] = processList.split(",");
				
				//list객체에 공백이 아닌 내용들만 add
				ArrayList<String> list = new ArrayList<String>();
				for (int i = 0; i < str.length; i++) {
					if(!str[i].isEmpty())
						list.add(str[i]);
				}
				
				//list.get(1)에는 항상 pid값이 들어가 있다.
				pid = list.get(1);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pid;
	}

	
	/**
	 * 클라이언트에서 넘어온 PID값을 가지고 해당 프로세스 KILL
	 * @param pid
	 */
	public void stopBatch(String pid){

		try{
			//windows
			if(System.getProperty("os.name").contains("Windows")){
				//검토필요
				//process = new ProcessBuilder("cmd.exe","/C","taskkill /F /PID " + pid).start();
				
			//linux
			}else{
				process = new ProcessBuilder("/bin/sh",  "-c", "kill "+ pid).start();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 배치명을 가지고 해당배치의 CPU 및 메모리와 배치서버의 CPU 및 메모리 정보를 얻어온다.
	 * @param batchName
	 * @param pid
	 * @return
	 */
	public String getServerInfo(String pid){
		
		serverInfo = "";
		tmp = "";
		processList = "";
		
		try {
			// Window
			if (System.getProperty("os.name").contains("Windows")) {
				//검토필요
				//serverInfo = getTotalMemory();
				//serverInfo += "@##" + getUsedMemory(batchName);

			// Linux
			} else {

				//1. 서버의 사용중인 MEM 정보를 얻기위한 명령어를 실행
				String[] cmd = { "/bin/sh", "-c", "top -b1 -n1 | grep Mem"};
				process = Runtime.getRuntime().exec(cmd);
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((tmp = bufferedReader.readLine()) != null) {
					processList = tmp;
				}
				//serverInfo변수에 상기 명령어에 해당하는 출력물을 변환하여 대입 
				serverInfo += doTrans(processList, "Y", "Mem");

				
				//2. 서버의 사용중인 CPU 정보를 얻기위한 명령어를 실행
				String[] cmd2 = { "/bin/sh", "-c", "top -b1 -n1 | grep Cpu"};
				process = Runtime.getRuntime().exec(cmd2);				
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((tmp = bufferedReader.readLine()) != null) {
					processList = tmp;				
				}
				//serverInfo변수에 상기 명령어에 해당하는 출력물을 변환하여 대입 
				serverInfo += ("@##" + doTrans(processList, "Y", "Cpu"));
				
				
				//3. pid로 서버에서 실행중인 프로세스의 CPU와 MEM리 정보를 얻기위한 명령어를 실행
				String[] cmd3 = { "/bin/sh", "-c", "ps -o pcpu,vsize -p " + pid};
				process = Runtime.getRuntime().exec(cmd3);
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));				
				while ((tmp = bufferedReader.readLine()) != null) {
					processList = tmp;				
				}
				//serverInfo변수에 상기 명령어에 해당하는 출력물을 변환하여 대입
				serverInfo += ("@##" + doTrans(processList, "N", null));

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return serverInfo;
	}
	
	
	/**
	 * getServerInfo관련 메서드 - processList의 null값을 제거
	 * @param processList
	 * @param serverYn
	 * @param gubun
	 * @return
	 */
	public String doTrans(String processList, String serverYn, String gubun){
		ArrayList<String> arrListTmp = new ArrayList<String>();
		String[] arrTmp = processList.split(" ");
		resultMsg = "";
		
		for (int i = 0; i < arrTmp.length; i++) {
			if(!arrTmp[i].equals("") && arrTmp[i]!=null){
				arrListTmp.add(arrTmp[i]);
			}			
		}
		
		// Window
		if (System.getProperty("os.name").contains("Windows")) {
			/*
			 *	TODO 윈도우버젼 미구현 
			 */
		}	
		
		// Linux
		else {
			//serverYn이 Y인경우 -> 서버의 정보를 원할 경우
			if(serverYn.equals("Y")){
				//메모리정보를 얻기 위한 경우
				if(gubun.equals("Mem"))	{
					//arrListTmp.get(3)에는 항상 MEM정보가 들어가있다.
					String tmpStr = arrListTmp.get(3);
					resultMsg = "MemA:" + tmpStr.substring(0, tmpStr.length()-1);
				}
				
				//CPU정보를 얻기 위한 경우
				else if(gubun.equals("Cpu")){
					//arrListTmp.get(1)에는 항상 CPU정보가 들어가있다.
					String tmpStr = arrListTmp.get(1);
					resultMsg = "CpuA:" + tmpStr.substring(0, tmpStr.length()-4);
				}
			}	
			
			//serverYn이 N인경우 -> 배치의 정보를 원할 경우	
			else{
				//arrListTmp.get(1):Mem, arrListTmp.get(0):CPU 정보가 들어가있다.
				resultMsg = "Mem:" + arrListTmp.get(1) + "@##Cpu:" + arrListTmp.get(0);
			}			
		}
		
		//ex: resultMsg = MemA:22552@##CpuA:42@##Mem:14523@##Cpu:11
		return resultMsg;		
	}
	

	/**
	 * pid를 이용하여 해당 프로세스의 존재여부를 확인하여 메세지를 리턴
	 * @param pid
	 * @return
	 */
	public String getProcessExist(String pid){
		tmp = "";
		processList = "";		
		resultMsg = "";
		
		try {
			// Window
			if (System.getProperty("os.name").contains("Windows")) {
				//TODO windows버젼 미구현
				
			// Linux
			} else {
				
				//pid를 이용하여 해당 프로세스가 존재하는지를 체크하기위한 명령어 실행
				String[] cmd = { "/bin/sh", "-c", "ps -ef | grep " + pid + " | grep -v grep"};
				process = Runtime.getRuntime().exec(cmd);
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));				
				while ((tmp = bufferedReader.readLine()) != null) {
					processList = tmp;
				}				
				
				//processList에 값이 있다면 'Exist', 없다면 'NotExist' 를 리턴
				if(!processList.equals("")) resultMsg = "Exist";
				else resultMsg = "NotExist";
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultMsg;
	}
	
	
	/**
	 * windows 버젼에서 사용할 총 메모리를 얻는 메서드 - 검토 필요
	 * @return
	 */
/*	
	public String getTotalMemory() {
    	String memoryList[] = new String[6];
    	String totalMemory = "";
    	int i = 0;
    	try {
    		String output;
    		String[] cmd = {"systeminfo.exe"};
    		Process proc = Runtime.getRuntime().exec(cmd);
    		
    		BufferedReader stdout = new BufferedReader(new InputStreamReader(proc.getInputStream(), "euc-kr"));
    		while((output = stdout.readLine()) != null) {
    			if(output.indexOf("메모리") > 0) {
    				memoryList[i] = output;
    				i++;
    			}
    		}
    		totalMemory = memoryList[4].replace(" ", "@##");
    		String memory[] = totalMemory.split("@##");
    		totalMemory = memory[7];
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return totalMemory;
    }
*/	
	
	/**
	 * windows 버젼에서 사용할 사용중인 메모리를 얻는 메서드 - 검토 필요
	 * @param batchName
	 * @return
	 */
/*
    public String getUsedMemory(String batchName) {
    	
    	String usedMemory = "";
		String tmp = "";
		String processList = "";
		Process process;
		BufferedReader bufferedReader;
		
		try {
			
			batchName = "관리자:  " + batchName;
			process = new ProcessBuilder("cmd.exe", "/C", "tasklist /fi \"WINDOWTITLE eq " + batchName + "\" /NH").start();
			
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			while((tmp=bufferedReader.readLine())!=null) {
				processList = tmp;
			}
			
			String processArr[] = processList.split("\n");
			String tasklist = processArr[0].replace(" ", "@##");
			String memoryList[] = tasklist.split("@##");
			usedMemory = memoryList[50]+memoryList[51];
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return usedMemory;
    }
*/
	
}
