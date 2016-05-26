package main;

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
import java.util.List;

@SuppressWarnings("serial")
public class Server extends JFrame {

	private ServerSocket serverSocket;
	private JTextArea textArea;
	private List<ConnectionThread> connections = new ArrayList<ConnectionThread>();
	private ArrayList<Player> playerlist = new ArrayList<Player>();
	private int people;

	// Constructor
	public Server(int portNum) {
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//playerlist.add(new Player(3,20,"HJH"));
		people=0;
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(0, 0, 366, 300);
		JScrollPane scrollPane = new JScrollPane(this.textArea);
		this.add(scrollPane);
		this.setVisible(true);

		try {// 設定serversocket port
			this.serverSocket = new ServerSocket(portNum);
			this.textArea.append("Server starts listening on port " + portNum + ".\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// the program process of server
	public void runForever() {
		this.textArea.append("Server starts waiting for client.\n");

		while (true) {
			
			//this.textArea.append("Server is waiting...\n");
			try {// 嘗試連接client
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

	class ConnectionThread extends Thread {
		Socket socket;
		Thread thread = new Thread();
		ObjectInputStream objIn;
		ObjectOutputStream objOut;
		String Answer, line;
		int ID;
		Player pl;
		boolean QuestionReady = false;

		public ConnectionThread(Socket socket) {// 連接socket與reader,writer
			// TODO Auto-generated constructor stub
			this.socket = socket;
			try {
				objIn = new ObjectInputStream(socket.getInputStream());
				objOut = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			while (true) {
				try {
					Object message = objIn.readObject();
					// a new Player adds in 
					if (message instanceof Player) {
						pl = (Player) message;
						pl.setID(people);
						ID = people;
						playerlist.add(pl);
						// send to the recent connected client only
						objOut.writeObject(people);
						objOut.flush();
						
						//以下幾行等同於refreshPlayerList(); 修改建議至refreshPlayerList()
						
						// sort
						Collections.sort(playerlist,new CompareScore());
						// broadcast the whole playerlist
						PlayerList PL = new PlayerList(playerlist);				
						for (ConnectionThread connection : connections) {
							//connection.objOut.writeObject(PL);
							//connection.objOut.flush();
							connection.objOut.writeObject("CLEAR");
							connection.objOut.flush();
							for(Player each : playerlist){
								connection.objOut.writeObject(each);
								connection.objOut.flush();
							}
							connection.objOut.writeObject("COMPLETE");
							connection.objOut.flush();
						}
						
						//以上幾行等同於refreshPlayerList(); 修改建議至refreshPlayerList()
					}else if (message instanceof String) {
						switch ((String)message) {
						case "frameEnd":
							int imageNumber = (int)objIn.readObject();
							float startX = (float)objIn.readObject();
							float startY = (float)objIn.readObject();
							float endX = (float)objIn.readObject();
							float endY = (float)objIn.readObject();						

							checkAnswer(imageNumber, startX, startY, endX, endY);
							break;
						case "attack":
							int targetID = (int)objIn.readObject();
							int color = (int)objIn.readObject();
							for (ConnectionThread connection : connections){
								connection.send("askID");
								if (targetID == (int)objIn.readObject()){
									connection.send("be attacked");
									connection.send(color);
									break;
								}
							}
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
		
		public void refreshPlayerList(){
			// sort
			Collections.sort(playerlist,new CompareScore());
			// broadcast the whole playerlist
			PlayerList PL = new PlayerList(playerlist);				
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
		
		public void send(Object o){
			try {
				objOut.writeObject(o);
				//System.out.println(o);
				objOut.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//deal with answer correct or not
		public void checkAnswer(int imageNumber, float startX, float startY, float endX, float endY){
			/*if answer is correct use connection.objOut.writeObject("Correct");
			 *if answer isn't correct use connection.objOut.writeObject("Wrong"); or send nothing*/
		}
	}
	
	// sort the player list
	class CompareScore implements Comparator{
	    public int compare(Object obj1, Object obj2){
	        Player o1=(Player) obj1;
	        Player o2=(Player) obj2;

	        if(o1.getScore()>o2.getScore()){
	            return -1;
	        }
	        else if(o1.getScore()<o2.getScore()){
	            return 1;
	        }
	        return 0;
	    }
	}
	// main
	public static void main(String[] args) {
		// create server
		Server server = new Server(8000);
		server.runForever();
	}
}
