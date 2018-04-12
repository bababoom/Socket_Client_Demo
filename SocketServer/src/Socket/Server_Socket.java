package Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server_Socket extends ServerSocket {

	/**
	 * @param args
	 */
private static final int PORT = 50321 ;
	
	
	private ArrayList<String> user_List  = new ArrayList<String>();
	private  Queue<String> message_List  = new LinkedList<String>();
	private  AtomicBoolean flag = new AtomicBoolean(false); 
	private ArrayList<ClientThread> client_List  = new ArrayList<ClientThread>();
	
	public Server_Socket() throws Exception{
		super(PORT) ;
		new Message_Send();
		try{
			while(true){
				Socket socket = accept() ;
				new ClientThread(socket) ;
			}
		}catch(Exception e){
			
		}finally{
			close() ;
		}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new Server_Socket() ;
	}
	
	class Message_Send extends Thread{
		public Message_Send(){
			this.start();
		}
		
		public void run(){
			while(true){
				
				if(flag.compareAndSet(true, true)){
				
					String message = message_List.poll() ;
					String temp[] = message.split("-") ;
					int t = Integer.valueOf(temp[1]) ;
					
					for(int i=0;i<client_List.size();i++){
						if(i!=t){
						client_List.get(i).sendMessage(temp[0]) ;
						}
						
					}
					flag.set(message_List.size()==0?false:true) ;
				}
			}
		}
	}
	
	class ClientThread extends Thread{
		private Socket client ;
		private PrintWriter pw ;
		private BufferedReader br ;
		private String name ;
		public ClientThread(Socket s) throws IOException{
			client = s ;
			System.out.println("已连接");
			pw = new PrintWriter(client.getOutputStream(),true) ;
			br = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
			client_List.add(this) ;
			int t = user_List.size() ;
			user_List.add(String.valueOf(t)) ;
			System.out.println(client_List.size());
			this.start(); 
		}
		
		public void run(){
			try{
				String mess ;
				while(!"exit".equals(mess=br.readLine())){
					
					if(mess!=null){
						this.pushMessage(mess+"-"+String.valueOf(client_List.indexOf(this)));
					System.out.println(mess);
						
					}
					
				}
			}catch(Exception e){
				
			}finally{
				 try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 int t = client_List.indexOf(this) ;
				 client_List.remove(this) ;
				 user_List.remove(t) ;
				 System.out.println("退出");
			}
		}
		
		public void pushMessage(String str){
			message_List.add(str) ;
			 flag.compareAndSet(false, true) ;
		}
		public  void sendMessage(String str){
			pw.println(str) ;
		}
	}

}
