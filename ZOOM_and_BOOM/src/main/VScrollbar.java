package main;

public class VScrollbar {

	int swidth, sheight; 		// width and height of bar
	int xpos, ypos; 			// x and y position of bar
	float spos, newspos; 		// x position of slider
	int sposMin, sposMax; 		// max and min values of slider
	int loose; 					// how loose/heavy
	boolean over; 				// is the mouse over the slider?
	boolean locked;
	float ratio;
	private MainApplet parent;

	VScrollbar(int xp, int yp, int sw, int sh, int l, MainApplet p) {
		swidth = sw;
		sheight = sh;
		int heighttowidth = sh - sw;
		ratio = (float) sh / (float) heighttowidth;
		xpos = xp;
		ypos = yp;
		//spos = ypos + sheight / 2 - swidth / 2;
		spos=0;
		newspos = spos;
		sposMin = ypos;
		sposMax = ypos + sheight - swidth;
		loose = l;
		this.parent = p;
	}

	void update() {
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

	int constrain(int val, int minv, int maxv) {
		return Math.min(Math.max(val, minv), maxv);
	}

	boolean over() {
		if (parent.mouseX > xpos && parent.mouseX < xpos + swidth && parent.mouseY > ypos
				&& parent.mouseY < ypos + sheight) {
			return true;
		} else {
			return false;
		}
	}

	void display() {
		parent.fill(187,255,255);
		parent.rect(xpos, ypos, 20, sheight);
		if (over || locked) {
			parent.fill(0, 250, 154);
		} else {
			parent.fill(85,26,139);
		}
		parent.rect(xpos, spos, swidth, 40);
	}
	
	float getPos() {
		// Convert spos to be values between
		// 0 and the total width of the scrollbar
		return spos * ratio;
	}

}
