package main;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import object.server.Answer;
import object.server.Player;

//TODO ���X�b��t��

@SuppressWarnings("serial")
public class Server extends JFrame {

	// server's socket
	private ServerSocket serverSocket;
	
	// GUI of server
	private JTextArea textArea = new JTextArea();
	
	// clients(listener/data) list
	private List<ConnectionThread> connections = new ArrayList<ConnectionThread>();
	private ArrayList<Player> playerlist = new ArrayList<Player>();
	private int people=0;
	
	// resources
	private HashMap<String, Answer> answer = new HashMap<String, Answer>();;
	private String path = new String("src/resource/pic_rsc");
	private String[] list;

	
	// Constructor
	public Server(int portNum) {
		
		// set up of server's frame
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create server's socket (set port)
		try {
			this.serverSocket = new ServerSocket(portNum);
			this.textArea.append("Server starts listening on port " + portNum + ".\n");
		} catch (IOException e) {
			System.out.println("Fail to create server's socket!");
			e.printStackTrace();
			System.exit(0);
		}
		
		// server's content field when working 
		textArea.setEditable(false);
		textArea.setBounds(0, 0, 366, 300);
		
		// scrollPane
		JScrollPane scrollPane = new JScrollPane(this.textArea);
		this.add(scrollPane);
		this.setVisible(true);
		
		// open file and load all file name to prepare answer sheet
		File folder = new File(path);
		list = folder.list();
		for(String file : list){
			answer.put(file, new Answer());
		}
		
	}

	
	// the program process of server
	// TODO �]�˼ƭp�ɾ��A�w�ɧ�s�Ʀ�]
	public void runForever() {
		
		this.textArea.append("Server starts waiting for client.\n");

		while (true) {
			
			// try to accept connect request of client and create connection channel
			try {
				Socket connectionToClient = this.serverSocket.accept();
				this.textArea.append("Server is connet!\n" + "Player" + (connections.size() + 1) + "'s host name is "
						+ connectionToClient.getInetAddress() + "\n" + "Player" + (connections.size() + 1)
						+ "'s IP Address is " + connectionToClient.getInetAddress() + "\n");
				ConnectionThread connThread = new ConnectionThread(connectionToClient);
				connThread.start();
				this.connections.add(connThread);
				people++;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	
	// Inner class: connection with client
	class ConnectionThread extends Thread {
		
		// client's socket and thread
		Socket socket;
		Thread thread = new Thread();
		
		// I/O of client
		ObjectInputStream objIn;
		ObjectOutputStream objOut;
		//String Answer, line;
		
		// information of player
		Player pl;
		int ID;
		//boolean QuestionReady = false;

		
		// Constructor: connect socket and reader/writer
		public ConnectionThread(Socket socket){
			
			// TODO Auto-generated constructor stub
			this.socket = socket;
			try {
				objIn = new ObjectInputStream(socket.getInputStream());
				objOut = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.out.println("Fail to establish I/O channel between server and client!");
				e.printStackTrace();
			}
			
		}

		
		// the program process of connection thread
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			
			while (true) {
				try {
					
					// read
					Object message = objIn.readObject();
					
					// identify which object is transmit
					if (message instanceof Player) {
						
						// when a new Player adds in 
						//TODO check! need to define player more detailed
						//TODO �Ѧ�client => �qserver�Ǹӱb�ᤧPlayer��client
						// set player information
						pl = (Player)message;
						pl.setID(people);
						ID = people;
						playerlist.add(pl);
						
						// send the number of online people
						objOut.writeObject(people);
						objOut.flush();
						
						// update score board
						refreshPlayerList();
						
					} else if (message instanceof String) {
						
						// when receive String flag
						
						// identify which flag is
						switch ((String)message) {
						
						// finish framing and request of check answer
						case "frameEnd":
							
							// receive answer information
							String image = (String)objIn.readObject();
							float startX = (float)objIn.readObject();
							float startY = (float)objIn.readObject();
							float width = (float)objIn.readObject();
							float height = (float)objIn.readObject();						
							
							// check answer and transmit result back to player
							if(checkAnswer(image, startX, startY, width, height))
								objOut.writeObject("Correct");
							else objOut.writeObject("Wrong");
							break;
							
						// request of attack another players
						case "attack":
							
							// receive attack information
							int targetID = (int)objIn.readObject();
							int color = (int)objIn.readObject();
							
							// attack
							for (ConnectionThread connection : connections){
								connection.send("askID");
								if (targetID == (int)objIn.readObject()){
									connection.send("be attacked");
									connection.send(color);
									break;
								}
							}
							
							//TODO transmit screenshot
							
						default:
							break;
							
						}
						
					}

				} catch (IOException e) {
					//e.printStackTrace();
					connections.remove(this);
					playerlist.remove(pl);
					refreshPlayerList();
					textArea.append("Player ["+pl.getName()+"] disconnect.");
					stop();
				} catch (Exception e) {

				}
				
			}
			
		}
		
		
		// send object to client
		public void send(Object o){
			
			try {
				objOut.writeObject(o);
				objOut.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	
	// Inner class: compare users' score
	// Inner class: sort the player list
	class CompareScore implements Comparator<Object>{
	    
		// define the method of compare
		public int compare(Object obj1, Object obj2){
	        
			Player o1 = (Player)obj1;
	        Player o2 = (Player)obj2;

	        // compare through player's score
	        if(o1.getScore()>o2.getScore()) return -1;
	        else if(o1.getScore()<o2.getScore()) return 1;
	        else return 0;
	        
	    }
		
	}

	
	// update score board
	// update score board
	public void refreshPlayerList(){
		
		// sort
		Collections.sort(playerlist, new CompareScore());
		
		// broadcast the whole player list
		//PlayerList PL = new PlayerList(playerlist);				
		for (ConnectionThread connection : connections) {
			//connection.objOut.writeObject(PL);
			//connection.objOut.flush();
			connection.send("CLEAR");
			for(Player each : playerlist){
				connection.send(each);
			}
			connection.send("COMPLETE");
		}
		
	}
	
	
	// compare players' answer with correct answer
	// deal with answer checking
	public boolean checkAnswer(String image, float startX, float startY, float width, float height){
		
		// calculate center point of answer frame
		float x = startX + width/2;
		float y = startY + height/2;
		
		// return checking result
		return answer.get(image).check(x, y, width, height);
	
	}
		
	
	// main
	// main
	public static void main(String[] args) {
		
		// create server
		Server server = new Server(8000);
		server.runForever();
		
	}

}
