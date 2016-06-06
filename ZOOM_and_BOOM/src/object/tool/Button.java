package object.tool;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class Button {
	
	protected PApplet parent;
	protected PImage image;
	protected ArrayList<PImage> animate = new ArrayList<PImage>();
	protected int color, width, height;
	protected float x, y, d, centerX, centerY, dis, scroll=0;
	protected boolean over;
	
	
	// Constructor - circle
	public Button(PApplet p, float x, float y, float d, int c){
		
		this.parent = p;
		this.x = x;
		this.y = y;
		this.d = d;
		this.over = false;
		this.color = c;
		this.centerX = x;
		this.centerY = y;
		this.dis = d/2;
		
	}
	
	
	// Constructor - image
	public Button(PApplet p, float x, float y, int w, int h, int dummy){
		
		this.parent = p;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.centerX = x + w/2;
		this.centerY = y + h/2;
		this.dis = (w+h)/4;
		
	}
	
	
	// draw button - circle version
	public void display_circle(){
		
		// button
		parent.noStroke();
		parent.fill(color);
		if(over) parent.ellipse(x-scroll, y, d+10, d+10);
		else parent.ellipse(x-scroll, y, d, d);
		
	}
	
	
	// draw button - image version
	public void display_image(){
		
		// button
		if(over) parent.image(image, x-5, y-5, width+10, height+10);
		else parent.image(image, x, y, width, height);
		
	}
	

	// detect mouse move into button
	public boolean inBtn(){
		if(PApplet.dist(parent.mouseX, parent.mouseY, centerX-scroll, centerY)<=dis)
			return true;
		else return false;
	}
	
	
	// add resource image to list
	public void addImage(String path){
		animate.add(parent.loadImage(path));
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public void setOver(boolean b){
		this.over = b;
	}
	
	public void setPosition(float scrollbar){
		this.scroll = scrollbar/(float)1.5;
	}
	
	public void setImage(int index){
		image = animate.get(index);
	}
	
}
