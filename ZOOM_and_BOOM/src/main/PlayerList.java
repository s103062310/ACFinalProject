package main;
import java.io.Serializable;
import java.util.ArrayList;


final class PlayerList implements Serializable{
	public final ArrayList<Player> players;

	public PlayerList(ArrayList<Player> players){
		this.players = players;
	}
	/*
	public void setPlayers(ArrayList<Player> players){
		this.players = players;
	}
	public ArrayList<Player> getPlayers(){
		return this.players;
	}*/
	
}
