package net.ntanet.client;

public class ChartEvent extends SimEvent {
	private int atoms;
	
	public ChartEvent (int atoms) {
		this.atoms = atoms;
	}
	
	public int getValue() {
		return atoms;
	}
}
