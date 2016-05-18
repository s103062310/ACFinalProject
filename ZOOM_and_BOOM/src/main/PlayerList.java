package main;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class PlayerList implements java.io.Serializable{
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public PlayerList(ArrayList<Player> players){
		this.players = players;
	}
	
	public void setPlayers(ArrayList<Player> players){
		this.players = players;
	}
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
}
