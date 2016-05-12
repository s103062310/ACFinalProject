package main;

import javax.swing.JFrame;

public class Client implements Runnable{
	
	private MainApplet applet;
	
	// Constructor
	public Client(String IPAddress, int portNum){
		
		// create applet
		applet = new MainApplet();
		applet.init();
		applet.start();
		applet.setFocusable(true);
	}
	
	// the program process of client
	public void run(){
		
	}
	
	// connect to server
	public void connect(){
		
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
