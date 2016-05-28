package object.client;

import java.util.ArrayList;
import javax.swing.JFrame;
import object.server.Player;
import object.tool.Button;
import main.MainApplet;
import processing.core.PApplet;

//TODO 試做 scrollbar
//TODO 需要一點美工設計(?

public class AttackWindow extends PApplet{
	
	private static final long serialVersionUID = 1L;
	
	// resources
	private JFrame window;
	private MainApplet parent;
	
	// content
	private Button[] btn;
	private String[] playerName;
	private ArrayList<Player> otherPlayers = new ArrayList<Player>();
	//TODO 新增取消按鈕~
	
	
	// initialize
	public void setup(){
		
		// set window size
		setSize(400, 700);
		
		//TODO 取消按鈕初始

	}
	
	
	// update screen content
	public void draw(){
		
		for(int i=0; i<btn.length; i++){
			
			btn[i].display();
			textSize(20);
			text(playerName[i], 200, 80+100*i);
			
		}
		
		//TODO 畫取消按鈕
		
	}
	
	
	// control mouse clicked
	public void mouseClicked(){
		
		for(int i=0; i<btn.length; i++){
			
			if(btn[i].inBtn()){
				
				//TODO call method to modify money in MainApplet
				//TODO call method to attack in MainApplet
				window.dispose();
				
			}
			
		}
		
		//TODO 取消按鈕點選 -> 什麼都不做只關閉視窗
		
	}
	
	
	// establish list and button of other players
	public void constructPlayer(ArrayList<Player> List, int id){
		
		// create list
		for(Player player: List){
			if(player.getID()!= id) otherPlayers.add(player);
		}
		
		// create button through list
		btn = new Button[otherPlayers.size()];
		playerName = new String[otherPlayers.size()];
		int i = 0;
		for(Player player: otherPlayers){
			btn[i] = new Button(this, 100, 80+100*i, 60, player.getColor());
			playerName[i] = player.getName();
			i++;
		}
		
	}


	/**-----------------------------------------------
	 * ↓ seter and geter
	 ----------------------------------------------**/
	
	public void setWindow(JFrame w){
		window = w;
	}
	
	public void setMainApplet(MainApplet p){
		//TODO
	}

}
