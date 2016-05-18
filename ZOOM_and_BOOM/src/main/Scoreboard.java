package main;

import java.awt.Color;
import java.util.ArrayList;


public class Scoreboard{
	
	private MainApplet parent;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	
	// Constructor 
	public Scoreboard(MainApplet p){
		this.parent = p;
		/**temp player list*
		for (int i= 0; i < 5; i++){
			player player = new player();
			player.color = Color.YELLOW.getRGB();
			player.name = "Ping~";
			player.score = 100;
			playerList.add(player);
		}
		*/
		
	}
	
	// update screen content
	public void display(){
		/*
		this.parent.fill(40, 50, 60, 200);
		this.parent.rect(800, 0, 300, 650);
		
		parent.textSize(40);
		parent.text("Score Board", 830, 45);
		
		//other player's score
		parent.noStroke();
		parent.textSize(20);
		for (int i = 0; i < playerList.size(); i++){
			parent.fill(playerList.get(i).color);
			parent.ellipse(820, 70+i*50, 30, 30);
			parent.text(playerList.get(i).name, 850, 75+i*50);
			parent.text(playerList.get(i).score, 1000, 75+i*50);
		}
		
		//my score
		parent.fill(Color.PINK.getRGB());
		parent.ellipse(830, 510, 50, 50);
		parent.textSize(30);
		parent.text("Ping~~~", 860, 520);
		*/
	}
	/*
	public void setPlayerList(ArrayList<player> playerList){
		this.playerList = playerList;
	}
	
	class player{
		int color;
		int score;
		String name;
	}*/
}
