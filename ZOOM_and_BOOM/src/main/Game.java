package main;

import processing.core.PFont;

public class Game {
	
	private MainApplet parent;
	private PFont font;
	
	private boolean isFrame;
	private int height=450, width=800;
	private float startX, startY, frameX, frameY, lineW;
	private int lastTime=0, remainTime=5;
	
	
	// Constructor
	public Game(MainApplet p){
		parent = p;
		isFrame = false;
		lineW = (float)2.5;
		font = parent.createFont("mvboli.ttc", 100, true);
		
	}
	
	// update screen content
	public void display(){
		
		// draw green frame
		if(isFrame){
			parent.stroke(130, 210, 75);
			parent.strokeWeight(lineW*2);
			parent.fill(130, 210, 75, 100);
			parent.rect(startX, startY, frameX, frameY);
		}
		parent.stroke(0);
		parent.strokeWeight(1);
		
		// draw timer
		if(parent.millis()-lastTime>1000){
			remainTime = (remainTime+5)%6;
			lastTime = parent.millis();
		}
		parent.fill(255, 0, 0);
		parent.textFont(font);
		parent.text(remainTime, 100, 100);
	}
	
	// start to frame object => draw green frame
	public void frameStart(){
		isFrame = true;
		startX = parent.mouseX;
		startY = parent.mouseY;
	}
	
	// end to frame object => frame disappear and transmit answer
	public void frameEnd(){
		isFrame = false;
		frameX = 0;
		frameY = 0;
	}
	
	// framing object
	public void frame(){
		if(parent.mouseX>=lineW&&parent.mouseX<width-lineW) frameX = parent.mouseX-startX;
		else if(parent.mouseX<lineW) frameX = lineW-startX;
		else frameX = width-lineW-startX;
		if(parent.mouseY>=lineW&&parent.mouseY<height-lineW) frameY = parent.mouseY-startY;
		else if(parent.mouseY<0) frameY = lineW-startY;
		else frameY = height-lineW-startY;
	}
	
	// to see whether player is framing object
	public boolean isFrame(){
		return isFrame;
	}
	
	// to see whether mouse event is in game region
	public boolean inGame(){
		if(parent.mouseX>=lineW&&parent.mouseX<width-lineW&&parent.mouseY>=lineW&&parent.mouseY<height-lineW)
			return true;
		else return false;
	}
}
