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
/**
	public static ArrayList<DayBuilder> fileReader(String filename) throws IOException {
		
		List<String> Lines = Files.readAllLines(Paths.get(filename)); // Determines how many sets of data are in the target file to create an array for that length.
		int arrayLength = Lines.size();
		
		int[] stepsList = new int[arrayLength];
		int[] heartRateList = new int[arrayLength];
		int[] sleepList = new int[arrayLength];
		for (int i =0; i<arrayLength; i++) {
			stepsList[i] = 0;
			heartRateList[i] = 0;
			sleepList[i] = 0;
		}
		
		try {

			File file = new File(filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader fileBuffer = new BufferedReader(fileReader);

			String line;
			int index = 0;
			
			while ((line = fileBuffer.readLine()) != null) {
				// Variables that are used for remembering where a number starts and ends to properly parse it into the phone data.
				int parseStartNumber = 0;
				int parseEndNumber = 0;
				int parseValueIndex = 0;

				for (int i =0; i<line.length(); i++) {
					if (line.charAt(i)==','||i==(line.length()-1)) { // "Splits the data by detecting the commas in the file.
						parseEndNumber = i;
						if (parseValueIndex==0) { // Determines what value it is placing by where it is on each line.
							stepsList[index] = (Integer.parseInt(line.substring(parseStartNumber, parseEndNumber)));
						} else if (parseValueIndex==1) {
							heartRateList[index] = (Integer.parseInt(line.substring(parseStartNumber, parseEndNumber)));
						} else if (parseValueIndex==2) {
							sleepList[index] = (Integer.parseInt(line.substring(parseStartNumber, parseEndNumber)));
						}
						parseValueIndex +=1;
						parseStartNumber = i+1;
					}
				}
				index +=1;
			}
			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<DayBuilder> dayBuilderArrayList = new ArrayList<>();
		
		for (int i =0; i<arrayLength; i++) {
			DayBuilder dayBuilder = new DayBuilder();
			dayBuilder.setSteps(stepsList[i]).setHeartRate(heartRateList[i]).setSleep(sleepList[i]);
			dayBuilderArrayList.add(dayBuilder);
		}
		
		return dayBuilderArrayList;
	}
	
	public static void fileWriter(String filename, ArrayList<Day> day) throws IOException {
	
		
		
	}
 * @throws ClassNotFoundException 
	*/
	
	public static ArrayList<Day> fileReader(String filename) throws IOException, ClassNotFoundException {
	
		List<String> Lines = Files.readAllLines(Paths.get(filename)); // Determines how many sets of data are in the target file to create an array for that length.
		int arrayLength = Lines.size();
		
		ArrayList<Day> dayArrayList = new ArrayList<>();
		
		FileInputStream readfile = new FileInputStream(new File(filename));
		ObjectInputStream input = new ObjectInputStream(readfile);

		for (int i =0; i<arrayLength; i++) {
			
			Day day = (Day) input.readObject();
			
			dayArrayList.add(day);
		}

		input.close();
		readfile.close();
		
		
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
