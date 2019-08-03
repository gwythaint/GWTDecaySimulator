package net.ntanet.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ControlPanel extends Composite implements HasHandlers {
	
	private int width;
	private int height;
	private Canvas canvas;
	private Button resetButton;
	private Button startButton;
	private TextBoxWithLabel nAtomText;
	boolean toggle = true;

	public ControlPanel (int halflife, int nAtoms, Dimension dim) {
		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		initWidget(panel);

		int xoffset = 0;
		int yoffset = 0;

		dim.setSize(new Dimension(dim.width + xoffset, dim.height + yoffset));
		width = dim.width;
		height = dim.height;
	
		panel.setWidth(width + "px");
         
		panel.setHeight(height + "px");      
	
		/* double buffer example */
		//Canvas backBuffer = Canvas.createIfSupported();
		//Context2d backBufferContext = backBuffer.getContext2d();
		// context.drawImage(backBufferContext.getCanvas(), 0, 0);

		//canvas = Canvas.createIfSupported();
		//canvas.setWidth(width + "px");
		//canvas.setCoordinateSpaceWidth(width);
     
		//canvas.setHeight(height + "px");      
		//canvas.setCoordinateSpaceHeight(height);

		//panel.add(canvas);

		resetButton = new Button("reset");
		resetButton.addClickHandler(new resetHandler());
		panel.add(resetButton);

		startButton = new Button("start");
		startButton.addClickHandler(new startHandler());
		panel.add(startButton);
		
		nAtomText = new TextBoxWithLabel("n Atoms", "");
		nAtomText.setText(Integer.toString(nAtoms));
		nAtomText.addChangeHandler(new textBoxHandler());
		panel.add(nAtomText);
		
	}


	private Object Integer(int nAtoms) {
		// TODO Auto-generated method stub
		return null;
	}


	public void drawLine(int x1, int y1, int x2, int y2) {
		Context2d context = canvas.getContext2d();
		CssColor color = CssColor.make(255,0,0);
		context.setFillStyle(color);
	    context.setStrokeStyle(color);

		context.beginPath();
		context.moveTo(x1, y1);
		context.lineTo(x2, y2);
		context.closePath();
	}

	class textBoxHandler implements ChangeHandler {
		@Override
		public void onChange(ChangeEvent event) {
			TextBoxWithLabel source = (TextBoxWithLabel) event.getSource();

			String msg;
			msg = nAtomText.getText();

			try {
			ChartEvent nAtomEvent = new ChartEvent(Integer.parseInt(msg));
			RadDecaySimulator.eventPlanner.fire(nAtomEvent);
			} catch (NumberFormatException e) {
				
			}
		}
	}

	class startHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String textAtoms;
			SimEvent msg = new SimEvent();
			msg.setName("control");

			if (toggle) {
				msg.setStringValue("start");
				toggle = false;
			} else {
				msg.setStringValue("stop");
				toggle = true;
			}
			RadDecaySimulator.eventPlanner.fire(msg);
		}
	}

	class resetHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String msg;
			msg = nAtomText.getText();

			ChartEvent nAtomEvent = new ChartEvent(Integer.parseInt(msg));
			RadDecaySimulator.eventPlanner.fire(nAtomEvent);
			toggle = true;
		}
		
	}
}
