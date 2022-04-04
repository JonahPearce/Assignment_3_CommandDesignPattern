package fta;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

public class MainWindow {

	protected Shell shell;
	private Text txtTheFitnessTracker;
	private Text txtJjjjjj;

	/**
	 * Launch the application.
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ParseException, ClassNotFoundException, IOException {
		
		AverageCalculator averageCalculator = new AverageCalculator();
		
		Average weeklyAverage = new Average(averageCalculator, 7);
		Average monthlyAverage = new Average(averageCalculator, 30);
		Average yearlyAverage = new Average(averageCalculator, 365);
		
		averageCalculator.registerObserver(weeklyAverage);
		averageCalculator.registerObserver(monthlyAverage);
		averageCalculator.registerObserver(weeklyAverage);
		
		FileIO fileIO = new FileIO();
		
		averageCalculator.setDays(FileIO.fileReader("Data.txt"));
		
		//Creating new data
		
		DayBuilder dayBuilder = new DayBuilder();
		
		dayBuilder.setSteps(1000).setHeartRate(80).setSleep(7.5).setDate("04/01/2022");
		
		Day day = dayBuilder.buildDay();
		
		averageCalculator.addDay(day);
		
		DayBuilder dayBuilder2 = new DayBuilder();
		
		dayBuilder2.setSteps(10000).setHeartRate(60).setSleep(4.5).setDate("04/05/2022");
		
		Day day2 = dayBuilder2.buildDay();
		
		averageCalculator.addDay(day2);
		
		DayBuilder dayBuilder3 = new DayBuilder();
		
		dayBuilder3.setSteps(5000).setHeartRate(70).setSleep(8.0).setDate("04/03/2022");
		
		Day day3 = dayBuilder3.buildDay();
		
		averageCalculator.addDay(day3);
		
		
		// Use the data
		DayIteratorClass dayIteratorClass = new DayIteratorClass(averageCalculator.getDays());

		Iterator dayIterator = dayIteratorClass.iterator();
		//java.util.Iterator<Day> arrayListIterartor = dayArrayList.iterator();
		

		while (dayIterator.hasLast()) {
			System.out.println(dayIterator.last().toString());
		}
		
		System.out.println(weeklyAverage.getAverageSteps());
		System.out.println(monthlyAverage.getAverageSteps());
		System.out.println(yearlyAverage.getAverageSteps());
		
		FileIO.fileWriter("Data.txt", averageCalculator.getDays());
		
		
		
		
		
		
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		shell.setSize(1329, 807);
		shell.setText("Fitness Tracker");

	}
}
