package object.server;

import java.util.HashMap;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import main.Client;
import main.Server;
import processing.core.PApplet;
import processing.core.PFont;

public class Database extends PApplet {
	
	private static final long serialVersionUID = 1L;
	
	// Database data
	private static final String jdbcDriver = "com.mysql.jdbc.Driver";
	private static final String sqlDriver = "jdbc:mysql://db4free.net:3306/player_database";
	private static final String sqlUser = "ssnthuac_final";
	private static final String sqlPass = "ssnthuac";
	
	// GUI
	private JFrame window;
	private static final int width = 600;
	private static final int height = 350;
	private PFont font;
	
	// Wait
	private int rand=0;
	private Color[] splashColors;
	private String text;
	
	// Update information
	private boolean canReturn = false;
	private String returnStr;
	
	
	// Constructor
	public Database(String txt){
		this.text = txt;
	}
	
	
	// Processing Setup
	public void setup() {
		
		background(255);
		frameRate(500);
		size(width, height);
		smooth();
		noStroke();
		font = createFont("../resource/fonts/HappyGiraffe.ttf",32);
		textFont(font);
		
		//Wait animation
		setColors();
		rand = (int) random(12);
		
	}

	
	// Processing Draw
	public void draw(){
		
		if (frameCount % 30 == 0){
		    rand = (int) random(12);
		    text(text + " DATABASE...",(width-textWidth("UPDATING DATABASE..."))/2,300);
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

	
	// load player information from database
	public String loadUserDatabase(String name, Client client){
		
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
					selectQuery = "SELECT username,password,score,completed,shield,color FROM player_table WHERE username = \"" + name +"\"";
					sqlResult = sqlState.executeQuery(selectQuery);
					
					//DEBUG
					System.out.println("QUERY: " + selectQuery);
					
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
					
						client.setPlayer( new Player(resultColor, resultName, resultScore, resultCompleted, resultShield) );
						returnStr = resultPass;
					
					} else returnStr = null;
					
					canReturn = true;
						
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
		
		while(!canReturn){
			System.out.print("");
		}

		return returnStr;
		
	}
	
	
	// create account
	public String newUser(String name, String pass){
		
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
					
					int randomColor = (int) random(8);
					newEntry = "INSERT INTO player_table (username,password,color) VALUES ('" + name + "', '" + pass + "', " + randomColor + " )";
							
					sqlState.executeUpdate(newEntry);
					
					//DEBUG
					System.out.println("NEWENTRY: " + newEntry);
					
					returnStr = "Sucess";
					
				}
				
				catch (SQLException ex){
					System.err.println("SQLException: " + ex.getMessage());
					System.err.println("VendorError: " + ex.getErrorCode());
					ex.printStackTrace();
					if(ex.getMessage().contains("Duplicate"))
						returnStr = "Fail";
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
				
				canReturn = true;
				
			}
		});
		databaseThread.start();
		
		while(!canReturn){
			System.out.print("");
		}

		return returnStr;
		
	}

	
	// update player information to database
	public void updateUserDatabase(Player player){
		
		Thread databaseThread = new Thread(new Runnable() {
			
			public void run(){
		
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
					
					canReturn = true;
					
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
		
		while(!canReturn){
			System.out.print("");
		}
		
	}
		
	
	// load answer from database
	public void loadAnswerDatabase(Server server){
		
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
					selectQuery = "SELECT picture,width,height,x,y,datanum FROM answer_table";
					sqlResult = sqlState.executeQuery(selectQuery);
					
					//while query returns results
					while (sqlResult.next() ){
						
						String resultPicture;
						float resultWidth=0, resultHeight=0, resultX=0, resultY=0;
						int resultDatanum=0;
						
						resultPicture = sqlResult.getString("picture");
						resultWidth = sqlResult.getFloat("width");
						resultHeight = sqlResult.getFloat("height");
						resultX = sqlResult.getFloat("x");
						resultY = sqlResult.getFloat("y");
						resultDatanum = sqlResult.getInt("datanum");
						
						Answer resultAnswer = new Answer(resultWidth,resultHeight,resultX,resultY,resultDatanum);
						
						server.answer.put(resultPicture, resultAnswer);
						
						//DEBUG
						System.out.println("Loading picture: " + resultPicture);

					}
					
					canReturn = true;
					
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
		
		while(!canReturn){
			System.out.print("");
		}
		
	}
	
	
	//Runs login JFrame, returns control to caller when login is successful
	
	
	// update answer from database
	public void updateAnswerDatabase(HashMap<String, Answer> answer){
		
		Thread databaseThread = new Thread(new Runnable() {
			
			public void run(){
		
				Connection sqlConn = null;
				Statement sqlState = null;
				String newEntry = null;
				
				try{
					
					Class.forName(jdbcDriver);
					
					sqlConn = DriverManager.getConnection(sqlDriver,sqlUser,sqlPass);
					sqlState = sqlConn.createStatement();
					
					//for each entry in hash
					for(String key : answer.keySet()){
						
						float entryWidth = answer.get(key).width;
						float entryHeight = answer.get(key).height;
						float entryX = answer.get(key).x;
						float entryY = answer.get(key).y;
						int entryDatanum = answer.get(key).dataNum;
						
						//CREATE
						//newEntry = "INSERT INTO answer_table (picture,width,height,x,y,datanum) VALUES ('" 
								 //+ key + "', " + entryWidth + ", " + entryHeight + ", " + entryX + ", "
								 //+ entryY + "', " + entryDatanum + ")";
						
						//UPDATE
						if(! (entryWidth==0 && entryHeight==0 && entryX==0 && entryY==0 && entryDatanum==0)){
							newEntry = "UPDATE answer_table SET width=" + entryWidth + ", height="
									 + entryHeight + ", x=" + entryX + ", y=" + entryY + ", datanum=" + 
									 + entryDatanum	+ " WHERE picture='" + key + "'";
							sqlState.executeUpdate(newEntry);
							//DEBUG
							System.out.println("updateEntry: " + newEntry + " dataNum " + entryDatanum);
						}
						
					}

					canReturn = true;
					
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
		
		while(!canReturn){
			System.out.print("");
		}
		
	}
	
	
	// run frame
	public void runFrame(){
		
		window = new JFrame("ZOOM and BOOM - Database");
		window.setContentPane(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600, 400);
		window.setLocation(400,200);
		window.setVisible(true);

	}
	

	// set colors
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

	
	// close frame
	public void closeFrame(){
		window.dispose();
	}
	
}