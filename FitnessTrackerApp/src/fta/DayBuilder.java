package fta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;  

public class DayBuilder {

	private int steps = 0;
	private int heartRate = 0;
	private double sleep = 0;
	private Date date = null;
	
	Day day;
	
	public int getSteps() {
		return steps;
	}
	public DayBuilder setSteps(int steps) {
		this.steps = steps;
		return this;
	}
	public int getHeartRate() {
		return heartRate;
	}
	public DayBuilder setHeartRate(int heartRate) {
		this.heartRate = heartRate;
		return this;
	}
	public double getSleep() {
		return sleep;
	}
	public DayBuilder setSleep(double sleep) {
		this.sleep = sleep;
		return this;
	}
	public Date getDate() {
		return date;
	}
	public DayBuilder setDate(String string) throws ParseException {
		this.date = new SimpleDateFormat("dd/MM/yyyy").parse(string);
		return this;
	}
	
	
	public Day buildDay() {
		
		day = new Day(steps, heartRate, sleep, date);
		
		return day;
	}
	
	public Day getDay() {
		return day;
	}
	
}
