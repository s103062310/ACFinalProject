package object.client;

import processing.core.PApplet;
<<<<<<< HEAD
import processing.core.PImage;
=======
import processing.core.PFont;
>>>>>>> 34a70ceaf432feed55539aa540fe167b37c6d044
import object.tool.Button;

public class ColorButton extends Button{
	
	private PFont font;
	private String colorName;
	private int money, color;

	
	// Constructor
	public ColorButton(PApplet p, float x, float y, int money, String colorName, int color){
		
		super(p, x, y, 70,70, color);
		this.money = money;
		this.color = color;
		this.colorName = colorName;
<<<<<<< HEAD
		this.image = image;
=======
		this.font = parent.createFont("src/resource/fonts/HappyGiraffe.ttf", 20);
>>>>>>> 34a70ceaf432feed55539aa540fe167b37c6d044
		
	}
	
	
	// draw good
	public void display(){
		
		// button
		super.display_circle();
		
		// text
		parent.fill(0);
		parent.textFont(font);
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
