package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import messagetext.JIMHistoryTextPane;
import messagetext.JIMSendTextPane;
import messagetext.Message;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import socket.Client;
import socket.Screen_parameter;

public class Main extends JFrame {
	private Screen_parameter screen = new Screen_parameter() ;//Ϊ�˻�ȡ��Ļ�Ĳ�����ʹ�����κε����ϵĴ�С��������һ��
	private JPanel pane ;
	private JScrollPane scrollpane ;
	private JScrollPane scrollpane_01 ;
	private JScrollPane scrollpane_02 ;
	private JTable table ;
	private JTextArea text_02 ;
	private JButton button_01 ;
	private JButton button_02 ;
	private Client client ;
	
	private JLabel label_01 ;
	private JIMHistoryTextPane historyMessage ;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try
			    {
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			        UIManager.put("RootPane.setupButtonVisible", false);
			        
			    }
			    catch(Exception e)
			    {
			        //TODO exception
			    }
			    
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Main() {
		
		this.setTitle("聊天客户端");
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int)(460*screen.getWidth_pro()), (int)(120*screen.getHeight_pro()), (int)(1000*screen.getWidth_pro()), (int)(680*screen.getHeight_pro()));
		getContentPane().setLayout(null);
		String str = JOptionPane.showInputDialog("服务器IP：");
		if(str==null)System.exit(0);
		
		initial() ;
		try {
			client = new Client(str) ;
			new ReadLine() ;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			label_01.setText("未知服务器！");
			button_02.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			label_01.setText("服务器连接失败！");
			button_02.setVisible(true);
			
		}
	}

	private void initial() {
		// TODO Auto-generated method stub

		pane = new JPanel() ;
		pane.setBounds((int)(100*screen.getWidth_pro()), (int)(20*screen.getHeight_pro()), (int)(800*screen.getWidth_pro()), (int)(550*screen.getHeight_pro()));
		pane.setLayout(null); 
		getContentPane().add(pane);
		
		historyMessage = new JIMHistoryTextPane() ;
	//	historyMessage.setBounds(0, 0, (int)(600*screen.getWidth_pro()), (int)(600*screen.getHeight_pro()));
		Font f_1 = new Font("微软雅黑",Font.BOLD,16) ;
		historyMessage.setFont(f_1);
	//	historyMessage.setPreferredSize(new Dimension(600,600));
		
		
		
		
		scrollpane = new JScrollPane(historyMessage) ;
	//	scrollpane.setLayout(null);
		scrollpane.setBounds(0, (int)(50*screen.getHeight_pro()), (int)(600*screen.getWidth_pro()), (int)(300*screen.getHeight_pro()));
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		historyMessage.setVisible(true) ;
		System.out.println(historyMessage.getHeight());
		pane.add(scrollpane) ;
		scrollpane.setVisible(true);
		
		
		
		text_02 = new JTextArea() ;
		text_02.getDocument().addDocumentListener(new TextListener());
		text_02.setLineWrap(true);
		text_02.setWrapStyleWord(true);
		Font font=new Font("微软雅黑",Font.BOLD,20) ;
		text_02.setFont(font);
		scrollpane_01 = new JScrollPane(text_02) ;
		scrollpane_01.setBounds(0, (int)(350*screen.getHeight_pro()), (int)(600*screen.getWidth_pro()), (int)(150*screen.getHeight_pro()));	
		scrollpane_01.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);;
		text_02.setVisible(true);
		pane.add(scrollpane_01) ; 
		scrollpane_01.setVisible(true);
		
		
		
		button_01 = new JButton("发  送") ;
		button_01.setBounds((int)(600*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()), (int)(100*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
		button_01.setEnabled(false);
		button_01.setVisible(true);
		button_01.addActionListener(new ButtonListener());
		pane.add(button_01) ;
		
		
		button_02 = new JButton("重新连接") ;
		button_02.setBounds((int)(600*screen.getWidth_pro()), (int)(500*screen.getHeight_pro()), (int)(100*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
		button_02.setEnabled(true);
		button_02.setVisible(false);
		button_02.addActionListener(new ButtonListener());
		pane.add(button_02) ;
		
		
		label_01 = new JLabel("") ;
		label_01.setBounds((int)(300*screen.getWidth_pro()), (int)(510*screen.getHeight_pro()), (int)(200*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
		Font f = new Font("微软雅黑",Font.BOLD,18) ;
		label_01.setFont(f);
		label_01.setForeground(Color.red);
		label_01.setVisible(true);
		pane.add(label_01) ;
		
		
		pane.setVisible(true);
		
	}
	
	
	class ReadLine extends Thread{
    	
    	public ReadLine(){
    		this.start();
    	
    	}
    	public void run(){
    		try{
    			
    		
    		while(true){
    			String result = client.getBr().readLine() ;
    			if(result!=null)historyMessage.getMessageConcurrentLinkedQueue().add(new Message(false,result));
    			historyMessage.repaint();
    		}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
	}
	
	class TextListener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			if(!button_01.isEnabled())button_01.setEnabled(true);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			if(e.getDocument().getLength()==0&&button_01.isEnabled()){
				
				button_01.setEnabled(false);
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(button_01)&&client!=null){
				String text = text_02.getText() ;
				historyMessage.getMessageConcurrentLinkedQueue().add(new Message(true,text)) ;
				historyMessage.repaint();
				System.out.println(historyMessage.getHeight());
				text_02.setText("");
				button_01.setEnabled(false);
				client.getPw().println(text);
				
			}else if(e.getSource().equals(button_02)){
				String str = JOptionPane.showInputDialog("服务器IP：");
				if(str!=null){
				try {
					client = new Client(str) ;
					new ReadLine() ;
					label_01.setText("");
					button_02.setVisible(false);
				} catch (UnknownHostException ee) {
					// TODO Auto-generated catch block
					label_01.setText("未知服务器！");
					button_02.setVisible(true);
				} catch (IOException ee) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					label_01.setText("服务器连接失败！");
					button_02.setVisible(true);
					
				}
				}
			}
		}
		
	}

}
