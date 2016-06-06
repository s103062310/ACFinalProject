package object.client;

import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PFont;

public class Wallet {
	
	private PApplet parent;
	private PImage image;
	private PFont font;
	private int money=0, w, h;
	private float x, y;
	private boolean over=false;
	
	
	// Constructor
	public Wallet(PApplet p, float x, float y, int w, int h){
		
		this.parent = p;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.image = parent.loadImage("src/resource/other_images/money.png");
		this.font = parent.createFont("src/resource/fonts/COOPBL.ttf",24);
		
	}
	
	
	// update screen content
	public void display(){
		
		if(over) parent.image(image, x-5, y-5, w+10, h+10);
		else parent.image(image, x, y, w, h);
		parent.textFont(font);
		if(money>=100) parent.text("+"+money, x+10, y+40);
		else if(money>=10) parent.text("+  "+money, x+10, y+40);
		else parent.text("+    "+money, x+10, y+40);
		
	}
	
	
	// detect image move into wallet
	public boolean in(float x, float y){
		if(x>=this.x&&x<=this.x+this.w&&y>=this.y&&y<=this.y+this.h) return true;
		else return false;
	}
	
	
	// add money
	public void add(int amount){
		this.money += amount;
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public int getMoney(){
		return this.money;
	}

	public void reset(){
		this.money = 0;
	}

	public void setIn(boolean b){
		this.over = b;
	}
	
	public boolean isIn(){
		return this.over;
	}
	
}
