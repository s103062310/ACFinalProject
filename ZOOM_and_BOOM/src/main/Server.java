package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import object.client.Splash;
import object.server.*;

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
	public HashMap<String, Answer> answer = new HashMap<String, Answer>();
	private HashMap<String, String> attackPair = new HashMap<String, String>();
	
	
	// Constructor
	public Server(int portNum) {
		
		//load database
		Database database = new Database();
		WaitWindow waitWindow = new WaitWindow("LOADING");
		waitWindow.init();
		waitWindow.start();
		waitWindow.runFrame();
		database.loadAnswerDatabase(this);
		waitWindow.closeFrame();
		
		// set up of server's frame
		setSize(400, 200);
		setLocation(0, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JFrame frame = this;
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(frame, 
		            "Are you sure you want to close the server?", "Really Closing?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
	        		frame.dispose();
		        	closeServer();
		        }
		    }
		});
		
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
		
	}

	
	// the program process of server
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
		
		// information of player
		String name;

		
		// Constructor: connect socket and reader/writer
		public ConnectionThread(Socket socket){
			
			// TODO Auto-generated constructor stub
			this.socket = socket;
			
			try {
				
				objIn = new ObjectInputStream(socket.getInputStream());
				objOut = new ObjectOutputStream(socket.getOutputStream());
			
			} catch (Exception e) {
				
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
						
						System.out.println("new player!");
						
						// update players' playing state
						Player player = (Player)message;
						name = player.getName();
						System.out.println(player.isOnLine());
						
						// update player list
						ArrayList<Player> list = new ArrayList<Player>();
						list.add(player);
						for(Player p : playerlist){
							if(!p.getName().equals(name)) list.add(p);
						}
						playerlist = list;
						
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
							String targetName = (String)objIn.readObject();
							int color = (int)objIn.readObject();
							
							// attack
							attackPair.put(targetName, name);
							for (ConnectionThread connection : connections){
								if (targetName.equals(connection.name)){
									connection.send("be attacked");
									connection.send(color);
									break;
								}
							}
							
							break;
						
						// receive screenshot
						case "picName":
							
							String picName = (String) objIn.readObject();
							ArrayList<Splash> splash= (ArrayList<Splash>)objIn.readObject();
							String sourceName1 = attackPair.get(name);
							attackPair.remove(name);
							for (ConnectionThread connection : connections) {
								if (sourceName1.equals(connection.name)) {
									connection.send("showPic");
									connection.send(picName);
									connection.send(splash);
									break;
								}
							}
							break;
						
						// victim is protected
						case "protected":
							
							String sourceName2 = attackPair.get(name);
							attackPair.remove(name);
							for (ConnectionThread connection : connections) {
								if (sourceName2.equals(connection.name)) {
									connection.send("protected");
									break;
								}
							}
							break;						
							
						default:
							break;
							
						}
						
					}

				} catch (IOException e) {
					
					// deal with client disconnect
					connections.remove(this);
					textArea.append("Player ["+name+"] disconnect.\n");
					
					// update player list
					/*bugggggggggggggggggggggggggggggggggggggggggggggggggggggggggg
					for (Player p : playerlist) {
						if (p.getName().equals(name))
							playerlist.remove(p);
					}*/
					ArrayList<Player> list = new ArrayList<Player>();
					for(Player p : playerlist){
						if(!p.getName().equals(name)) list.add(p);
					}
					playerlist = list;
					refreshPlayerList();
					
					stop();
					
				} catch (Exception e) {

				}
				
			}
			
		}
		
		
		// send object to client
		public void send(Object o){
			
			try {
				
				objOut.reset();
				objOut.writeObject(o);
				objOut.flush();
				
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
		}
		
	}

	
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
	public void refreshPlayerList(){
		
		// sort
		Collections.sort(playerlist, new CompareScore());
		
		// broadcast the whole player list
		for (ConnectionThread connection : connections) {
			connection.send(playerlist);
		}
		
	}
	
	
	// compare players' answer with correct answer
	public boolean checkAnswer(String image, float startX, float startY, float width, float height){
		
		// calculate center point of answer frame
		float x = startX + width/2;
		float y = startY + height/2;
		
		// return checking result
		return answer.get(image).check(x, y, width, height);
	
	}
	
	
	// output theme of images
	private void createOutputImage(){
		
		// create PApplet
		CreateOutput applet = new CreateOutput(answer);
		applet.init();
		applet.start();
		applet.setFocusable(true);
		
	}
	
	
	// close Server
	private void closeServer(){
		
		// interrupt all connections
		for(ConnectionThread client : connections){
			client.send("terminate");
		}
		
		// create output image
		createOutputImage();
		
		// update answer database
		Database database = new Database();
		WaitWindow waitWindow = new WaitWindow("UPDATING");
		waitWindow.init();
		waitWindow.start();
		waitWindow.runFrame();
		//database.resetAnswerDatabase();
		database.updateAnswerDatabase(answer);
		waitWindow.closeFrame();
    	
    	System.exit(0);
    	
	}
	
	
	// main
	public static void main(String[] args) {
		
		// create server
		Server server = new Server(8000);
		server.runForever();
		
	}

}
