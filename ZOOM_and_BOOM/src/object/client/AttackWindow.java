package object.client;

import java.util.ArrayList;
import javax.swing.JFrame;
import object.server.Player;
import object.tool.Button;
import object.tool.VScrollbar;
import main.MainApplet;
import processing.core.PApplet;
import processing.core.PImage;

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
	private ImageButton marketBtn;
	private String[] playerName;
	private Button cancelBtn;
	
	
	// Constructor: initialize
	public AttackWindow(MainApplet p, ArrayList<Player> list, ImageButton marketBtn){
		
		parent = p;
		this.marketBtn = marketBtn;
		
		// set window size
		setSize(400, 600);
		
		// set scroll & background img
		this.vs = new VScrollbar(365, 0, 17, 600, 16, this);
		this.bg = parent.loadImage("src/resource/other_images/scoreboard.PNG");
		
		// construct "otherPlayers"
		ArrayList<Player> otherPlayers = new ArrayList<Player>();
		for(Player player: list){
			if(!player.getName().equals(parent.getPlayer().getName())&&player.isOnLine()) 
				otherPlayers.add(player);
		}
		
		// create button through list
		btn = new Button[otherPlayers.size()];
		playerName = new String[otherPlayers.size()];
		int i=0;
		for(Player player: otherPlayers){
			btn[i] = new Button(this, 60, 60+80*i, 60, player.getColor());
			playerName[i] = player.getName();
			i++;
		}
		
		// cancel button
		cancelBtn = new Button(this, 270, 70, 60, 60, 1);
		cancelBtn.addImage("src/resource/other_images/cancel-button.png");
		cancelBtn.setImage(0);
	}
	
	
	// update screen content
	public void draw(){
		
		// background
		background(0, 0, 0);
		image(bg, 0, 0, 400, 600);
		
		// scroll bar
		vs.update();
		scroll = vs.getspos();
		
		// buttons
		for(int i=0; i<btn.length; i++){
			btn[i].setPosition(0,scroll);
			btn[i].display_circle();
			textSize(20);
			fill(0);
			text(playerName[i], 141, 60+80*i-scroll/(float)1.5);
		}
		
		// cancel button
		cancelBtn.setPosition(0,scroll);
		cancelBtn.display_image();
		
		vs.display();
	}
	
	
	// control mouse clicked
	public void mouseClicked(){
		
		for(int i=0; i<btn.length; i++){
			
			if(btn[i].inBtn()){
				
				parent.calMoney(-marketBtn.getMoney());
				parent.attacked(playerName[i], marketBtn.getColor());
				this.dispose();
				window.dispose();
				
			}
			
		}
		
		if(cancelBtn.inBtn()){
			this.dispose();
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
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	
	public void setWindow(JFrame w){
		window = w;
	}

}
