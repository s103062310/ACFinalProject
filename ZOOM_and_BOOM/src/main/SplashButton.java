package main;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PImage;
import javax.imageio.ImageIO;
import java.awt.Color;

/**
* Creates Buttons with Splash image
* 2 constructors: 1) default height and width values, 2) customizable
* checkLimit() checks if mouse is over Button
*/

public class SplashButton {
	
	private PApplet parent;
	private PImage splashImg;
	private int normalWidth = 200;
	private int normalHeight = 130;
	private int selectedWidth = 220;
	private int selectedHeight = 150;
	private final int normSelDifference = 20;
	//Public
	public int x, y;
	public Color color;
	
	// Constructor with default width and height
	public SplashButton(PApplet p, int x, int y, Color color){
		this.parent = p;
		this.x = x;
		this.y = y;
		this.color = color;
		//loadImage
		splashImg = parent.loadImage("resource/splash.png");
		setSplash(color);
	}
	
	//Constructor with costumized values
	public SplashButton(PApplet p, int x, int y, int height, int width, Color color){
		this(p, x, y, color);
		this.normalWidth = height;
		this.normalHeight = width;
		this.selectedWidth = height + normSelDifference;
		this.selectedHeight = width + normSelDifference;
	}
	
	// draw button
	public void display(){
		if (checkLimits()){
			splashImg.resize(selectedWidth,selectedHeight);
			parent.image(splashImg,x-normSelDifference/2,y-normSelDifference/2);
		}
		else{
			splashImg.resize(normalWidth,normalHeight);
			parent.image(splashImg,x,y);
		}
	}
	
	//See if mouse is in Button
	public boolean checkLimits(){
		if (   parent.mouseX > this.x  &&  parent.mouseX < (this.x + this.normalWidth )
			&& parent.mouseY > this.y  &&  parent.mouseY < (this.y + this.normalHeight) )
			return true;
		else
			return false;
	}
	
	//set Color
	private void setSplash(Color color){
		int splashColor = color.getRGB();
		for(int i=0; i<splashImg.pixels.length; i++){
			if(splashImg.pixels[i]==-16777216) splashImg.pixels[i] = splashColor;
			else splashImg.pixels[i] = 16777215;
		}
	}
}
