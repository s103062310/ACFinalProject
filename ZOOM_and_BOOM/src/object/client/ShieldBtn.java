package object.client;

import processing.core.PApplet;

public class ShieldBtn {
	protected PApplet parent;
	protected float x, y, scroll;
	protected boolean over;
	protected int color;
	private int money;
	
	// Constructor
	public ShieldBtn(PApplet p, float x, float y, int money, int c){
		
		this.parent = p;
		this.x = x;
		this.y = y;
		this.money = money;
		this.over = false;
		this.color = c;
	}
	
	
	// draw button
	public void display(){
		
		// button
		parent.noStroke();
		parent.fill(color);
		if(over){
			parent.quad(x-scroll-42, y-17, x-scroll, y-42, x-scroll+42, y-17, x-scroll, y+42);
		}
		else {
			parent.quad(x-scroll-35, y-10, x-scroll, y-35, x-scroll+35, y-10, x-scroll, y+35);
		}
		parent.fill(0);
		parent.textSize(20);
		parent.text("Shield", x-25-scroll, y+80);
		parent.text("$"+money, x-20-scroll, y+100);
		
	}
	

	// detect mouse move into button
	public boolean inBtn(){
		if(PApplet.dist(parent.mouseX, parent.mouseY, x-scroll, y)<=30)
			return true;
		else return false;
	}
	
	public void setPosition(float scrollbar){
		this.scroll = scrollbar/(float)1.5;
	}
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public void setOver(boolean b){
		this.over = b;
	}
	public int getMoney(){
		return money;
	}
}
