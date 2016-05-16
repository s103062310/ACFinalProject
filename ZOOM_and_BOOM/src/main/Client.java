package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;

public class Client implements Runnable{
	
	private MainApplet applet;
	private String IPAddress;
	private int portNum;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	ObjectInputStream objIn;
	ObjectOutputStream objOut;
	
	// Constructor
	public Client(String IPAddress, int portNum){
		// set IP & port
		this.IPAddress = IPAddress;
		this.portNum = portNum;
		
		// create applet
		applet = new MainApplet();
		applet.init();
		applet.start();
		applet.setFocusable(true);
	}
	
	// the program process of client
	public void run(){
		
		/*while (true) {
			String line = "";
			
			try {
				line = reader.readLine();
				System.out.println(line);
				switch (line) {
				case value:
					
					break;

				default:
					break;
				}
			} catch (IOException e) {
				 //TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
	
	// connect to server
	public void connect(){
		try {
			socket = new Socket(IPAddress, portNum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			reader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(
				new OutputStreamWriter(socket.getOutputStream()));
			objIn = new ObjectInputStream(socket.getInputStream());
			objOut = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//send message to server
	private void sendMessage(String message) {//­t³d¶Ç°e°T®§µ¹server
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(message);
		this.writer.println(sBuilder.toString());
		this.writer.flush();
	}
	
	// main
	public static void main(String[] args){
		
		// create client
		Client client = new Client("127.0.0.1", 8000);
		Thread thread = new Thread(client);
		thread.start();
		client.connect();
		
		// create frame
		JFrame window = new JFrame("ZOOM and BOOM");
		window.setContentPane(client.applet);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1117, 690);
		window.setVisible(true);
	}
	
}
