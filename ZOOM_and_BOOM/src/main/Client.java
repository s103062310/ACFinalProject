package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Client {

	private MainApplet applet;
	private String IPAddress;
	private int portNum;
	private Socket socket;
	private boolean add ;
	private ArrayList<Player> allPlayer = new ArrayList<Player>();
	ObjectInputStream objIn;
	ObjectOutputStream objOut;

	// Constructor
	public Client(String IPAddress, int portNum) {
		// set IP & port
		this.IPAddress = IPAddress;
		this.portNum = portNum;
		this.add = false;
		// create applet
		applet = new MainApplet();
		applet.init();
		applet.start();
		applet.setFocusable(true);
	}

	class ClientThread extends Thread {

		// the program process of client
		public void run() {

			while (true) {
				try {
					Object message = objIn.readObject();
					if (message instanceof PlayerList) {
						allPlayer=((PlayerList)message).getPlayers();
						System.out.println(allPlayer.size()+"##");
					}

				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {

				}
			}
		}
	}

	// connect to server
	public void connect() {
		try {
			socket = new Socket(IPAddress, portNum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());
			//System.out.println("c");
			ClientThread connection = new ClientThread();
			connection.start();
			//System.out.println("add members");
			Player sp = new Player(5,100,"HA");
			//PlayerList pl = new PlayerList(new ArrayList<Player>());
			objOut.writeObject(sp);
			objOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// main
	public static void main(String[] args) {

		// create client
		Client client = new Client("127.0.0.1", 8000);
		// Thread thread = new Thread(client);
		// thread.start();
		client.connect();

		// create frame
		JFrame window = new JFrame("ZOOM and BOOM");
		window.setContentPane(client.applet);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1117, 690);
		window.setVisible(true);
	}

}
