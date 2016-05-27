package main;

import java.awt.Color;

import javax.swing.JFrame;

import processing.core.PApplet;

public class AttackWindow extends PApplet{
	public Button[] btn;
	public Color color;
	public JFrame window;
	public Boolean isBuy;
	
	public void setup(){
		setSize(400, 700);
		btn = new Button[5];
		btn[0] = new Button(this, 100, 80, 60, Color.cyan);
		btn[1] = new Button(this, 100, 180, 60, Color.blue);
		btn[2] = new Button(this, 100, 280, 60, Color.gray);
		btn[3] = new Button(this, 100, 380, 60, Color.magenta);
		btn[4] = new Button(this, 100, 480, 60, Color.orange);
	}
	public void draw(){
		for(int i=0;i<btn.length;i++){
			btn[i].display();
		}
	}
	public void mouseClicked(){
		for(int i=0;i<btn.length;i++){
			if(btn[i].inBtn()){
				isBuy = true;
				window.dispose();
			}
		}
	}
	
	public void setWindow(JFrame w){
		window = w;
	}
}
