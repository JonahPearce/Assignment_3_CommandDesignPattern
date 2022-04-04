package fta;

import java.util.ArrayList;
import java.util.List;

public class AverageCalculator implements Subject {

	private ArrayList<Day> dayArrayList = new ArrayList<>();
	private List<Observer> observerList;

	public AverageCalculator() {
		observerList = new ArrayList<Observer>();

	}

	@Override
	public void registerObserver(Observer o) {
		observerList.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observerList.remove(o);

	}

	@Override
	public void notifyObservers() {

		for (Observer observer : observerList) {

			observer.update();
		}

	}

	public ArrayList<Day> getDays() {
		return dayArrayList;
	}

	public void setDays(ArrayList<Day> dayList) {
		for(int i=0; i<dayList.size(); i++) {
			dayArrayList.add(dayList.get(i));
			notifyObservers();
		}
	}

	public void addDay(Day day) {
		dayArrayList.add(day);
		notifyObservers();

	}

}
