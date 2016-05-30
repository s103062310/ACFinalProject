package object.client;

import java.util.ArrayList;
import javax.swing.JFrame;
import object.server.Player;
import object.tool.Button;
import main.MainApplet;
import processing.core.PApplet;

//TODO �հ� scrollbar
//TODO �ݭn�@�I���u�]�p(?

public class AttackWindow extends PApplet{
	
	private static final long serialVersionUID = 1L;
	
	// resources
	private JFrame window;
	private MainApplet parent;
	
	// content
	private Button[] btn;
	private String[] playerName;
	//TODO �s�W�������s~
	
	
	// Constructor: initialize
	public AttackWindow(MainApplet p, ArrayList<Player> list){
		
		parent = p;
		
		// set window size
		setSize(400, 700);
		
		// create button through list
		btn = new Button[list.size()];
		playerName = new String[list.size()];
		for(int i=0; i<list.size()-1; i++){
			if(list.get(i).getName()!=parent.getPlayer().getName()){
				btn[i] = new Button(this, 100, 80+100*i, 60, parent.getPlayer().getColor());
				playerName[i] = parent.getPlayer().getName();
				i++;
			}
		}
		
		//TODO �������s��l
		
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
		
		//TODO �e�������s
		
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
		
		//TODO �������s�I�� -> ���򳣤����u��������
		
	}
	
	
	// control mouse moved
	public void mouseMoved(){
		
		for(Button b : btn){
			
			if(b.inBtn()) b.setOver(true);
			else b.setOver(false);
			
		}
		
	}


	/**-----------------------------------------------
	 * �� seter and geter
	 ----------------------------------------------**/
	
	public void setWindow(JFrame w){
		window = w;
	}

}
