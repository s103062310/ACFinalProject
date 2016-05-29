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
	private Login loginApplet;
	
	// resources
	private Random rand = new Random();
	private static JFrame window;
	
	//Player
	private Player player;

	// Constructor
	public Client(String IPAddress, int portNum) {
		
		// set IP & port
		this.IPAddress = IPAddress;
		this.portNum = portNum;
		
		// create PApplet
		applet = new MainApplet();
		applet.init();
		applet.start();
		applet.setFocusable(true);
		
		// create Login Applet
		loginApplet = new Login(this);
		loginApplet.init();
		loginApplet.start();

		player = null;
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
			if(player==null)
				applet.setPlayer(new Player(color, color*10,"*"+Integer.toString(color)+"*"));
			else
				applet.setPlayer(player);
			// send the new add player's information to server
			objOut.writeObject(applet.getPlayer());
			objOut.flush();
			
		} catch (IOException e) {
			
			//TODO showmessagedialog
			System.out.println("Fail to establish I/O channel with server!");
			System.exit(0);
			
		}
		
	}
		
	
	// Inner class: connection with server
	class ClientThread extends Thread {
		
		// the program process of client
		@SuppressWarnings("deprecation")
		public void run() {
			
			while (true) {
				
				try {
					
					// read
					Object message = objIn.readObject();
					System.out.println("receive: "+message);
					
					// identify which object is transmit
					if (message instanceof String) {
						
						// when receive String flag
						
						// identify which flag is
						switch ((String) message) {
						
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
							
							//send(applet.getID());
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
						
					} else if(message instanceof Integer){
						
						applet.getPlayer().setID(((Integer) message).intValue());
						System.out.println("set ID");
					
					} else if (message instanceof ArrayList<?>) {
						
						applet.resetReference((ArrayList<Player>) message);
						
					}
					
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
	
	//set player
	public void setPlayer(Player player){
		this.player = player;
	}

		
	// main
	public static void main(String[] args) {
		
		// create client & connect
		Client client = new Client("127.0.0.1", 8000);
		client.connect();
		
		//Run login app
		client.loginApplet.runFrame();
		
		// create frame and connect to server
		window = new JFrame("ZOOM and BOOM");
		window.setContentPane(client.applet);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1117, 690);
		window.setFocusable(true);
		window.setVisible(true);
		

	}
	
}
