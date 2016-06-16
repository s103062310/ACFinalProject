package object.client;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import object.client.Splash;

public class ConfirmWindow extends PApplet {
	
	private static final long serialVersionUID = 1L;
	
	// content
	private PImage img;
	private ArrayList<Splash> splash = new ArrayList<Splash>();
	
	
	// Constructor
	public ConfirmWindow(PImage img, ArrayList<Splash> list){
		
		this.img = img;
		for(Splash spl : list){
			this.splash.add(new Splash(this, spl));
			System.out.println("trans= "+spl.getTrans());
		}
		
	}
	
	
	// update screen content
	public void draw(){
		
		image(img, 0, 0, 800, 450);
		for(Splash s:splash) s.display();
		
	}

}
