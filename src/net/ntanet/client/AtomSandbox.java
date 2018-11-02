package net.ntanet.client;

import java.awt.Graphics;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Composite;

public class AtomSandbox extends Composite {
	private static final int GRIDSIZE = 50;
	private Atom atomTable[][];
	public int nAtoms, nRow, nCol;
	public int nAtomsStart;
	private Canvas canvas;
	private double devicePixelRatio = 1.0;
	private Dimension dim;

	private static int width = 12;
	private static int height = 12;
	private static int stride = 20;
	private static int xoffset = 6;
	private static int yoffset = 6;

	private void generateRowCol(Dimension dim) {
		// absolute visible max
		nCol = (int) (dim.getWidth() / stride);
		nRow = (int) (dim.getHeight() / stride);
		GWT.log("nRow " + nRow + " nCol " + nCol + " nAtoms " + nAtomsStart);
		
		// make it square shaped
		if (nCol < nRow) {
			nRow = nCol;
		} else {
			nCol = nRow;
		}
		GWT.log("nRow " + nRow + " nCol " + nCol + " nAtoms " + nAtomsStart);
		
		// minimized by number of atoms
		if (nCol > nAtomsStart) {
			nRow = nAtomsStart;
			nCol = 1;
		}
		nRow = nAtomsStart / nCol;
		GWT.log("nRow " + nRow + " nCol " + nCol + " nAtoms " + nAtomsStart);
		
	}
	public AtomSandbox(int nAtoms, Dimension dim) {
		nAtomsStart = nAtoms;
		this.dim = dim;
		canvas = Canvas.createIfSupported();
        canvas.setWidth(dim.width * devicePixelRatio + "px");
        canvas.setCoordinateSpaceWidth(dim.width);

        canvas.setHeight(dim.height * devicePixelRatio + "px");      
        canvas.setCoordinateSpaceHeight(dim.height);
        generateRowCol(dim);
        GWT.log("space width " + canvas.getCoordinateSpaceWidth());
        GWT.log("space height " + canvas.getCoordinateSpaceHeight());

		if (canvas != null)
			initWidget(canvas);
		
		reset();
	}

	private void repaint() {
		paintCanvas();
	}
	
	public void draw(Atom atom) {
		int x = atom.getXpos() * stride + xoffset;
		int y = atom.getYpos() * stride + yoffset;
	    Context2d context = canvas.getContext2d();
	    context.setFillStyle(atom.color);
	    
    	drawCircle(canvas, x, y, width, height, false, false);
	}
	
	public void paintCanvas(){
		int i = 0;
		int j = 0;
		

		for (i = 0; i < nRow; i++) {
			for ( j = 0; j < nCol; j++) {
				draw(atomTable[i][j]);
			}
		}
	}

	public void tick() {
		int i,j;

		for (i = 0; i < nRow; i++) {
			for ( j = 0; j < nCol; j++) {
					Atom atom = this.atomTable[i][j];
					if (!atom.decayed)
						if(atom.decay()) {
							nAtoms--;
							draw(atom);
						}
				}
			}
		GWT.log("nAtoms = " + nAtoms);
	}

	public void reset() {
		int i = 0;
		int j = 0;

		nAtoms = nAtomsStart;
//		GWT.log("nAtomsStart = " + nAtoms);

		generateRowCol(this.dim);
		atomTable = new Atom[nRow][nCol];
//		GWT.log("atomTable " + atomTable.length + " " + atomTable[0].length + " " + nRow + " " + nCol);

		/* generate a 2D array of atoms */
		for (i = 0; i < nRow; i++) {
			for ( j = 0; j < nCol; j++) {
				atomTable[i][j] = new Atom(CssColor.make("#0000ff"), i, j);
			}
		}
//		i = 9; j = 300;
//		GWT.log("atom# " + i + " " + j);
//		atomTable[i][j] = new Atom(CssColor.make("#0000ff"), i, j);

		clearCanvas();
		repaint();
	}

	public void clearCanvas() {
	    Context2d context = canvas.getContext2d();
	    CssColor color = CssColor.make(255,255,255);
	    int w = canvas.getCoordinateSpaceWidth();
	    int h = canvas.getCoordinateSpaceHeight();
	    
	    context.setFillStyle(color);
        context.fillRect( 0, 0, w, h);
        context.fill();

	}


	public void drawCircle(Canvas canvas, int x, int y, int width, int height, boolean inbox, boolean stroke) {
	    int cx = x + width / 2;
	    int cy = y + height / 2;
	    double rad;
	    if (inbox) {
	        rad = width > height ? height / 2 : width / 2;
	    } else {
	        double longer = width > height ? width / 2 : height / 2;
	        rad = Math.sqrt(Math.pow(longer, 2) * 2);
	    }

	    Context2d context = canvas.getContext2d();
	    context.beginPath();
	    canvas.getContext2d().arc(cx, cy, rad, 0, Math.PI * 2);
	    context.closePath();
	    if (stroke) {
	        context.stroke();
	    } else {
	        context.fill();
	    }
	}	

}
