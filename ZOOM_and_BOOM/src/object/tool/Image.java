package object.tool;

import java.io.Serializable;
import processing.core.PImage;

public class Image implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int[] screenshot = new int[800*450];
	
	public Image(PImage p){
		for(int i=0; i<800*450; i++){
			screenshot[i] = p.get(i%800, i/800);
		}
	}
	
	public int getImage(int i){
		return screenshot[i];
	}

}
