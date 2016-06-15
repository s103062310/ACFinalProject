package object.client;

import processing.core.PApplet;
import processing.core.PImage;
import java.io.Serializable;
public class Splash  implements Serializable{
	
	private transient PApplet parent;
	private transient PImage img;
	private int x, y, d, color;
	private int lastTime=0, trans=255;
	
	
	// Constructor
	public Splash(PApplet p, int x, int y, int t, int c){
		
		parent = p;
		img = parent.loadImage("src/resource/other_images/splash.png");
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
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getColor(){
		return color;
	}
	
}
