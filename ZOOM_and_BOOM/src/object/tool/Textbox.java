package object.tool;

import processing.core.PApplet;
import java.awt.Color;

/**
* Creates Textboxes with default text, wraps text, adds cursor
* checkLimit() checks if mouse is over
* select() and unselect() select and unselect the textbox for input entry
* isSelected() returns true if the textBox is selected
* getText() and setText() set and return the box's text
* appendText() appends characters or strings to the box's text
* deleteLastChar() deletes last character of the box's text
*/

public class Textbox {

	protected PApplet parent;
	protected boolean firstClick;
	protected final int defaultWidth;
	protected String defaultText;
	protected Color color;
	protected String text;
	protected int textSize;
	protected enum boxState {
	    SELECTED, UNSELECTED
	}
	protected boxState state;
	protected int x;
	protected int y;
	
	//Constructor
	public Textbox(PApplet parent, int x, int y, Color color, String text){
		this.parent = parent;
		this.color = color;
		this.state = boxState.UNSELECTED;
		this.defaultText = text;
		this.text="";
		this.x = x;
		this.y = y;
		//default values
		this.textSize = 36;
		this.defaultWidth = 250;
		//adjust textSize
		parent.textSize(textSize);
		while(parent.textWidth(text) > defaultWidth + 10 ){
			textSize--;
			parent.textSize(textSize);
		}
	}
	
	//displays Textbox
	public void display(){
		String displayText = this.defaultText;
		if (firstClick && state == boxState.SELECTED || !text.isEmpty() && state == boxState.UNSELECTED ){
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
		if(parent.millis() % 1000 < 500 && state == boxState.SELECTED){
			if (parent.textWidth(text) < defaultWidth)
				parent.rect(x + parent.textWidth(text),y-30,5,30);
			else
				parent.rect(x + defaultWidth,y-30,5,30);
		}
	}
	
	//checks if mouse is in textbox
	public boolean isHovered (){
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
	
	//resets text fields' contents
	public void reset(){
		this.firstClick = false;
		this.text = "";
		this.state = boxState.UNSELECTED;
	}
	
	public String getText(){
		return this.text;
	}
	public void setText(String newText){
		this.text = newText;	
	}
	
	public void appendText(String appendedText){
		this.text = this.text + appendedText;
	}
	
	public void appendText(char appendedText){
		this.text = this.text + appendedText;
	}

	public void deleteLastChar(){
		this.text = this.text.substring(0,this.text.length()-1);
	}
	
	public void select(){
		if(!firstClick) firstClick = true;
		state = boxState.SELECTED;
	}
	
	public void unselect(){
		if ( state == boxState.SELECTED )
			state = boxState.UNSELECTED;
	}
	
	public boolean isSelected(){
		if ( state==boxState.SELECTED )
			return true;
		else
			return false;
	}
}
