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
	
	
	// Constructor: initialize
	public AttackWindow(MainApplet p, ArrayList<Player> list){
		
		parent = p;
		
		// set window size
		setSize(400, 700);
		
		//construct "otherPlayers"
		int j=0;
		for(Player player: list){
			if(list.get(j).getName()!=parent.getPlayer().getName()) otherPlayers.add(player);
			j++;
		}
		
		// create button through list
		btn = new Button[otherPlayers.size()];
		playerName = new String[otherPlayers.size()];
		int i=0;
		for(Player player: otherPlayers){
			btn[i] = new Button(this, 100, 80+100*i, 60, player.getColor());
			playerName[i] = player.getName();
			i++;
		}
		
		//TODO 取消按鈕初始
		
	}
	
	
	// update screen content
	public void draw(){
		
		// background
		background(0, 0, 0);
		
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
	
	
	// control mouse moved
	public void mouseMoved(){
		
		for(Button b : btn){
			
			if(b.inBtn()) b.setOver(true);
			else b.setOver(false);
			
		}
		
	}


	/**-----------------------------------------------
	 * ↓ seter and geter
	 ----------------------------------------------**/
	
	public void setWindow(JFrame w){
		window = w;
	}

}
