package main;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class AnimatedBackground extends PApplet{

	private static final long serialVersionUID = 1L;
	
	private final int width = 600;
	private final int height = 300;
	private AnimatedSplash[] splashArr;
	private Color[] splashColors;
	private int splashInTurn;
	private PImage backgroundImg;
	
	public void setup(){
		size(width,height);
		background(255);
		try { 
			backgroundImg = loadImage("resource/pic_rsc/M" + (int) random (15)+ ".png");
		}
		catch(Exception ex){
			System.err.println("Unable to laod Splash Image");
			ex.printStackTrace();
		}
		backgroundImg.resize(width, height);
		image(backgroundImg, 0, 0);
		setColors();
		splashArr = new AnimatedSplash[50];
		for( int i=0; i<50 ; i++){
			splashArr[i] = new AnimatedSplash( this, splashColors[(int)random(11)] );
		}

	}
	
	public void draw(){
		smooth();

		if (splashInTurn>=49){
			background(255);
			try { 
				backgroundImg = loadImage("resource/pic_rsc/M" + (int) random (15)+ ".png");
			}
			catch(Exception ex){
				System.err.println("Unable to laod Splash Image");
				ex.printStackTrace();
			}
			backgroundImg.resize(width, height);
			image(backgroundImg, 0, 0);
			splashInTurn = 0;
		}
		else{
			splashInTurn++;	
			//splashArr[splashInTurn].display();
		}
		//delay(500);
	
	}
		
	//set Color array
	private void setColors(){
		splashColors = new Color[12];
		splashColors[0]  = new Color(255,0,128);//PINK
		splashColors[1]  = new Color(255,0,0); //RED
		splashColors[2]  = new Color(255,128,0); //ORANGE
		splashColors[3]  = new Color(255,255,0);//YELLOW
		splashColors[4]  = new Color(128,255,0); //LIME GREEN
		splashColors[5]  = new Color(0,255,0);//GREEN
		splashColors[6]  = new Color(0,255,128);//AQUA
		splashColors[7]  = new Color(0,255,255);//CYAN
		splashColors[8]  = new Color(0,128,255);//LIGHT BLUE
		splashColors[9]  = new Color(0,255,255);//BLUE
		splashColors[10] = new Color(128,0,255);//PURPLE
		splashColors[11]  = new Color(255,0,255);//MAGENTA
	}

}
