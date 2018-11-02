package net.ntanet.client;

import com.google.web.bindery.event.shared.binder.GenericEvent;


public class SimEvent extends GenericEvent {
	String name;
	String stringValue;
	Integer integerValue;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public Integer getIntegerValue() {
		return integerValue;
	}
	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}   
}
