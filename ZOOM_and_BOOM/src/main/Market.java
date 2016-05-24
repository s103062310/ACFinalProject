package main;

import java.awt.Color;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PFont;

@SuppressWarnings("serial")
public class Market{
	
	private MainApplet parent;
	public int money = 100;
	public int click_yellow, click_green, click_blue, click_red, click_random;
	
	// Constructor
	public Market(MainApplet p){
		this.parent = p;
		/*PFont font = parent.createFont("resource/msjh.ttc", 15, true);
		cp5 = new ControlP5(parent);
		cp5.setFont(font);
		cp5.setColorCaptionLabel(Color.BLACK.getRGB());
		cp5.addButton("Own_color").setPosition(20, 570).setSize(100,40);
		this.parent.fill(Color.YELLOW.getRGB());
		parent.ellipse(140, 530, 100, 100);
		cp5.addButton("Yellow").setLabel("Yellow").setPosition(140, 570).setSize(100,40).setColorBackground(Color.YELLOW.getRGB()).setColorActive(Color.YELLOW.brighter().getRGB());
		cp5.addButton("Green").setPosition(260, 570).setSize(100,40).setColorBackground(Color.GREEN.getRGB()).setColorActive(Color.GREEN.brighter().getRGB());
		cp5.addButton("Blue").setPosition(380, 570).setSize(100,40).setColorBackground(Color.BLUE.getRGB()).setColorActive(Color.BLUE.brighter().getRGB());
		cp5.addButton("Red").setPosition(500, 570).setSize(100,40).setColorBackground(Color.RED.getRGB()).setColorActive(Color.RED.brighter().getRGB());
		cp5.addButton("Random").setPosition(620, 570).setSize(100,40).setColorBackground(Color.GRAY.getRGB()).setColorActive(Color.GRAY.brighter().getRGB());*/
	}
	
	// update screen content
	public void display(){
		this.parent.fill(50, 7, 250, 200);
		this.parent.rect(0, 450, 800, 200);
		parent.noStroke();
		parent.fill(Color.BLACK.getRGB());
		parent.ellipse(70, 510, 70, 70);
		parent.fill(Color.YELLOW.getRGB());
		parent.ellipse(190, 510, 70, 70);
		parent.rect(140, 570, 100,40);
		parent.fill(Color.GREEN.getRGB());
		parent.ellipse(310, 510, 70, 70);
		parent.rect(260, 570, 100,40);
		parent.fill(Color.BLUE.getRGB());
		parent.ellipse(430, 510, 70, 70);
		parent.rect(380, 570, 100,40);
		parent.fill(Color.RED.getRGB());
		parent.ellipse(550, 510, 70, 70);
		parent.rect(500, 570, 100,40);
		parent.fill(Color.GRAY.getRGB());
		parent.ellipse(670, 510, 70, 70);
		parent.rect(620, 570, 100,40);
		parent.fill(0);
		parent.textSize(20);
		parent.text("Own_color", 20, 600);
		if(Yellow()){
			parent.fill(200);
			parent.textSize(25);
		}
		parent.text("Yellow", 155, 600);
		parent.fill(0);
		parent.textSize(20);
		if(Green()){
			parent.fill(200);
			parent.textSize(25);
		}
		parent.text("Green", 280, 600);
		parent.fill(0);
		parent.textSize(20);
		if(Blue()){
			parent.fill(200);
			parent.textSize(25);
		}
		parent.text("Blue", 410, 600);
		parent.fill(0);
		parent.textSize(20);
		if(Red()){
			parent.fill(200);
			parent.textSize(25);
		}
		parent.text("Red", 530, 600);
		parent.fill(0);
		parent.textSize(20);
		if(Random()){
			parent.fill(200);
			parent.textSize(25);
		}
		parent.text("Random", 625, 600);
		parent.fill(0);
		parent.textSize(20);
		parent.text("Money: "+money, 550, 635);
	}
	
	public boolean Yellow(){
		if (parent.mouseX >= 140 && parent.mouseX <= 240 && 
			parent.mouseY >= 570 && parent.mouseY <= 610) {
			return true;
		} else {
			return false;
		}
	}
	public boolean Green(){
		if (parent.mouseX >= 260 && parent.mouseX <= 360 && 
			parent.mouseY >= 570 && parent.mouseY <= 610) {
			return true;
		} else {
			return false;
		}
	}
	public boolean Blue(){
		if (parent.mouseX >= 380 && parent.mouseX <= 480 && 
			parent.mouseY >= 570 && parent.mouseY <= 610) {
			return true;
		} else {
			return false;
		}
	}
	public boolean Red(){
		if (parent.mouseX >= 500 && parent.mouseX <= 600 && 
			parent.mouseY >= 570 && parent.mouseY <= 610) {
			return true;
		} else {
			return false;
		}
	}
	public boolean Random(){
		if (parent.mouseX >= 620 && parent.mouseX <= 720 && 
			parent.mouseY >= 570 && parent.mouseY <= 610) {
			return true;
		} else {
			return false;
		}
	}
	public void test(){
		System.out.println("123");
	}
}