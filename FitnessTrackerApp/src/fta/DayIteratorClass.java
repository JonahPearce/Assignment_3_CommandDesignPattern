package fta;

import java.util.ArrayList;
import java.util.Arrays;

public class DayIteratorClass implements Aggregate {

	ArrayList<Day> dayArrayList = new ArrayList<Day>();
	
	
	
	public DayIteratorClass(ArrayList<Day> dayArrayList) {
		super();
		this.dayArrayList = dayArrayList;
	}

	private class DayIterator implements Iterator {

		private ArrayList<Day> elements;
		private int index = 0;
		private int revindex = 0;

		public DayIterator(DayIteratorClass dayIteratorClass) {
			elements = new ArrayList<Day>();
			elements.addAll(dayIteratorClass.dayArrayList);
			revindex = elements.size()-1;
		}

		@Override
		public boolean hasNext() {

			return index < elements.size();
		}

		@Override
		public Object next() {

			if (hasNext()) {
				return elements.get(index++);
			}
			return null;
		}

		@Override
		public boolean hasLast() {
			
			return revindex >= 0;
		}

		@Override
		public Object last() {
			
			if (hasLast()) {
				return elements.get(revindex--);
			}
			return null;
		}

	}

//********************************************************
	@Override
	public Iterator iterator() {

		return new DayIterator(this);
	}
}
