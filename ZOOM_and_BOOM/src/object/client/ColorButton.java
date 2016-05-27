package object.client;

import processing.core.PApplet;
import object.tool.Button;

public class ColorButton extends Button{
	
	public String colorName;
	public int money;

	
	// Constructor
	public ColorButton(PApplet p, int x, int y, int money, String colorName, int color){
		
		super(p, x, y, 70, color);
		this.money = money;
		this.colorName = colorName;
		
	}
	
	
	// draw good
	public void display(){
		
		/*if(name == "YELLOW"){
			this.money = 10;
		} else if(name == "GREEN"){
			this.money = 20;
		} else if(name == "BLUE"){
			this.money = 10;
		} else if(name == "RED"){
			this.money = 15;
		} else if(name == "RANDOM"){
			this.money = 30;
		}*/
		
		// button
		super.display();
		
		// text
		parent.fill(255);
		parent.textSize(20);
		parent.text(colorName, x-25, y+80);
		parent.text("$"+money, x-20, y+100);
		
	}
	
}
