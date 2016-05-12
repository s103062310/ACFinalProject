package main;

public class Game {
	
	private MainApplet parent;
	
	// Constructor
	public Game(MainApplet p){
		this.parent = p;
	}
	
	// update screen content
	public void display(){
		this.parent.fill(125, 56, 78, 200);
		this.parent.rect(0, 0, 800, 450);
	}
}
