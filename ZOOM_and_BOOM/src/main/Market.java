package main;

public class Market {
	
	private MainApplet parent;
	
	// Constructor
	public Market(MainApplet p){
		this.parent = p;
	}
	
	// update screen content
	public void display(){
		this.parent.fill(50, 7, 250, 200);
		this.parent.rect(0, 450, 800, 200);
	}

}
