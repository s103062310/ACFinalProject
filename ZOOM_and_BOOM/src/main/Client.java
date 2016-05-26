package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Client extends JFrame{

	private MainApplet applet;
	private String IPAddress;
	private int portNum;
	private Socket socket;
	private ArrayList<Player> allPlayer;
	private Player player;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private Random rand;
	private static JFrame window;
	// debuggggggggggggggggggggggggggggggggggg
	//JLabel label = new JLabel("Waiting...");
	//public int people=0;
	

	// Constructor
	public Client(String IPAddress, int portNum) {
		// set IP & port
		this.rand = new Random();
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

	class ClientThread extends Thread {
		
		// the program process of client
		@SuppressWarnings("deprecation")
		public void run() {
			
			while (true) {
				try {	
					Object message = objIn.readObject();
					if (message instanceof String) {
						switch ((String) message) {
						// start to receive the sorted player list
						case "CLEAR":
							allPlayer = new ArrayList<Player>();
							break;
						// all received
						case "COMPLETE":
							applet.resetReference(allPlayer);
							break;
						case "Correct":
							break;
						case "Wrong":
							break;
						case "askID":
							send(applet.getID());
						case "be attacked":
							int color = (int)objIn.readObject();
							applet.beAttacked(color);
						default:
							break;
						}
						/*replace with switch case
						if(((String) message).equals("CLEAR")){
							allPlayer = new ArrayList<Player>();
						}
						// all received
						else if(((String) message).equals("COMPLETE")){
							applet.resetReference(allPlayer);
						}*/
					}
					else if (message instanceof Player) {
						player = (Player) message;
						allPlayer.add(player);
					}
					// set ID
					else if(message instanceof Integer){
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
					//e.printStackTrace();
					JOptionPane.showMessageDialog(null,"Server did not respond.\nThe window will be closed.");
					window.dispose();
					applet.dispose();
					stop();
				} catch (Exception e) {

				}
			}
		}
		
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

	// connect to server
	public void connect() {
		try {
			socket = new Socket(IPAddress, portNum);
		} catch (IOException e) {
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Server did not exist.\nPlease try again.");
			window.dispose();
			applet.dispose();
			return;
		}
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());
			ClientThread connection = new ClientThread();
			connection.start();
			applet.setClientThread(connection);
		
			int color = rand.nextInt(5);
			// int color , int score , String name // tmppppppppppppppppppppppppppp
			Player pl = new Player(color, color*10,"*"+Integer.toString(color)+"*");
			// send the new add player's information to server
			objOut.writeObject(pl);
			objOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// main
	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 8000);
		// create frame
		window = new JFrame("ZOOM and BOOM");
		window.setContentPane(client.applet);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1117, 690);
		window.setVisible(true);
		client.connect();
	}
	
}
