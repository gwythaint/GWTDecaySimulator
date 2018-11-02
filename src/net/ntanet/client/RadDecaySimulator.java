package net.ntanet.client;
 
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.Random;

public class RadDecaySimulator implements EntryPoint {
     
    public static EventPlanner eventPlanner = null;
    static final int canvasHeight = 300;
    static final int canvasWidth = 300;
    static final String divTagId = "canvasExample"; // must match div tag in html file
	private Canvas canvas;
	private HorizontalPanel panel;
    DecayChart chart;
	AtomSandbox sandbox;
    ControlPanel control;
    Timer t;
	private int nAtoms;

	public RadDecaySimulator () {
		eventPlanner = new SimEventPlanner(this);
		this.nAtoms = 200;
	}

    public void onModuleLoad() {
    	canvas = Canvas.createIfSupported();
         
        if (canvas == null) {
              RootPanel.get().add(new Label("Sorry, your browser doesn't support the HTML5 Canvas element"));
              return;
        }

        panel = new HorizontalPanel();
    	RootPanel.get( divTagId ).add(panel);

   	 	sandbox = new AtomSandbox(nAtoms, new Dimension(400,600));
   	 	chart = new DecayChart(nAtoms, new Dimension(600,450));
        control = new ControlPanel(nAtoms, new Dimension(250,250));

        panel.add(sandbox);
        panel.add(chart);
        panel.add(control);
        
  	    t = new Timer() {
		  @Override
		  public void run() {
			  sandbox.tick();
			  chart.update(sandbox.nAtoms);
			  chart.tick();
			  GWT.log("run tick nAtoms " + sandbox.nAtoms);
			  if (sandbox.nAtoms == 0)
				  t.cancel();
		  }
	  };

    }
    
	private void drawAtom(int i, int j) {
		int x = i * 20 + 6;
		int y = j * 20 + 6;
		int width = 12;
		int height = 12;
		drawCircle(canvas, x, y, width, height, false, false);
	}

	public void drawLine(Canvas canvas, int x1, int y1, int x2, int y2) {
	    Context2d context = canvas.getContext2d();
	    context.beginPath();
	    context.moveTo(x1, y1);
	    context.lineTo(x2, y2);
	    context.closePath();
        context.stroke();
		
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
	
      public void drawSomethingNew() {
  	    Context2d context = canvas.getContext2d();

           
          // Get random coordinates and sizing
          int rndX = Random.nextInt(canvasWidth);
          int rndY = Random.nextInt(canvasHeight);        
          int rndWidth = Random.nextInt(canvasWidth);
          int rndHeight = Random.nextInt(canvasHeight);
           
          // Get a random color and alpha transparency
          int rndRedColor = Random.nextInt(255);
          int rndGreenColor = Random.nextInt(255);
          int rndBlueColor = Random.nextInt(255);
          double rndAlpha = Random.nextDouble();
 
          CssColor randomColor = CssColor.make("rgba(" + rndRedColor + ", " 
                                               + rndGreenColor + "," + rndBlueColor + ", " + rndAlpha + ")");
           
          context.setFillStyle(randomColor);
          context.fillRect( rndX, rndY, rndWidth, rndHeight);
          context.fill();
      }

      @EventHandler
      void onMyEvent(SimEvent event) {
    	  if (event.getName() == "control") {
    		  if (event.getStringValue() == "start")
    		  {
    			  t.scheduleRepeating(100);		  
    		  } else {
    			  t.cancel();
    		  }
    		  GWT.log(event.getStringValue());
    	  } else {
    		  GWT.log("not a control SimEvent");
    	  }
    	  
      }

      @EventHandler
      void onMyEvent(ChartEvent event) {

    	  nAtoms = event.getValue();
    	  sandbox.nAtomsStart = nAtoms;
    	  sandbox.reset();
    	  chart.update(nAtoms);
    	  chart.reset(nAtoms);

    	  t.cancel();
      
      }
      
}