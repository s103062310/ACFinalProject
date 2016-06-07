package object.server;

import processing.core.PApplet;

public class Answer {
	
	// answer information
	int dataNum=0;
	float x, y, width, height;
	float x2, y2, w2, h2;
	
	//Default Constructor
	public Answer(){
		//empty
	}
	
	public Answer(float width, float height, float x, float y){
		this.width=width;
		this.height=height;
		this.x=x;
		this.y=y;
	}
	
	public Answer(float width, float height, float x, float y, int datanum){
		this(width,height,x,y);
		this.dataNum=datanum;
	}
	
	// compare answers
	public boolean check(float x, float y, float width, float height){
		
		// player's answer
		System.out.println("("+x+", "+y+") , width: "+width+" height: "+height);
		
		// compare method
		if(dataNum==0){	
			dataNum++;// always correct
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			return true;
			
		} else if(dataNum==1){		// always correct
			
			dataNum++;
			this.x2 = x;
			this.y2 = y;
			this.w2 = width;
			this.h2 = height;
			return true;
			
		} else if(dataNum==2) {		// always correct
			
			// decide correct answer
			calAverage(x, y, width, height);
			return true;
			
		} else {
			
			// judge the correctness of answer
			if(PApplet.dist(x, y, this.x, this.y)>50) return false;
			if(PApplet.abs(width-this.width)>50) return false;
			if(PApplet.abs(height-this.height)>50) return false;
			
			// correct => add to average
			System.out.println("=> Correct");
			updateAverage(x, y, width, height);
			return true;
			
		}
		
	}

	// calculate current average
	private void calAverage(float x, float y, float w, float h){
		
		// average of three
		float avg_x = (this.x + this.x2 + x)/3;
		float avg_y = (this.y + this.y2 + y)/3;
		float avg_w = (this.width + this.w2 + w)/3;
		float avg_h = (this.height + this.h2 + h)/3;
		
		// decide x
		if(PApplet.abs(this.x-avg_x)>PApplet.abs(this.x2-avg_x)&&PApplet.abs(this.x-avg_x)>PApplet.abs(x-avg_x))
			this.x = (this.x2 + x)/2;
		else if(PApplet.abs(this.x2-avg_x)>PApplet.abs(this.x-avg_x)&&PApplet.abs(this.x2-avg_x)>PApplet.abs(x-avg_x))
			this.x = (this.x + x)/2;
		else this.x = (this.x + this.x2)/2;
		
		// decide y
		if(PApplet.abs(this.y-avg_y)>PApplet.abs(this.y2-avg_y)&&PApplet.abs(this.y-avg_y)>PApplet.abs(y-avg_y))
			this.y = (this.y2 + y)/2;
		else if(PApplet.abs(this.y2-avg_y)>PApplet.abs(this.y-avg_y)&&PApplet.abs(this.y2-avg_y)>PApplet.abs(y-avg_y))
			this.y = (this.y + y)/2;
		else this.y = (this.y + this.y2)/2;
		
		// decide w
		if(PApplet.abs(this.width-avg_w)>PApplet.abs(this.w2-avg_w)&&PApplet.abs(this.width-avg_w)>PApplet.abs(w-avg_w))
			this.width = (this.w2 + w)/2;
		else if(PApplet.abs(this.w2-avg_w)>PApplet.abs(this.width-avg_w)&&PApplet.abs(this.w2-avg_w)>PApplet.abs(w-avg_w))
			this.width = (this.width + w)/2;
		else this.width = (this.width + this.w2)/2;
		
		// decide h
		if(PApplet.abs(this.height-avg_h)>PApplet.abs(this.h2-avg_h)&&PApplet.abs(this.height-avg_h)>PApplet.abs(h-avg_h))
			this.height = (this.h2 + h)/2;
		else if(PApplet.abs(this.h2-avg_h)>PApplet.abs(this.height-avg_h)&&PApplet.abs(this.h2-avg_h)>PApplet.abs(h-avg_h))
			this.height = (this.height + h)/2;
		else this.height = (this.height + this.h2)/2;
		
		System.out.println("Ans: ("+this.x+", "+this.y+") , width: "+this.width+" height: "+this.height);
	
	}
	
	
	// add new data to answer
	private void updateAverage(float x, float y, float w, float h){
		
		dataNum++;
		this.x = (this.x + x)/2;
		this.y = (this.y + y)/2;
		this.width = (this.width + w)/2;
		this.height = (this.height + h)/2;
		System.out.println("Ans: ("+this.x+", "+this.y+") , width: "+this.width+" height: "+this.height);
		
	}
	
	
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public int getX(){
		return (int)this.x;
	}
	
	public int getY(){
		return (int)this.y;
	}
	
	public int getW(){
		return (int)this.width;
	}
	
	public int getH(){
		return (int)this.height;
	}

}
