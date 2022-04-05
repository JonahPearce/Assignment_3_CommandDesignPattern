package fta;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.text.ParseException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DataInputDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Button btnNewButton;
	private Day day;
	private Label lblNewLabel;
	private Text text_3;
	private Label lblDate;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Label label;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DataInputDialog(Shell parent) {
		super(parent);
		setText("Data Input - Fitness Tracker");
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day inputDay) {
		day = inputDay;
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
		shell.setSize(1000, 720);
		shell.setText(getText());
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		text = new Text(shell, SWT.BORDER);
		text.setBounds(143, 196, 167, 32);
		
		Label lblStepsGoal = new Label(shell, SWT.NONE);
		lblStepsGoal.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblStepsGoal.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		lblStepsGoal.setBounds(157, 146, 149, 38);
		lblStepsGoal.setText("Steps Log");
		
		Label lblHeartrateGoal = new Label(shell, SWT.NONE);
		lblHeartrateGoal.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblHeartrateGoal.setText("Heartrate Log");
		lblHeartrateGoal.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		lblHeartrateGoal.setBounds(424, 146, 187, 38);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(424, 196, 167, 32);
		
		Label lblSleepGoal = new Label(shell, SWT.NONE);
		lblSleepGoal.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblSleepGoal.setText("Sleep Log");
		lblSleepGoal.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		lblSleepGoal.setBounds(686, 146, 149, 38);
		
		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(693, 196, 167, 32);
		
		
		lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		lblNewLabel.setBounds(348, 37, 323, 51);
		lblNewLabel.setText("New Fitness Entry");
		
		text_3 = new Text(shell, SWT.BORDER);
		text_3.setBounds(424, 515, 167, 32);
		
		lblDate = new Label(shell, SWT.NONE);
		lblDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblDate.setText("Date");
		lblDate.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		lblDate.setBounds(474, 471, 70, 38);
		
		lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_1.setImage(SWTResourceManager.getImage(DataInputDialog.class, "/fta/Steps2.png"));
		lblNewLabel_1.setBounds(136, 245, 200, 210);
		
		lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_2.setImage(SWTResourceManager.getImage(DataInputDialog.class, "/fta/Heart2.png"));
		lblNewLabel_2.setBounds(255, 109, 342, 322);
		
		label = new Label(shell, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label.setImage(SWTResourceManager.getImage(DataInputDialog.class, "/fta/Sleep2.png"));
		label.setBounds(671, 234, 181, 221);
		
		btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DayBuilder dayBuilder = new DayBuilder();
				try {
					dayBuilder.setSteps(Integer.parseInt(text.getText())).setHeartRate(Integer.parseInt(text_1.getText())).setSleep(Double.parseDouble(text_2.getText())).setDate(text_3.getText());
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				day = dayBuilder.buildDay();
				shell.close();
				
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		btnNewButton.setBounds(424, 588, 167, 66);
		btnNewButton.setText("Submit");


	}
}
