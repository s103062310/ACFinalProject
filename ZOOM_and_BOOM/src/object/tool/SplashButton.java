package object.tool;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PImage;
import javax.imageio.ImageIO;
import ddf.minim.signals.*;
import java.awt.Color;

/**
* Creates Buttons with Splash image and text, wraps text
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
	private String text;
	private int textSize;
	//Public
	public int x, y;
	public Color color;
	
	// Constructor with default width and height
	public SplashButton(PApplet p, int x, int y, Color color, String text){
		this.parent = p;
		this.x = x;
		this.y = y;
		this.color = color;
		this.text = text;
		//adjust textsize
		this.textSize = 34;
		parent.textSize(textSize);
		while(parent.textWidth(text) > normalWidth - 120 ){
			textSize--;
			parent.textSize(textSize);
		}
		//loadImage
		try { 
			splashImg = parent.loadImage("resource/splash.png");
			setSplash(color);
		}
		catch(Exception ex){
			System.err.println("Unable to laod Splash Image");
			ex.printStackTrace();
		}

	}
	
	//Constructor with costumized values
	public SplashButton(PApplet p, int x, int y, int height, int width, Color color, String text){
		this(p, x, y, color, text);
		this.normalWidth = height;
		this.normalHeight = width;
		this.selectedWidth = height + normSelDifference;
		this.selectedHeight = width + normSelDifference;
	}
	
	// draw button
	public void display(){
		//display selected button
		if (checkLimits()){
			splashImg.resize(selectedWidth,selectedHeight);
			parent.image(splashImg,x-normSelDifference/2,y-normSelDifference/2);
		}
		//display unselected button
		else{
			splashImg.resize(normalWidth,normalHeight);
			parent.image(splashImg,x,y);
		}
		//display text
		parent.fill(255);
		parent.textSize(textSize);
		parent.text(text, x + 47, y + normalHeight/2 + 10);
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

