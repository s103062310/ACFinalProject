package object.tool;

import processing.core.PApplet;

//TODO image! (( 2 type

public class Button {
	
	protected PApplet parent;
	protected float x, y,d, scroll;
	protected boolean over;
	protected int color;
	
	// Constructor
	public Button(PApplet p, float x, float y, float d, int c){
		
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
		if(over) parent.ellipse(x-scroll, y, d+10, d+10);
		else parent.ellipse(x-scroll, y, d, d);
		
	}
	

	// detect mouse move into button
	public boolean inBtn(){
		if(PApplet.dist(parent.mouseX, parent.mouseY, x-scroll, y)<=d/2)
			return true;
		else return false;
	}
	
	public void setPosition(float scrollbar){
		this.scroll = scrollbar/2;
	}
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public void setOver(boolean b){
		this.over = b;
	}
	
}
