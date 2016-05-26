package main;

import processing.core.PApplet;

public class Answer {
	
	int dataNum=0;
	float x, y, width, height;
	float x2, y2, w2, h2;
	
	public boolean check(float x, float y, float width, float height){
		System.out.println("("+x+", "+y+") , width: "+width+" height: "+height);
		if(dataNum==0){
			dataNum++;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			return true;
		} else if(dataNum==1){
			dataNum++;
			this.x2 = x;
			this.y2 = y;
			this.w2 = width;
			this.h2 = height;
			return true;
		} else {
			calAverage(x, y, width, height);
			if(PApplet.dist(x, y, this.x, this.y)>30) return false;
			if(PApplet.abs(width-this.width)>30) return false;
			if(PApplet.abs(height-this.height)>30) return false;
			dataNum++;
			System.out.println("=> Correct");
			return true;
		}
	}
	
	private void calAverage(float x, float y, float w, float h){
		
		float avg_x = (this.x + this.x2 + x)/3;
		float avg_y = (this.y + this.y2 + y)/3;
		float avg_w = (this.width + this.w2 + w)/3;
		float avg_h = (this.height + this.h2 + h)/3;
		
		if(PApplet.abs(this.x-avg_x)>PApplet.abs(this.x2-avg_x)&&PApplet.abs(this.x-avg_x)>PApplet.abs(x-avg_x))
			this.x = (this.x2 + x)/2;
		else if(PApplet.abs(this.x2-avg_x)>PApplet.abs(this.x-avg_x)&&PApplet.abs(this.x2-avg_x)>PApplet.abs(x-avg_x))
			this.x = (this.x + x)/2;
		else this.x = (this.x + this.x2)/2;
		
		if(PApplet.abs(this.y-avg_y)>PApplet.abs(this.y2-avg_y)&&PApplet.abs(this.y-avg_y)>PApplet.abs(y-avg_y))
			this.y = (this.y2 + y)/2;
		else if(PApplet.abs(this.y2-avg_y)>PApplet.abs(this.y-avg_y)&&PApplet.abs(this.y2-avg_y)>PApplet.abs(y-avg_y))
			this.y = (this.y + y)/2;
		else this.y = (this.y + this.y2)/2;
		
		if(PApplet.abs(this.width-avg_w)>PApplet.abs(this.w2-avg_w)&&PApplet.abs(this.width-avg_w)>PApplet.abs(w-avg_w))
			this.width = (this.w2 + w)/2;
		else if(PApplet.abs(this.w2-avg_w)>PApplet.abs(this.width-avg_w)&&PApplet.abs(this.w2-avg_w)>PApplet.abs(w-avg_w))
			this.width = (this.width + w)/2;
		else this.width = (this.width + this.w2)/2;
		
		if(PApplet.abs(this.height-avg_h)>PApplet.abs(this.h2-avg_h)&&PApplet.abs(this.height-avg_h)>PApplet.abs(h-avg_h))
			this.height = (this.h2 + h)/2;
		else if(PApplet.abs(this.h2-avg_h)>PApplet.abs(this.height-avg_h)&&PApplet.abs(this.h2-avg_h)>PApplet.abs(h-avg_h))
			this.height = (this.height + h)/2;
		else this.height = (this.height + this.h2)/2;
		
		System.out.println("Ans: ("+this.x+", "+this.y+") , width: "+this.width+" height: "+this.height);
	}
	
}
