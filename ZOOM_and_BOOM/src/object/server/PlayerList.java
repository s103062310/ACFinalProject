package object.server;

import java.io.Serializable;
import java.util.ArrayList;

final class PlayerList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
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
