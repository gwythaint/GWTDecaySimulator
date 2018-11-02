package net.ntanet.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;

public class SimEventPlanner implements EventPlanner {
    interface MySimEventBinder extends EventBinder<RadDecaySimulator> {}
	
    private static final MySimEventBinder eventBinder = GWT.create(MySimEventBinder.class);
    static SimpleEventBus eventBus;

	public SimEventPlanner(RadDecaySimulator app) {
		eventBus = new SimpleEventBus();
    	eventBinder.bindEventHandlers(app, eventBus);
	}

	@Override
	public void fire(SimEvent event) {
		eventBus.fireEvent(event);
	}
}
