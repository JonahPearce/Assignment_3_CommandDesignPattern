package fta;

public class BarDisplayOnCommand implements Command {

	BarDisplay barDisplay;
	
	public BarDisplayOnCommand(BarDisplay barDisplay) {
		this.barDisplay = barDisplay;
	}
	
	@Override
	public void execute() {
		barDisplay.off();
		
	}
	
	
}
