package fta;

public class BarDisplayOffCommand implements Command {

	BarDisplay barDisplay;
	
	public BarDisplayOffCommand(BarDisplay barDisplay) {
		this.barDisplay = barDisplay;
	}
	
	@Override
	public void execute() {
		barDisplay.on();
		
	}
	
	
}
