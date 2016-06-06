package object.tool;

import main.MainApplet;
import processing.core.PApplet;

public class VScrollbar {

	private int swidth, sheight; 		// width and height of bar
	private int xpos, ypos; 			// x and y position of bar
	private float spos, newspos; 		// x position of slider
	private int sposMin, sposMax; 		// max and min values of slider
	private int loose; 					// how loose/heavy
	private boolean over; 				// is the mouse over the slider?
	private boolean locked;
	//private float ratio;
	private PApplet parent;  ////****MainApplet -> PApplet

	
	// Constructor
	public VScrollbar(int xp, int yp, int sw, int sh, int l, PApplet p) {  ////****MainApplet -> PApplet
		
		swidth = sw;
		sheight = sh;
		//int heighttowidth = sh - sw;
		//ratio = (float) sh / (float) heighttowidth;
		xpos = xp;
		ypos = yp;
		//spos = ypos + sheight / 2 - swidth / 2;
		spos=0;
		newspos = spos;
		sposMin = ypos;
		sposMax = ypos + sheight - 2*swidth;
		loose = l;
		this.parent = p;
		
	}

	
	// draw scroll bar
	public void display() {
		
		// bar
		parent.fill(209,238,238);
		parent.rect(xpos, ypos, 17, sheight);
		
		// slider
		if (over || locked) {
			parent.fill(34,139,34);
		} else {
			parent.fill(0, 250, 154);
		}
		parent.rect(xpos, spos, swidth, 27);
		
	}


	// update scroll bar
	public void update() {
		
		if (over()) {
			over = true;
		} else {
			over = false;
		}
		
		if (parent.mousePressed && over) {
			locked = true;
		}
		
		if (!parent.mousePressed) {
			locked = false;
		}
		
		if (locked) {
			newspos = constrain(parent.mouseY - swidth / 2, sposMin, sposMax);
		}
		
		if (Math.abs(newspos - spos) > 1) {
			spos = spos + (newspos - spos) / loose;
		}
		
	}

	
	//TODO =>brief explain
	private int constrain(int val, int minv, int maxv) {
		return Math.min(Math.max(val, minv), maxv);
	}

	
	// see if mouse is cover the slider
	private boolean over() {
		
		if (parent.mouseX > xpos && parent.mouseX < xpos + swidth && parent.mouseY > ypos
				&& parent.mouseY < ypos + sheight) {
			return true;
		} else {
			return false;
		}
		
	}

	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public float getspos(){
		return this.spos;
	}

}

