package object.client;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import object.server.Player;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class UpdateDatabaseWindow extends PApplet{
	
	//Database data
	private static final String jdbcDriver = "com.mysql.jdbc.Driver";
	private static final String sqlDriver = "jdbc:mysql://db4free.net:3306/player_database";
	private static final String sqlUser = "ssnthuac_final";
	private static final String sqlPass = "ssnthuac";
	
	//GUI		
	private static final int width = 600;
	private static final int height = 350;
	private PImage backgroundImg;
	private PFont loginFont;
	
	//Wait
	private int rand=0;
	private Color[] splashColors;
	
	//Update successful
	boolean updateSuccessful;
	
	//Constructor
	public UpdateDatabaseWindow(){
		updateSuccessful=false;
	}
		
	//Processing Setup
	public void setup() {
		frameRate(500);
		size(width, height);
		smooth();
		noStroke();
		loginFont = createFont("resource/fonts/HappyGiraffe.ttf",32);
		textFont(loginFont);
		//loadImages
		try { 
			backgroundImg = loadImage("resource/other_images/paintBackground.png");
		}
		catch(Exception ex){
			System.err.println("Unable to laod cursor or Backgroun Images");
			ex.printStackTrace();
		}
		tint(255, 130);
		image(backgroundImg,0,0);
		tint(255, 255);
		//Wait animation
		setColors();
		rand = (int) random(12);
	}
		
	//Processing Draw
	public void draw(){
		
		if (frameCount % 30 == 0){
		    rand = (int) random(12);
		    text("UPDATING DATABASE...",(width-textWidth("UPDATING DATABASE..."))/2,300);
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
	
	
	//update player data in database
	public void updateDatabase(Player player){
		
		Thread databaseThread = new Thread(new Runnable() {
			public void run(){
		//Money and Color will be set to 0 by default as per database's settings
				
				Connection sqlConn = null;
				Statement sqlState = null;
				String updateEntry = null;
				
				try{
					
					Class.forName(jdbcDriver);
					
					sqlConn = DriverManager.getConnection(sqlDriver,sqlUser,sqlPass);
					sqlState = sqlConn.createStatement();
					
					updateEntry = "UPDATE player_table SET score="+ player.getScore() +",shield="+  player.getShield() +", completed="+  player.getCompleted() +" WHERE username='"+player.getName()+"'";
							
					sqlState.executeUpdate(updateEntry);
					
					//DEBUG
					System.out.println("updateEntry: " + updateEntry);
					
					updateSuccessful=true;
					
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
	
	public void runFrame(Player player){
		JFrame updateFrame;
		
		updateFrame = new JFrame("ZOOM and BOOM - UPDATING DATABASE");
		updateFrame.setContentPane(this);
		updateFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		updateFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(updateFrame, 
		            "Are you sure you don't want to save your progress?", "Really Closing?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	updateFrame.dispose();
		        }
		    }
		});
		updateFrame.setSize(600, 400);
		updateFrame.setLocation(400,200);
		updateFrame.setVisible(true);		
		
		updateDatabase(player);
		
		while(!updateSuccessful){
			System.out.print("*");
		}

		updateFrame.dispose();
		
		return;
	}

}


