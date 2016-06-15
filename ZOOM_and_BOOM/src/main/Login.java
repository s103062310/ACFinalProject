package main;

import java.awt.Color;
import javax.swing.JFrame;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import object.server.Database;
import object.server.WaitWindow;
import object.tool.SplashButton;
import object.tool.Textbox;
import object.tool.SecretTextbox;

/**
*	Login & Register Window
*	Function runFrame() creates a JFrame and returns control to callor until 
*	a successful login
*	Uses MySql online database to make queries
*	Upon successful login, window changes, press play to send player info to server
*	Upon failure at login, window changes, button retry goes back to main login window
*/

public class Login extends PApplet{
	
	private static final long serialVersionUID = 1L;
	
	//GUI		
	public static enum loginState {
	    WAIT,LOGINATTEMPT,LOGINSUCCESS,LOGINPASSFAIL,LOGINUSERFAIL,LOGININFO,
	    REGISTER,REGISTERSUCCESS,REGISTERWRONGPASS,REGISTERDUPLICATED,REGISTERINFO
	}
	private loginState state;
	private static final int width = 600;
	private static final int height = 350;
	private PImage backgroundImg;
	private PImage aimCursor;
	private PFont loginFont;
	private SplashButton loginBtn;
	private SplashButton registerBtn;
	private SplashButton confirmBtn;
	private SplashButton backBtn;
	private SplashButton retryLoginBtn;
	private SplashButton playBtn;
	private Textbox nameBox;
	private SecretTextbox passBox;
	private Textbox newNameBox;
	private SecretTextbox newPassBox;
	private SecretTextbox confirmPassBox;
	
	//Client
	private Client client;
	private boolean loginSucc;
	
	
	//Constructor
	public Login(Client client){
		this.client = client;
		this.loginSucc = false;
	}
	
	
	//Processing Setup
	public void setup() {
		
		state = loginState.LOGINATTEMPT;
		
		//GUI
		size(width, height);
		smooth();
		noStroke();
		loginFont = createFont("resource/fonts/HappyGiraffe.ttf",52);
		textFont(loginFont);
		
		//Buttons
		loginBtn = new SplashButton(this, 110, 200, Color.RED, "Login");
		registerBtn = new SplashButton(this, 310, 200, Color.RED, "New");
		confirmBtn = new SplashButton(this, 110, 200, Color.RED, "Create");
		backBtn = new SplashButton(this, 310, 200, Color.RED, "Back");
		playBtn = new SplashButton(this, 210, 200, Color.RED, "Play");
		retryLoginBtn = new SplashButton(this, 210, 200, Color.RED, "Retry");
		
		//TextBoxes
		nameBox = new Textbox(this, 160, 75, Color.CYAN, "Enter Username");
		passBox = new SecretTextbox(this, 160, 150, Color.GREEN, "Enter Password");
		newNameBox = new Textbox(this, 160, 50, Color.CYAN, "Enter Username");
		newPassBox = new SecretTextbox(this, 160, 110, Color.GREEN, "Enter Password");
		confirmPassBox = new SecretTextbox(this, 160, 170, Color.GREEN, "Check Password");
		
		//loadImages
		try { 
			aimCursor = loadImage("resource/other_images/aimCursor.png");
			backgroundImg = loadImage("resource/other_images/paintBackground.png");
		}
		catch(Exception ex){
			System.err.println("Unable to laod cursor or Backgroun Images");
			ex.printStackTrace();
		}
		
	}
	
	
	//Processing Draw
	public void draw(){

		frameRate(60);
		background(255);
		tint(255, 130);
		image(backgroundImg,0,0);
		tint(255, 255);
		
		//Main login window
		if(state == loginState.LOGINATTEMPT){
			
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 35, 300, 140, 60);
			fill(126, 126, 126);
			
			//Buttons
			loginBtn.display();
			registerBtn.display();
			
			//Textfields
			nameBox.display();
			passBox.display();
			fill(122,122,122);
			
			//Cursor
			 if (nameBox.isHovered() || passBox.isHovered()) {
				 cursor(TEXT);
			 } 
			 else if(loginBtn.isHovered() || registerBtn.isHovered()){
				 cursor(HAND);
			 }
			 else
				 cursor(aimCursor, 16, 16);
		
		}
		
