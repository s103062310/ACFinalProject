package object.tool;

import processing.core.PApplet;

//TODO clock image!
//TODO if server need => develop other type timer

public class Timer {
	
	PApplet parent;
	private int x, y, d, max, time;
	
	
	// Constructor
	public Timer(PApplet p, int x, int y, int d, int max){
		
		parent = p;
		this.x = x;
		this.y = y;
		this.d = d;
		this.max = max;
		this.time = max;
		
	}

	
	// display timer
	public void display(){
		
		parent.noStroke();
		parent.fill(255, 255, 255);
		parent.ellipse(x, y, d, d);
		parent.fill(255, 0, 0);
		parent.textSize(d);
		parent.text(time, x-d/3, y+d/3);
		
	}
	
		
	// count down
	public void work(){
		time = (time+max)%(max+1);
	}
	
	
	// reset timer
	public void reset(){
		time = max;
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public int getValue(){
		return time;
	}

}
