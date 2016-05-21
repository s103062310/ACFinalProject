package main;

import processing.core.PApplet;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

/**
* Creates Textboxes with default text, wraps text, adds cursor
* checkLimit() checks if mouse is over
*/

public class Textbox {

	protected PApplet parent;
	protected final int defaultWidth;
	protected String defaultText;
	protected int textSize;
	//Public
	public Color color;
	public String text;
	public int state;
	public int x;
	public int y;
	public boolean firstClick;

	
	//Constructor
	public Textbox(PApplet parent, int x, int y, Color color, String text){
		this.parent = parent;
		this.color = color;
		this.state = 0;
		this.defaultText = text;
		this.text="";
		this.x = x;
		this.y = y;
		//default values
		this.textSize = 36;
		this.defaultWidth = 250;
	}
	
	//displays Textbox
	public void display(){
		String displayText = this.defaultText;
		if (firstClick && state ==1 || !text.isEmpty() && state == 0){
			displayText = text;
			while (parent.textWidth(displayText) > defaultWidth - 50)
				displayText = displayText.substring(1);
		}
	    parent.fill(color.getRed(),color.getGreen(),color.getBlue());
	    parent.textSize(textSize);
	    parent.text(displayText, x, y);
	    parent.noStroke();
	    parent.rect(x, y+5 , this.defaultWidth + 20, 5);
	    drawCursor();
	}
	
	//draw blinking cursor
	public void drawCursor(){
		if(state ==1){
			if (parent.textWidth(text) < defaultWidth)
				parent.rect(x + parent.textWidth(text),y-30,5,30);
			else
				parent.rect(x + defaultWidth,y-30,5,30);
		}
	}
	
	//checks if mouse is in textbox
	public boolean checkLimits (){
		float width = this.defaultWidth;
		if (parent.textWidth(text) > this.defaultWidth)
			width = parent.textWidth(text);
		if(   parent.mouseX > this.x        &&   parent.mouseX < (this.x + width)
		   && parent.mouseY > this.y - 25   &&   parent.mouseY < (this.y ) ){
			if (parent.mousePressed)
				firstClick = true;
			return true;
		}
		else return false;
	}
	
}
