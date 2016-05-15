package main;

import java.awt.Color;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;

@SuppressWarnings("serial")
public class Market extends PApplet{
	
	private MainApplet parent;
	private ControlP5 cp5;
	
	// Constructor
	public Market(MainApplet p){
		this.parent = p;
		PFont font = createFont("resource/msjh.ttc", 15, true);
		cp5 = new ControlP5(parent);
		cp5.setFont(font);
		cp5.setColorCaptionLabel(Color.BLACK.getRGB());
		cp5.addButton("Own_color").setPosition(20, 570).setSize(100,40);
		this.parent.fill(Color.YELLOW.getRGB());
		parent.ellipse(140, 530, 100, 100);
		cp5.addButton("Yellow").setPosition(140, 570).setSize(100,40).setColorBackground(Color.YELLOW.getRGB()).setColorActive(Color.YELLOW.brighter().getRGB());
		cp5.addButton("Green").setPosition(260, 570).setSize(100,40).setColorBackground(Color.GREEN.getRGB()).setColorActive(Color.GREEN.brighter().getRGB());
		cp5.addButton("Blue").setPosition(380, 570).setSize(100,40).setColorBackground(Color.BLUE.getRGB()).setColorActive(Color.BLUE.brighter().getRGB());
		cp5.addButton("Red").setPosition(500, 570).setSize(100,40).setColorBackground(Color.RED.getRGB()).setColorActive(Color.RED.brighter().getRGB());
		cp5.addButton("Random").setPosition(620, 570).setSize(100,40).setColorBackground(Color.GRAY.getRGB()).setColorActive(Color.GRAY.brighter().getRGB());
	}
	
	// update screen content
	public void display(){
		this.parent.fill(50, 7, 250, 200);
		this.parent.rect(0, 450, 800, 200);
		parent.noStroke();
		parent.fill(Color.YELLOW.getRGB());
		parent.ellipse(190, 510, 70, 70);
		parent.fill(Color.GREEN.getRGB());
		parent.ellipse(310, 510, 70, 70);
		parent.fill(Color.BLUE.getRGB());
		parent.ellipse(430, 510, 70, 70);
		parent.fill(Color.RED.getRGB());
		parent.ellipse(550, 510, 70, 70);
		parent.fill(Color.GRAY.getRGB());
		parent.ellipse(670, 510, 70, 70);
	}
	
	public void test(){
		System.out.println("123");
	}
}
