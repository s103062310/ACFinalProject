package main;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.Client.ClientThread;
import object.server.Player;
import object.client.ColorButton;
import object.client.AttackWindow;
import object.client.ConfirmWindow;

public class MainApplet extends PApplet{
	
	private static final long serialVersionUID = 1L;

	// sub window
	private Game game;
	private Market market;
	private Scoreboard scoreboard;
	
	// resources
	private ClientThread thread;
	private Random r = new Random();
	private static AudioPlayer audio;
	
	// player content
	private Player player = new Player();
	private ArrayList<Player> list = new ArrayList<Player>();
	
	
	// initialize
	public void setup(){
		
		// set window size
		setSize(800, 1100);
		
		// create sub window
		game = new Game(this);
		market = new Market(this);
		scoreboard = new Scoreboard(this);
		audio = new AudioPlayer(new File("src/resource/refrigerater2_O.wav"));
		audio.setPlayCount(1);
	}
	
	
	// update screen content
	public void draw(){
		
		game.display();
		market.display();
		scoreboard.display();
		
	}
	
	
	// control mouse pressed
	public void mousePressed(){
		
		if(game.inGame()&&game.isPlay()) game.frameStart();
		
	}
	
	
	// control mouse released
	public void mouseReleased(){
		
		if(game.isFrame()&&game.isPlay()) game.frameEnd(false);
		
	}
	
	
	// control mouse clicked
	public void mouseClicked(){

		// game process control
		if(game.getGameControlButton().inBtn()){
			
			if(game.isPlay()) game.gameEnd();
			else game.gameStart();
			
		}
		
		// buy and use at market
		ColorButton[] btns = market.getButtons();
		for(ColorButton btn : btns){
			audio.play();
			if(btn.inBtn()){
				
				if(player.getScore() >= btn.getMoney()){
					game.pause();
					// create new PApplet
					AttackWindow app = new AttackWindow(this, list, btn, game);
					app.init();
					app.start();
					app.setFocusable(true);
					
					// create new frame
					JFrame window = new JFrame("Attack!!!");
					window.setContentPane(app);
					window.setSize(400, 600);
					window.setVisible(true);
					app.setWindow(window);
						
				} else {
					
					// remind that player doesn't have enough money
					JOptionPane.showMessageDialog(null,"Sorry, your money is not enough!");
				}
			}
		}
		if(market.getRandomBtn().inBtn()){
			if(player.getScore() >= market.getRandomBtn().getMoney()){
				calMoney(-market.getShieldBtn().getMoney());
				//attacked(list.get(r.nextInt(list.size())).getName(), new Color(0, 0, 0).getRGB());
			} else {
				// remind that player doesn't have enough money
				JOptionPane.showMessageDialog(null,"Sorry, your money is not enough!");
			}
		}
		if(market.getShieldBtn().inBtn()){
			if(player.getScore() >= market.getShieldBtn().getMoney()){
				calMoney(-market.getShieldBtn().getMoney());
				player.buyShield();
				beProtected();
			} else {
				// remind that player doesn't have enough money
				JOptionPane.showMessageDialog(null,"Sorry, your money is not enough!");
			}
		}
	}
	
	
	// control mouse moved
	public void mouseMoved(){

		// game process control
		if(game.getGameControlButton().inBtn()) game.getGameControlButton().setOver(true);
		else game.getGameControlButton().setOver(false);
		
		// buy and use at market
		ColorButton[] btns = market.getButtons();
		for(ColorButton btn : btns){
			
			if(btn.inBtn()) btn.setOver(true);
			else btn.setOver(false);
			
		}
		if(market.getRandomBtn().inBtn()) market.getRandomBtn().setOver(true);
		else market.getRandomBtn().setOver(false);
		if(market.getShieldBtn().inBtn()) market.getShieldBtn().setOver(true);
		else market.getShieldBtn().setOver(false);
	}
	
	
	// control mouse dragged
	public void mouseDragged(){
		
		if(game.isFrame()&&game.isPlay()) game.frame();
		
	}
	
	
	// control key pressed
	public void keyPressed(){
		if(keyCode==32){
			attacked(list.get(r.nextInt(list.size())).getName(), new Color(0, 0, 0).getRGB());
		}
	}
	
	
	// attack other players
	public void attacked(String name, int color){
		
		//TODO
		// transmit data to server
		thread.send("attack");
		thread.send(player.getName());
		thread.send(name);
		thread.send(color);
		
		// create new PApplet
		ConfirmWindow confirm = new ConfirmWindow((PImage)thread.receive());
		confirm.init();
		confirm.start();
		confirm.setFocusable(true);
		
		// create new frame
		JFrame window = new JFrame("*~ Attack Sucessful ~*");
		window.setContentPane(confirm);
		window.setSize(800, 450);
		window.setVisible(true);
		
	}
	
	
	// be attacked by other players
	public void beAttacked(int color){
		
		game.addSplash(color);
		thread.send(game.screenshot());
		
	}
	// be protected     //////******
	public void beProtected(){
		
	}
	
	// send object to server
	public void send(Object o){
		thread.send(o);
	}
	
	
	// modify money by amount
	public void calMoney(int amount){
		player.setScore(player.getScore()+amount);
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public void setPlayer(Player p){
		player = p;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public ArrayList<Player> getList(){
		return list;
	}
	 
	public void resetReference(ArrayList<Player> list){
		this.list = list;
	}
	
	public void setClientThread(ClientThread thread){
		this.thread = thread; 
	}
	
	public Game getGame(){
		return this.game;
	}
	
}
