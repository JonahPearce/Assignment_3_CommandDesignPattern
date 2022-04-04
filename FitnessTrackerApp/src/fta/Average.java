package fta;

import java.util.ArrayList;

public class Average implements Observer{

	private ArrayList<Day> dayArrayList = new ArrayList<>();
	private Double averageSteps;
	private Double averageHeartRate;
	private Double averageSleep;
	private int dataLength;
	private AverageCalculator averageCalculator;

	public Average(AverageCalculator averageCalculator, int dataLength) {

		this.averageCalculator = averageCalculator;
		this.dataLength = dataLength;
	}
	
	@Override
	public void update() {
		
		this.averageSteps = 0.0;
		this.averageHeartRate = 0.0;
		this.averageSleep = 0.0;
		
		this.dayArrayList = averageCalculator.getDays();
		
		DayIteratorClass dayIteratorClass = new DayIteratorClass(this.dayArrayList);

		Iterator dayIterator = dayIteratorClass.iterator();

		int Index = 0;
		
		while (dayIterator.hasLast()&&Index<(this.dataLength-1)) {
			Index++;
			Day day = (Day) dayIterator.last();
			this.averageSteps += day.getSteps();
			this.averageHeartRate += day.getHeartRate();
			this.averageSleep += day.getSleep();
			
		}
		this.averageSteps = this.averageSteps / (double) Index;
		this.averageHeartRate = this.averageHeartRate / (double) Index;
		this.averageSleep = this.averageSleep / (double) Index;
	}

	public double getAverageSteps() {
		return this.averageSteps;
	}
	
	public double getAverageHeartRate() {
		return this.averageHeartRate;
	}
	
	public double getAverageSleep() {
		return this.averageSleep;
	}
	
}
