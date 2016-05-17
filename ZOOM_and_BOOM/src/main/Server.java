package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Server extends JFrame{
	
	private ServerSocket serverSocket;
	private JTextArea textArea;
	private List<ConnectionThread> connections = new ArrayList<ConnectionThread>();
	private ArrayList<Player> playerlist = new ArrayList<Player>();
	
	// Constructor
	public  Server(int portNum){
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(0, 0 , 366, 300);
		JScrollPane scrollPane = new JScrollPane(this.textArea);
	    this.add(scrollPane);
	    this.setVisible(true);
	    
	    try {//設定serversocket port
			this.serverSocket = new ServerSocket(portNum);
			this.textArea.append("Server starts listening on port "+portNum+".\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// the program process of server
	public void runForever() {
		this.textArea.append("Server starts waiting for client.\n");
		
		while (true) {
			Socket connectionToClient = null;
			this.textArea.append("Server is waiting...\n");
			try {//嘗試連接client
				connectionToClient = this.serverSocket.accept();
				this.textArea.append("Server is connet!\n"
						+ "Player"+(connections.size()+1)+"'s host name is "+connectionToClient.getInetAddress()+"\n"
						+ "Player"+(connections.size()+1)+"'s IP Address is "+connectionToClient.getInetAddress()+"\n");
				/*System.out.println("Get connection from client " +
									connectionToClient.getInetAddress() + ":" +
									connectionToClient.getPort());*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			//System.out.println("Start");
			ConnectionThread connThread = new ConnectionThread(connectionToClient);
			connThread.start();
			connections.add(connThread);
			/*
			if (connections.size()%2 == 0){
				//GameMaster GM = new GameMaster(connections.get(connections.size()-2), connections.get(connections.size()-1));
				//GM.start();
			}*/
		
		}
	}
	
	class ConnectionThread extends Thread{
		Socket socket;
		Thread thread = new Thread();
		//PrintWriter stringWriter;
		//BufferedReader stringReader;
		ObjectInputStream objIn;
		ObjectOutputStream objOut;
		String Answer,line;
		boolean QuestionReady = false;
		
		
		public ConnectionThread(Socket socket) {//連接socket與reader,writer
			// TODO Auto-generated constructor stub
			this.socket = socket;
			try {
				//stringWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				//stringReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));			
				//System.out.println("s");
				objIn = new ObjectInputStream(socket.getInputStream());		
				objOut = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while (true) {
				try {
					Object message = objIn.readObject();
					if (message instanceof Player) {
						Player pl = (Player)message;			
						playerlist.add(pl);
						for (ConnectionThread connection : connections) {			
							objOut.writeObject(new PlayerList(playerlist));
							objOut.flush();						
						}
		            }
					
					
				} catch (IOException e) {				
					e.printStackTrace();
				}catch(Exception e){
					
				}
			}
			
		}
		/*
		void sendMessage(Object message){
			try {
				Player pl = new Player(5,100,"HA");
				objOut.writeObject(pl);
				objOut.flush();
			} catch (Exception e) {
				System.out.println("EXCEPTION");
			}
			//stringWriter.println(message);
			//stringWriter.flush();
		}
		*/
	}
	
	// main
	public static void main(String[] args) {
		// create server
		Server server = new Server(8000);
		server.runForever();
	}
}
