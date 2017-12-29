package fitbitData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;

public class Util {
	BufferedReader br = null;
	Logger log = new Logger("/Users/Maria/Documents/HAW/Sommersemester 2015_AI6/Bachelor/fitbitDaten/Log/fitbitParserLog.txt");
	Writer writer = null;
	
	
	/**
	 * Erzeugt eine leere Datei mit den Timestamps am Anfang die aus der angegebenen
	 * Datei gelesen werden. Die erzeugte Datei liegt im gleichen Ordner unter dem
	 * Namen clearData.txt
	 * @return gibt das Erzeugte File als File zurück
	 */
	public File createTimeLineSheet(String path) { 
		File file = new File(Properties.MINUTES_PER_DAY_TIMESTAMP_FILE);
		String filePath = path+
						Properties.FOLDER_NAME_PARSED+
						"/"+
						Properties.FOLDER_NAME_META+
						Properties.MINUTES_PER_DAY_SUMMARY_FILE_NAME;
		File clearData = new File(filePath);

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"));
			br = new BufferedReader(new FileReader(file));
			writer.write(Properties.SEPERATION_LETTER + br.readLine());
			
			br.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return clearData;
	}
	
	/**
	 * Erstellt ein Dokument in dem alle Minuten des Tages im Header stehen
	 * und mit einer Null als Wert belegt werden:
	 * @param path der Pfad an den das File geschrieben werden soll
	 */
	public void createNullMinuteDataFile(String path){
		String time = "";
		log.logInfo("Benutzt! von: "+ path); 
		File timeData = new File(Properties.MINUTES_PER_DAY_TIMESTAMP_FILE);
		try {
			//Minuten Header auslesen
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
			br = new BufferedReader(new FileReader(timeData));
			time = br.readLine();
			
			//Minuten Header schreiben und einen Absatz anfügen
			writer.write(time);
			writer.append(System.getProperty("line.separator"));
			
			//Für jeden Minute am Tag eine 0 in das File schrieben
			for(int i = 0;i<=Properties.MINUTES_PER_DAY;i++){
				writer.write("0,");
			}
			
			br.close();
			writer.close();
		} catch (IOException e) {
			log.logWarning(Properties.MINUTES_PER_DAY_TIMESTAMP_FILE+" konnte nicht ausgelesen werden");
			e.printStackTrace();
		}
	}
	
	/**
	 * Erstellt ein Array das alle Minuten des Tages enthält, fehlende Minuten werden mit 0 befüllt
	 * @param arrayPath
	 * @param parsedFile
	 * @return
	 */
	public ArrayList<String> clearMinuteData(File parsedFile) {
		ArrayList<String> clearedValue = new ArrayList<String>();
		int clearTimeIndex = 0;
		int parsedIndex = 0;

		try {
			br = new BufferedReader(new FileReader(Properties.MINUTES_PER_DAY_TIMESTAMP_FILE));
			String arrayString = br.readLine();
			br.close();
			String[] timeArray = arrayString.split(Properties.SEPERATION_LETTER);

			// geparste Zeit auslesen
			br = new BufferedReader(new FileReader(parsedFile.getPath()));
			String parsedTimeString = br.readLine();
			String[] parsedTimeArray = parsedTimeString.split(Properties.SEPERATION_LETTER);

			// dazugehörige geparste Values auslesen
			String parsedValueString = br.readLine();
//			System.out.println(parsedValueString);
			String[] parsedValueArray = parsedValueString.split(Properties.SEPERATION_LETTER);

			while (clearTimeIndex < timeArray.length) {
				if (parsedIndex == (parsedTimeArray.length - 1)) {
					clearedValue.add("0");
					clearTimeIndex++;
				} else if (timeArray[clearTimeIndex].equals(parsedTimeArray[parsedIndex])) {
					clearedValue.add(parsedValueArray[parsedIndex]);
					parsedIndex++;
					clearTimeIndex++;
				} else {
					clearedValue.add("0");
					clearTimeIndex++;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clearedValue;
	}
	
	
	/**
	 * 
	 * Schreibt das gegebene Array in die angegebene Datei
	 * 
	 * @param name der Name der Datei um ihn an den Anfang der Zeile zu schreiben
	 * @param clearedValue bereinigte Daten als Array
	 * @param oneSheetFile das File in das gespeichert werden soll
	 */
	public void createOneSheetClear(File dataFile, File oneSheetFile) {
		ArrayList<String> clearedValue = new ArrayList<String>();
		clearedValue = clearMinuteData(dataFile);
		String name = dataFile.getName();
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(oneSheetFile, true), "utf-8"));
			writer.append(System.getProperty("line.separator"));
			writer.append(name + Properties.SEPERATION_LETTER);

			for (String value : clearedValue) {
				writer.append(value);
				writer.append(Properties.SEPERATION_LETTER);
			}
			writer.close();
		} catch (IOException e) {
			log.logWarning("CreateOneSheetClear konnte nicht in das angegebene File: "+ oneSheetFile.getPath()+ " schreiben.");
			e.printStackTrace();
		}
		System.out.println("Datei: " + name + " wurde gespeichert.");
	}

}
