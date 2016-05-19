package main;

import processing.core.PApplet;
import processing.core.PImage;

public class MainApplet extends PApplet{
	
	private static final long serialVersionUID = 1L;
	private final static int width = 1100, height = 700;
	private Game game;
	private Market market;
	private Scoreboard scoreboard;
	private PImage screenshot;
	
	// initialize
	public void setup(){
		game = new Game(this);
		market = new Market(this);
		scoreboard = new Scoreboard(this);
		screenshot = createImage(800, 450, ARGB);
	}
	
	// update screen content
	public void draw(){
		background(255);
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
		for(int i = 0; i<screenshot.pixels.length; i++) {
			int c = this.get(i%800, i/800);
			screenshot.pixels[i] = c;
		}
		screenshot.save("src/resource/screenshot.png");
	}
}
