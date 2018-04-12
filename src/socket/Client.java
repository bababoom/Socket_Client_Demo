package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Socket {
	//private static final String SERVER_IP ;
    private static final int SERVER_PORT =50321 ;
    private Socket client ;
    private PrintWriter pw ;
    private BufferedReader br ;
    
    
    public Client(){} ;
    public Client(String ip) throws UnknownHostException, IOException{
    	super(ip,SERVER_PORT) ;
    	client = this ;
    	pw = new PrintWriter(this.getOutputStream(),true) ;
    	br = new BufferedReader(new InputStreamReader(this.getInputStream())) ;
    	
    	
    }
    
    public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	
    
}
