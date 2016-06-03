package main;

import java.awt.Color;
import object.server.Player;
import object.tool.VScrollbar;
import processing.core.PImage;

//TODO potential bug: 前五名分數相同的話是5個黃冠/只有幸運的前3個是?

public class Scoreboard {

	// content
	private VScrollbar vs;
	public float scroll = 0;
	private PImage crown, bg, selfbg;


	// resources
	private MainApplet parent;


	// Constructor
	public Scoreboard(MainApplet p) {


		this.parent = p;
		this.vs = new VScrollbar(1083, 0, 17, 450, 16, parent);
		this.crown = parent.loadImage("src/resource/other_images/crown.png");

		this.bg = parent.loadImage("src/resource/other_images/scoreboard.PNG");
		this.selfbg = parent.loadImage("src/resource/other_images/self.PNG");
	}

	// update screen content
	public void display() {


		// Players' area (background)
		parent.image(bg, 800, 0, 300, 450);

		// set
		parent.textSize(20);
		vs.update();
		scroll = vs.getspos();
		int num = parent.getList().size();
		int radius = Math.max(50 - 6 * num / 3, 32);


		// players' information
		for (int i = 0; i < num; i++) {

			Player pl = parent.getList().get(i);
			parent.fill(pl.getColor());
			parent.ellipse(840, 100 - scroll + i * (radius + 15), radius, radius);
			// for-loop for text shadow
			parent.fill(255, 193, 193);
			for (int j = 0; j < 2; j++) {
				if (j == 1) parent.fill(0);
				parent.text(pl.getName(), j + 870, j + 105 - scroll + i * (radius + 15));
				parent.text("#" + pl.getScore(), j + 1000, j + 105 - scroll + i * (radius + 15));

			}
			// self
			if (parent.getPlayer().getName().equals(pl.getName())) {

				parent.fill(238, 224, 229);
				parent.ellipse(840, 100 - scroll + i * (radius + 15), 20, 20);
			}

		}


		// crown
		if (num >= 5) {
			for (int i = 0; i < 3; i++) {
				parent.image(crown, 840, 100 - scroll + i * (radius + 15) - 4 * radius / 5, 20, 20);
			}
		}

		// text "Score Board"
		parent.textSize(42);
		parent.fill(0, 0, 205);
		parent.text("Score Board", 820, 50 - scroll);
		parent.fill(0, 191, 255);
		parent.text("Score Board", 822, 52 - scroll);


		// scroll bar
		vs.display();

		// my score (background)
		// parent.stroke(130, 210, 75);
		// parent.strokeWeight(10);
		// parent.fill(0, 0, 0);
		// parent.rect(800, 450, 300, 200);
		// parent.rect(805, 455, 290, 190);


		// parent.noStroke();

		// own information
		parent.image(selfbg, 800, 450, 300, 200);
		parent.stroke(100, 100, 100);
		parent.strokeWeight(3);
		parent.fill(parent.getPlayer().getColor());
		parent.ellipse(860, 510, 60, 60);
		parent.noStroke();
		// own information - text & shadow
		parent.fill(178, 34, 34);
		for (int i = 0, j = 0; i < 2; i++, j++) {
			if (i == 1) parent.fill(0);
			parent.textSize(28);
			parent.text(parent.getPlayer().getName(), 950 + j, 520 + j);
			parent.textSize(20);
			parent.text("coin : " + parent.getPlayer().getScore(), 900 + j, 565 + j);
			parent.text("completed : " + parent.getPlayer().getCompleted(), 900 + j, 595 + j);
			parent.text("shield : " + parent.getPlayer().getShield(), 900 + j, 625 + j);

		}

	}

}
