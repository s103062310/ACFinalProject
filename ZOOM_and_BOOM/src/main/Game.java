package main;

import java.io.File;
import java.util.Random;
import java.util.ArrayList;
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
	private ArrayList<Splash> splash, remove;
	private boolean isFrame=false, isPlay=false;
	private int height=450, width=800, lastTime=0;
	private float startX, startY, frameX, frameY, lineW=(float)2.5;
	
	
	// Constructor
	public Game(MainApplet p){
		parent = p;
		
		// open file and load all file name
		File folder = new File(path);
		list = folder.list();
		
		// load image
		img = parent.loadImage(path + "/" + list[r.nextInt(list.length)]);
		
		// set tool bar
		Color c = new Color(130, 180, 150);
		ctrlBtn = new Button(parent, 760, 40, 50, c);
		timer = new Timer(p, 690, 40, 50, 5);
		
		// set splash list
		splash = new ArrayList<Splash>();
		remove = new ArrayList<Splash>();
	}
	
	// update screen content
	public void display(){
		if(isPlay){
			// draw image
			parent.image(img, 0, 0, width, height);
			
			// draw splash
			for(Splash s : splash){
				s.display();
				if(s.getTrans()==0) remove.add(s);
			}
			for(Splash s: remove){
				splash.remove(s);
			}
			remove.clear();
			
			// draw green frame
			if(isFrame){
				parent.stroke(130, 210, 75);
				parent.strokeWeight(lineW*2);
				parent.fill(130, 210, 75, 100);
				parent.rect(startX, startY, frameX, frameY);
			}
			parent.noStroke();
			
			// time related: timer, change image
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
		timer.reset();
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
	
	// add new splash
	public void addSplash(){
		splash.add(new Splash(parent, r.nextInt(695), r.nextInt(365), 10, new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255))));
	}

}
