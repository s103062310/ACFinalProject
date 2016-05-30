package main;

import java.awt.Color;
import object.server.Player;
import object.tool.VScrollbar;
import processing.core.PImage;

//TODO potential bug: 前五名分數相同的話是5個黃冠/只有幸運的前3個是?

public class Scoreboard {

	// content
	private VScrollbar vs;
	public float scroll=0;
	private PImage img;
	
	// resources
	private MainApplet parent;
	

	// Constructor
	public Scoreboard(MainApplet p) {
		
		this.parent = p;
		this.vs = new VScrollbar(1080, 80, 20, 370, 16, parent);
		this.img = parent.loadImage("src/resource/crown.png");
		
	}
	
	
	// update screen content
	public void display() {
		
		// Players' area (background)
		parent.stroke(130, 210, 75);
		parent.strokeWeight(10);
		parent.fill(0 ,0 ,0);
		parent.rect(805,0, 290, 460);
		parent.noStroke();
		
		// set
		parent.textSize(20);
		vs.update();
		scroll = vs.getspos();
		int num = parent.getList().size();
		int radius = Math.max(50-6*num/3, 32);
		
		// players' information
		for (int i = 0; i < num; i++) {
			
			Player pl = parent.getList().get(i);
			parent.fill(pl.getColor());
			parent.ellipse(840, 190-scroll+i*(radius+15), radius, radius);
			parent.text(pl.getName(), 870, 195-scroll + i*(radius+15));
			parent.text("#"+pl.getScore(), 1000, 195-scroll + i*(radius+15));
			
			// self
			if (parent.getPlayer().getName().equals(pl.getName())) {
				parent.fill(Color.PINK.getRGB());
				parent.ellipse(840, 190-scroll+i*(radius+15), 20, 20);
			}
			
		}
		
		// crown
		if(num>=5){
			for(int i=0; i<3; i++){
				parent.image(img, 840, 190-scroll+i*(radius+15)-4*radius/5, 20, 20);
			}
		}
		
		// scroll bar
		vs.display();
		
		// Score Board area
		this.parent.fill(205, 0, 0);
		this.parent.rect(800, 0, 300, 80);
		
		// text "Score Board"
		parent.textSize(42);
		this.parent.fill(0, 0, 205);
		parent.text("Score Board", 820, 50);
		this.parent.fill(0, 191, 255);
		parent.text("Score Board", 822, 52);
		
		// my score	(background)
		parent.stroke(130, 210, 75);
		parent.strokeWeight(10);
		parent.fill(0, 0, 0);
		parent.rect(805, 455, 290, 190);
		parent.noStroke();
		
		// own information
		parent.fill(parent.getPlayer().getColor()); 
		parent.ellipse(860, 510, 60, 60);
		parent.textSize(28); 
		parent.text(parent.getPlayer().getName(), 950, 520);
		parent.textSize(20);
		parent.text("coin : "+parent.getPlayer().getScore(), 830, 570);
		parent.text("completed : "+parent.getPlayer().getCompleted(), 830, 600);
		parent.text("shield : "+parent.getPlayer().getShield(), 830, 630);
		
	}

}
