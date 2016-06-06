package object.tool;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import java.util.*;

public class DigitalTimer {
	
	private PApplet parent;
	private PFont digitalFont;
	private PImage clockImg;
	private static enum timerState{ON,OFF};
	private timerState state;
	private int x, y, max, time;
	private String timeString;
	private java.util.Timer timer = new java.util.Timer ();
	
	
	// Constructor
	public DigitalTimer(PApplet p, int x, int y, int max){
		
		this.parent = p;
		this.x = x;
		this.y = y;
		this.max = max * 1000 + 100 ;
		this.time = max * 1000 ;
		this.state = timerState.OFF;
		digitalFont = parent.createFont("resource/fonts/digital7segment.ttf", 48);
		//load Image
		try { 
			clockImg = parent.loadImage("resource/other_images/clock.png");
			clockImg.resize(80, 70);
		}
		catch(Exception ex){
			System.err.println("Unable to load clock Image");
			ex.printStackTrace();
		}
		//Schedule timer task
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(state == timerState.ON)
					tick();
			}
		}, 0, 1);
		timeString = String.format("%d.%d", time / 1000, time % 1000 /100);
	}
	
	// display timer
	public void display(){
		
		parent.noStroke();
		parent.image(clockImg, x, y);
		parent.textFont(digitalFont);
		parent.fill(255,0,0);
		if(time/1000 == 1)
			parent.text(timeString, x+27, y+49);
		else
			parent.text(timeString, x+12, y+49);
		
	}
		
	// count down
	public void tick(){
		
		if(time==0)
			time=max;
		else 
			time--;
		timeString = String.format("%d.%d", time / 1000, time % 1000 /100);
	}
	
	// reset timer
	public void reset(){
		time = max;
	}
	
	//pause timer
	public void pause(){
		if(state == timerState.ON)
			state = timerState.OFF;
	}	
	
	//resume timer
	public void resume(){
		if(state == timerState.OFF)
			state = timerState.ON;
	}
	
	//returns values in millis
	public int getValue(){
		return time;
	}
	
}
