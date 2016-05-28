package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import object.server.Player;

@SuppressWarnings("serial")
public class Client extends JFrame{

	// information of server
	private String IPAddress;
	private int portNum;
	
	// connection between server and client
	private Socket socket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	
	// content
	private MainApplet applet;
	private Player player;
	private ArrayList<Player> allPlayer;
	
	// resources
	private Random rand = new Random();
	private static JFrame window;
	// debuggggggggggggggggggggggggggggggggggg
	//JLabel label = new JLabel("Waiting...");
	//public int people=0;
	

	// Constructor
	public Client(String IPAddress, int portNum) {
		
		// set IP & port
		this.IPAddress = IPAddress;
		this.portNum = portNum;
		
		// create applet
		applet = new MainApplet();
		applet.init();
		applet.start();
		applet.setFocusable(true);
		/* debugggggggggggggggggggggggggggggggggggggggggggg
		label.setBounds(10,10,60, 80);
		this.add(label);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(200, 100);
		this.setVisible(true);*/
		
	}

	
	// connect to server
	public void connect() {
		
		// create server's socket
		try {
			socket = new Socket(IPAddress, portNum);
		} catch (IOException e) {
			System.out.println("Fail to connect with server!");
			JOptionPane.showMessageDialog(null,"Server did not exist.\nPlease try again.");
			window.dispose();
			applet.dispose();
			return;
		}
		
		// bind I/O between server and client
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());
			ClientThread connection = new ClientThread();
			connection.start();
			applet.setClientThread(connection);
		
			//TODO 等有login系統後，可能要搬到server分配給client，以下就不用傳了~
			// server 創造一個player給client，每次登入都傳
			int color = rand.nextInt(5);
			// int color , int score , String name // tmppppppppppppppppppppppppppp
			player = new Player(color, color*10,"*"+Integer.toString(color)+"*");
			
			// send the new add player's information to server
			objOut.writeObject(player);
			objOut.flush();
		} catch (IOException e) {
			//TODO showmessagedialog
			System.out.println("Fail to establish I/O channel with server!");
			System.exit(0);
		}
		
	}
		
	
	// Inner class: connection with server
	// Inner class: connection with server
	class ClientThread extends Thread {
		
		// the program process of client
		@SuppressWarnings("deprecation")
		public void run() {
			
			while (true) {
				
				try {
					
					// read
					Object message = objIn.readObject();
					
					// identify which object is transmit
					if (message instanceof String) {
						
						// when receive String flag
						
						// identify which flag is
						switch ((String) message) {
						
						// start to receive the sorted player list
						case "CLEAR":
							
							allPlayer = new ArrayList<Player>();
							break;
							
						// all received
						case "COMPLETE":
							
							applet.resetReference(allPlayer);
							break;
						
						// send answer is correct
						case "Correct":
							
							//TODO add money and display message
							break;
							
						// send answer is wrong
						case "Wrong":
							
							//TODO display message
							break;
							
						// request of transmitting ID
						case "askID":
							
							send(applet.getID());
							break;
							
						// being attacked by other players
						case "be attacked":
							
							int color = (int)objIn.readObject();
							applet.beAttacked(color);
							//TODO transmit screenshot
							break;
							
						default:
							break;
							
						}
						
					} else if (message instanceof Player) {
						
						//TODO try transmit list directly
						// get score board data
						
						player = (Player) message;
						allPlayer.add(player);
						
					} else if(message instanceof Integer){
						
						applet.setSelf(((Integer) message).intValue());
					
					}
					/* bug GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
					else if (message instanceof PlayerList) {
						//allPlayer = ((PlayerList) message).players;
						//label.setText("");
						//System.out.println(allPlayer.size() + "##");
						//label.setText(Integer.toString(people++));
						//label.setText(Integer.toString(allPlayer.size()));
						//applet.scoreboard.setPlayerList(allPlayer);
						//System.out.println(allPlayer.size() + "##");
					}*/
				} catch (IOException e) {
					System.out.println("Server doesn't exist any more.");
					JOptionPane.showMessageDialog(null,"Server did not respond.\nThe window will be closed.");
					window.dispose();
					applet.dispose();
					stop();
				} catch (Exception e) {

				}
				
			}
			
		}

		
		// transmit object to server
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

		
	// main
	public static void main(String[] args) {
		
		// create client
		Client client = new Client("140.114.86.229", 8000);
		
		// create frame and connect to server
		window = new JFrame("ZOOM and BOOM");
		window.setContentPane(client.applet);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1117, 690);
		window.setVisible(true);
		client.connect();
	
	}
	
}
