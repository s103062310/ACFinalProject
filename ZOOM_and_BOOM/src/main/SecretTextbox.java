package main;

import java.awt.Color;

import processing.core.PApplet;

public class SecretTextbox extends Textbox{
	
	//Constructor
	public SecretTextbox(PApplet parent, int x, int y, Color color, String text) {
		super(parent, x, y, color, text);
	}
	

	public void display(){
		//if not clicked on yet or empty
		if(!firstClick || text.isEmpty() && state == 0)
			super.display();
		//else show asterisks
		else{
			String asteriskString = "";
			for ( int i=0; i<text.length() &&  parent.textWidth(asteriskString) < defaultWidth ; i++)
				asteriskString += "*";
			float width = this.defaultWidth;
			if ( parent.textWidth(text) > this.defaultWidth )
				width = super.parent.textWidth(text);
		    parent.fill(color.getRed(),color.getGreen(),color.getBlue());
		    parent.textSize(textSize);
		    parent.text(asteriskString, x, y);
		    parent.noStroke();
		    parent.rect(x, y+5 , this.defaultWidth + 20, 5);
			drawCursor();
		}

	}

}
