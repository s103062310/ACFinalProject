package main;

import java.awt.Color;
import object.client.ColorButton;
import object.tool.HScrollbar;
import processing.core.PImage;

public class Market{
	
	// scrollbar
	private HScrollbar hs;
	private float scroll;
	// content
	private ColorButton[] button;
	private int[] price = {0, 10, 20, 10, 15, 30};
	
	// resources
	private MainApplet parent;
	private PImage bg;
	
	// Constructor
	public Market(MainApplet p){
		
		this.parent = p;
		//this.bg = parent.loadImage("src/resource/market.PNG");
		this.bg = parent.loadImage("src/resource/other_images/marketBackground.jpg");
		this.hs = new HScrollbar(0 ,640, 800, 20 ,16, parent);
		// create buttons
		int s=70, d=120;
		button = new ColorButton[10];
		//TODO try to use for loop
		button[0] = new ColorButton(parent, s, 515, price[0], "DEMON", Color.BLACK.getRGB());
		button[1] = new ColorButton(parent, s+d*2, 515, price[2], "LIME", new Color(0,255,127).getRGB());
		button[2] = new ColorButton(parent, s+d, 515, price[1], "MOON",  new Color(238,221,130).getRGB());
		button[3] = new ColorButton(parent, s+d*3, 515, price[3], "NAVY", new Color(0,0,128).getRGB());
		button[4] = new ColorButton(parent, s+d*4, 515, price[4], "BLOOD", Color.RED.getRGB());
		button[5] = new ColorButton(parent, s+d*5, 515, price[4], "SKY", new Color(135,206,235).getRGB());
		button[6] = new ColorButton(parent, s+d*6, 515, price[4], "GOLD", new Color(238,180,34).getRGB());
		button[7] = new ColorButton(parent, s+d*7, 515, price[4], "ORCHID", new Color(255,0,255).getRGB());
		button[8] = new ColorButton(parent, s+d*8, 515, price[4], "OLIVE", new Color(105,139,34).getRGB());
		button[9] = new ColorButton(parent, s+d*9, 515, price[5], "GUESS", Color.GRAY.getRGB());
	
	}
	
	
	// update screen content
	public void display(){
		
		// background
		this.parent.fill(50, 7, 250, 200);
		this.parent.rect(0, 450, 800, 200);
		parent.image(bg, 0, 450, 800, 200);
		
		hs.update();
		
		// button
		for(ColorButton btn : button){
			btn.setPosition(hs.getPos());
			btn.display();
		}
		hs.display();
		
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public ColorButton[] getButtons(){
		return button;
	}
	
}