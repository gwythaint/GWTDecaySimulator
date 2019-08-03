package net.ntanet.client;


import java.util.Random;
import java.util.logging.Logger;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.shared.GWT;


public class Atom {
	private CssColor color;
	private int halflife;
	private int xpos;
	private int ypos;

	private Boolean decayed;
	private Double lambda;

	static Random rng = new Random();

	public Atom(int halflife, CssColor color, int i, int j) {
		this.color = color;
		this.xpos = i;
		this.ypos = j;
		this.halflife = halflife;
		
		/* synchronize half life and decay constant */
		this.setHalflife(halflife);
		this.setDecayed(false);
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
	
	public CssColor getColor() {
		return color;
	}

	public void setColor(CssColor color) {
		this.color = color;
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

	public Boolean getDecayed() {
		return decayed;
	}

	public void setDecayed(Boolean decayed) {
		this.decayed = decayed;
	}

	public Double getLambda() {
		return lambda;
	}

	public void setLambda(Double lambda) {
		this.lambda = lambda;
		this.halflife = (int) (0.693 / lambda);
	}

	public int getHalflife() {
		return halflife;
	}

	public void setHalflife(int halflife) {
		this.halflife = halflife;
		this.lambda = 0.693 / this.getHalflife();
	}


}
