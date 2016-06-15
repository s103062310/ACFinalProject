package object.tool;

import java.io.Serializable;
import processing.core.PImage;

public class Image implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private PImage screenshot;
	
	public Image(PImage p){
		this.screenshot = p;
	}
	
	public PImage getImage(int i){
		return screenshot;
	}

}
