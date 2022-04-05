package fta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

	public static ArrayList<Day> fileReader(String filename) throws IOException, ClassNotFoundException {

		ArrayList<Day> dayArrayList = new ArrayList<>();
		try {
		FileInputStream readfile = new FileInputStream(new File(filename));
		ObjectInputStream input = new ObjectInputStream(readfile);

		while(readfile.available()>0) {
			
			Day day = (Day) input.readObject();
			dayArrayList.add(day);
		}

		input.close();
		readfile.close();		
		
		} catch (Exception e) {
			System.out.println("No file");
			
		}		
		//DayBuilder dayBuilderGoals = new DayBuilder();
		
		//dayBuilderGoals.setSteps(10000).setHeartRate(80).setSleep(8.0);

		//dayArrayList.add(dayBuilderGoals.buildDay());
		
		
		return dayArrayList;
	}
	
	public static void fileWriter(String filename, ArrayList<Day> dayArrayList) throws IOException {
		
		FileOutputStream fileWriter = new FileOutputStream(new File(filename));
		ObjectOutputStream output = new ObjectOutputStream(fileWriter);

		for (int i =0; i<dayArrayList.size(); i++) {
			
			output.writeObject(dayArrayList.get(i));
		}
		
		output.close();
		fileWriter.close();
	}
}
