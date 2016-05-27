package main;

import java.awt.Color;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;

@SuppressWarnings("serial")
public class Market{
	
	private MainApplet parent;
	public int money = 100;
	public ColorButton[] button;
	
	// Constructor
	public Market(MainApplet p){
		this.parent = p;
	}
	
	// update screen content
	public void display(){
		this.parent.fill(50, 7, 250, 200);
		this.parent.rect(0, 450, 800, 200);
		parent.fill(Color.BLACK.getRGB());
		parent.ellipse(70, 510, 70, 70);
		parent.fill(0);
		parent.textSize(20);
		parent.text("Own_color", 20, 600);
		button = new ColorButton[5];
		button[0] = new ColorButton(parent, 190, 510, "YELLOW", Color.YELLOW.getRGB());
		button[1] = new ColorButton(parent, 310, 510, "GREEN", Color.GREEN.getRGB());
		button[2] = new ColorButton(parent, 430, 510, "BLUE", Color.BLUE.getRGB());
		button[3] = new ColorButton(parent, 550, 510, "RED", Color.RED.getRGB());
		button[4] = new ColorButton(parent, 670, 510, "RANDOM", Color.GRAY.getRGB());
		for(int i=0;i<button.length;i++){
			if(checkBoundary(i)){
				button[i].diameter = 75;
			}
		}
		parent.fill(255);
		parent.textSize(20);
		parent.text("Money: "+money, 550, 635);
	}
	public boolean checkBoundary(int i){
		if ((parent.mouseX-button[i].x)*(parent.mouseX-button[i].x)+
			(parent.mouseY-button[i].y)*(parent.mouseY-button[i].y) <= 1225) {  //1225=35*35
			//button[i].diameter = 75;
			return true;
		} else {
			return false;
		}
	}
	public void test(){
		System.out.println("123");
	}
}