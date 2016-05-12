package main;

import processing.core.PApplet;

public class MainApplet extends PApplet{
	
	private static final long serialVersionUID = 1L;
	private final static int width = 1100, height = 700;
	private Game game;
	private Market market;
	private Scoreboard scoreboard;
	
	// initialize
	public void setup(){
		game = new Game(this);
		market = new Market(this);
		scoreboard = new Scoreboard(this);
	}
	
	// update screen content
	public void draw(){
		background(255);
		game.display();
		market.display();
		scoreboard.display();
	}
}
