package main;
import java.io.Serializable;

public class Player implements Serializable{
	private int color;		// a int of random from 0~5	// will be mapped to real color in other class
	private int score;
	private String name;
	public Player(){
		
	}
	public Player(int color , int score , String name){
		this.color=color;
		this.score=score;
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
	public int getColor(){
		return this.color;
	}
	public void setColor(int color){
		this.color=color;
	}
	public int getScore(){
		return this.score;
	}
	public void setScore(int score){
		this.score=score;
	}
}
