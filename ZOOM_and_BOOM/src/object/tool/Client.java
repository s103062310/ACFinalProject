package main;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import object.client.Splash;
import object.server.Database;
import object.server.Player;
import object.server.WaitWindow;
import object.tool.AudioPlayer;

@SuppressWarnings("serial")
public class Client extends JFrame {

	// information of server
	private String IPAddress;
	private int portNum;

	// connection between server and client
	private Socket socket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;

	// content
	private MainApplet applet;

	// resources
	private static JFrame window;
	private static AudioPlayer audio;

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

		// create Login PApplet
		Login loginApplet = new Login(this);
		loginApplet.init();
		loginApplet.start();
		loginApplet.runFrame();

	}

	// connect to server
	public void connect() {

		try {

			// create server's socket
			socket = new Socket(IPAddress, portNum);

			// bind I/O between server and client
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());
			ClientThread connection = new ClientThread();
			connection.start();
			connection.send(applet.getPlayer());
			applet.setClientThread(connection);

		} catch (IOException e) {

			JOptionPane.showMessageDialog(null, "Server did not exist.\nPlease try again.");
			System.exit(0);
			return;

		}

	}

	// Inner class: connection with server
	class ClientThread extends Thread {

		// the program process of client
		public void run() {

			while (true) {

				// read
				Object message = receive();

				// identify which object is transmit
				if (message instanceof String) {

					// identify which flag is
					switch ((String) message) {

					// send answer is correct
					case "Correct":

						applet.getGame().answerCorrect();
						break;

					// send answer is wrong
					case "Wrong":

						applet.getGame().answerWrong();
						break;

					// being attacked by other players
					case "be attacked":
						System.out.println("be attacked");
						int color = (int) receive();
						applet.beAttacked(color);
						break;

					case "showPic":
						System.out.println("SHOWPIC");
						String picName = (String) receive();
						//Splash splash = (Splash) receive();
						ArrayList<Splash> splash = (ArrayList<Splash>) receive();
						applet.showPic(picName, splash);
						break;

					// server is closing
					case "terminate":

						JOptionPane.showMessageDialog(null, "Server did not respond.\nThe window will be closed.");
						closeClient();
						break;

					default:
						break;

					}

				} else if (message instanceof ArrayList<?>) {
					applet.resetReference((ArrayList<Player>) message);
					System.out.println("update score board");

				}

			}

		}

		// transmit object to server
		public void send(Object o) {

			try {
				objOut.reset();
				objOut.writeObject(o);
				objOut.flush();

			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

		// receive object from server
		public Object receive() {

			Object message = null;

			try {

				message = objIn.readObject();
				System.out.println("receive: " + message);

			} catch (IOException e) {

				JOptionPane.showMessageDialog(null, "Server did not respond.\nThe window will be closed.");
				closeClient();

			} catch (Exception e) {

			}

			return message;
		}

	}

	// set player
	public void setPlayer(Player player) {
		this.applet.setPlayer(player);
	}

	// close client
	public void closeClient() {

		applet.dispose();
		window.dispose();
		Database database = new Database();
		WaitWindow waitWindow = new WaitWindow("UPDATING");
		waitWindow.init();
		waitWindow.start();
		waitWindow.runFrame();
		database.updateUserDatabase(applet.getPlayer());
		waitWindow.closeFrame();
		System.exit(0);

	}

	// main
	public static void main(String[] args) {

		// create client & Run login app
		Client client = new Client("127.00.00.01", 8000);
		client.connect();

		// set sound
		audio = new AudioPlayer(new File("src/resource/bgm.wav"));
		audio.setPlayCount(0);
		audio.play();

		// create frame and connect to server
		window = new JFrame("ZOOM and BOOM");
		window.setContentPane(client.applet);
		window.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(window, "Are you sure you want to close this game?",
						"Really Closing?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					client.closeClient();
				}
			}
		});
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setSize(1117, 690);
		window.setLocation(120, 30);
		window.setVisible(true);

	}

}