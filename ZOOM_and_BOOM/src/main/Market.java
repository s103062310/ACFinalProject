package main;

import java.awt.Color;
import java.util.TimerTask;
import object.client.ImageButton;
import object.client.ShieldBtn;
import object.tool.HScrollbar;
import processing.core.PImage;

public class Market{
	
	// scrollbar
	private HScrollbar hs;

	// content
	private ImageButton[] button;
	private ShieldBtn shieldbutton;
	private ImageButton randomBtn;
	private int[] price = {10, 15, 20, 10, 20, 15, 25, 35, 30};
	private java.util.Timer timer = new java.util.Timer ();
	private int delate = 100;
	private boolean temp = true;
	
	private int[] colorArray = {Color.BLACK.getRGB(), new Color(0,255,127).getRGB(), new Color(238,221,130).getRGB()
			, new Color(0,0,128).getRGB(), Color.RED.getRGB(), new Color(135,206,235).getRGB()
			, new Color(238,180,34).getRGB(), new Color(255,0,255).getRGB(), new Color(105,139,34).getRGB()};
	private String[] colorNameArray = {"DEMON", "LIME", "MOON", "NAVY", "BLOOD", "SKY", "GOLD", "ORCHID", "OLIVE"};
	private PImage[] btnImages = new PImage[9];
	private PImage guessBtnImage;
	
	// resources
	private MainApplet parent;
	private PImage bg;
	
	// Constructor
	public Market(MainApplet p){
		
		this.parent = p;
		this.bg = parent.loadImage("resource/other_images/marketBackground.jpg");
		this.hs = new HScrollbar(0 ,640, 800, 20 ,16, parent);    ////* TODO increase size
		
		// create buttons
		int s=70, d=120;
		button = new ImageButton[9];
		loadBtnColors();
		for (int i = 0; i < 9; i++)
			button[i] = new ImageButton(parent,btnImages[i], s+d*i, 515, price[i], colorNameArray[i], colorArray[i]);
		randomBtn = new ImageButton(parent,guessBtnImage, s+d*9, 515, price[5], "GUESS", Color.GRAY.getRGB());
		shieldbutton = new ShieldBtn(parent, s+d*10, 515, price[5], Color.WHITE.getRGB());
		
	}
	
	
	// update screen content
	public void display(){
		if (temp){
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
				
					for (int i = 0; i < 9; i++){
						if (parent.getPlayer().getColor()==colorArray[i])price[i] = 10;
						else price[i] = 30;
					}
					for (int i = 0; i < 9; i++)
						button[i] = new ImageButton(parent,btnImages[i], 70+120*i, 515, price[i], colorNameArray[i], colorArray[i]);
				
				}
			}, 0, delate);
			temp = false;
		}
		
		// background
		this.parent.fill(50, 7, 250, 200);
		this.parent.rect(0, 450, 800, 200);
		parent.image(bg, 0, 450, 800, 200);
		
		hs.update();
		
		// button
		for(ImageButton btn : button){
			btn.setPosition(hs.getPos(),0);
			btn.display();
		}
		
		randomBtn.setPosition(hs.getPos(),0);
		randomBtn.display();
		
		shieldbutton.setPosition(hs.getPos());
		shieldbutton.display();
		
		hs.display();
		
	}
	
	public void loadBtnColors(){
		try { 
			btnImages[0] = parent.loadImage("resource/btn_images/demon.png");
			btnImages[1] = parent.loadImage("resource/btn_images/lime.png");
			btnImages[2] = parent.loadImage("resource/btn_images/moon.png");
			btnImages[3] = parent.loadImage("resource/btn_images/navy.png");
			btnImages[4] = parent.loadImage("resource/btn_images/blood.png");
			btnImages[5] = parent.loadImage("resource/btn_images/sky.png");
			btnImages[6] = parent.loadImage("resource/btn_images/gold.png");
			btnImages[7] = parent.loadImage("resource/btn_images/orchid.png");
			btnImages[8] = parent.loadImage("resource/btn_images/olive.png");
			guessBtnImage = parent.loadImage("resource/btn_images/guess.png");
		}
		catch(Exception ex){
			System.err.println("Unable to laod cursor or Backgroun Images");
			ex.printStackTrace();
		}
		for(PImage img : btnImages){
			img.resize(100,100);
		}
		guessBtnImage.resize(100, 100);
	}
	/**-----------------------------------------------
	 * ¡õ seter and geter
	 ----------------------------------------------**/
	public ImageButton getRandomBtn(){
		return randomBtn;
	}
	public ImageButton[] getButtons(){
		return button;
	}
	public ShieldBtn getShieldBtn(){
		return shieldbutton;
	}
	public int[] getColorArray(){return colorArray;}
}