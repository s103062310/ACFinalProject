package main;

import java.awt.Color;
import object.client.ColorButton;

public class Market{
	
	private MainApplet parent;
	private ColorButton[] button;
	public int money=100;			// temp
	private int[] price = {0, 10, 20, 10, 15, 30};
	
	
	// Constructor
	public Market(MainApplet p){
		
		this.parent = p;
		
		// create buttons
		int s=70, d=120;
		button = new ColorButton[6];
		//TODO try to use for loop
		button[0] = new ColorButton(parent, s, 510, price[0], "BLACK", Color.BLACK.getRGB());
		button[1] = new ColorButton(parent, s+d, 510, price[1], "YELLOW", Color.YELLOW.getRGB());
		button[2] = new ColorButton(parent, s+d*2, 510, price[2], "GREEN", Color.GREEN.getRGB());
		button[3] = new ColorButton(parent, s+d*3, 510, price[3], "BLUE", Color.BLUE.getRGB());
		button[4] = new ColorButton(parent, s+d*4, 510, price[4], "RED", Color.RED.getRGB());
		button[5] = new ColorButton(parent, s+d*5, 510, price[5], "RANDOM", Color.GRAY.getRGB());
	
	}
	
	
	// update screen content
	public void display(){
		
		// background
		this.parent.fill(50, 7, 250, 200);
		this.parent.rect(0, 450, 800, 200);
		
		/*for(int i=0;i<button.length;i++){
			if(checkBoundary(i)){
				button[i].diameter = 75;
			}
		}*/
		
		// button
		for(ColorButton btn : button){
			btn.display();
		}
		
		// temp money
		parent.fill(255);
		parent.textSize(20);
		parent.text("Money: "+money, 550, 635);
	}
	
	
	/*
	public boolean checkBoundary(int i){
		if ((parent.mouseX-button[i].x)*(parent.mouseX-button[i].x)+
			(parent.mouseY-button[i].y)*(parent.mouseY-button[i].y) <= 1225) {  //1225=35*35
			//button[i].diameter = 75;
			return true;
		} else {
			return false;
		}
	}*/
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public ColorButton[] getButtons(){
		return button;
	}

}