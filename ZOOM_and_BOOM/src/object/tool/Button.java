package object.tool;

import processing.core.PApplet;
import java.awt.Color;

//TODO image! (( 2 type

public class Button {
	
	private PApplet parent;
	private int x, y, d, color;
	private boolean over;
	
	
	// Constructor
	public Button(PApplet p, int x, int y, int d, Color c){
		
		this.parent = p;
		this.x = x;
		this.y = y;
		this.d = d;
		this.over = false;
		this.color = c.getRGB();
		
	}
	
	
	// draw button
	public void display(){
		
		// button
		parent.noStroke();
		parent.fill(color);
		if(over) parent.ellipse(x, y, d+10, d+10);
		else parent.ellipse(x, y, d, d);
		
	}
	

	// see if mouse is in the button
	public boolean inBtn(){
		if(PApplet.dist(parent.mouseX, parent.mouseY, x, y)<=d)
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
