package object.tool;

import main.MainApplet;

public class VScrollbar {

	private int swidth, sheight; 		// width and height of bar
	private int xpos, ypos; 			// x and y position of bar
	private float spos, newspos; 		// x position of slider
	private int sposMin, sposMax; 		// max and min values of slider
	private int loose; 					// how loose/heavy
	private boolean over; 				// is the mouse over the slider?
	private boolean locked;
	//private float ratio;
	private MainApplet parent;

	
	// Constructor
	public VScrollbar(int xp, int yp, int sw, int sh, int l, MainApplet p) {
		
		swidth = sw;
		sheight = sh;
		//int heighttowidth = sh - sw;
		//ratio = (float) sh / (float) heighttowidth;
		xpos = xp;
		ypos = yp;
		//spos = ypos + sheight / 2 - swidth / 2;
		spos=80;
		newspos = spos;
		sposMin = ypos;
		sposMax = ypos + sheight - 2*swidth;
		loose = l;
		this.parent = p;
		
	}

	
	// draw scroll bar
	public void display() {
		
		// bar
		parent.fill(187,255,255);
		parent.rect(xpos, ypos, 20, sheight);
		
		// slider
		if (over || locked) {
			parent.fill(0, 250, 154);
		} else {
			parent.fill(85,26,139);
		}
		parent.rect(xpos, spos, swidth, 40);
		
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
	
	// Convert spos to be values between 0 and the total width of the scrollbar
	/*private float getPos() {
		return spos * ratio;
	}*/

}

