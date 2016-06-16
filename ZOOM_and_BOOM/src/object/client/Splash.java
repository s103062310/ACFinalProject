package object.client;

import processing.core.PApplet;
import processing.core.PImage;
import java.io.Serializable;

public class Splash implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private transient PApplet parent;
	private transient PImage img;
	private int x, y, d, color, trans;
	private int lastTime=0;
	
	
	// Constructor 1
	public Splash(PApplet p, int x, int y, int t, int c){
		
		parent = p;
		img = parent.loadImage("resource/other_images/splash.png");
		color = c;
		setSplash();
		this.x = x;
		this.y = y;
		this.d = t*1000/255;
		this.trans = 255;
		
	}
	
	
	// Constructor 2
	public Splash(PApplet p, Splash spl){
		
		this(p, spl.x, spl.y, 10, spl.color);
		this.trans = spl.trans;
		
	}
	
	
	// draw splash
	public void display(){
		
		// change transparent
		if(parent.millis()-lastTime>=d){
			trans--;
			lastTime = parent.millis();
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
	
}
