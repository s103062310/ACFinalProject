package object.tool;

import processing.core.PApplet;

//TODO image! (( 2 type

public class Button {
	
	protected PApplet parent;
	protected int x, y, d, color;
	protected boolean over;
	
	
	// Constructor
	public Button(PApplet p, int x, int y, int d, int c){
		
		this.parent = p;
		this.x = x;
		this.y = y;
		this.d = d;
		this.over = false;
		this.color = c;
		
	}
	
	
	// draw button
	public void display(){
		
		// button
		parent.noStroke();
		parent.fill(color);
		if(over) parent.ellipse(x, y, d+10, d+10);
		else parent.ellipse(x, y, d, d);
		
	}
	

	// detect mouse move into button
	public boolean inBtn(){
		if(PApplet.dist(parent.mouseX, parent.mouseY, x, y)<=d/2)
			return true;
		else return false;
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public void setOver(boolean b){
		this.over = b;
	}
	
}
