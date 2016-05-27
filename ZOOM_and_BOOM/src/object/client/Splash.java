package object.client;

import processing.core.PApplet;
import processing.core.PImage;
import java.awt.Color;

public class Splash {
	
	private PApplet parent;
	private PImage img;
	private Color color;
	private int x, y, lastTime=0, d, trans=255;
	
	
	// Constructor
	public Splash(PApplet p, int x, int y, int t, Color c){
		
		parent = p;
		img = parent.loadImage("src/resource/splash.png");
		color = c;
		setSplash();
		this.x = x;
		this.y = y;
		this.d = t*2000/255;
		
	}
	
	
	// draw splash
	public void display(){
		
		// change transparent
		if(parent.millis()-lastTime>=d){
			trans--;
		}
		
		// draw
		parent.tint(255, trans);
		parent.image(img, x, y, 420, 340);
		parent.tint(255, 255);
		
	}
	
	
	// produce new color of splash
	private void setSplash(){
		
		int color = this.color.getRGB();
		for(int i=0; i<img.pixels.length; i++){
			if(img.pixels[i]==-16777216) img.pixels[i] = color;
			else img.pixels[i] = 16777215;
		}
		
	}

	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public int getTrans(){
		return trans;
	}
	
}
