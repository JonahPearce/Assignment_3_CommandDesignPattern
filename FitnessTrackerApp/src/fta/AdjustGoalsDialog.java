package fta;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AdjustGoalsDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text txtGoal;
	private Text text_1;
	private Text text_2;
	private Button btnNewButton;
	private Day Goals;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AdjustGoalsDialog(Shell parent) {
		super(parent);
		setText("Adjusting Goals");
	}

	public Day getGoals() {
		return Goals;
	}

	public void setGoals(Day goals) {
		Goals = goals;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(540, 720);
		shell.setText(getText());
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		text = new Text(shell, SWT.BORDER);
		text.setBounds(178, 196, 167, 32);
		
		txtGoal = new Text(shell, SWT.BORDER);
		txtGoal.setFont(SWTResourceManager.getFont("Ubuntu", 40, SWT.NORMAL));
		txtGoal.setText("Goals");
		txtGoal.setBounds(186, 45, 149, 66);
		
		Label lblStepsGoal = new Label(shell, SWT.NONE);
		lblStepsGoal.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		lblStepsGoal.setBounds(196, 146, 149, 38);
		lblStepsGoal.setText("Steps Goal");
		
		Label lblHeartrateGoal = new Label(shell, SWT.NONE);
		lblHeartrateGoal.setText("Heartrate Goal");
		lblHeartrateGoal.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		lblHeartrateGoal.setBounds(174, 260, 187, 38);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(178, 309, 167, 32);
		
		Label lblSleepGoal = new Label(shell, SWT.NONE);
		lblSleepGoal.setText("Sleep Goal");
		lblSleepGoal.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		lblSleepGoal.setBounds(196, 380, 149, 38);
		
		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(178, 424, 167, 32);
		
		btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DayBuilder dayBuilder = new DayBuilder();
				dayBuilder.setSteps(Integer.parseInt(text.getText())).setHeartRate(Integer.parseInt(text_1.getText())).setSleep(Double.parseDouble(text_2.getText()));
				
				Goals = dayBuilder.buildDay();
				shell.close();
				
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		btnNewButton.setBounds(178, 514, 167, 66);
		btnNewButton.setText("Submit");

	}
}
