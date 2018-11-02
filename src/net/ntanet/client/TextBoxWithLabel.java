package net.ntanet.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;


public class TextBoxWithLabel extends Composite implements HasChangeHandlers {
	
	private Label label;
	private TextBox textBox;

	public TextBoxWithLabel() {
		HorizontalPanel panel = new HorizontalPanel();
		initWidget(panel);

		panel.add(this.label);
		panel.add(textBox);
	}
	
	public TextBoxWithLabel(String label, String text) {
		HorizontalPanel panel = new HorizontalPanel();
		initWidget(panel);
		
		this.textBox = new TextBox();
		this.textBox.setText(text);
		this.label = new Label(label);

		panel.add(this.label);
		panel.add(textBox);
	}
	
	public void setText(String text) {
		textBox.setText(text);
	}
	public String getText() {
		return textBox.getText();
	}

	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

}
