package object.server;

import java.awt.Color;

import javax.swing.JFrame;

import processing.core.PApplet;
import processing.core.PFont;

public class WaitWindow extends PApplet{
	
	private static final long serialVersionUID = 1L;
	
	// GUI
	private JFrame window;
	private static final int width = 600;
	private static final int height = 350;
	private PFont font;
	
	// Wait
	private int rand=0;
	private Color[] splashColors;
	private String text;
	
	// Constructor
	public WaitWindow(String txt){
		this.text = txt;
	}
	
	// Processing Setup
	public void setup() {
		
		background(255);
		frameRate(500);
		size(width, height);
		smooth();
		noStroke();
		font = createFont("../resource/fonts/HappyGiraffe.ttf",32);
		textFont(font);
		
		//Wait animation
		setColors();
		rand = (int) random(12);
		
	}

	
	// Processing Draw
	public void draw(){
		
		if (frameCount % 30 == 0){
		    rand = (int) random(12);
		    text(text + " DATABASE...",(width-textWidth("UPDATING DATABASE..."))/2,300);
		}
		if (frameCount % 2 == 0) {
		    fill(splashColors[rand].getRed(),splashColors[rand].getGreen(),splashColors[rand].getBlue());
		    pushMatrix();
		    translate(300, 175);
		    rotate(radians(frameCount/2  % 360));
		    rect(0, 25, 50, 2);
		    popMatrix();  
		}
		
	}
	
	// set colors
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
	
	//Runs login JFrame, returns control to caller when login is successful
	public void runFrame(){
		
		window = new JFrame("ZOOM and BOOM - Database");
		window.setContentPane(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600, 400);
		window.setLocation(400,200);
		window.setVisible(true);

	}
	
	// close frame
	public void closeFrame(){
		window.dispose();
	}

}
