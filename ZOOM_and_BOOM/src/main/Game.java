package main;

import java.io.File;
import java.util.Random;
import java.util.ArrayList;
import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PFont;
import object.client.Splash;
import object.tool.DigitalTimer;
import object.tool.Button;

//TODO start animate

public class Game {
	
	// content
	private PImage img;
	private PFont gameFont;
	private Button ctrlBtn;
	private ArrayList<Splash> splash;
	private int accumulateMoney=0;
	
	// variable & constant
	private boolean isFrame=false, isPlay=false;
	private int height=450, width=800, imageNumber;
	private float startX, startY, frameX, frameY, lineW=(float)2.5;
	
	// resources
	private MainApplet parent;
	private String[] list;
	private Random r = new Random();
	
	// timer related resources
	private DigitalTimer imageTimer;
	private Thread timerThread;
	
	
	// Constructor
	public Game(MainApplet p){
		
		parent = p;
		
		// open file and load all file name
		File folder = new File("src/resource/pic_rsc");
		list = folder.list();
		
		// load image
		imageNumber = r.nextInt(list.length);
		img = parent.loadImage("src/resource/pic_rsc/" + list[imageNumber]);
		
		// set tool bar
		ctrlBtn = new Button(parent, 735, 15, 50, 50, 0);
		ctrlBtn.addImage("src/resource/other_images/pause.png");
		ctrlBtn.addImage("src/resource/other_images/play.png");
		ctrlBtn.setImage(1);
		
		// set splash list
		splash = new ArrayList<Splash>();
		
		//font
		gameFont = parent.createFont("resource/fonts/HappyGiraffe.ttf",32);

		//Timer and Timer Control Thread
		imageTimer = new DigitalTimer(parent,this,5,5,5);

	}

	
	// update screen content
	public void display(){
		
		parent.textFont(gameFont);
		if(isPlay){
			// draw image
			parent.image(img, 0, 0, width, height);
		
			// draw splash
			for(int i=0; i<splash.size(); i++){
				if(splash.get(i).getTrans()==0) splash.remove(i);
			}
			for(int i=0; i<splash.size(); i++){
				splash.get(i).display();
			}
			
			// draw green frame
			if(isFrame){
				parent.stroke(130, 210, 75);
				parent.strokeWeight(lineW*2);
				parent.fill(130, 210, 75, 100);
				parent.rect(startX, startY, frameX, frameY);
			}
			parent.noStroke();
	
		} else {
			parent.background(200, 200, 0);
		}
		
		// draw tool bar
		ctrlBtn.display_image();
		imageTimer.display();
		parent.textFont(gameFont);
		
	}
	
	
	// start to frame object => draw green frame
	public void frameStart(){
		
		isFrame = true;
		startX = parent.mouseX;
		startY = parent.mouseY;
		
	}

	
	// end to frame object => frame disappear and transmit answer
	public void frameEnd(boolean timeout){
		
		// prevent regarding clicking as framing
		isFrame = false;
		if(startX==parent.mouseX&&startY==parent.mouseY) return;
		
		// transmit answer
		if(!timeout) sendFrame();
		
		// reset variable
		frameX = 0;
		frameY = 0;
		
		// change image
		int temp = imageNumber;
		while (imageNumber==temp) imageNumber = r.nextInt(list.length);
		img = parent.loadImage("src/resource/pic_rsc/" + list[imageNumber]);
		imageTimer.reset();
		
	}

	
	// send framing answer to server
	private void sendFrame(){
		
		parent.send("frameEnd");
		parent.send(list[imageNumber]);
		parent.send(startX);
		parent.send(startY);
		parent.send(frameX);
		parent.send(frameY);
		
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

	
	// to see whether mouse event is in game region
	public boolean inGame(){
		
		if(parent.mouseX>=lineW&&parent.mouseX<width-lineW&&parent.mouseY>=lineW&&parent.mouseY<height-lineW)
			return true;
		else return false;
		
	}

	
	// start to play game
	public void gameStart(){
		
		isPlay = true;
		imageTimer.resume();
		ctrlBtn.setImage(0);
		
	}

	
	// close the game process
	public void gameEnd(){
		
		isPlay = false;
		imageTimer.pause();
		//TODO confirm earned money
		parent.calMoney(accumulateMoney);
		accumulateMoney = 0;
		ctrlBtn.setImage(1);
	}
	
	
	// save money temporarily and show animate
	public void answerCorrect(){
		
		//TODO
		parent.getPlayer().Completed();
		accumulateMoney += 10;
		
	}
	
	
	// show animate
	public void answerWrong(){
		
		//TODO
		
	}

	
	// add new splash
	public void addSplash(int color){
		
		splash.add(new Splash(parent, r.nextInt(800)-210, r.nextInt(450)-170, 10, color));
	
	}
	
	
	// take screenshot of main game
	public PImage screenshot(){
		
		// create image
		PImage screenshot  = parent.createImage(800, 450, PApplet.ARGB);
		
		// map pixel of sub window to image
		for(int i = 0; i<screenshot.pixels.length; i++) {
			int c = parent.get(i%800, i/800);
			screenshot.pixels[i] = c;
		}
		
		//Image img = new Image(screenshot);
		
		return screenshot;
	
	}

	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public boolean isFrame(){
		return isFrame;
	}

	public boolean isPlay(){
		return isPlay;
	}
	
	public void play(){
		this.isPlay=true;
		this.imageTimer.resume();
		ctrlBtn.setImage(0);
	}
	
	public void pause(){
		this.isPlay=false;
		this.imageTimer.pause();
		ctrlBtn.setImage(1);
	}
	
	public Button getGameControlButton(){
		return ctrlBtn;
	}

	
}
