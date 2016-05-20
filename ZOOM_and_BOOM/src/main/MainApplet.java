package main;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class MainApplet extends PApplet{
	
	private static final long serialVersionUID = 1L;

	private Game game;
	private Market market;
	public Scoreboard scoreboard;
	private PImage screenshot;
	private  ArrayList<Player> List;
	private int id;
	private VScrollbar vs;
	
	// initialize
	public void setup(){
		setSize(800, 1100);
		game = new Game(this);
		market = new Market(this);
		scoreboard = new Scoreboard(this);
		//scoreboard.setPlayerList(List);
		scoreboard.setMyself(id);
		screenshot = createImage(800, 450, ARGB);
		vs = new VScrollbar(1080, 0, 20, 650, 3*5+1,this);

	}
	// reset the reference of the arraylist List 
	public void resetReference(ArrayList<Player> list){
		this.List=list;
	}
	public void setSelf(int id){
		this.id=id;
	}
	
	// update screen content
	public void draw(){
		scoreboard.setPlayerList(List);	
		background(255);
		game.display();
		market.display();

		scoreboard.setScroll(vs.spos);
		scoreboard.display();
		vs.update();
		vs.display();

	}
	
	// control mouse pressed
	public void mousePressed(){
		if(game.inGame()&&game.isPlay()) game.frameStart();
	}
	
	// control mouse released
	public void mouseReleased(){
		if(game.isFrame()&&game.isPlay()) game.frameEnd();
	}
	
	// control mouse clicked
	public void mouseClicked(){

		if(game.getGameControlButton().inBtn()){
			if(game.isPlay()) game.gameEnd();
			else game.gameStart();
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
			game.addSplash();
		} else {
			for(int i = 0; i<screenshot.pixels.length; i++) {
				int c = this.get(i%800, i/800);
				screenshot.pixels[i] = c;
			}
			screenshot.save("src/resource/screenshot.png");
		}

	}
}
