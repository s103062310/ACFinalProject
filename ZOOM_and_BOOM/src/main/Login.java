package main;

import java.awt.Color;

import javax.swing.JFrame;

import java.sql.*;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import object.server.Player;
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
	
	//Database data
	private static final String jdbcDriver = "com.mysql.jdbc.Driver";
	private static final String sqlDriver = "jdbc:mysql://db4free.net:3306/player_database";
	private static final String sqlUser = "ssnthuac_final";
	private static final String sqlPass = "ssnthuac";
	
	//GUI		
	private static enum loginState {
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
	
	//Wait
	private int rand=0;
	private Color[] splashColors;
	
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
		//Wait animation
		setColors();
		rand = (int) random(12);
	}
	
	//Processing Draw
	public void draw(){
		//If not WAIT
		if (!(state == loginState.WAIT)){
			frameRate(60);
			background(255);
			tint(255, 130);
			image(backgroundImg,0,0);
			tint(255, 255);
		}
		//Wait state
		else if (state == loginState.WAIT){
			 if (frameCount % 30 == 0){
				rand = (int) random(12);
			  }
			  if (frameCount % 2 == 0) {
			    fill(splashColors[rand].getRed(),splashColors[rand].getGreen(),splashColors[rand].getBlue());
			    pushMatrix();
			    translate(300, 175);
			    rotate(radians(frameCount/2  % 360));
			    rect(0, 25, 50, 2);
			    popMatrix();
			    
			  }
		}
		//Main login window
		if(state == loginState.LOGINATTEMPT){
			//transparent rectangle
			fill(255, 255, 255, 240);
			rect(145, 35, 300, 140, 60);
			fill(126,126,126);
			
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
				 cursor(aimCursor,16,16);
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
					setWaitScreen();
					checkDatabase();
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
						setWaitScreen();
						state = loginState.WAIT;
						newUser();
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
	
	private void setColors(){
		splashColors = new Color[12];
		splashColors[0]  = new Color(255,0,128);//PINK
		splashColors[1]  = new Color(255,0,0); //RED
		splashColors[2]  = new Color(255,128,0); //ORANGE
		splashColors[3]  = new Color(255,255,0);//YELLOW
		splashColors[4]  = new Color(128,255,0); //LIME GREEN
		splashColors[5]  = new Color(0,255,0);//GREEN
		splashColors[6]  = new Color(0,255,128);//AQUA
		splashColors[7]  = new Color(0,255,255);//CYAN
		splashColors[8]  = new Color(0,128,255);//LIGHT BLUE
		splashColors[9]  = new Color(0,255,255);//BLUE
		splashColors[10] = new Color(128,0,255);//PURPLE
		splashColors[11]  = new Color(255,0,255);//MAGENTA
	}
	
	public void setWaitScreen(){
		background(255);
		tint(255, 130);
		image(backgroundImg,0,0);
		tint(255, 255);
		frameRate(500);
	}
	
	//Check user/pass in database
	private void checkDatabase(){
		
		Thread databaseThread = new Thread(new Runnable() {
			public void run(){
				Connection sqlConn = null;
				Statement sqlState = null;
				String selectQuery = null;
				ResultSet sqlResult = null;
				
				try{
					
					Class.forName(jdbcDriver);
					
					sqlConn = DriverManager.getConnection(sqlDriver,sqlUser,sqlPass);
					sqlState = sqlConn.createStatement();
					selectQuery = "SELECT username,password,score,completed,shield,color FROM player_table WHERE username = \"" + nameBox.getText() +"\"";
					sqlResult = sqlState.executeQuery(selectQuery);
					
					//DEBUG
					//System.out.println("QUERY: " + selectQuery);
					
					//if query returns results
					if (sqlResult.next()){
						
						String resultName, resultPass;
						int resultScore=0, resultColor=0, resultShield =0, resultCompleted=0;
						
						resultName = sqlResult.getString("username");
						resultPass = sqlResult.getString("password");
						resultScore = sqlResult.getInt("score");
						resultCompleted = sqlResult.getInt("completed");
						resultShield = sqlResult.getInt("shield");
						resultColor = sqlResult.getInt("color");
						
						if (resultPass.equals(passBox.getText())){
							state = loginState.LOGINSUCCESS;
							client.setPlayer( new Player(resultColor, resultName, resultScore, resultCompleted, resultShield) );
						}
						//if password doesn't match database's
						else{
							state = loginState.LOGINPASSFAIL;
						}
					}
					//if username not in database
					else{
						state = loginState.LOGINUSERFAIL;	
					}
				}
				
				catch (SQLException ex){
					System.err.println("SQLException: " + ex.getMessage());
					System.err.println("VendorError: " + ex.getErrorCode());
					ex.printStackTrace();
				}
				
				catch (ClassNotFoundException ex){
					System.err.println("Unable to locate Driver");
					ex.printStackTrace();
				}
				//close connection
				finally{
					try{
						if(sqlState!=null && sqlConn!=null)
							sqlConn.close();
					}
					catch(SQLException ex){
						System.err.println("Unable to close SSL connection to database");
						ex.printStackTrace();
					}
				}
			}
		});
		
		databaseThread.start();
	}
	
	//Add newUser to database
	private void newUser(){
		
		Thread databaseThread = new Thread(new Runnable() {
			public void run(){
		//Money and Color will be set to 0 by default as per database's settings
				
				Connection sqlConn = null;
				Statement sqlState = null;
				String newEntry = null;
				
				try{
					
					Class.forName(jdbcDriver);
					
					sqlConn = DriverManager.getConnection(sqlDriver,sqlUser,sqlPass);
					sqlState = sqlConn.createStatement();
					
					int randomColor = (int) random(1,5);
					newEntry = "INSERT INTO player_table (username,password,color) VALUES ('" + newNameBox.getText() + "', '" + newPassBox.getText() + "', " + randomColor + " )";
							
					sqlState.executeUpdate(newEntry);
					
					//DEBUG
					System.out.println("NEWENTRY: " + newEntry);
					
					state = loginState.REGISTERSUCCESS;
					
				}
				
				catch (SQLException ex){
					System.err.println("SQLException: " + ex.getMessage());
					System.err.println("VendorError: " + ex.getErrorCode());
					ex.printStackTrace();
					if(ex.getMessage().contains("Duplicate"))
						state = loginState.REGISTERDUPLICATED;
				}
				
				catch (ClassNotFoundException ex){
					System.err.println("Unable to locate Driver");
					ex.printStackTrace();
				}
				//close connection
				finally{
					try{
						if(sqlState!=null && sqlConn!=null)
							sqlConn.close();
					}
					catch(SQLException ex){
						System.err.println("Unable to close SSL connection to database");
						ex.printStackTrace();
					}
				}
			}
		});
		databaseThread.start();
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
		loginWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loginWindow.setSize(600, 400);
		loginWindow.setLocation(400,200);
		loginWindow.setVisible(true);
		
		//loop while login unsuccesful
		while(!loginSuccess());
			
		loginWindow.setVisible(false);
		loginWindow.dispose();
		
		return;
	}

}
