package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.sun.javafx.tk.Toolkit;
import com.sun.prism.Image;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
*	Login Window
*	Uses user_database.json to compare user input with user records
*	Upon successful login, window changes, press play to send player info to server
*	Upon failure at login, window changes, button retry goes back to main login window
*/

public class Login extends PApplet{
	
		//load database
		private String path = "resource/user_database/";
		private String file = "user_database.json";
		private String usernameInput;
		private String passInput;
		JSONObject data;
		JSONArray userNodes;
		
		//GUI		
		private enum loginState {
		    LOGINATTEMPT,LOGINSUCCESS,LOGINFAILURE
		}
		private PImage backgroundImg;
		private PImage aimCursor;
		private loginState state;
		private final int width = 600;
		private final int height = 350;
		private SplashButton loginBtn;
		private SplashButton backBtn;
		private SplashButton retryLoginBtn;
		private SplashButton playBtn;
		private Textbox nameBox;
		private SecretTextbox passBox;

	
	public void setup() {
		state = loginState.LOGINATTEMPT;
		//GUI
		size(width, height);
		smooth();
		//Buttons and TextBoxes
		loginBtn = new SplashButton(this, 110, 200, Color.RED);
		backBtn = new SplashButton(this, 310, 200, Color.RED);
		playBtn = new SplashButton(this, 210, 200, Color.RED);
		retryLoginBtn = new SplashButton(this, 210, 200, Color.RED);
		nameBox = new Textbox(this, 160, 75, Color.CYAN, "Enter Username");
		passBox = new SecretTextbox(this, 160, 150, Color.GREEN, "Enter Password");
		//loadImages
		try { 
			aimCursor = loadImage("resource/aimCursor.png");

			backgroundImg = loadImage("resource/paintBackground.png");
		}
		catch(Exception ex){
			System.err.println("Unable to laod cursor or Backgroun Images");
			ex.printStackTrace();
		}
	}
	
	public void draw(){
		background(255);
		tint(255, 130);
		image(backgroundImg,0,0);
		tint(255, 255);
		fill(255, 255, 255, 240);
		rect(145, 35, 300, 140, 60);
		fill(126,126,126);
		//Main login window
		if(state == loginState.LOGINATTEMPT){
			//Buttons
			loginBtn.display();
			backBtn.display();
			fill(255);
			textSize(28);
			text("Login",155,275);
			text("Back",365,275);
			//Textfields
			nameBox.display();
			passBox.display();
			fill(122,122,122);
			
			//Cursor
			 if (nameBox.checkLimits() || passBox.checkLimits()) {
				 cursor(TEXT);
			 } 
			 else if(loginBtn.checkLimits() || backBtn.checkLimits()){
				 cursor(HAND);
			 }
			 else
				 cursor(aimCursor,16,16);
		}
		//Succesful login
		else if (state == loginState.LOGINSUCCESS){
			textSize(36);
			fill(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue());
			text("Login Success!",170,80);
			textSize(28);
			fill(Color.CYAN.getRed(),Color.CYAN.getGreen(),Color.CYAN.getBlue());
			text("Press Play to",210,120);
			text("start playing",210,150);
			playBtn.display();
			fill(255);
			text("Play",265,275);
			//Cursor
			if(playBtn.checkLimits())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		}
		//Failure at login
		else if (state == loginState.LOGINFAILURE){
			fill(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue());
			textSize(36);
			text("Login Failure!",175,80);
			textSize(28);
			fill(Color.CYAN.getRed(),Color.CYAN.getGreen(),Color.CYAN.getBlue());
			text("User and password",170,120);
			text("don't match",210,150);
			retryLoginBtn.display();
			fill(255);
			text("Retry",260,275);
			//Cursor
			if(retryLoginBtn.checkLimits())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		}
	}
	public void keyPressed() {
		
	  Textbox selectedBox = null;
	  Boolean boxSet = false;
	  
	  if (nameBox.state == 1) {
		  selectedBox = nameBox;
		  boxSet = true;
	  }
	  
	  else if (passBox.state == 1){
		  selectedBox = passBox;
		  boxSet = true;
	  }
		
	  if (boxSet) {
	    if (key>='a'&&key<='z' ||key>='0'&&key<='9') {
	    	selectedBox.text+=key;
	    }
	    else if (key==ENTER || key==RETURN || key==TAB) {
	  		 selectedBox.state = 0;
	    	 if (selectedBox.equals(nameBox)){
	    		 passBox.state = 1;
	    		 passBox.firstClick = true;
	    	 }
	    	 else{
	    		 nameBox.state = 1;
	    		 nameBox.firstClick = true;
	    	 }
	    }
	    else if (key==DELETE || key==BACKSPACE){
	    	if(selectedBox.text.length()>0)
	    		selectedBox.text=selectedBox.text.substring(0,selectedBox.text.length()-1);
	    }
	  }
	}
		 
	public void mousePressed(){
		//main login window
		if ( state == loginState.LOGINATTEMPT){
			if (nameBox.checkLimits()){
				nameBox.state = 1;
				passBox.state = 0;
			}
			else if(passBox.checkLimits()){
				passBox.state = 1;
				nameBox.state = 0;
			 }
			else if(loginBtn.checkLimits()){
				checkDatabase();
			}
			else if(backBtn.checkLimits()){
				//define behaviour to go back
			}
		}
		//main login window
		else if ( state == loginState.LOGINFAILURE ){
			if (retryLoginBtn.checkLimits()){
				state = loginState.LOGINATTEMPT;
			}
		}
		//main login window
		else if ( state == loginState.LOGINSUCCESS ){
			if (playBtn.checkLimits()){
				this.destroy();
				this.exit();
				//create player
				//and send player data to client
			}
		}
	}
	
	private void checkDatabase(){
		
		boolean valueSet =false;
		data = loadJSONObject(path+file);
		userNodes = data.getJSONArray("playerNodes");

		for(int i=0; i<userNodes.size(); i++){
			JSONObject node = userNodes.getJSONObject(i);
			if(node.getString("username").equals(nameBox.text) && node.getString("password").equals(passBox.text)){
				valueSet =  true;
				state = loginState.LOGINSUCCESS;
				break;
			}
		}
		
		if (!valueSet){
			state = loginState.LOGINFAILURE;
		}
	}
}
