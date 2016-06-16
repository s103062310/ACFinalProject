package object.client;

import processing.core.PImage;
import processing.core.PApplet;

public class ImageMessage {
	
	private PApplet parent;
	private PImage image;
	private float x, y;
	private int d;
	private boolean correct;
	
	
	// Constructor
	public ImageMessage(PApplet p, String filename, float x, float y, int d){
		
		this.parent = p;
		this.x = x;
		this.y = y;
		this.d = d;
		this.image = parent.loadImage("resource/other_images/"+filename);
		if(filename.equals("correct.png")) correct = true;
		else correct = false;
		
	}
	
	
	// update screen content
	public void display(){
		
		parent.image(image, x, y, d, d);
		x += 17;
		y += 11.5;
		d -= 10;
		
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public int getD(){
		return this.d;
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
	public boolean isCorrect(){
		return correct;
	}

}
