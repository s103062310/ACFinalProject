package main;

import java.io.File;
import java.util.Random;
import processing.core.PImage;

public class Game {
	
	private MainApplet parent;
	private PImage img;
	private String path = new String("src/resource/pic_rsc");
	private String[] list;
	private Random r = new Random();
	private boolean isFrame;
	private int height=450, width=800;
	private float startX, startY, frameX, frameY, lineW;
	private int lastTime=0, remainTime=5;
	
	
	// Constructor
	public Game(MainApplet p){
		parent = p;
		isFrame = false;
		lineW = (float)2.5;
		File folder = new File(path);
		list = folder.list();
		img = parent.loadImage(path + "/" + list[0]);
	}
	
	// update screen content
	public void display(){
		// draw image
		parent.image(img, 0, 0, width, height);
		
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
			// change image
			if(remainTime==0)	img = parent.loadImage(path + "/" + list[r.nextInt(3)]);
			remainTime = (remainTime+5)%6;
			lastTime = parent.millis();
		}
		parent.fill(255, 255, 255);
		parent.ellipse(65, 50, 50, 50);
		parent.fill(255, 0, 0);
		parent.textSize(50);
		parent.text(remainTime, 50, 70);
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
