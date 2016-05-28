package object.server;

import java.io.Serializable;
import java.awt.Color;
//import java.util.Random;

public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// player information
	private String name;
	private int color;		// a int of random from 0~5 will be mapped to real color in other class
	private int score;
	private int id;
	private int completed=0;
	private int shield=0;
	
	// resources	//TODO 之後可能搬到server
	// new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)).getRGB()
	//private int [] colours = {Color.YELLOW.getRGB(),Color.GREEN.getRGB(),Color.BLUE.getRGB(),Color.RED.getRGB(),Color.PINK.getRGB()};
	private int [] colors = {Color.YELLOW.getRGB(),new Color(0,255,127).getRGB(),new Color(135,206,235).getRGB(),Color.RED.getRGB(),new Color(205,105,201).getRGB()};
	//private Random rand = new Random();
	

	// Constructor
	public Player(int color ,int score ,String name){
		
		this.color = colors[color];
		this.score = score;
		this.name = name;
		
	}
	
	
	/**-----------------------------------------------
	 * ↓ seter and geter
	 ----------------------------------------------**/
	
	public String getName(){
		return this.name;
	}
	
	/*public void setName(String name){
		this.name = name;
	}*/
	
	public int getColor(){
		return this.color;
	}
	
	/*public void setColor(int color){
		this.color = color;
	}*/
	
	public int getScore(){
		return this.score;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getID(){
		return this.id;
	}
	
	public int getCompleted(){
		return this.completed;
	}
	
	public void Completed(){
		this.completed++;
	}
	
	public int getShield(){
		return this.shield;
	}
	
	public void buyShield(){
		this.shield++;
	}
	
	public void useShield(){
		this.shield--;
	}
	
}
