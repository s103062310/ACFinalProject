package object.client;

import java.awt.Color;
import processing.core.PApplet;
import processing.core.PImage;
import object.tool.Button;

public class ConfirmWindow extends PApplet {
	
	private static final long serialVersionUID = 1L;
	
	// content
	private PImage img;
	private Button btn;
	
	
	// Constructor
	public ConfirmWindow(PImage img){
		
		this.img = img;
		this.btn = new Button(this, 200, 200, 50, new Color(0, 0, 0).getRGB());
		
	}
	
	
	// update screen content
	public void draw(){
		
		image(img, 0, 0, 800, 450);
		btn.display();
		
	}

}
