package main;

import java.awt.Color;

public class ColorButton {
	private MainApplet parent;
	public int x,y;
	public int money;
	public String colorName;
	public int diameter = 70;
	public int color;

	public ColorButton(MainApplet p, int x, int y, String colorName, int color){
		this.parent = p;
		this.x = x;
		this.y = y;
		this.colorName = colorName;
		this.color = color;
		creatButton(x,y,colorName,color);
	}
	public void creatButton(int x, int y, String name, int color){  //+¶Ç¤JÃC¦â
		parent.noStroke();
		parent.fill(color);
		parent.ellipse(x, y, diameter, diameter);
		if(name == "YELLOW"){
			this.money = 10;
		} else if(name == "GREEN"){
			this.money = 20;
		} else if(name == "BLUE"){
			this.money = 10;
		} else if(name == "RED"){
			this.money = 15;
		} else if(name == "RANDOM"){
			this.money = 30;
		}
		parent.fill(255);
		parent.textSize(20);
		parent.text(name, x-25, y+80);
		parent.text("$"+money, x-20, y+100);
	}
}
