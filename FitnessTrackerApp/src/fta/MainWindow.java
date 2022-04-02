package fta;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ParseException, ClassNotFoundException, IOException {
		
		ArrayList<Day> dayArrayList = new ArrayList<>();
		
		FileIO fileIO = new FileIO();
		
		dayArrayList = fileIO.fileReader("Data.txt");
		
		//Creating new data
		
		DayBuilder dayBuilder = new DayBuilder();
		
		dayBuilder.setSteps(1000).setHeartRate(80).setSleep(7.5).setDate("04/01/2022");
		
		Day day = dayBuilder.buildDay();
		
		dayArrayList.add(day);
		
		DayBuilder dayBuilder2 = new DayBuilder();
		
		dayBuilder2.setSteps(10000).setHeartRate(60).setSleep(4.5).setDate("04/05/2022");
		
		Day day2 = dayBuilder2.buildDay();
		
		dayArrayList.add(day2);
		
		DayBuilder dayBuilder3 = new DayBuilder();
		
		dayBuilder3.setSteps(5000).setHeartRate(70).setSleep(8.0).setDate("04/03/2022");
		
		Day day3 = dayBuilder3.buildDay();
		
		dayArrayList.add(day3);
		
		
		// Use the data
		DayIteratorClass dayIteratorClass = new DayIteratorClass(dayArrayList);

		Iterator dayIterator = dayIteratorClass.iterator();
		//java.util.Iterator<Day> arrayListIterartor = dayArrayList.iterator();
		

		while (dayIterator.hasLast()) {
			System.out.println(dayIterator.last().toString());
		}
		
		
		fileIO.fileWriter("Data.txt", dayArrayList);
		
		
		
		
		
		
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
		shell.setSize(450, 300);
		shell.setText("Fitness Tracker");

	}

}
