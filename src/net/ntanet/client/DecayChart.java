package net.ntanet.client;

import java.awt.Color;
import java.awt.Graphics;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DecayChart extends Composite {
	int xpos, ypos;
	int xscale, yscale;
	int ticks;
//	private Image offScreenImage = null;
	private Canvas osg = null;
	private Canvas canvas;
	private Context2d context;
	private int denominator;
	private int numerator;
	private int xoffset = 0;
	private int yoffset = 0;
	int width;
	int height;
	private Double tickStride;
	private Double atomStride;
    CssColor color;
	private int nAtoms;

    public DecayChart(int halflife, int nAtoms, Dimension dim) {
		xpos = 0;
		ypos = 0;
		this.xscale = dim.width;
		this.yscale = dim.height;
		this.numerator = nAtoms;
		this.denominator = nAtoms;

		canvas = Canvas.createIfSupported();
		initWidget(canvas);

		dim.setSize(new Dimension(dim.width + xoffset, dim.height + yoffset));
		width = dim.width;
		height = dim.height;
		
        canvas.setStyleName("decayChart");
        canvas.setWidth(width + "px");
        canvas.setCoordinateSpaceWidth(width);
         
        canvas.setHeight(height + "px");      
        canvas.setCoordinateSpaceHeight(height);
        context = canvas.getContext2d();

	    reset(nAtoms);
	}

	public void clearCanvas(Canvas canvas, double x, double y, double w, double h) {
	    Context2d context = canvas.getContext2d();
	    CssColor color = CssColor.make(255,255,255);
	    context.setFillStyle(this.color);
        context.fillRect( x, y, w, h);
        context.fill();
	}

	public void drawRect(Canvas canvas, double x, double y, double w, double h) {
	    Context2d context = canvas.getContext2d();
	    context.setFillStyle(this.color);
        context.fillRect( x, y, w, h);
        context.fill();
	}
	
	public void drawLine(Canvas canvas, int x1, int y1, int x2, int y2) {
		double lineWidth = 1.0;
		drawLine(canvas, x1, y1, x2, y2, lineWidth);
	}

	public void drawLine(Canvas canvas, int x1, int y1, int x2, int y2, double lineWidth) {
	    Context2d context = canvas.getContext2d();
	    context.setFillStyle(color);
	    context.setLineWidth(lineWidth);
	    context.setStrokeStyle(color);
	    context.beginPath();
	    context.moveTo(x1, y1);
	    context.lineTo(x2, y2);
	    context.closePath();
        context.stroke();

	}

	public void update(int nAtoms) {
		this.nAtoms = nAtoms;
		numerator = nAtoms;
		double fraction = ((double)numerator / (double)denominator);
		GWT.log("fraction: " + fraction + " numerator " + numerator + " denominator " + denominator);
		ypos = yscale - (int) (fraction * yscale);
		GWT.log("ypos " + ypos + " yscale " + yscale);
	}

	public void reset(int nAtoms) {
		xpos = 0;
		ticks = 0;
		numerator = nAtoms;
		denominator = nAtoms;
		
		update(nAtoms);
//		offScreenImage = null;
	    this.color = CssColor.make(255,255,255);
	    clearCanvas(canvas, 0, 0, width, height);
		paintGrid(canvas);
		paintHalfLife(canvas);
		repaint();
	}

	public void tick() {
	    paintEFunction();
	    repaint();
		ticks++;
	}

	private void repaint() {
		paintComponent(canvas);
	}
	
	private void paintEFunction () {
		int y1;
		int x1 = (int) ((getXpos() + xoffset) * tickStride);
		y1 = yscale - (int)(yscale * Math.exp(-ticks * 0.693 / 100));

		this.color = CssColor.make(0,255,0);
	    this.drawRect(canvas, x1, x1, 5, 5);

	    this.color = CssColor.make(0,0,255);

	    this.drawRect(canvas, x1, y1, 5, 5);
		
		GWT.log("x1: " + x1 + " y1 " + y1 + " denom: " + denominator + " yscale: " + yscale);
	}
	
	private int getXpos() {
		return ticks;
	}

	private void paintGrid(Canvas canvas) {
		int x, y;

	    this.color = CssColor.make(0,0,0);
	    drawLine(canvas, 1, 1, width - 1, 1);
	    drawLine(canvas, 1, 1, 1, height - 1);
	    drawLine(canvas, 1, height - 1, width - 1, height - 1);
	    drawLine(canvas, width - 1, 1, width - 1 , height - 1);
	    drawLine(canvas, 50, 50, 50, 50);

	    /* horizontal grid lines */
	    for (y = height / 10; y < height;) {
		    drawLine(canvas, 0, y, width, y, 0.2);
	    	y += (height / 10);
	    }
	    /* vertical grid lines */
//	    for (x = width / 10; x < width;) {
//	    	drawLine(canvas, x, height, x, 0, 0.2);
//	    	x += (width / 10);
//	    }
	}

	private void paintHalfLife(Canvas canvas) {
		int x, y;

	    this.color = CssColor.make(255,0,255);

	    /* vertical grid lines */
	    for (x = 100; x < width; x += 100) {
	    	drawLine(canvas, x, height, x, 0, 2.0);
	    }
	}
	
	private void paintComponent(Canvas canvas) {
		if (osg == null)
			osg = canvas;
		
//		if (offScreenImage == null) {
//			offScreenImage = createImage(xscale + xoffset, yscale + yoffset);
//			osg = offScreenImage.getGraphics();
//		}
//
//		
		if ((getXpos() < xscale) && (ypos > 0) && (osg != null))
			renderOffScreen(osg);
//		
//		g.drawImage(offScreenImage, 0, 0, null);

	}

	private void renderOffScreen(Canvas g) {
		int x1, y1, x2, y2, xstride, ystride;
		x1 = (int) ((getXpos() + xoffset) * tickStride);
		y1 = (int) ((ypos + yoffset) * atomStride);
		
		xstride = xscale / 10;
		ystride = yscale / 10;

		Context2d context = canvas.getContext2d();
	    CssColor color = CssColor.make(255,0,0);
	    context.setFillStyle(color);
        context.fillRect(x1, y1, 5, 5);
        context.fill();
        GWT.log("painting xpos " + getXpos() + " ypos " + ypos);
	}

	public void tick(int nAtoms) {
		update(nAtoms);
		tick();
	}

	public void setTickStride(Double i) {
		tickStride = i;
	}

	public void setAtomStride(Double i) {
		atomStride = i;
	}
}
