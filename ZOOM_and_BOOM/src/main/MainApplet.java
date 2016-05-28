package main;

import processing.core.PApplet;
import processing.core.PImage;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.Client.ClientThread;
import object.server.Player;
import object.client.ColorButton;

public class MainApplet extends PApplet{
	
	private static final long serialVersionUID = 1L;

	// sub window
	private Game game;
	private Market market;
	public Scoreboard scoreboard;
	private VScrollbar vs;
	
	// resources
	private ClientThread thread;
	private Random r = new Random();
	
	// player content
	private PImage screenshot = createImage(800, 450, ARGB);
	private  ArrayList<Player> List = new ArrayList<Player>();
	private int money=100;
	private int id;
	
	
	// initialize
	public void setup(){
		
		// set window size
		setSize(800, 1100);
		
		// create sub window
		game = new Game(this);
		market = new Market(this);
		scoreboard = new Scoreboard(this);
		vs = new VScrollbar(1080, 80, 20, 370, 16,this);
		
		//scoreboard.setPlayerList(List);
		scoreboard.setMyself(id);

	}
	
	
	// update screen content
	public void draw(){
		
		scoreboard.setPlayerList(List);	
		
		// main game and market
		game.display();
		market.display();

		scoreboard.setScroll(vs.spos);
		scoreboard.display();
		vs.update();
		vs.display();
		
	}
	
	
	public void setClientThread(ClientThread thread){
		this.thread = thread; 
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
			
			if(btn.inBtn()){
				
				if(money >= btn.getMoney()){
					
					// create new PApplet
					AttackWindow app = new AttackWindow();
					app.init();
					app.start();
					app.setFocusable(true);
					
					// create new frame
					JFrame window = new JFrame("Attack!!!");
					window.setContentPane(app);
					window.setSize(400, 700);
					window.setVisible(true);
					
					// set attack window
					app.setWindow(window);
					//TODO 把mainapplet傳進去app
					//TODO 把playerlist傳進去app => construct
					
					//if(app.isBuy==true){   ///問助教
						//money = money-btn.getMoney();
					//}
						
				} else {
					
					// remind that player doesn't have enough money
					int dialogButton = 0;
					dialogButton = JOptionPane.showConfirmDialog (null, "Sorry, your money is not enough!","Confirm", dialogButton);
				
				}
				
			}
			
		}
		
	}
	
	
	// control mouse moved
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
		
	}
	
	
	// control mouse dragged
	// control mouse dragged
	public void mouseDragged(){
		
		if(game.isFrame()&&game.isPlay()) game.frame();
		
	}
	
	
	// control key pressed
	public void keyPressed(){
		if(keyCode==32){
			game.addSplash(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)).getRGB());
		} else {
			for(int i = 0; i<screenshot.pixels.length; i++) {
				int c = this.get(i%800, i/800);
				screenshot.pixels[i] = c;
			}
			screenshot.save("src/resource/screenshot.png");
		}

	}
	
	
	// attack other players
	public void attacked(int color){
		//TODO
		// 傳送資料給server -> 收截圖(另跳一個視窗) -> confirm後關掉
	}
	
	
	// be attacked by other players
	public void beAttacked(int color){
		game.addSplash(color);
		//TODO 傳截圖
	}

	
	// if answer is correct
	public void correct(){
		game.answerCorrect();
	}
	
	
	// if answer is correct
	public void wrong(){
		game.answerWrong();
	}
	
	
	// send object to server
	public void send(Object o){
		thread.send(o);
	}
	
	
	// modify money
	public void setMoney(int amount){
		this.money += amount;
	}
	
	// reset the reference of the arraylist List 
	public void resetReference(ArrayList<Player> list){
		this.List=list;
	}
	
	
	public void setSelf(int id){
		this.id=id;
	}
	
	
	public int getID(){
		return id;
	}
	
	
}
