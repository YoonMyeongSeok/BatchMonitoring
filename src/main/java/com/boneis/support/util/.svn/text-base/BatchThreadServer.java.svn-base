package com.boneis.support.util;
/************
 * 
 * BatchServerProject 
  * 에서 사용되는 클래스
 * 
 ************/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class BatchThreadServer extends Thread {
	protected Socket sock;

	BatchThreadServer(Socket sock) {
		this.sock = sock;
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSock = new ServerSocket(10002);

		while (true) {
			Socket client = serverSock.accept();
			BatchThreadServer myServer = new BatchThreadServer(client);
			myServer.start();
		}
	}
	
	//Thread로 배치실행
	public void run() {
		try {
			System.out.println(sock + ": 연결됨");
			InputStream fromClient = sock.getInputStream();
			OutputStream toClient = sock.getOutputStream();			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(toClient));
			BufferedReader br = new BufferedReader(new InputStreamReader(fromClient));

			String clientMsg = "";
			String serverInfo = "";
			String[] msgSplit;
			String batchName = "";
			String pid = "";
			
			while((clientMsg = br.readLine()) != null){				
				System.out.println("clint---->" + clientMsg);
				msgSplit = clientMsg.split("@##");

				if(msgSplit[0].equals("1")){
					batchName = msgSplit[1];
					pid = startBatch(batchName);
					serverInfo = getServerInfo(batchName, pid);
					bw.write(pid+"\n");
					bw.write(serverInfo+"\n");
					bw.flush();					
					break;									
				}else if(msgSplit[0].equals("2")){
					pid = msgSplit[1];
					stopBatch(pid);
					bw.write("");
					bw.write("exit");					
					bw.flush();					
					break;
				}else{
					if(!msgSplit[1].equals("killed")){
						Thread.sleep(2000);
						pid = getPid(batchName);
						serverInfo = getServerInfo(batchName, pid);					
						bw.write(pid+"\n");
						bw.write(serverInfo+"\n");
						bw.flush();					
						break;
					}else{
						break;
					}
				}
			}
			bw.close();
			toClient.close();
		} catch (Exception ex) {
			System.out.println(sock + ": 연결 종료 (" + ex + ")");
		} finally {
			try {
				if (sock != null) sock.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public String startBatch(String batchName) {
		FileWriter fileWriter;
		Process process;
		StringBuffer stringBuffer;
		String pid = "";
		try {
			// Window
			if (System.getProperty("os.name").contains("Windows")) {
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

				// Linux
			} else {
				// startBatch.sh 생성 후 각 배치명에 맞는 쉘 호출
				fileWriter = new FileWriter("/usr/develop/startBatch.sh");
				stringBuffer = new StringBuffer();
				stringBuffer.append("#!/bin/bash");
				stringBuffer.append("\n");
				stringBuffer.append("gnome-terminal -x /usr/develop/" + batchName + ".sh");
				fileWriter.write(stringBuffer.toString());
				fileWriter.close();

				process = new ProcessBuilder("/bin/bash", "-c", "sh /usr/develop/startBatch.sh").start();
				Thread.sleep(2000);
				process = new ProcessBuilder("/bin/bash", "-c", "rm /usr/develop/startBatch.sh").start();
				
				pid = getPid(batchName);
				
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return pid;
	}
	
	/* 프로세스 상에서 진행 중인 batchName의 PID 가져오기 */
	public String getPid(String batchName) {

		String pid = "";
		String tmp = "";
		String processList = "";
		Process process;
		BufferedReader bufferedReader;
		try {

			// Window
			if (System.getProperty("os.name").contains("Windows")) {

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
				System.out.println(pid);

			// Linux
			} else {

				String[] cmd = { "/bin/sh", "-c", "ps -ef | grep " + batchName +".sh | grep -v grep"};
				process = Runtime.getRuntime().exec(cmd);
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((tmp = bufferedReader.readLine()) != null) {
					processList = tmp;
				}

				processList = processList.replaceAll(" ", ",");
				String str[] = processList.split(",");
				ArrayList<String> list = new ArrayList<String>();
				for (int i = 0; i < str.length; i++) {
					if(!str[i].isEmpty())
						list.add(str[i]);
				}
				
				pid = list.get(1);
				System.out.println("getPid():"+pid);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pid;
	}

	/* 클라이언트에서 넘어온 PID값을 가지고 해당 프로세스 KILL */
	public void stopBatch(String pid){
		Process process;
		try{
			if(System.getProperty("os.name").contains("Windows")){
				process = new ProcessBuilder("cmd.exe","/C","taskkill /F /PID " + pid).start();
			}else{
				process = new ProcessBuilder("/bin/sh",  "-c", "kill "+ pid).start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* 배치명을 가지고 해당배치의 CPU 및 메모리와 배치서버의 CPU 및 메모리 정보를 얻어온다. */
	public String getServerInfo(String batchName, String pid){
		String serverInfo = "";
		String tmp = "";
		String processList = "";
		Process process;
		BufferedReader bufferedReader;
		
		try {
			// Window
			if (System.getProperty("os.name").contains("Windows")) {
				serverInfo = getTotalMemory();
				serverInfo += "@##" + getUsedMemory(batchName);
			// Linux
			} else {
				
				String[] cmd = { "/bin/sh", "-c", "vmstat"};
				process = Runtime.getRuntime().exec(cmd);				
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((tmp = bufferedReader.readLine()) != null) {
					processList = tmp;				
				}				
				serverInfo += doTrans(processList, "Y");

				String[] cmd2 = { "/bin/sh", "-c", "ps -o pcpu,vsize -p " + pid};
				process = Runtime.getRuntime().exec(cmd2);
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));				
				while ((tmp = bufferedReader.readLine()) != null) {
					processList = tmp;				
				}				
				serverInfo += ("@##" + doTrans(processList, "N"));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("getServerInfo():"+serverInfo);
		return serverInfo;
	}
	
	
	/* getServerInfo관련 메서드 - processList의 null값을 제거 */
	public String doTrans(String processList, String allYn){
		ArrayList<String> arrListTmp = new ArrayList<String>();
		String[] arrTmp = processList.split(" ");
		String resultMsg = "";
		
		for (int i = 0; i < arrTmp.length; i++) {
			if(!arrTmp[i].equals("") && arrTmp[i]!=null){
				arrListTmp.add(arrTmp[i]);
			}			
		}
		
		// Window
		if (System.getProperty("os.name").contains("Windows")) {
			String[] resultStr = { arrListTmp.get(4), arrListTmp.get(8) };
			arrTmp = resultStr;
		
			// Linux
		} else {
			//Y:All, N:Special Batch
			if(allYn.equals("Y")){
				//4 = mem, 12 = cpu%
				resultMsg = "MemA:" + arrListTmp.get(4) +
						    "@##CpuA:" + arrListTmp.get(12);
			}else{
				//4 = mem, 8 = cpu%
				resultMsg = "Mem:" + arrListTmp.get(1) +
					    	"@##Cpu:" + arrListTmp.get(0);
			}			
		}		
		return resultMsg;		
	}
	
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
    		System.out.println("사용 중인 총 메모리 : " + totalMemory);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return totalMemory;
    }
    
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
			System.out.println("사용 중 메모리 : " + usedMemory);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return usedMemory;
    }	
}