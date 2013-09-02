package com.boneis.support.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import com.boneis.batchjob.base.BaseJob;
import com.boneis.batchjob.base.constant.Batch;

public class ServerConnection extends Thread{

	private String serverIp;
	private String toServerMsg;
	private long prefix;
	private BaseJob basejob;
	
	private ServerInfo serverInfo = new ServerInfo();
	private Socket client;
	private BufferedWriter toServer;
	private BufferedReader fromServer;
	
	public ServerConnection(String serverIp, String toServerMsg, long prefix, BaseJob basejob) {
		this.serverIp = serverIp;
		this.toServerMsg = toServerMsg;
		this.prefix = prefix;
		this.basejob = basejob;
	}
	
	public void run(){
		socketBatchServer(serverIp, toServerMsg, prefix, basejob);
		yield();
	}
	/**
	 * WAS SERVER와 BATCH SERVER의 연결 및 주고 받는 값 처리.
	 * @param serverIp
	 * @param toServerMsg
	 * @param prefix
	 * @param basejob
	 */
	public void socketBatchServer(String serverIp, String toServerMsg, long prefix, BaseJob basejob) {

		client = new Socket();
		SocketAddress address = new InetSocketAddress(serverIp, 10002);
		String fromServerMsg = "";
		String tmpMsg = "";
		String[] msgList = null;
		long resultCode = 0;

		try{
			//서버에 접속
			client.connect(address, 5000);
			
			if(client.isConnected()){
				
				InputStream is = client.getInputStream();
				OutputStream os = client.getOutputStream();				
				fromServer = new BufferedReader(new InputStreamReader(is));
		
				/*
				 * 1. 배치 서버로 메시지 전송 - selection: 1~4, toServerMsg: batchName 또는 PID
				 *    prefix : toServerMsg : fromServerMsg
				 * 	       1 : batchName   : PID, serverInfo
				 * 		   2 : PID         : 
				 *         3 : PID         : serverInfo
				 *         4 : PID         : serverInfo, "Exist" or "NotExist" 
				 */
				toServer = new BufferedWriter(new OutputStreamWriter(os));
				toServer.write(prefix + "@##" + toServerMsg + "\n");
				toServer.flush();

				/*
				 * 2. 배치서버에서 받아온 메세지
				 * 서버로부터 받아온 메시지. 배치 실행 시 PID, 서버 CPU&메모리 정보(MemA:678048@##CpuA:18.5@##Mem:43596@##Cpu:6.6)
				 */
				while((tmpMsg=fromServer.readLine()) != null){
					fromServerMsg += (tmpMsg + "\n");
				}				
				msgList = fromServerMsg.split("\n");
				for (int i = 0; i < msgList.length; i++) {
					System.out.println("Server Messege--->" + msgList[i]);
				}												
								
				/*
				 * 3-1. 배치시작인경우 -> DB에 PID 와 CPU, Memory정보 업데이트 후 서버와 연결 지속
				 * 3-2. 서버모니터링인경우 -> 서버에서 받아온 서버정보 메세지 세팅
				 */
				if(prefix==Batch.BATCH_START){
					//joblog 시작
					basejob.jobStart(Batch.BATCH_START, "배치처리시작");
					basejob.updatePid(msgList[0]);
					basejob.updateServerInfo(msgList[1]);
					
					connKeep();
					
				}else if(prefix==Batch.SERVER_MONITOR){
					setMsg(msgList[1]);
				}
				
				fromServer.close();
				toServer.close();
				client.close();
			}
			
		} catch (Exception e) {
			if(!client.isConnected()){
				System.out.println("Connect Fail");
				resultCode = Batch.RESULT_CONNECTFAIL;
			}
			if(prefix == Batch.BATCH_START){
				basejob.jobStart(Batch.RESULT_CONNECTFAIL, "서버연결실패");
			}
		} finally {
			
			if(prefix == Batch.BATCH_START){
				if(client.isConnected()){				
					if(basejob.getStopYn().equals("Y")){
						resultCode = Batch.RESULT_STOP;
					} else if(basejob.getStopYn().equals("N")){
						resultCode = Batch.RESULT_SUCCESS;
					}
					
					basejob.updateResult(resultCode);
					//jobLog종료
					basejob.jobEnd(Batch.RESULT_SUCCESS, "배치처리완료");
				}				
			}
		}
	}

	
	/**
	 * serverInfo에 메시지 SET
	 * @param msgList
	 */
	public void setMsg(String msgList){		
		String[] arrMsg = msgList.split("@##");			
		serverInfo.setServerUseMem(arrMsg[0].substring(5));
		serverInfo.setServerUseCpu(arrMsg[1].substring(5));
		if(!arrMsg[2].substring(4).equals("VSZ"))
			serverInfo.setBatchUseMem(arrMsg[2].substring(4));
		else
			serverInfo.setBatchUseMem("0");
		
		if(!arrMsg[3].substring(4).equals("%CPU"))
			serverInfo.setBatchUseCpu(arrMsg[3].substring(4));
		else
			serverInfo.setBatchUseCpu("0");
	}
	
	/**
	 * serverInfo에서 메시지 GET
	 * @param info
	 * @return
	 */
	public String getMsg(String info){
		String result = "";
		if(info.equals("serverCpu")) result = serverInfo.getServerUseCpu();
		if(info.equals("serverMem")) result = serverInfo.getServerUseMem();
		if(info.equals("batchCpu")) result = serverInfo.getBatchUseCpu();
		if(info.equals("batchMem")) result = serverInfo.getBatchUseMem();		
		return result;
	}
	
	/**
	 * Server와 연결 유지
	 * @throws Exception
	 */
	public void connKeep() throws Exception{
		//연결유지			
		String pid = basejob.getPid();
		String fromServerMsg = "";

		do{								
			client = new Socket(serverIp, 10002);
			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();	
			
			fromServer = new BufferedReader(new InputStreamReader(is));
			toServer = new BufferedWriter(new OutputStreamWriter(os));
			toServer.write(Batch.CONNECT_KEEP + "@##" + pid + "\n");
			toServer.flush();
			
			String tmpMsg = "";
			while((tmpMsg=fromServer.readLine()) != null){
				fromServerMsg = tmpMsg;
			}
			
			System.out.println("Server Messege ---> " + fromServerMsg);

			if(fromServerMsg.contains("NotExist")) {
				basejob.updateResult();
			}else{
				//jobLog Process
				basejob.jobProcess();
			}

		}while(!fromServerMsg.contains("NotExist"));
		
		client.close();
	}
}