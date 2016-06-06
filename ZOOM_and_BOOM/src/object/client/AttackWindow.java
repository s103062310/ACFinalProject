package object.client;

import java.util.ArrayList;
import javax.swing.JFrame;
import object.server.Player;
import object.tool.Button;
import object.tool.VScrollbar;
import main.MainApplet;
import processing.core.PApplet;
import processing.core.PImage;

//TODO 需要一點美工設計(?

public class AttackWindow extends PApplet{
	
	private static final long serialVersionUID = 1L;
	
	// scrollbar
	private VScrollbar vs;
	private float scroll;
	
	// resources
	private JFrame window;
	private MainApplet parent;
	private PImage bg;
	
	// content
	private Button[] btn;
	private ColorButton marketBtn;
	private String[] playerName;
	private ArrayList<Player> otherPlayers = new ArrayList<Player>();
	//TODO 新增取消按鈕~
	private Button cancelBtn;
	
	// Constructor: initialize
	public AttackWindow(MainApplet p, ArrayList<Player> list, ColorButton marketBtn){
		
		parent = p;
		this.marketBtn = marketBtn;
		
		// set window size
		setSize(400, 600);
		
		// set scroll & background img
		this.vs = new VScrollbar(365, 0, 17, 600, 16, this);
		this.bg = parent.loadImage("src/resource/other_images/scoreboard.PNG");
		
		//construct "otherPlayers"
		for(Player player: list){
			if(!player.getName().equals(parent.getPlayer().getName())) otherPlayers.add(player);
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
		cancelBtn = new Button(this, 300, 100, 50, 255);
	}
	
	
	// update screen content
	public void draw(){
		
		// background
		background(0, 0, 0);
		image(bg, 0, 0, 400, 600);
		vs.update();
		scroll = vs.getspos();
		
		for(int i=0; i<btn.length; i++){
			btn[i].setPosition(0,scroll);
			btn[i].display_circle();
			textSize(20);
			text(playerName[i], 200, 80+100*i-scroll/(float)1.5);
			
		}
		
		//TODO 畫取消按鈕
		cancelBtn.setPosition(0,scroll);
		cancelBtn.display_circle();
		fill(255);
		textSize(15);
		text("Cancel", 275, 180-scroll/(float)1.5);
		
		vs.display();
	}
	
	
	// control mouse clicked
	public void mouseClicked(){
		
		for(int i=0; i<btn.length; i++){
			
			if(btn[i].inBtn()){
				
				//TODO call method to modify money in MainApplet
				parent.calMoney(-marketBtn.getMoney());
				
				//TODO call method to attack in MainApplet
				//parent.attacked(playerName[i], new Color(0, 0, 0).getRGB());
				
				window.dispose();
				
			}
			
		}
		
		//TODO 取消按鈕點選 -> 什麼都不做只關閉視窗
		if(cancelBtn.inBtn()){
			window.dispose();
		}
	}
	
	
	// control mouse moved
	public void mouseMoved(){
		
		for(Button b : btn){
			
			if(b.inBtn()) b.setOver(true);
			else b.setOver(false);
			
		}
		if(cancelBtn.inBtn()) cancelBtn.setOver(true);
		else cancelBtn.setOver(false);
	}


	/**-----------------------------------------------
	 * ↓ seter and geter
	 ----------------------------------------------**/
	
	public void setWindow(JFrame w){
		window = w;
	}

}
