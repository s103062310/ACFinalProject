package main;

import processing.core.PApplet;

public class Timer {
	
	PApplet parent;
	private int x, y, d, max, time;
	
	public Timer(PApplet p, int x, int y, int d, int max){
		parent = p;
		this.x = x;
		this.y = y;
		this.d = d;
		this.max = max;
		this.time = max;
	}
	
	public void display(){
		parent.noStroke();
		parent.fill(255, 255, 255);
		parent.ellipse(x, y, d, d);
		parent.fill(255, 0, 0);
		parent.textSize(d);
		parent.text(time, x-d/3, y+d/3);
	}
	
	public int getValue(){
		return time;
	}
	
	public void work(){
		time = (time+max)%(max+1);
	}
	
	public void reset(){
		time = max;
	}
	
}
