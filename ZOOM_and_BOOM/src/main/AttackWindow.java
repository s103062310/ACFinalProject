package main;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import object.server.Player;
import object.tool.Button;
import processing.core.PApplet;

public class AttackWindow extends PApplet{
	public Button[] btn;
	public String[] playerName;
	public Color color;
	public JFrame window;
	public Boolean isBuy;
	private ArrayList<Player> otherPlayers = new ArrayList<Player>();
	private int selfID;
	
	public void setup(){
		setSize(400, 700);
		btn = new Button[otherPlayers.size()];
		playerName = new String[otherPlayers.size()];
		int i = 0;
		for(Player player: otherPlayers){
			btn[i] = new Button(this, 100, 80+100*i, 60, player.getColor());
			playerName[i] = player.getName();
			i++;
		}
		/*btn = new Button[5];
		btn[0] = new Button(this, 100, 80, 60, Color.cyan);
		btn[1] = new Button(this, 100, 180, 60, Color.blue);
		btn[2] = new Button(this, 100, 280, 60, Color.gray);
		btn[3] = new Button(this, 100, 380, 60, Color.magenta);
		btn[4] = new Button(this, 100, 480, 60, Color.orange);*/
	}
	public void draw(){
		for(int i=0;i<btn.length;i++){
			btn[i].display();
			textSize(20);
			text(playerName[i], 200, 80+100*i);
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
	public void constructPlayer(ArrayList<Player> List, int id){
		this.selfID = id;
		for(Player player: List){
			if(player.getID()!= selfID) otherPlayers.add(player);
		}
	}
}
