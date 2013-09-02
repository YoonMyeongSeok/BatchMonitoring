package com.boneis.server;
/************
 * 
 * BatchServerProject 
 * 에서 사용되는 클래스
 * 
 ************/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BatchServer extends Thread {

	private SupportServerImpl serverImpl = new SupportServerImpl();
	
	protected Socket sock;

	BatchServer(Socket sock) {
		this.sock = sock;
	}
	
	/**
	 * main메서드 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		ServerSocket serverSock = new ServerSocket(10002);

		while (true) {
			Socket client = serverSock.accept();
			BatchServer myServer = new BatchServer(client);
			myServer.start();
		}
	}
	
	/**
	 * Thread로 배치실행 
	 */
	public void run() {
		runServer();
		yield();
	}
	
	/**
	 * server에서 처리 할 내용들
	 */
	public void runServer(){
		
		try {
				//클라이언트와 통신할 스트림 및 reader & writer 생성
				InputStream fromClient = sock.getInputStream();
				OutputStream toClient = sock.getOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(toClient));
				BufferedReader br = new BufferedReader(new InputStreamReader(fromClient));
	
				String clientMsg = "";		//클라이언트로부터 날아온 메세지
				String serverInfo = "";		//서버정보 -> mem & cpu
				String[] msgSplit;			//메세지 split
				String batchName = "";		//배치명
				String pid = "";			//해당 배치의 PID
				String prefix = "";			//서버에서 구분할 접두사 1~4

				//클라이언트로부터 전달된 메세지가 null이 될때까지 연결한다.
				while((clientMsg = br.readLine()) != null){				
					
					System.out.println("Client Messege : " + clientMsg);
					
					//msgSplit[0] : prefix - 1~4
					//msgSplit[1] : batchName or PID
					msgSplit = clientMsg.split("@##");
					
					prefix = msgSplit[0];
					
					//1@##batchName 일 경우
					if(prefix.equals("1")){
						//넘어온 배치명으로 배치실행 후 PID 리턴
						batchName = msgSplit[1];
						pid = serverImpl.startBatch(batchName);						
						//배치명과 PID로 서버의 정보를 리턴
						serverInfo = serverImpl.getServerInfo(pid);						
						//pid, serverInfo를 클라이언트로 메세지를 보낸다.
						bw.write(pid+"\n");
						bw.write(serverInfo+"\n");
						bw.flush();
						break;
					
					//2@##PID 일 경우
					}else if(prefix.equals("2")){
						//pid변수에 클라이언트로 넘어온 PID값 대입
						pid = msgSplit[1];
						//PID를 이용해 해당 프로세스 킬
						serverImpl.stopBatch(pid);
						//exit 메세지를 클라이언트로 보낸다.
						bw.write("exit\n");
						bw.flush();					
						break;
	
					//3@##PID 일 경우
					}else if(prefix.equals("3")){
						Thread.sleep(3000);
						//pid변수에 클라이언트로 넘어온 PID값 대입
						pid = msgSplit[1];
						//배치명과 PID로 서버의 정보를 리턴
						serverInfo = serverImpl.getServerInfo(pid);
						//pid, serverInfo를 클라이언트로 메세지를 보낸다.
						bw.write(pid+"\n");
						bw.write(serverInfo+"\n");
						bw.flush();
						break;
	
					//4@##PID 일 경우
					}else if(prefix.equals("4")){
						Thread.sleep(3000);
						//pid변수에 클라이언트로 넘어온 PID값 대입
						pid = msgSplit[1];
						//resultMsg에 pid:Exist  or pid:NotExist 형식으로 메세지를 클라이언트로 전송
						String resultMsg = pid + ":" + serverImpl.getProcessExist(pid);
						bw.write(resultMsg+"\n");
						bw.flush();
						break;
						
					}//if - else if 문 종료
					
				}//do-while 문 종료
				
				bw.close();
				toClient.close();
			
		} catch (Exception ex) {
			System.out.println(sock + ": 연결 종료 (" + ex + ")");
		
		} finally {
			try {
				if (sock != null){ 
					sock.close(); 
				}
	
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}