		//Succesful login
		else if (state == loginState.LOGINSUCCESS){
			
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 35, 300, 140, 60);
			fill(126,126,126);
			
			//Login Success Message
			textSize(36);
			fill(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue());
			text("Login Success!",170,80);
			textSize(28);
			fill(Color.CYAN.getRed(),Color.CYAN.getGreen(),Color.CYAN.getBlue());
			text("Press Play to",210,120);
			text("start playing",210,150);
			
			//Button
			playBtn.display();
			
			//Cursor
			if(playBtn.isHovered())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		
		}
		
		//Failure at login
		else if (state == loginState.LOGINPASSFAIL){
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 35, 300, 140, 60);
			fill(126,126,126);
			//Login Failure Message
			fill(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue());
			textSize(36);
			text("Login Failure!",175,80);
			textSize(28);
			fill(Color.CYAN.getRed(),Color.CYAN.getGreen(),Color.CYAN.getBlue());
			text("User and password",170,120);
			text("don't match",210,150);
			//Button
			retryLoginBtn.display();
			//Cursor
			if(retryLoginBtn.isHovered())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		}
		
		//Non existent user for login
		else if (state == loginState.LOGINUSERFAIL){
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 35, 300, 140, 60);
			fill(126,126,126);
			//Login Failure Message
			fill(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue());
			textSize(36);
			text("Login Failure!",175,80);
			textSize(28);
			fill(Color.CYAN.getRed(),Color.CYAN.getGreen(),Color.CYAN.getBlue());
			text("Username typed",185,120);
			text("doesn't exist",210,150);
			//Button
			retryLoginBtn.display();
			//Cursor
			if(retryLoginBtn.isHovered())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		}
		
		//Login has blank boxes
		else if (state == loginState.LOGININFO){
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 35, 300, 140, 60);
			fill(126,126,126);
			//Register success message
			fill(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue());
			textSize(32);
			text("Login Fail!",215,80);
			textSize(28);
			fill(Color.CYAN.getRed(),Color.CYAN.getGreen(),Color.CYAN.getBlue());
			text("Missing info",210,120);
			text("or blank boxes",190,150);
			//Button
			retryLoginBtn.display();
			//Cursor
			if(retryLoginBtn.isHovered())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		}
		
		//Register window
		if(state == loginState.REGISTER){
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 10, 300, 190, 40);
			fill(126,126,126);
			//Buttons
			confirmBtn.display();
			backBtn.display();
			//Textfields
			newNameBox.display();
			newPassBox.display();
			confirmPassBox.display();
			fill(122,122,122);
			
			//Cursor
			 if (newNameBox.isHovered() || newPassBox.isHovered() || confirmPassBox.isHovered()) {
				 cursor(TEXT);
			 } 
			 else if(confirmBtn.isHovered() || backBtn.isHovered()){
				 cursor(HAND);
			 }
			 else
				 cursor(aimCursor,16,16);
		}
		
		//Register Success
		else if (state == loginState.REGISTERSUCCESS){
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 35, 300, 140, 60);
			fill(126,126,126);
			//Register success message
			fill(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue());
			textSize(32);
			text("Register Success!",165,80);
			textSize(28);
			fill(Color.CYAN.getRed(),Color.CYAN.getGreen(),Color.CYAN.getBlue());
			text("Click Back",235,120);
			text("to Login",245,150);
			//Button
			retryLoginBtn.display();
			//Cursor
			if(retryLoginBtn.isHovered())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		}
		
		//Registered non matching passwords
		else if (state == loginState.REGISTERWRONGPASS){
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 35, 300, 140, 60);
			fill(126,126,126);
			//Register success message
			fill(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue());
			textSize(32);
			text("Register Fail!",190,80);
			textSize(28);
			fill(Color.CYAN.getRed(),Color.CYAN.getGreen(),Color.CYAN.getBlue());
			text("Passwords",220,120);
			text("don't match",210,150);
			//Button
			retryLoginBtn.display();
			//Cursor
			if(retryLoginBtn.isHovered())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		}
		
		//Registered non matching passwords
		else if (state == loginState.REGISTERDUPLICATED){
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 35, 300, 140, 60);
			fill(126,126,126);
			//Register success message
			fill(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue());
			textSize(32);
			text("Register Fail!",190,80);
			textSize(28);
			fill(Color.CYAN.getRed(),Color.CYAN.getGreen(),Color.CYAN.getBlue());
			text("Username",225,120);
			text("already taken",200,150);
			//Button
			retryLoginBtn.display();
			//Cursor
			if(retryLoginBtn.isHovered())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		}
		
		//Register has blank boxes
		else if (state == loginState.REGISTERINFO){
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 35, 300, 140, 60);
			fill(126,126,126);
			//Register success message
			fill(Color.GREEN.getRed(),Color.GREEN.getGreen(),Color.GREEN.getBlue());
			textSize(32);
			text("Register Fail!",190,80);
			textSize(28);
			fill(Color.CYAN.getRed(),Color.CYAN.getGreen(),Color.CYAN.getBlue());
			text("Missing info",210,120);
			text("or blank boxes",190,150);
			//Button
			retryLoginBtn.display();
			//Cursor
			if(retryLoginBtn.isHovered())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		}
		
	}
	
	
	//Processing handle input event
	public void keyPressed() {
		
	  Textbox selectedBox = null;
	  Boolean boxSet = false;
	  
	  //Select textbox according to loginState and boxState
	  if ( state == loginState.LOGINATTEMPT){
		  if (nameBox.isSelected()) {
			  selectedBox = nameBox;
			  boxSet = true;
		  }	  
		  else if (passBox.isSelected()){
			  selectedBox = passBox;
			  boxSet = true;
		  }
	  }
	  else if( state == loginState.REGISTER){
		  if (newNameBox.isSelected()) {
			  selectedBox = newNameBox;
			  boxSet = true;
		  }
		  else if (newPassBox.isSelected()){
			  selectedBox = newPassBox;
			  boxSet = true;
		  }
		  else if (confirmPassBox.isSelected()){
			  selectedBox = confirmPassBox;
			  boxSet = true;
		  }
	  }
	
	  //Take input / switch box
	  if (boxSet) {
	    if (Character.isAlphabetic(key) || Character.isDigit(key) || key=='.' || key=='-' || key=='_') {
	    	selectedBox.appendText(key);
	    }
	    else if (key==ENTER || key==RETURN || key==TAB) {
	  		 selectedBox.unselect();
	    	if( state == loginState.LOGINATTEMPT){
		    	 if (selectedBox.equals(nameBox))
		    		 passBox.select();
		    	 else
		    		 nameBox.select();
	    	}
	    	else if ( state == loginState.REGISTER){
		    	 if (selectedBox.equals(newNameBox))
		    		 newPassBox.select();
		    	 else if(selectedBox.equals(newPassBox))
		    		 confirmPassBox.select();
		    	 else if (selectedBox.equals(confirmPassBox))
		    		 newNameBox.select();
	    	}
	    }
	    else if (key==DELETE || key==BACKSPACE){
	    	if(selectedBox.getText().length()>0)
	    		selectedBox.deleteLastChar();
	    }
	  }
	}
		 
	
	//processing handle mouse event
	public void mousePressed(){
		
		//main login window
		if ( state == loginState.LOGINATTEMPT ){
			
			if (nameBox.isHovered()){
				nameBox.select();
				passBox.unselect();
			}
			else if(passBox.isHovered()){
				passBox.select();
				nameBox.unselect();
			 }
			else if(registerBtn.isHovered()){
				nameBox.reset();
				passBox.reset();
				state = loginState.REGISTER;
			}
			else if(loginBtn.isHovered()){
				if (!nameBox.getText().isEmpty() && !passBox.getText().isEmpty()){
					state = loginState.WAIT;
					Database database = new Database();
					WaitWindow waitWindow = new WaitWindow("LOADING");
					waitWindow.init();
					waitWindow.start();
					waitWindow.runFrame();
					String pass = database.loadUserDatabase(nameBox.getText(), client);
					// if don't have user name
					if(pass==null){
						state = loginState.LOGINUSERFAIL;
					}
					// if password match
					else if (pass.equals(passBox.getText())){
						state = loginState.LOGINSUCCESS;
					}
					//if password doesn't match database's
					else{
						state = loginState.LOGINPASSFAIL;
					}
					waitWindow.closeFrame();
				}
				else{
					nameBox.reset();
					passBox.reset();
					state = loginState.LOGININFO;
				}
			}
			
		}
		
		//return to login
		else if ( state == loginState.LOGINPASSFAIL || state == loginState.LOGINUSERFAIL ||
				state == loginState.LOGININFO || state == loginState.REGISTERSUCCESS){
			if (retryLoginBtn.isHovered()){
				state = loginState.LOGINATTEMPT;
			}
		}
		
		//return to register
		else if (state == loginState.REGISTERWRONGPASS || 
				state == loginState.REGISTERDUPLICATED || state == loginState.REGISTERINFO){
			if (retryLoginBtn.isHovered()){
				state = loginState.REGISTER;
			}
		}
		
		//login success
		else if ( state == loginState.LOGINSUCCESS ){
			if (playBtn.isHovered()){
				this.loginSucc = true;
			}
		}
		
		//register window 
		else if ( state == loginState.REGISTER){
			if (newNameBox.isHovered()){
				newNameBox.select();
				newPassBox.unselect();
				confirmPassBox.unselect();
			}
			else if(newPassBox.isHovered()){
				newNameBox.unselect();
				newPassBox.select();
				confirmPassBox.unselect();
			 }
			else if(confirmPassBox.isHovered()){
				newNameBox.unselect();
				newPassBox.unselect();
				confirmPassBox.select();
			 }
			else if (confirmBtn.isHovered()){
				//check if user existed before
				if(!newNameBox.getText().isEmpty() && !newPassBox.getText().isEmpty() && !confirmPassBox.getText().isEmpty()){
					if( newPassBox.getText().equals( confirmPassBox.getText() ) ){
						state = loginState.WAIT;
						Database database = new Database();
						WaitWindow waitWindow = new WaitWindow("CREATING");
						waitWindow.init();
						waitWindow.start();
						waitWindow.runFrame();
						String str = database.newUser(newNameBox.getText(), newPassBox.getText());
						if(str.equals("Sucess")) state = loginState.REGISTERSUCCESS;
						else if(str.equals("Fail")) state = loginState.REGISTERWRONGPASS;
						waitWindow.closeFrame();
					}
					else
						state = loginState.REGISTERWRONGPASS;
				}
				else
					state = loginState.REGISTERINFO;
			}
			else if (backBtn.isHovered()){
				newNameBox.reset();
				newPassBox.reset();
				confirmPassBox.reset();
				state = loginState.LOGINATTEMPT;
			}
		}
	}

	
	//Returns true if player already logged in
	public boolean loginSuccess(){
		if (loginSucc)
			return true;
		else 
			return false;
	}
	
	
	//Runs login JFrame, returns control to caller when login is successful
	public void runFrame(){
		JFrame loginWindow;
		
		loginWindow = new JFrame("ZOOM and BOOM - LOGIN");
		loginWindow.setContentPane(this);
		loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginWindow.setSize(600, 400);
		loginWindow.setLocation(400,200);
		loginWindow.setVisible(true);
		
		//loop while login unsuccesful
		System.out.println("Waiting for login success...");
		while(!loginSuccess()){
			System.out.print("");
		}
		System.out.println("Login success!!");
			
		loginWindow.setVisible(false);
		loginWindow.dispose();
		
		return;
	}

}
