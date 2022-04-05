package fta;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.text.ParseException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainWindow {

	protected Shell FitnessTracker;
	protected Shell History;
	private Text txtTheFitnessTracker;
	private Text txtJjjjjj;
	private Day Goals;
	private Boolean Exit = false;

	
	/**
	 * Launch the application.
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) {

		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Open the window.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void open()  throws ParseException, ClassNotFoundException, IOException {
		
		InterfaceRemoteControl interfaceRemoteControl = new InterfaceRemoteControl();
		
		BarDisplay barDisplay = new BarDisplay();
		
		BarDisplayOnCommand barDisplayOnCommand = new BarDisplayOnCommand(barDisplay);
		BarDisplayOffCommand barDisplayOffCommand = new BarDisplayOffCommand(barDisplay);
		
		interfaceRemoteControl.setCommand(barDisplayOnCommand, barDisplayOffCommand);
		
		
		DayBuilder dayBuilderGoals = new DayBuilder();
		
		dayBuilderGoals.setSteps(10000).setHeartRate(80).setSleep(8.0);

		
		Goals = dayBuilderGoals.buildDay();
		
		//Data Setup
		
		AverageCalculator averageCalculator = new AverageCalculator();
		
		Average weeklyAverage = new Average(averageCalculator, 7);
		Average monthlyAverage = new Average(averageCalculator, 30);
		Average yearlyAverage = new Average(averageCalculator, 365);
		
		averageCalculator.registerObserver(weeklyAverage);
		averageCalculator.registerObserver(monthlyAverage);
		averageCalculator.registerObserver(yearlyAverage);
		
		FileIO fileIO = new FileIO();
		
		averageCalculator.setDays(FileIO.fileReader("Data.txt"));
		
		if (averageCalculator.getDays().size()<=0) {
			DayBuilder dayBuilder = new DayBuilder();
			
			dayBuilder.setSteps(10000).setHeartRate(80).setSleep(8.0).setDate("2022/01/01");
			averageCalculator.addDay(dayBuilder.buildDay());
		}
		

		//Display Setting
		
		
		Display display = Display.getDefault();
		FitnessTracker = new Shell();
		FitnessTracker.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		FitnessTracker.setSize(1920, 1080);
		FitnessTracker.setText("Fitness Tracker");
		FitnessTracker.open();
		FitnessTracker.layout();
		
		History = new Shell();
		
		History.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		History.setSize(1920, 1080);
		History.setText("Fitness Tracker - History");
		
		Label imageSteps = new Label(FitnessTracker, SWT.NONE);
		imageSteps.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		imageSteps.setImage(SWTResourceManager.getImage(MainWindow.class, "/fta/Steps.png"));
		imageSteps.setBounds(368, 151, 150, 150);
		
		Label imageHeart = new Label(FitnessTracker, SWT.NONE);
		imageHeart.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		imageHeart.setImage(SWTResourceManager.getImage(MainWindow.class, "/fta/Heart.png"));
		imageHeart.setBounds(870, 151, 150, 150);
		
		Label imageSleep = new Label(FitnessTracker, SWT.NONE);
		imageSleep.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		imageSleep.setImage(SWTResourceManager.getImage(MainWindow.class, "/fta/Sleep.png"));
		imageSleep.setBounds(1390, 151, 150, 150);
		
		Label varWeeklySteps = new Label(FitnessTracker, SWT.NONE);
		varWeeklySteps.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varWeeklySteps.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		varWeeklySteps.setBounds(376, 352, 142, 39);
		varWeeklySteps.setText("10,000");
		
		Label textWeekly = new Label(FitnessTracker, SWT.NONE);
		textWeekly.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		textWeekly.setText("Weekly");
		textWeekly.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		textWeekly.setBounds(378, 306, 142, 53);
		
		Label textMonthly = new Label(FitnessTracker, SWT.NONE);
		textMonthly.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		textMonthly.setText("Monthly");
		textMonthly.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		textMonthly.setBounds(348, 434, 84, 24);
		
		Label textFitnessTracker = new Label(FitnessTracker, SWT.NONE);
		textFitnessTracker.setText("Fitness Tracker");
		textFitnessTracker.setFont(SWTResourceManager.getFont("Ubuntu", 40, SWT.NORMAL));
		textFitnessTracker.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		textFitnessTracker.setBounds(756, 10, 368, 60);
		
		ProgressBar progressBarWeeklySteps = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarWeeklySteps.setSelection(50);
		progressBarWeeklySteps.setBounds(326, 397, 233, 15);
		
		Label textYearly = new Label(FitnessTracker, SWT.NONE);
		textYearly.setText("Yearly");
		textYearly.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		textYearly.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		textYearly.setBounds(472, 434, 74, 24);
		
		Label varMonthlySteps = new Label(FitnessTracker, SWT.NONE);
		varMonthlySteps.setText("10,000");
		varMonthlySteps.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varMonthlySteps.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varMonthlySteps.setBounds(348, 464, 74, 30);
		
		Label varYearlySteps = new Label(FitnessTracker, SWT.NONE);
		varYearlySteps.setText("10,000");
		varYearlySteps.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varYearlySteps.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varYearlySteps.setBounds(472, 464, 74, 30);
		
		ProgressBar progressBarMonthlySteps = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarMonthlySteps.setSelection(10);
		progressBarMonthlySteps.setBounds(324, 500, 115, 15);
		
		ProgressBar progressBarYearlySteps = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarYearlySteps.setSelection(10);
		progressBarYearlySteps.setBounds(445, 500, 115, 15);
		
		Label lblWeeklySteps_1_3_1 = new Label(FitnessTracker, SWT.NONE);
		lblWeeklySteps_1_3_1.setText("Heartrate");
		lblWeeklySteps_1_3_1.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		lblWeeklySteps_1_3_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblWeeklySteps_1_3_1.setBounds(870, 101, 182, 60);
		
		Label lblWeeklySteps_1_3_1_1 = new Label(FitnessTracker, SWT.NONE);
		lblWeeklySteps_1_3_1_1.setText("Sleep");
		lblWeeklySteps_1_3_1_1.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		lblWeeklySteps_1_3_1_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblWeeklySteps_1_3_1_1.setBounds(1412, 101, 115, 60);
		
		Label lblWeeklySteps_1_3_1_2 = new Label(FitnessTracker, SWT.NONE);
		lblWeeklySteps_1_3_1_2.setText("Steps");
		lblWeeklySteps_1_3_1_2.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		lblWeeklySteps_1_3_1_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblWeeklySteps_1_3_1_2.setBounds(390, 101, 115, 53);
		
		ProgressBar progressBarYearlySteps_1 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarYearlySteps_1.setSelection(10);
		progressBarYearlySteps_1.setBounds(949, 501, 115, 15);
		
		ProgressBar progressBarMonthlySteps_1 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarMonthlySteps_1.setSelection(10);
		progressBarMonthlySteps_1.setBounds(828, 501, 115, 15);
		
		Label varMonthlySteps_1 = new Label(FitnessTracker, SWT.NONE);
		varMonthlySteps_1.setText("10,000");
		varMonthlySteps_1.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varMonthlySteps_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varMonthlySteps_1.setBounds(852, 465, 74, 30);
		
		Label textMonthly_1 = new Label(FitnessTracker, SWT.NONE);
		textMonthly_1.setText("Monthly");
		textMonthly_1.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		textMonthly_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		textMonthly_1.setBounds(852, 435, 84, 24);
		
		Label textYearly_1 = new Label(FitnessTracker, SWT.NONE);
		textYearly_1.setText("Yearly");
		textYearly_1.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		textYearly_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		textYearly_1.setBounds(976, 435, 74, 24);
		
		Label varYearlySteps_1 = new Label(FitnessTracker, SWT.NONE);
		varYearlySteps_1.setText("10,000");
		varYearlySteps_1.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varYearlySteps_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varYearlySteps_1.setBounds(976, 465, 74, 30);
		
		ProgressBar progressBarWeeklySteps_1 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarWeeklySteps_1.setSelection(10);
		progressBarWeeklySteps_1.setBounds(830, 398, 233, 15);
		
		Label varWeeklySteps_1 = new Label(FitnessTracker, SWT.NONE);
		varWeeklySteps_1.setText("10,000");
		varWeeklySteps_1.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		varWeeklySteps_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varWeeklySteps_1.setBounds(880, 353, 142, 39);
		
		Label textWeekly_1 = new Label(FitnessTracker, SWT.NONE);
		textWeekly_1.setText("Weekly");
		textWeekly_1.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		textWeekly_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		textWeekly_1.setBounds(882, 307, 142, 53);
		
		ProgressBar progressBarYearlySteps_2 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarYearlySteps_2.setSelection(10);
		progressBarYearlySteps_2.setBounds(1466, 501, 115, 15);
		
		ProgressBar progressBarMonthlySteps_2 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarMonthlySteps_2.setSelection(10);
		progressBarMonthlySteps_2.setBounds(1345, 501, 115, 15);
		
		Label varMonthlySteps_2 = new Label(FitnessTracker, SWT.NONE);
		varMonthlySteps_2.setText("10,000");
		varMonthlySteps_2.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varMonthlySteps_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varMonthlySteps_2.setBounds(1369, 465, 74, 30);
		
		Label textMonthly_2 = new Label(FitnessTracker, SWT.NONE);
		textMonthly_2.setText("Monthly");
		textMonthly_2.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		textMonthly_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		textMonthly_2.setBounds(1369, 435, 84, 24);
		
		Label textYearly_2 = new Label(FitnessTracker, SWT.NONE);
		textYearly_2.setText("Yearly");
		textYearly_2.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		textYearly_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		textYearly_2.setBounds(1493, 435, 74, 24);
		
		Label varYearlySteps_2 = new Label(FitnessTracker, SWT.NONE);
		varYearlySteps_2.setText("10,000");
		varYearlySteps_2.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varYearlySteps_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varYearlySteps_2.setBounds(1493, 465, 74, 30);
		
		ProgressBar progressBarWeeklySteps_2 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarWeeklySteps_2.setSelection(10);
		progressBarWeeklySteps_2.setBounds(1347, 398, 233, 15);
		
		Label varWeeklySteps_2 = new Label(FitnessTracker, SWT.NONE);
		varWeeklySteps_2.setText("10,000");
		varWeeklySteps_2.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		varWeeklySteps_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varWeeklySteps_2.setBounds(1397, 353, 142, 39);
		
		Label textWeekly_2 = new Label(FitnessTracker, SWT.NONE);
		textWeekly_2.setText("Weekly");
		textWeekly_2.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		textWeekly_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		textWeekly_2.setBounds(1399, 307, 142, 53);
		
		Label todayName = new Label(FitnessTracker, SWT.NONE);
		todayName.setText("Today");
		todayName.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		todayName.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		todayName.setBounds(113, 654, 91, 39);
		
		Label varTodayDate = new Label(FitnessTracker, SWT.NONE);
		varTodayDate.setText("Date: N/A");
		varTodayDate.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayDate.setBounds(63, 702, 182, 24);
		
		Label varTodaySteps = new Label(FitnessTracker, SWT.NONE);
		varTodaySteps.setText("Steps: N/A");
		varTodaySteps.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySteps.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySteps.setBounds(87, 737, 131, 24);
		
		Label varTodayHeartrate = new Label(FitnessTracker, SWT.NONE);
		varTodayHeartrate.setText("Heartrate: N/A");
		varTodayHeartrate.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayHeartrate.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayHeartrate.setBounds(87, 816, 131, 24);
		
		Label varTodaySleep = new Label(FitnessTracker, SWT.NONE);
		varTodaySleep.setText("Sleep: N/A");
		varTodaySleep.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySleep.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySleep.setBounds(87, 891, 131, 24);
		
		ProgressBar progressBarTodaySteps = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySteps.setSelection(10);
		progressBarTodaySteps.setBounds(80, 780, 148, 15);
		
		ProgressBar progressBarTodayHeartrate = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodayHeartrate.setSelection(10);
		progressBarTodayHeartrate.setBounds(80, 856, 148, 15);
		
		ProgressBar progressBarTodaySleep = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySleep.setSelection(10);
		progressBarTodaySleep.setBounds(80, 932, 148, 15);
		
		Label todayName_1 = new Label(FitnessTracker, SWT.NONE);
		todayName_1.setText("3 Days Ago");
		todayName_1.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		todayName_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		todayName_1.setBounds(867, 654, 159, 39);
		
		Label varTodayDate_1 = new Label(FitnessTracker, SWT.NONE);
		varTodayDate_1.setText("Date: N/A");
		varTodayDate_1.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayDate_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayDate_1.setBounds(852, 702, 182, 24);
		
		Label varTodaySteps_1 = new Label(FitnessTracker, SWT.NONE);
		varTodaySteps_1.setText("Steps: N/A");
		varTodaySteps_1.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySteps_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySteps_1.setBounds(876, 737, 131, 24);
		
		ProgressBar progressBarTodaySteps_1 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySteps_1.setSelection(10);
		progressBarTodaySteps_1.setBounds(869, 780, 148, 15);
		
		Label varTodayHeartrate_1 = new Label(FitnessTracker, SWT.NONE);
		varTodayHeartrate_1.setText("Heartrate: N/A");
		varTodayHeartrate_1.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayHeartrate_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayHeartrate_1.setBounds(876, 816, 131, 24);
		
		ProgressBar progressBarTodayHeartrate_1 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodayHeartrate_1.setSelection(10);
		progressBarTodayHeartrate_1.setBounds(869, 856, 148, 15);
		
		Label varTodaySleep_1 = new Label(FitnessTracker, SWT.NONE);
		varTodaySleep_1.setText("Sleep: N/A");
		varTodaySleep_1.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySleep_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySleep_1.setBounds(876, 891, 131, 24);
		
		ProgressBar progressBarTodaySleep_1 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySleep_1.setSelection(10);
		progressBarTodaySleep_1.setBounds(869, 932, 148, 15);
		
		Label todayName_2 = new Label(FitnessTracker, SWT.NONE);
		todayName_2.setText("6 Days Ago");
		todayName_2.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		todayName_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		todayName_2.setBounds(1663, 654, 158, 39);
		
		Label varTodayDate_2 = new Label(FitnessTracker, SWT.NONE);
		varTodayDate_2.setText("Date: N/A");
		varTodayDate_2.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayDate_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayDate_2.setBounds(1639, 702, 182, 24);
		
		Label varTodaySteps_2 = new Label(FitnessTracker, SWT.NONE);
		varTodaySteps_2.setText("Steps: N/A");
		varTodaySteps_2.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySteps_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySteps_2.setBounds(1663, 737, 131, 24);
		
		ProgressBar progressBarTodaySteps_2 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySteps_2.setSelection(10);
		progressBarTodaySteps_2.setBounds(1656, 780, 148, 15);
		
		Label varTodayHeartrate_2 = new Label(FitnessTracker, SWT.NONE);
		varTodayHeartrate_2.setText("Heartrate: N/A");
		varTodayHeartrate_2.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayHeartrate_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayHeartrate_2.setBounds(1663, 816, 131, 24);
		
		ProgressBar progressBarTodayHeartrate_2 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodayHeartrate_2.setSelection(10);
		progressBarTodayHeartrate_2.setBounds(1656, 856, 148, 15);
		
		Label varTodaySleep_2 = new Label(FitnessTracker, SWT.NONE);
		varTodaySleep_2.setText("Sleep: N/A");
		varTodaySleep_2.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySleep_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySleep_2.setBounds(1663, 891, 131, 24);
		
		ProgressBar progressBarTodaySleep_2 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySleep_2.setSelection(10);
		progressBarTodaySleep_2.setBounds(1656, 932, 148, 15);
		
		Label todayName_3 = new Label(FitnessTracker, SWT.NONE);
		todayName_3.setText("Yesterday");
		todayName_3.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		todayName_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		todayName_3.setBounds(348, 654, 142, 39);
		
		Label varTodayDate_3 = new Label(FitnessTracker, SWT.NONE);
		varTodayDate_3.setText("Date: N/A");
		varTodayDate_3.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayDate_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayDate_3.setBounds(326, 702, 182, 24);
		
		Label varTodaySteps_3 = new Label(FitnessTracker, SWT.NONE);
		varTodaySteps_3.setText("Steps: N/A");
		varTodaySteps_3.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySteps_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySteps_3.setBounds(350, 737, 131, 24);
		
		ProgressBar progressBarTodaySteps_3 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySteps_3.setSelection(10);
		progressBarTodaySteps_3.setBounds(343, 780, 148, 15);
		
		Label varTodayHeartrate_3 = new Label(FitnessTracker, SWT.NONE);
		varTodayHeartrate_3.setText("Heartrate: N/A");
		varTodayHeartrate_3.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayHeartrate_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayHeartrate_3.setBounds(350, 816, 131, 24);
		
		ProgressBar progressBarTodayHeartrate_3 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodayHeartrate_3.setSelection(10);
		progressBarTodayHeartrate_3.setBounds(343, 856, 148, 15);
		
		Label varTodaySleep_3 = new Label(FitnessTracker, SWT.NONE);
		varTodaySleep_3.setText("Sleep: N/A");
		varTodaySleep_3.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySleep_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySleep_3.setBounds(350, 891, 131, 24);
		
		ProgressBar progressBarTodaySleep_3 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySleep_3.setSelection(10);
		progressBarTodaySleep_3.setBounds(343, 932, 148, 15);
		
		Label todayName_4 = new Label(FitnessTracker, SWT.NONE);
		todayName_4.setText("2 Days Ago");
		todayName_4.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		todayName_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		todayName_4.setBounds(595, 654, 159, 39);
		
		Label varTodayDate_4 = new Label(FitnessTracker, SWT.NONE);
		varTodayDate_4.setText("Date: N/A");
		varTodayDate_4.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayDate_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayDate_4.setBounds(580, 702, 182, 24);
		
		Label varTodaySteps_4 = new Label(FitnessTracker, SWT.NONE);
		varTodaySteps_4.setText("Steps: N/A");
		varTodaySteps_4.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySteps_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySteps_4.setBounds(604, 737, 131, 24);
		
		ProgressBar progressBarTodaySteps_4 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySteps_4.setSelection(10);
		progressBarTodaySteps_4.setBounds(597, 780, 148, 15);
		
		Label varTodayHeartrate_4 = new Label(FitnessTracker, SWT.NONE);
		varTodayHeartrate_4.setText("Heartrate: N/A");
		varTodayHeartrate_4.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayHeartrate_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayHeartrate_4.setBounds(604, 816, 131, 24);
		
		ProgressBar progressBarTodayHeartrate_4 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodayHeartrate_4.setSelection(10);
		progressBarTodayHeartrate_4.setBounds(597, 856, 148, 15);
		
		Label varTodaySleep_4 = new Label(FitnessTracker, SWT.NONE);
		varTodaySleep_4.setText("Sleep: N/A");
		varTodaySleep_4.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySleep_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySleep_4.setBounds(604, 891, 131, 24);
		
		ProgressBar progressBarTodaySleep_4 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySleep_4.setSelection(10);
		progressBarTodaySleep_4.setBounds(597, 932, 148, 15);
		
		Label todayName_5 = new Label(FitnessTracker, SWT.NONE);
		todayName_5.setText("4 Days Ago");
		todayName_5.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		todayName_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		todayName_5.setBounds(1124, 654, 164, 39);
		
		Label varTodayDate_5 = new Label(FitnessTracker, SWT.NONE);
		varTodayDate_5.setText("Date: N/A");
		varTodayDate_5.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayDate_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayDate_5.setBounds(1107, 702, 182, 24);
		
		Label varTodaySteps_5 = new Label(FitnessTracker, SWT.NONE);
		varTodaySteps_5.setText("Steps: N/A");
		varTodaySteps_5.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySteps_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySteps_5.setBounds(1131, 737, 131, 24);
		
		ProgressBar progressBarTodaySteps_5 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySteps_5.setSelection(10);
		progressBarTodaySteps_5.setBounds(1124, 780, 148, 15);
		
		Label varTodayHeartrate_5 = new Label(FitnessTracker, SWT.NONE);
		varTodayHeartrate_5.setText("Heartrate: N/A");
		varTodayHeartrate_5.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayHeartrate_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayHeartrate_5.setBounds(1131, 816, 131, 24);
		
		ProgressBar progressBarTodayHeartrate_5 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodayHeartrate_5.setSelection(10);
		progressBarTodayHeartrate_5.setBounds(1124, 856, 148, 15);
		
		Label varTodaySleep_5 = new Label(FitnessTracker, SWT.NONE);
		varTodaySleep_5.setText("Sleep: N/A");
		varTodaySleep_5.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySleep_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySleep_5.setBounds(1131, 891, 131, 24);
		
		ProgressBar progressBarTodaySleep_5 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySleep_5.setSelection(10);
		progressBarTodaySleep_5.setBounds(1124, 932, 148, 15);
		
		Label todayName_6 = new Label(FitnessTracker, SWT.NONE);
		todayName_6.setText("5 Days Ago");
		todayName_6.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		todayName_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		todayName_6.setBounds(1407, 654, 160, 39);
		
		Label varTodayDate_6 = new Label(FitnessTracker, SWT.NONE);
		varTodayDate_6.setText("Date: N/A");
		varTodayDate_6.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayDate_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayDate_6.setBounds(1385, 702, 182, 24);
		
		Label varTodaySteps_6 = new Label(FitnessTracker, SWT.NONE);
		varTodaySteps_6.setText("Steps: N/A");
		varTodaySteps_6.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySteps_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySteps_6.setBounds(1409, 737, 131, 24);
		
		ProgressBar progressBarTodaySteps_6 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySteps_6.setSelection(10);
		progressBarTodaySteps_6.setBounds(1402, 780, 148, 15);
		
		Label varTodayHeartrate_6 = new Label(FitnessTracker, SWT.NONE);
		varTodayHeartrate_6.setText("Heartrate: N/A");
		varTodayHeartrate_6.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodayHeartrate_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodayHeartrate_6.setBounds(1409, 816, 131, 24);
		
		ProgressBar progressBarTodayHeartrate_6 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodayHeartrate_6.setSelection(10);
		progressBarTodayHeartrate_6.setBounds(1402, 856, 148, 15);
		
		Label varTodaySleep_6 = new Label(FitnessTracker, SWT.NONE);
		varTodaySleep_6.setText("Sleep: N/A");
		varTodaySleep_6.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		varTodaySleep_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		varTodaySleep_6.setBounds(1409, 891, 131, 24);
		
		ProgressBar progressBarTodaySleep_6 = new ProgressBar(FitnessTracker, SWT.NONE);
		progressBarTodaySleep_6.setSelection(10);
		progressBarTodaySleep_6.setBounds(1402, 932, 148, 15);
		
		Button addNewDataButton = new Button(FitnessTracker, SWT.NONE);
		addNewDataButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				DataInputDialog newWindow = new DataInputDialog(FitnessTracker);
				newWindow.open();
				
				Day day = newWindow.getDay();
				if (day.getDate()!=null) {
				averageCalculator.addDay(newWindow.getDay());
				}
				
				try {
					FileIO.fileWriter("Data.txt", averageCalculator.getDays());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		addNewDataButton.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		addNewDataButton.setToolTipText("Add new data to the system");
		addNewDataButton.setBounds(28, 22, 206, 53);
		addNewDataButton.setText("Add New Data");
		
		Button adjustGoalsButton = new Button(FitnessTracker, SWT.NONE);
		adjustGoalsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AdjustGoalsDialog newWindow = new AdjustGoalsDialog(FitnessTracker);
				newWindow.open();
				Goals = newWindow.getGoals();
			}
		});
		adjustGoalsButton.setToolTipText("Add new data to the system");
		adjustGoalsButton.setText("Adjust Goals");
		adjustGoalsButton.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		adjustGoalsButton.setBounds(28, 102, 206, 53);
		
		Label lblStepsGoal = new Label(FitnessTracker, SWT.NONE);
		lblStepsGoal.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		
		lblStepsGoal.setBounds(28, 180, 206, 30);
		
		Label lblHeartrateGoal = new Label(FitnessTracker, SWT.NONE);
		lblHeartrateGoal.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		lblHeartrateGoal.setBounds(28, 216, 206, 30);
		
		Label lblSleepGoal = new Label(FitnessTracker, SWT.NONE);
		
		lblSleepGoal.setFont(SWTResourceManager.getFont("Ubuntu", 15, SWT.NORMAL));
		lblSleepGoal.setBounds(28, 252, 206, 30);
		
		Button toggleProgressBars = new Button(FitnessTracker, SWT.NONE);
		toggleProgressBars.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if (barDisplay.isBarDisplay()==true) {
					interfaceRemoteControl.buttonOffPressed();
				} else {
					interfaceRemoteControl.buttonOnPressed();
				}
				
			}
		});
		toggleProgressBars.setToolTipText("Togglers the Progress Bars");
		toggleProgressBars.setText("Toggle Bars");
		toggleProgressBars.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		//toggleProgressBars.setBounds(1664, 22, 206, 53);
		
		Button exitButton = new Button(FitnessTracker, SWT.NONE);
		exitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FitnessTracker.close();
			}
		});
		exitButton.setToolTipText("Togglers the Progress Bars");
		exitButton.setText("Exit");
		exitButton.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		exitButton.setBounds(1664, 22, 206, 53);
		
		DayIteratorClass dayIteratorClass2 = new DayIteratorClass(averageCalculator.getDays());

		Iterator dayIterator2 = dayIteratorClass2.iterator();
		//java.util.Iterator<Day> arrayListIterartor = dayArrayList.iterator();
		

		while (dayIterator2.hasLast()) {
			System.out.println(dayIterator2.last().toString());
		}
		
		
		while (!FitnessTracker.isDisposed()) {
			if (!display.readAndDispatch()) {
				
				
				lblStepsGoal.setText("Steps Goal: "+Goals.getSteps());
				lblHeartrateGoal.setText("Heartrate Goal: "+Goals.getHeartRate());
				lblSleepGoal.setText("Sleep Goal: "+Goals.getSleep());
				
				varWeeklySteps.setText(""+weeklyAverage.getAverageSteps());
				varMonthlySteps.setText(""+monthlyAverage.getAverageSteps());
				varYearlySteps.setText(""+yearlyAverage.getAverageSteps());
				
				varWeeklySteps_1.setText(""+weeklyAverage.getAverageHeartRate());
				varMonthlySteps_1.setText(""+monthlyAverage.getAverageHeartRate());
				varYearlySteps_1.setText(""+yearlyAverage.getAverageHeartRate());
				
				varWeeklySteps_2.setText(""+weeklyAverage.getAverageSleep());
				varMonthlySteps_2.setText(""+monthlyAverage.getAverageSleep());
				varYearlySteps_2.setText(""+yearlyAverage.getAverageSleep());
				
				progressBarWeeklySteps.setSelection((int)(100*((double)weeklyAverage.getAverageSteps()/Goals.getSteps())));
				progressBarMonthlySteps.setSelection((int)(100*((double)monthlyAverage.getAverageSteps()/Goals.getSteps())));
				progressBarYearlySteps.setSelection((int)(100*((double)yearlyAverage.getAverageSteps()/Goals.getSteps())));
				
				progressBarWeeklySteps_1.setSelection((int)(100*((double)weeklyAverage.getAverageHeartRate()/Goals.getHeartRate())));
				progressBarMonthlySteps_1.setSelection((int)(100*((double)monthlyAverage.getAverageHeartRate()/Goals.getHeartRate())));
				progressBarYearlySteps_1.setSelection((int)(100*((double)yearlyAverage.getAverageHeartRate()/Goals.getHeartRate())));
				
				progressBarWeeklySteps_2.setSelection((int)(100*((double)weeklyAverage.getAverageSleep()/Goals.getSleep())));
				progressBarMonthlySteps_2.setSelection((int)(100*((double)monthlyAverage.getAverageSleep()/Goals.getSleep())));
				progressBarYearlySteps_2.setSelection((int)(100*((double)yearlyAverage.getAverageSleep()/Goals.getSleep())));
				
				DayIteratorClass dayIteratorClass = new DayIteratorClass(averageCalculator.getDays());

				Iterator dayIterator = dayIteratorClass.iterator();
				//java.util.Iterator<Day> arrayListIterartor = dayArrayList.iterator();
				
				if (dayIterator.hasLast()) { // Day 1
					Day day = (Day) dayIterator.last();
					
					if (day.getDate()!=null) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
					varTodayDate.setText(simpleDateFormat.format(day.getDate()));
					}
					varTodaySteps.setText(""+day.getSteps());
					varTodayHeartrate.setText(""+day.getHeartRate());
					varTodaySleep.setText(""+day.getSleep());
					progressBarTodaySteps.setSelection((int)(100*((double)day.getSteps()/Goals.getSteps())));
					progressBarTodayHeartrate.setSelection((int)(100*((double)day.getHeartRate()/Goals.getHeartRate())));
					progressBarTodaySleep.setSelection((int)(100*((double)day.getSleep()/Goals.getSleep())));
					
					
				}
				if (dayIterator.hasLast()) { // Day 2
					Day day = (Day) dayIterator.last();
					if (day.getDate()!=null) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
					varTodayDate_3.setText(simpleDateFormat.format(day.getDate()));
					}
					varTodaySteps_3.setText(""+day.getSteps());
					varTodayHeartrate_3.setText(""+day.getHeartRate());
					varTodaySleep_3.setText(""+day.getSleep());
					progressBarTodaySteps_3.setSelection((int)(100*((double)day.getSteps()/Goals.getSteps())));
					progressBarTodayHeartrate_3.setSelection((int)(100*((double)day.getHeartRate()/Goals.getHeartRate())));
					progressBarTodaySleep_3.setSelection((int)(100*((double)day.getSleep()/Goals.getSleep())));
				}
				if (dayIterator.hasLast()) { // Day 3
					Day day = (Day) dayIterator.last();
					if (day.getDate()!=null) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
					varTodayDate_4.setText(simpleDateFormat.format(day.getDate()));
					}
					varTodaySteps_4.setText(""+day.getSteps());
					varTodayHeartrate_4.setText(""+day.getHeartRate());
					varTodaySleep_4.setText(""+day.getSleep());
					progressBarTodaySteps_4.setSelection((int)(100*((double)day.getSteps()/Goals.getSteps())));
					progressBarTodayHeartrate_4.setSelection((int)(100*((double)day.getHeartRate()/Goals.getHeartRate())));
					progressBarTodaySleep_4.setSelection((int)(100*((double)day.getSleep()/Goals.getSleep())));
				}
				if (dayIterator.hasLast()) { // Day 4
					Day day = (Day) dayIterator.last();
					if (day.getDate()!=null) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
					varTodayDate_1.setText(simpleDateFormat.format(day.getDate()));
					}
					varTodaySteps_1.setText(""+day.getSteps());
					varTodayHeartrate_1.setText(""+day.getHeartRate());
					varTodaySleep_1.setText(""+day.getSleep());
					progressBarTodaySteps_1.setSelection((int)(100*((double)day.getSteps()/Goals.getSteps())));
					progressBarTodayHeartrate_1.setSelection((int)(100*((double)day.getHeartRate()/Goals.getHeartRate())));
					progressBarTodaySleep_1.setSelection((int)(100*((double)day.getSleep()/Goals.getSleep())));
				}
				if (dayIterator.hasLast()) { // Day 5
					Day day = (Day) dayIterator.last();
					if (day.getDate()!=null) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
					varTodayDate_5.setText(simpleDateFormat.format(day.getDate()));
					}
					varTodaySteps_5.setText(""+day.getSteps());
					varTodayHeartrate_5.setText(""+day.getHeartRate());
					varTodaySleep_5.setText(""+day.getSleep());
					progressBarTodaySteps_5.setSelection((int)(100*((double)day.getSteps()/Goals.getSteps())));
					progressBarTodayHeartrate_5.setSelection((int)(100*((double)day.getHeartRate()/Goals.getHeartRate())));
					progressBarTodaySleep_5.setSelection((int)(100*((double)day.getSleep()/Goals.getSleep())));
				}
				if (dayIterator.hasLast()) { // Day 6
					Day day = (Day) dayIterator.last();
					if (day.getDate()!=null) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
					varTodayDate_6.setText(simpleDateFormat.format(day.getDate()));
					}
					varTodaySteps_6.setText(""+day.getSteps());
					varTodayHeartrate_6.setText(""+day.getHeartRate());
					varTodaySleep_6.setText(""+day.getSleep());
					progressBarTodaySteps_6.setSelection((int)(100*((double)day.getSteps()/Goals.getSteps())));
					progressBarTodayHeartrate_6.setSelection((int)(100*((double)day.getHeartRate()/Goals.getHeartRate())));
					progressBarTodaySleep_6.setSelection((int)(100*((double)day.getSleep()/Goals.getSleep())));
				}
				if (dayIterator.hasLast()) { // Day 7
					Day day = (Day) dayIterator.last();
					if (day.getDate()!=null) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
					varTodayDate_2.setText(simpleDateFormat.format(day.getDate()));
					}
					varTodaySteps_2.setText(""+day.getSteps());
					varTodayHeartrate_2.setText(""+day.getHeartRate());
					varTodaySleep_2.setText(""+day.getSleep());
					progressBarTodaySteps_2.setSelection((int)(100*((double)day.getSteps()/Goals.getSteps())));
					progressBarTodayHeartrate_2.setSelection((int)(100*((double)day.getHeartRate()/Goals.getHeartRate())));
					progressBarTodaySleep_2.setSelection((int)(100*((double)day.getSleep()/Goals.getSleep())));
				}
				
				
				//display.sleep();
			}
		}
	}
}
