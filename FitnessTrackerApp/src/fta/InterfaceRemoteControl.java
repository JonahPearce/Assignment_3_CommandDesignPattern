package fta;

// Invoker
public class InterfaceRemoteControl {

	Command onCommand;
	Command offCommand;
	Command lastCommand;

	public void setCommand(Command onCommand, Command offCommand) {
		this.onCommand = onCommand;
		this.offCommand = offCommand;
	}
	
	public void buttonOnPressed() {
		lastCommand = onCommand;
		onCommand.execute();
	}
	
	public void buttonOffPressed() {
		lastCommand = offCommand;
		offCommand.execute();
	}
	
	public void buttonUndoPressed() {
		lastCommand.undo();
	}
	
}
