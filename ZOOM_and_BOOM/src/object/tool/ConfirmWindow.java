package object.client;

import java.awt.Color;
import java.util.ArrayList;

//import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import object.tool.Button;

public class ConfirmWindow extends PApplet {

	private static final long serialVersionUID = 1L;

	// content
	private PImage img;
	private Button btn;
	private ArrayList<Splash> splash = new ArrayList<Splash>();
	//private Splash splash;
	
	// Constructor
	public ConfirmWindow(PImage img, ArrayList<Splash> list) {

		this.img = img;
		this.btn = new Button(this, 200, 200, 50, new Color(0, 0, 0).getRGB());
		//this.splash = new Splash(this, spl.getX(), spl.getY(), 10, spl.getColor());
		
		for(Splash spl : list){
			this.splash.add(new Splash(this, spl.getX(), spl.getY(),spl.getTrans(), spl.getColor()));
			System.out.println("trans= "+spl.getTrans());
		}
	}

	// update screen content
	public void draw() {

		image(img, 0, 0, 800, 450);
		btn.display_circle();
		//splash.display();
		for(Splash s:splash) s.display();
		// draw splash
	}

}
