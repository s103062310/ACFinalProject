package object.client;

import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PFont;

public class Wallet {
	
	private PApplet parent;
	private PImage image;
	private PFont font;
	private int money=0, f=24, w, h, nw, nh;
	private float x, y, nx, ny;
	private boolean over=false, move=false;
	
	
	// Constructor
	public Wallet(PApplet p, float x, float y, int w, int h){
		
		this.parent = p;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.nx = x;
		this.ny = y;
		this.nw = w;
		this.nh = h;
		this.image = parent.loadImage("resource/other_images/money.png");
		this.font = parent.createFont("resource/fonts/COOPBL.ttf", f);
		
	}
	
	
	// update screen content
	public void display(){
		
		// draw wallet
		if(over) parent.image(image, nx-5, ny-5, nw+10, nh+10);
		else parent.image(image, nx, ny, nw, nh);
		parent.textFont(font);
		parent.fill(255, 0, 0);
		if(money>=100) parent.text("+"+money, nx+nw/9, ny+nh*2/3);
		else if(money>=10) parent.text("+  "+money, nx+nw/9, ny+nh*2/3);
		else parent.text("+    "+money, nx+nw/9, ny+nh*2/3);
		
		// move
		if(move){
			f+=25;
			font = parent.createFont("resource/fonts/COOPBL.ttf", f);
			if(nx>=220) nx -= 120;
			if(ny>=105) ny -= 68.75;
			if(nw<=360) nw += 90;
			if(nh<=240) nh += 60;
			if(nx<=220&&ny<=105&&nw>=360&&nh>=240) move = false;
		}
		
	}
	
	
	// detect image move into wallet
	public boolean in(float x, float y){
		if(x>=this.nx&&x<=this.nx+this.nw&&y>=this.ny&&y<=this.ny+this.nh) return true;
		else return false;
	}
	
	
	// add money
	public void add(int amount){
		this.money += amount;
	}
	
	
	// reset wallet
	public void reset(){
		
		this.over = false;
		this.money = 0;
		this.nx = x;
		this.ny = y;
		this.nw = w;
		this.nh = h;
		this.f = 24;
		this.font = parent.createFont("resource/fonts/COOPBL.ttf", f);
	
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public int getMoney(){
		return this.money;
	}

	public void setOver(boolean b){
		this.over = b;
	}
	
	public boolean isOver(){
		return this.over;
	}
	
	public void setMove(){
		this.move = true;
	}
	
}
