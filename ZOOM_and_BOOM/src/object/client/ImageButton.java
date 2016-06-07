package object.client;

import object.tool.Button;
import processing.core.PApplet;
import processing.core.PImage;

public class ImageButton extends Button{
	
	private String colorName;
	private int money, color;

	
	// Constructor
	public ImageButton(PApplet p,PImage image, float x, float y, int money, String colorName, int color){
		
		super(p, x, y, 70, color);
		
		this.money = money;
		this.color = color;
		this.colorName = colorName;
		this.image = image;
		
	}
	
	
	// draw good
	public void display(){
		
		// button
		parent.noStroke();
		parent.fill(color);
		parent.tint(255, 130);
		if(over) parent.ellipse(x-scrollX, y-scrollY, d+10, d+10);
		else parent.ellipse(x-scrollX, y-scrollY, d, d);
		parent.tint(255, 255);
		display_image();
		
		// text
		parent.fill(0);
		parent.textSize(20);
		parent.text(colorName, x-25-scrollX, y+80);
		parent.text("$"+money, x-20-scrollX, y+100);
		
	}
	
	// draw button - image version
	public void display_image(){
		
		// button
		if(over) parent.image(image, x-25-scrollX, y-25-scrollY, width+50, height+50);
		else parent.image(image, x-20-scrollX, y-20-scrollY, width+40, height+40);
		
	}
	
	public void displayOnlyCircle(){
		
		// button
		super.display_circle();
		
		// text
		parent.fill(0);
		parent.textSize(20);
		parent.text(colorName, x-25-scrollX, y+80);
		parent.text("$"+money, x-20-scrollX, y+100);
		
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public int getMoney(){
		return money;
	}
	
	public int getColor(){
		return color;
	}
	
}
