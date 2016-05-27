package main;

import java.awt.Color;
import java.util.ArrayList;
import object.server.Player;


import processing.core.*;

public class Scoreboard {

	private MainApplet parent;
	private ArrayList<Player> playerList;
	public float scroll;
	private int id;
	private Player player;
	PImage img;

	public Scoreboard(MainApplet p) {
		this.parent = p;
		scroll = 0;
		playerList = new ArrayList<Player>();
		img = parent.loadImage("src/resource/crown.png");
	}
	public void setMyself(int id){
		this.id=id;
	}
	public void setPlayerList(ArrayList<Player> list) {
		this.playerList = list;
	}
	
	public void setScroll(float sc){
		this.scroll = sc;
	}
	// update screen content
	public void display() {
		// Players' area
		parent.stroke(130, 210, 75);
		parent.strokeWeight(10);
		parent.fill(0 ,0 ,0);
		parent.rect(805,0, 290, 460);
		parent.noStroke();
		
		parent.textSize(20);
		
		int num = playerList.size();
		int radius = Math.max(50-6*num/3,32);
		
		for (int i = 0; i < num; i++) {
			Player pl = playerList.get(i);
			parent.fill(pl.getColor());
			parent.ellipse(840, 190-scroll+i*(radius+15), radius, radius);
			parent.text(pl.getName(), 870, 195-scroll + i*(radius+15));
			parent.text("#"+pl.getScore(), 1000, 195-scroll + i*(radius+15));
			// self
			if (this.id == pl.getID()) {
				parent.fill(Color.PINK.getRGB());
				parent.ellipse(840, 190-scroll+i*(radius+15), 20, 20);
				player=pl;
			}
		}
		if(num>=5){
			for(int i=0;i<3;i++){
				parent.image(img, 840, 190-scroll+i*(radius+15)-4*radius/5, 20, 20);
			}
		}
		
		// Score Board area
		this.parent.fill(205,0,0);
		this.parent.rect(800,0,300,80);
		// text "Score Board"
		parent.textSize(42);
		this.parent.fill(0,0,205);
		parent.text("Score Board", 820, 50);
		this.parent.fill(0,191,255);
		parent.text("Score Board", 822, 52);
		// my score	
		parent.stroke(130, 210, 75);
		parent.strokeWeight(10);
		parent.fill(new Color(0,0,0).getRGB());
		parent.rect(805, 455, 290, 190);
		parent.noStroke();
		parent.fill(player.getColor()); 
		parent.ellipse(860, 510, 60, 60);
		parent.textSize(28); 
		parent.text(player.getName(), 950, 520);
		parent.textSize(20);
		parent.text("coin : "+player.getScore(), 830, 570);
		parent.text("completed : 50", 830, 600);
		parent.text("shield : 3", 830, 630);
		
	}

}
