package main;

import processing.core.PApplet;
import processing.core.PImage;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import main.Client.ClientThread;
import object.server.Player;

public class MainApplet extends PApplet{
	
	private static final long serialVersionUID = 1L;

	// sub window
	private Game game;
	private Market market;
	public Scoreboard scoreboard;
	private VScrollbar vs;
	
	// resources
	public ClientThread thread;
	private Random r = new Random();
	
	// player content
	private PImage screenshot = createImage(800, 450, ARGB);
	private  ArrayList<Player> List = new ArrayList<Player>();
	private int id;
	
	
	// initialize
	public void setup(){
		
		// set window size
		setSize(800, 1100);
		
		// create sub window
		game = new Game(this);
		market = new Market(this);
		scoreboard = new Scoreboard(this);
		vs = new VScrollbar(1080, 0, 20, 650, 3*5+1,this);
		
		
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

		if(game.getGameControlButton().inBtn()){
			if(game.isPlay()) game.gameEnd();
			else game.gameStart();
		}
		for(int i=0;i<market.button.length;i++){
			if(market.checkBoundary(i)){
				market.money = market.money-market.button[i].money;
				int dialogButton = 0;
				dialogButton = JOptionPane.showConfirmDialog (null, "Throw?","Confirm", dialogButton);
				if(dialogButton == JOptionPane.YES_OPTION){
					
				}
			}
		}
	}
	
	// control mouse moved
	public void mouseMoved(){

		if(game.getGameControlButton().inBtn()) game.getGameControlButton().setOver(true);
		else game.getGameControlButton().setOver(false);
	}
	
	// control mouse dragged
	public void mouseDragged(){
		if(game.isFrame()&&game.isPlay()) game.frame();
	}
	
	// control key pressed (take a screenshot of main game frame)
	public void keyPressed(){
		if(keyCode==32){
			game.addSplash(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		} else {
			for(int i = 0; i<screenshot.pixels.length; i++) {
				int c = this.get(i%800, i/800);
				screenshot.pixels[i] = c;
			}
			screenshot.save("src/resource/screenshot.png");
		}

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
	
	public void beAttacked(int color){
		game.addSplash(new Color(color));
	}
}
