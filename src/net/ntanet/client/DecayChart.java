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
	
	public DecayChart(int nAtoms, Dimension dim) {
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
	    context.setFillStyle(color);
        context.fillRect( x, y, w, h);
        context.fill();
	}

	public void drawRect(Canvas canvas, double x, double y, double w, double h) {
	    Context2d context = canvas.getContext2d();
	    CssColor color = CssColor.make(255,0,0);
	    context.setFillStyle(color);
        context.fillRect( x, y, w, h);
        context.fill();
	}
	
	public void drawLine(Canvas canvas, int x1, int y1, int x2, int y2) {
	    Context2d context = canvas.getContext2d();
	    CssColor color = CssColor.make(0,0,0);
	    context.setFillStyle(color);
	    context.setStrokeStyle(color);
	    context.beginPath();
	    context.moveTo(x1, y1);
	    context.lineTo(x2, y2);
	    context.closePath();
        context.stroke();

	}

	public void update(int nAtoms) {
		numerator = nAtoms;
		double fraction = ((double)numerator / (double)denominator);
		GWT.log("fraction: " + fraction + " numerator " + numerator + " denominator " + denominator);
		ypos = yscale - (int) (fraction * yscale);
		GWT.log("ypos " + ypos + " yscale " + yscale);
	}

	public void reset(int nAtoms) {
		xpos = 0;

		numerator = nAtoms;
		denominator = nAtoms;
		
		update(nAtoms);
//		offScreenImage = null;
		clearCanvas(canvas, 0, 0, width, height);
		repaint();
	}

	public void tick() {
	    repaint();
		xpos++;
	}

	private void repaint() {
		paintComponent(canvas);
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
		if ((xpos < xscale) && (ypos > 0) && (osg != null))
			renderOffScreen(osg);
//		
//		g.drawImage(offScreenImage, 0, 0, null);

		drawLine(canvas, 1, 1, width - 1, 1);
	    drawLine(canvas, 1, 1, 1, height - 1);
	    drawLine(canvas, 1, height - 1, width - 1, height - 1);
	    drawLine(canvas, width - 1, 1, width - 1 , height - 1);
	    drawLine(canvas, 50, 50, 50, 50);

	    drawLine(canvas, 0, height / 4, 15, height / 4);
	    drawLine(canvas, 0, height / 2, 15, height / 2);
	    drawLine(canvas, 0, 3 * height / 4, 15, 3 * height / 4);
	    
	    drawLine(canvas, width / 4, height - 1, width / 4, height - 15);
	    drawLine(canvas, width / 2, height - 1, width / 2, height - 15);
	    drawLine(canvas, 3 * width / 4, height - 1, 3 * width / 4, height - 15);
	}

	private void renderOffScreen(Canvas g) {
		int x1, y1, x2, y2, xstride, ystride;
		xstride = xscale / 10;
		ystride = yscale / 10;

		Context2d context = canvas.getContext2d();
	    CssColor color = CssColor.make(255,0,0);
	    context.setFillStyle(color);
        context.fillRect((xpos + xoffset), (ypos + yoffset), 5, 5);
        context.fill();
        GWT.log("painting xpos " + xpos + " ypos " + ypos);
	}
}
