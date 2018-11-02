package net.ntanet.client;

import java.util.Random;
import java.util.logging.Logger;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.shared.GWT;


public class Atom {
	CssColor color;
	int halflife;
	int xpos;
	int ypos;

	public Boolean decayed;
	private Double lambda;

	static Random rng = new Random();

	public Atom(CssColor color, int i, int j) {
		this.color = color;
//		this.color = CssColor.make(rng.nextInt() % 255, rng.nextInt() % 255, rng.nextInt() % 255);
		this.setXpos(i);
		this.setYpos(j);
		this.setHalflife(100);
		
		/* synchronize half life and decay constant */
		this.lambda = 0.693 / this.getHalflife();
		this.decayed = false;
	}

	public CssColor getColor() {
		return color;
	}

	public void setColor(CssColor color) {
		this.color = color;
	}

	public int getHalflife() {
		return halflife;
	}

	public void setHalflife(int halflife) {
		this.halflife = halflife;
	}


	public int getXpos() {
		return xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = (xpos);
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = (ypos);
	}

	public Boolean decay() {
		Double chance = rng.nextDouble();
		
		/* chance of decaying when chance is less than decay constant */
		if (chance < this.lambda) {
			this.decayed = true;
			color = CssColor.make(255, 255, 255);
		}

		return this.decayed;
	}

}
