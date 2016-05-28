package main;

import java.awt.Color;
import object.client.ColorButton;

public class Market{
	
	private MainApplet parent;
	private ColorButton[] button;
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
		
		// button
		for(ColorButton btn : button){
			btn.display();
		}

	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public ColorButton[] getButtons(){
		return button;
	}
	
}