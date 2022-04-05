package fta;

// Invoker
public class InterfaceRemoteControl {

	Command onCommand;
	Command offCommand;

	public void setCommand(Command onCommand, Command offCommand) {
		this.onCommand = onCommand;
		this.offCommand = offCommand;
	}
	
	public void buttonOnPressed() {;
		onCommand.execute();
	}
	
	public void buttonOffPressed() {
		offCommand.execute();
	}
	
}
