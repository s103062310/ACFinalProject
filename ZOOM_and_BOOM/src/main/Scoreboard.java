package main;

public class Scoreboard {
	
	private MainApplet parent;
	
	// Constructor
	public Scoreboard(MainApplet p){
		this.parent = p;
	}
	
	// update screen content
	public void display(){
		this.parent.fill(40, 50, 60, 200);
		this.parent.rect(800, 0, 300, 650);
	}
}
