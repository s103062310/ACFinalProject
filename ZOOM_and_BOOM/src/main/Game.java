package main;

import java.io.File;
import java.util.Random;
import java.util.ArrayList;
import processing.core.PImage;
import processing.core.PFont;
import object.client.Splash;
import object.client.Wallet;
import object.client.ImageMessage;
import object.tool.DigitalTimer;
import object.tool.Button;
import object.tool.AudioPlayer;

public class Game {
	
	// content
	private PImage img;
	private PImage[] rest;
	private PFont font;
	private Wallet wallet;
	private Button ctrlBtn;
	private DigitalTimer imageTimer;
	private ArrayList<Splash> splash;
	private ArrayList<ImageMessage> message;
	
	// variable & constant
	private boolean isFrame=false, isPlay=false, end=false;
	private int height=450, width=800, imageNumber, changeRest;
	private float startX, startY, frameX, frameY, lineW=(float)2.5;
	
	// resources
	private MainApplet parent;
	private String[] list;
	private Random r = new Random();
	private static AudioPlayer audio;
	
	
	// Constructor
	public Game(MainApplet p){
		
		parent = p;
		
		// open file and load all file name
		File folder = new File("src/resource/pic_rsc");
		list = folder.list();
		
		// load image
		rest = new PImage[4];
		rest[0] = parent.loadImage("src/resource/other_images/coffee4.png");
		rest[1] = parent.loadImage("src/resource/other_images/coffee3.png");
		rest[2] = parent.loadImage("src/resource/other_images/coffee2.png");
		rest[3] = parent.loadImage("src/resource/other_images/coffee1.png");
		
		// set control button
		ctrlBtn = new Button(parent, 735, 15, 50, 50, 0);
		ctrlBtn.addImage("src/resource/other_images/pause.png");
		ctrlBtn.addImage("src/resource/other_images/play.png");
		ctrlBtn.setImage(1);
		
		// tools
		wallet = new Wallet(parent, 700, 380, 90, 60);
		imageTimer = new DigitalTimer(parent, this, 5, 5, 5);
		
		// set splash list
		splash = new ArrayList<Splash>();
		message = new ArrayList<ImageMessage>();
		
		// font and audio
		font = parent.createFont("src/resource/fonts/JOKERMAN.ttf", 70);
		audio = new AudioPlayer(new File("src/resource/crrect_answer2.wav"));
		audio.setPlayCount(1);
		
	}

	
	// update screen content
	public void display(){
		
		if(isPlay){
			
			// draw image
			parent.image(img, 0, 0, width, height);
		
			// draw splash
			for(int i=0; i<splash.size(); i++){
				if(splash.get(i).getTrans()==0) splash.remove(i);
				else break;
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
			
			// background
			parent.background(255, 255, 150);
			
			// animate
			changeRest++;
			if(changeRest<=10) parent.image(rest[0], 360, 200, 100, 100);
			else if(changeRest<=20) parent.image(rest[1], 360, 200, 100, 100);
			else if(changeRest<=30) parent.image(rest[2], 360, 200, 100, 100);
			else if(changeRest<=40) parent.image(rest[3], 360, 200, 100, 100);
			else {
				changeRest=0;
				parent.image(rest[0], 360, 200, 100, 100);
			}
			
			// title
			parent.fill(255, 80, 0);
			parent.textFont(font);
			parent.text("ZOOM & BOOM", 150, 150);
			
		}
		
		// draw tools
		ctrlBtn.display_image();
		imageTimer.display();
		
		// draw answer message
		for(int i=0; i<message.size(); i++){
			message.get(i).display();
		}
		if(message.size()>0){
			ImageMessage m = message.get(0);
			if(m.isCorrect()&&wallet.in(m.getX()+m.getD(), m.getY()+m.getD())){
				if(!wallet.isOver()){
					wallet.setOver(true);
					wallet.add(10);
				}
			}
			if(m.getD()==0){
				wallet.setOver(false);
				message.remove(m);
			}
		}
		
		// game end check
		if(end) {
			parent.fill(0, 0, 0, 100);
			parent.rect(0, 0, 800, 450);
		}
		wallet.display();
		
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
		
		//audio.loadAudio("src/resource/crrect_answer2.wav", null); 
		audio.stop();
		audio.play();
		
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
		imageNumber = r.nextInt(list.length);
		img = parent.loadImage("src/resource/pic_rsc/" + list[imageNumber]);
		
	}

	
	// check money of this time's game
	public void checkMoney(){
		
		imageTimer.pause();
		wallet.setMove();
		end = true;
		
	}
	
	
	// close the game process
	public void gameEnd(){
		
		isPlay = false;
		end = false;
		parent.calMoney(wallet.getMoney());
		wallet.reset();
		imageTimer.reset();
		ctrlBtn.setImage(1);
		
	}
	
	
	// save money temporarily and show animate
	public void answerCorrect(){
	
		parent.getPlayer().Completed();
		message.add(new ImageMessage(parent, "correct.png", 250, 75, 300));
		
	}
	
	
	// show animate
	public void answerWrong(){
		
		message.add(new ImageMessage(parent, "wrong.png", 250, 75, 300));
		
	}

	
	// add new splash
	public void addSplash(int color){
		
		splash.add(new Splash(parent, r.nextInt(800)-210, r.nextInt(450)-170, 10, color));
	
	}

	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public boolean isFrame(){
		return this.isFrame;
	}

	public boolean isPlay(){
		return this.isPlay;
	}
	
	public Wallet getWallet(){
		return this.wallet;
	}
	
	public Button getGameControlButton(){
		return this.ctrlBtn;
	}
	
	public String getPicName(){
		return this.list[imageNumber];
	}
	public ArrayList<Splash> getSplashList(){
		return this.splash;
	}

	
}
