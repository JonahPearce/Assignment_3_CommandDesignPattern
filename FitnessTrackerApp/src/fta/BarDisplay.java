package fta;

// Receiver
public class BarDisplay {

	boolean BarDisplay = true;
	
	public boolean isBarDisplay() {
		return BarDisplay;
	}
	
	public void on() {
		BarDisplay = true;
	}
	public void off() {
		BarDisplay = false;
	}
}
