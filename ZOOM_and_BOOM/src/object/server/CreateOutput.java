package object.server;

import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;

public class CreateOutput extends PApplet{
	
	private static final long serialVersionUID = 1L;
	private String[] list;
	private HashMap<String, Answer> answer;
	
	public CreateOutput(String[] list, HashMap<String, Answer> answer){
		setSize(800, 450);
		this.list = list;
		this.answer = answer;
	}
	
	public void draw(){
		for(int i=0; i<list.length; i++){
			Answer ans = answer.get(list[i]);
			if(ans.getW()==0||ans.getW()==0) continue;
			PImage image = loadImage("src/resource/pic_rsc/"+list[i]);
			image(image, 0, 0, 800, 450);
			PImage img = createImage(ans.getW(), ans.getH(), ARGB);
			for(int j=0; j<img.pixels.length; j++){
				img.pixels[j] = this.get(ans.getX()+j%ans.getW(), ans.getY()+j/ans.getW());
			}
			img.save("src/resource/pic_out/"+list[i]);
		}
	}
	
}
