package object.server;

import java.io.Serializable;

public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// player information
	private String name = new String();
	private int color;
	private int score=0;
	private int completed=0;
	private int shield=0;
	private boolean online;
	

	// Constructor 1
	public Player(int color ,String name){
		
		this.color = color;
		this.name = name;
		this.online = false;
		
	}
	
	
	// Constructor 2
	public Player(int color ,String name, int score, int completed, int shield){
		
		this(color, name);
		this.score = score;
		this.completed = completed;
		this.shield = shield;
		
	}
	
	
	// Constructor 3
	public Player(Player p){
		
		this.name = p.getName();
		this.color = p.getColor();
		this.score = p.getScore();
		this.completed = p.getCompleted();
		this.shield = p.getShield();
		this.online = p.online;
		
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
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
	
	public boolean isOnLine(){
		return online;
	}
	
	public void setOnLine(boolean b){
		this.online = b;
	}
	
}
