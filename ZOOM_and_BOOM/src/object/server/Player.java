package object.server;

import java.io.Serializable;
import java.awt.Color;

public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// player information
	private String name = new String();
	private int color;		// a int of random from 0~5 will be mapped to real color in other class
	private int score=0;
	private int completed=0;
	private int shield=0;
	
	// resources	//TODO 之後可能搬到server
	// new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)).getRGB()
	//private int [] colours = {Color.YELLOW.getRGB(),Color.GREEN.getRGB(),Color.BLUE.getRGB(),Color.RED.getRGB(),Color.PINK.getRGB()};
	private int[] colors = { Color.BLACK.getRGB(), new Color(0, 255, 127).getRGB(), new Color(238, 221, 130).getRGB(),
			new Color(0, 0, 128).getRGB(), Color.RED.getRGB(), new Color(135, 206, 235).getRGB(),
			new Color(238, 180, 34).getRGB(), new Color(255, 0, 255).getRGB(), new Color(105, 139, 34).getRGB() };
	//private Random rand = new Random();
	

	// Constructor 1
	public Player(int color ,String name){
		
		this.color = colors[color];
		this.name = name;
		
	}
	
	
	// Constructor 2
	public Player(int color ,String name, int score, int completed, int shield){
		
		this(color, name);
		this.score = score;
		this.completed = completed;
		this.shield = shield;
		
	}
	
	// Constructor 3
	public Player(){}
	
	/**-----------------------------------------------
	 * ↓ seter and geter
	 ----------------------------------------------**/
	
	public String getName(){
		return this.name;
	}
	
	public int getColor(){
		return this.color;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public void setScore(int score){
		this.score = score;
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
