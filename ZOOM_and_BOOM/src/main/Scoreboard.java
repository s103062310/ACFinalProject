package main;

import java.awt.Color;
import java.util.ArrayList;
import processing.core.*;

public class Scoreboard {

	private MainApplet parent;
	private ArrayList<Player> playerList;
	public float scroll;
	private int id;
	
	

	public Scoreboard(MainApplet p) {
		this.parent = p;
		scroll = 0;
		playerList = new ArrayList<Player>();
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
		
		this.parent.fill(0 ,0 ,0);
		//this.parent.fill(110 ,123 ,139, 200);
		this.parent.rect(800, 0, 300, 650);

		parent.textSize(42);
		this.parent.fill(0,0,205);
		parent.text("Score Board", 820, 45-scroll);
		this.parent.fill(0,191,255);
		parent.text("Score Board", 822, 47-scroll);
		
		parent.noStroke();
		parent.textSize(20);
		
		int num = playerList.size();
		int radius = Math.max(50-6*num/3,32);
		for (int i = 0; i < num; i++) {
			Player pl = playerList.get(i);
			parent.fill(pl.getColor());
			parent.ellipse(830, 90-scroll+i*(radius+15), radius, radius);
			parent.text(pl.getName(), 860, 95-scroll + i*(radius+15));
			parent.text("#"+pl.getScore(), 1000, 95-scroll + i*(radius+15));
			// self
			if (this.id == pl.getID()) {
				parent.fill(Color.PINK.getRGB());
				parent.ellipse(830, 90-scroll+i*(radius+15), 20, 20);
			}
		}
		/*
		// my score	
		parent.fill(player.getColor()); 
		parent.ellipse(830, 510, 50, 50);
		parent.textSize(30); 
		parent.text(player.getName(), 860, 520);*/
	}

}
