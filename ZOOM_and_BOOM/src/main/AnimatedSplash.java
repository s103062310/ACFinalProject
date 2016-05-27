package main;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;


public class AnimatedSplash {

	private PApplet parent;
	private PImage splashImg;
	private Color color;
	public int x;
	public int y;
	
	public AnimatedSplash(PApplet parent, Color color){
		this.parent = parent;
		this.x = (int) parent.random(parent.width);
		this.y = (int) parent.random(parent.height);
		this.color = color;
		try { 
			splashImg = parent.loadImage("resource/splash.png");
		}
		catch(Exception ex){
			System.err.println("Unable to laod Splash Image");
			ex.printStackTrace();
		}
		setSplashColor();
	}
	
	public void display(){
		randPosition();
		splashImg = parent.loadImage("resource/splash.png");
		setSplashColor();
		parent.fill(color.getRed(),color.getGreen(),color.getBlue());
		parent.tint(255,126);
		int rand = (int)parent.random(90);
		splashImg.resize(10 + rand, 10 +  rand);
		parent.image(splashImg, x, y);
		parent.tint(255,255);
		parent.delay(500);
	}
	
	public void randPosition(){
		x = (int) parent.random(parent.width);
		y = (int) parent.random(parent.height);
	}
	
	private void setSplashColor(){
		int splashColor = color.getRGB();
		for(int i=0; i<splashImg.pixels.length; i++){
			if(splashImg.pixels[i]==-16777216) splashImg.pixels[i] = splashColor;
			else splashImg.pixels[i] = 16777215;
		}
	}
}
