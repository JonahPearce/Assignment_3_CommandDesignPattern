package fta;

import java.io.Serializable;
import java.util.Date;

public class Day implements Serializable {

	private final int steps;
	private final int heartRate;
	private final double sleep;
	private final Date date;
	
	@Override
	public String toString() {
		return "Day [steps=" + steps + ", heartRate=" + heartRate + ", sleep=" + sleep + ", date=" + date + "]";
	}

	public Day(int steps, int heartRate, double sleep, Date date) {
		super();
		this.steps = steps;
		this.heartRate = heartRate;
		this.sleep = sleep;
		this.date = date;
	}
	
	public int getSteps() {
		return steps;
	}
	
	public int getHeartRate() {
		return heartRate;
	}
	
	public double getSleep() {
		return sleep;
	}
	
	public Date getDate() {
		return date;
	}
	
}
