package com.boneis.support.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.boneis.batchjob.base.BaseJob;

public class BatchServerConnection extends Thread{
	
	private String serverIp;
	private String toServerMsg;
	private String selection;
	private BaseJob basejob;
	
	public BatchServerConnection(String serverIp, String toServerMsg, String selection, BaseJob basejob) {
		this.serverIp = serverIp;
		this.toServerMsg = toServerMsg;
		this.selection = selection;
		this.basejob = basejob;
	}
	
	public void run(){
		socketBatchServer(serverIp, toServerMsg, selection, basejob);
		yield();
	}
	
	public void socketBatchServer(String serverIp, String toServerMsg, String selection, BaseJob basejob) {

		Socket client = null;
		BufferedWriter bw = null;
		BufferedReader toServer = null;
		BufferedReader fromServer = null;
		String fromServerMsg = "";
		String tmpMsg = "";
		String[] msgList = null;
		String[] msgList2 = null;
		String tmpPid;
		try {
			client = new Socket(serverIp, 10002);

			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();
			
			toServer = new BufferedReader(new InputStreamReader(System.in));
			fromServer = new BufferedReader(new InputStreamReader(is));
			
			//client -> server 로 보낼 것
			bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write(selection + "@##" + toServerMsg + "\n");
			bw.flush();

			while((tmpMsg=fromServer.readLine()) != null){
				fromServerMsg += (tmpMsg + "\n");
			}
			
			msgList = fromServerMsg.split("\n");
			for (int i = 0; i < msgList.length; i++) {
				System.out.println("실행시 server--->" + msgList[i]);
			}												
			
			basejob.updatePid(msgList[0]);
			basejob.updateServerInfo(msgList[1]);
			
		/***************************************************************/
		if(!selection.equals("2")){
			do{						
				tmpPid = basejob.getPid();
				
				fromServerMsg = "";
				client = new Socket(serverIp, 10002);
				is = client.getInputStream();
				os = client.getOutputStream();	
				
				fromServer = new BufferedReader(new InputStreamReader(is));
				
				//client -> server 로 보낼 것
				bw = new BufferedWriter(new OutputStreamWriter(os));
				if(tmpPid.isEmpty())
					bw.write(3 + "@##" + "killed" + "\n");
				else
					bw.write(3 + "@##" + toServerMsg + "\n");
				bw.flush();
				
				while((tmpMsg=fromServer.readLine()) != null){
					fromServerMsg += (tmpMsg + "\n");
				}
				msgList = fromServerMsg.split("\n");
				for (int i = 0; i < msgList.length; i++) {
					System.out.println("give&take server ---> " + msgList[i]);
				}
					
				basejob.updateServerInfo(msgList[1]);

			}while(!msgList[1].equals("exit"));
		}
		/***************************************************************/

		bw.close();
		client.close();

		} catch (Exception e) {}
		
	}
}