package object.tool;

import java.awt.Color;

import processing.core.PApplet;

/**
* Inherits from Textbox Class
* Displays asterisks in text fields instead of text
*/

public class SecretTextbox extends Textbox{
	
	//Constructor
	public SecretTextbox(PApplet parent, int x, int y, Color color, String text) {
		super(parent, x, y, color, text);
	}
	

	public void display(){
		//if not clicked on yet or empty
		if(!firstClick || text.isEmpty() && state == boxState.UNSELECTED)
			super.display();
		//else show asterisks
		else{
			String asteriskString = "";
			for ( int i=0; i<text.length() &&  parent.textWidth(asteriskString) < defaultWidth ; i++)
				asteriskString += "*";
		    parent.fill(color.getRed(),color.getGreen(),color.getBlue());
		    parent.textSize(textSize);
		    parent.text(asteriskString, x, y);
		    parent.noStroke();
		    parent.rect(x, y+5 , this.defaultWidth + 20, 5);
			drawCursor();
		}

	}

}

