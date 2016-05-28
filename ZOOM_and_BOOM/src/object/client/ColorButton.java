package object.client;

import processing.core.PApplet;
import object.tool.Button;

public class ColorButton extends Button{
	
	private String colorName;
	private int money;

	
	// Constructor
	public ColorButton(PApplet p, int x, int y, int money, String colorName, int color){
		
		super(p, x, y, 70, color);
		this.money = money;
		this.colorName = colorName;
		
	}
	
	
	// draw good
	public void display(){
		
		// button
		super.display();
		
		// text
		parent.fill(255);
		parent.textSize(20);
		parent.text(colorName, x-25, y+80);
		parent.text("$"+money, x-20, y+100);
		
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public int getMoney(){
		return money;
	}
	
}
