package main;

import java.io.File;
import java.util.Random;
import java.awt.Color;
import processing.core.PImage;

public class Game {
	
	private MainApplet parent;
	private PImage img;
	private String path = new String("src/resource/pic_rsc");
	private String[] list;
	private Random r = new Random();
	private Button ctrlBtn;
	private Timer timer;
	private boolean isFrame=false, isPlay=false;
	private int height=450, width=800, lastTime=0;
	private float startX, startY, frameX, frameY, lineW=(float)2.5;
	
	
	// Constructor
	public Game(MainApplet p){
		parent = p;
		File folder = new File(path);
		list = folder.list();
		img = parent.loadImage(path + "/" + list[r.nextInt(list.length)]);
		Color c = new Color(130, 180, 150);
		ctrlBtn = new Button(parent, 760, 40, 50, c);
		timer = new Timer(p, 690, 40, 50, 5);
	}
	
	// update screen content
	public void display(){
		if(isPlay){
			// draw image
			parent.image(img, 0, 0, width, height);
			
			// draw green frame
			if(isFrame){
				parent.stroke(130, 210, 75);
				parent.strokeWeight(lineW*2);
				parent.fill(130, 210, 75, 100);
				parent.rect(startX, startY, frameX, frameY);
			}
			parent.noStroke();
			
			// timer work and change image
			if(parent.millis()-lastTime>1000){
				if(timer.getValue()==0){
					frameEnd();
				} else {
					timer.work();
					lastTime = parent.millis();
				}
			}
		} else {
			parent.background(200, 200, 0);
		}
		
		// draw tool bar
		parent.fill(125, 125, 125, 150);
		parent.rect(650, 0, 150, 80);
		timer.display();
		ctrlBtn.display();
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
		img = parent.loadImage(path + "/" + list[r.nextInt(list.length)]);
		timer.reset();
		lastTime = parent.millis();
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
	
	// start to play game
	public void gameStart(){
		isPlay = true;
		lastTime = parent.millis();
	}
	
	// close the game process
	public void gameEnd(){
		isPlay = false;
	}
	
	// to see whether game is in play stage
	public boolean isPlay(){
		return isPlay;
	}
	
	// pass play/pause button to Papplet
	public Button getGameControlButton(){
		return ctrlBtn;
	}


}
