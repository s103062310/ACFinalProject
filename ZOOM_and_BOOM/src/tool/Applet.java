package tool;

import processing.core.PApplet;
import processing.core.PImage;
import java.io.File;

public class Applet extends PApplet {
	
	private static final long serialVersionUID = 1L;
	private PImage image;
	private String path = new String("src/resource/");
	private String[] list;
	private int num = 0;
	
	public void setup(){
		setSize(800, 450);
		
		// open directory and load all file name
		File folder = new File(path + "pic_ori");
		list = folder.list();
	}
	
	public void draw(){
		
		// main
		while(num<list.length){
			// load image
			String[] file = list[num].split("\\.");
			if(!file[1].equals("png")&&!file[1].equals("jpg")) continue;
			image = loadImage(path + "pic_ori/" + list[num]);
			System.out.println(list[num] + " " + image.width + " " + image.height);
			
			// resize the image and draw
			if(image.width<800||image.height<450){
				float w = 800/image.width;
				float h = 450/image.height;
				if(w>h) image(image, 0, 0-(image.height*w-450)/2, 800, image.height*w);
				else image(image, 0-(image.width*h-800)/2, 0, image.width*h, 450);
			} else{
				float w = image.width/800;
				float h = image.height/450;
				if(w>h) image(image, 0-(image.width/h-800)/2, 0, image.width/h, 450);
				else image(image, 0, 0-(image.height/w-450)/2, 800, image.height/w);
			}
			
			// create empty image to map the applet
			PImage img = createImage(800, 450, ARGB);
			for(int i=0; i<img.pixels.length; i++){
				int c = this.get(i%800, i/800);
				img.pixels[i] = c;
			}
			
			// save image
			img.save(path + "pic_rsc/M" + num + ".png");
			num++;
		}
		
		// process finish
		System.exit(0);
	}
	
}
