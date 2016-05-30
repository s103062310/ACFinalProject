package main;

import java.awt.Color;
import java.util.*;

import javax.swing.JFrame;

import java.sql.*;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

import object.server.Player;

/**
*	Login & Register Window
*	Function runFrame() creates a JFrame and returns control to callor until 
*	a successful login
*	Uses MySql online database to make queries
*	Upon successful login, window changes, press play to send player info to server
*	Upon failure at login, window changes, button retry goes back to main login window
*/

public class Login extends PApplet{
	
		//Database data
		private static final String jdbcDriver = "com.mysql.jdbc.Driver";
		private static final String sqlDriver = "jdbc:mysql://db4free.net:3306/player_database";
		private static final String sqlUser = "ssnthuac_final";
		private static final String sqlPass = "ssnthuac";
		
		//GUI		
		private enum loginState {
		    LOGINATTEMPT,LOGINSUCCESS,LOGINPASSFAIL,LOGINUSERFAIL,LOGININFO,
		    REGISTER,REGISTERSUCCESS,REGISTERWRONGPASS,REGISTERDUPLICATED,REGISTERINFO
		}
		private loginState state;
		private PImage backgroundImg;
		private PImage aimCursor;
		private final int width = 600;
		private final int height = 350;
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
		
	//TODO -> fix
	public Login(Client client){
		this.client = client;
		this.loginSucc = false;
	}
	
	public void setup() {
		state = loginState.LOGINATTEMPT;
		//GUI
		size(width, height);
		smooth();
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
		noStroke();
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
			 if (nameBox.checkLimits() || passBox.checkLimits()) {
				 cursor(TEXT);
			 } 
			 else if(loginBtn.checkLimits() || registerBtn.checkLimits()){
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
			if(playBtn.checkLimits())
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
			if(retryLoginBtn.checkLimits())
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
			if(retryLoginBtn.checkLimits())
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
			if(retryLoginBtn.checkLimits())
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
			 if (newNameBox.checkLimits() || newPassBox.checkLimits() || confirmPassBox.checkLimits()) {
				 cursor(TEXT);
			 } 
			 else if(confirmBtn.checkLimits() || backBtn.checkLimits()){
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
			if(retryLoginBtn.checkLimits())
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
			if(retryLoginBtn.checkLimits())
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
			if(retryLoginBtn.checkLimits())
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
			if(retryLoginBtn.checkLimits())
				cursor(HAND);
			else 
				cursor(aimCursor,16,16);
		}
		
	}
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
		 
	public void mousePressed(){
		//main login window
		if ( state == loginState.LOGINATTEMPT ){
			if (nameBox.checkLimits()){
				nameBox.select();
				passBox.unselect();
			}
			else if(passBox.checkLimits()){
				passBox.select();
				nameBox.unselect();
			 }
			else if(registerBtn.checkLimits()){
				nameBox.reset();
				passBox.reset();
				state = loginState.REGISTER;
			}
			else if(loginBtn.checkLimits()){
				if (!nameBox.getText().isEmpty() && !passBox.getText().isEmpty()){
					checkDatabase();
				}
				else{
					nameBox.reset();
					passBox.reset();
					state = loginState.LOGININFO;
				}
			}
		}
		//login or register failure
		else if ( state == loginState.LOGINPASSFAIL || state == loginState.LOGINUSERFAIL ||
				state == loginState.LOGININFO || state == loginState.REGISTERWRONGPASS || 
				state == loginState.REGISTERDUPLICATED || state == loginState.REGISTERINFO){
			if (retryLoginBtn.checkLimits()){
				state = loginState.LOGINATTEMPT;
			}
		}
		//login success
		else if ( state == loginState.LOGINSUCCESS ){
			if (playBtn.checkLimits()){
				this.loginSucc = true;
				//TODO -> fix
				//OPEN GAME

			}
		}
		//register window 
		else if ( state == loginState.REGISTER){
			if (newNameBox.checkLimits()){
				newNameBox.select();
				newPassBox.unselect();
				confirmPassBox.unselect();
			}
			else if(newPassBox.checkLimits()){
				newNameBox.unselect();
				newPassBox.select();
				confirmPassBox.unselect();
			 }
			else if(confirmPassBox.checkLimits()){
				newNameBox.unselect();
				newPassBox.unselect();
				confirmPassBox.select();
			 }
			else if (confirmBtn.checkLimits()){
				//check if user existed before
				if(!newNameBox.getText().isEmpty() && !newPassBox.getText().isEmpty() && !confirmPassBox.getText().isEmpty()){
					if( newPassBox.getText().equals( confirmPassBox.getText() ) )
						newUser();
					else
						state = loginState.REGISTERWRONGPASS;
				}
				else
					state = loginState.REGISTERINFO;
			}
			else if (backBtn.checkLimits()){
				newNameBox.reset();
				newPassBox.reset();
				confirmPassBox.reset();
				state = loginState.LOGINATTEMPT;
			}
		}
	}
	
	//Check user/pass in database
	private void checkDatabase(){
		
		Connection sqlConn = null;
		Statement sqlState = null;
		String selectQuery = null;
		ResultSet sqlResult = null;
		
		try{
			
			Class.forName(jdbcDriver);
			
			sqlConn = DriverManager.getConnection(sqlDriver,sqlUser,sqlPass);
			sqlState = sqlConn.createStatement();
			selectQuery = "SELECT username,password,money,color FROM player_table WHERE username = \"" + nameBox.getText() +"\"";
			sqlResult = sqlState.executeQuery(selectQuery);
			
			//DEBUG
			//System.out.println("QUERY: " + selectQuery);
			
			//if query returns results
			if (sqlResult.next()){
				
				String resultName, resultPass;
				int resultMoney=0, resultColor=0;
				
				resultName = sqlResult.getString("username");
				resultPass = sqlResult.getString("password");
				resultMoney = sqlResult.getInt("money");
				resultColor = sqlResult.getInt("color");
				
				if (resultPass.equals(passBox.getText())){
					state = loginState.LOGINSUCCESS;
					Player tempPlayer = new Player(resultColor, resultName);
					tempPlayer.setScore(resultMoney);
					client.setPlayer(tempPlayer);
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
	
	
	//Add newUser to database
	private void newUser(){
		//Money and Color will be set to 0 by default as per database's settings
		
		Connection sqlConn = null;
		Statement sqlState = null;
		String newEntry = null;
		
		try{
			
			Class.forName(jdbcDriver);
			
			sqlConn = DriverManager.getConnection(sqlDriver,sqlUser,sqlPass);
			sqlState = sqlConn.createStatement();
			
			newEntry = "INSERT INTO player_table VALUES ('" + newNameBox.getText() + "', '" + newPassBox.getText() + "', "+ "NULL, NULL)";
					
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
		
		while(!loginSuccess());
			System.out.println("LoginSuccess :"+loginSuccess());
			
		loginWindow.setVisible(false);
		loginWindow.dispose();
		System.out.println("disposed of LoginWindow");
		
		return;
	}
}
