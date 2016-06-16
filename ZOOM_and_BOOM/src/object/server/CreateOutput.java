package object.server;

import java.io.File;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;

public class CreateOutput extends PApplet{
	
	private static final long serialVersionUID = 1L;
	private String[] list;
	private HashMap<String, Answer> answer;
	
	
	// Constructor
	public CreateOutput(HashMap<String, Answer> answer){
		
		setSize(800, 450);
		this.answer = answer;
		File folder = new File("src/resource/pic_rsc");
		this.list = folder.list();
		
	}
	
	
	// main process
	public void draw(){
		
		// for each image file
		for(int i=0; i<list.length; i++){
			
			// get image answer (if none => skip)
			Answer ans = answer.get(list[i]);
			if(ans.getW()==0||ans.getW()==0) continue;
			
			// create focus image and save
			PImage image = loadImage("src/resource/pic_rsc/"+list[i]);
			image(image, 0, 0, 800, 450);
			PImage img = createImage(ans.getW(), ans.getH(), ARGB);
			int x = ans.getX()-ans.getW()/2;
			int y = ans.getY()-ans.getH()/2;
			for(int j=0; j<img.pixels.length; j++){
				img.pixels[j] = this.get(x+j%ans.getW(), y+j/ans.getW());
			}
			img.save("src/resource/pic_out/"+list[i]);
			
		}
		
	}
	
}
