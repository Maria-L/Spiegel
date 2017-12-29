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

import org.omg.CORBA.portable.ValueBase;

import com.eclipsesource.json.*;

public class SleepDataParser {

	Util util = new Util();
	BufferedReader br = null;
	Writer writer = null;

	Logger log = new Logger("/Users/Maria/Documents/HAW/Sommersemester 2015_AI6/Bachelor/fitbitDaten/Log/fitbitSleepParserLog.txt");

	
	public void clearSimpleSleepData(File awakeFile, String path, String objectName, String header){
		int index = 0;
		try {
			// File Einlesen
			br = new BufferedReader(new FileReader(awakeFile));

			// Die eine Zeile die darin steht auslesen
			String string = br.readLine();
			
			//Wenn der String null ist, also fehlerhafte Datenvorliegen einfach ausnullen und wegschreiben
			if(string == null){
				log.logInfo(string);
				path = path.replace(awakeFile.getName(),Properties.FOLDER_NAME_PARSED+"\\"+awakeFile.getName());
				util.createNullMinuteDataFile(path);
			}else{
				// In ein JSON Array Konvertieren
				JsonObject jsonObject = Json.parse(string).asObject();	
			
				JsonValue value = jsonObject.get(objectName);
				JsonArray dataArray = value.asArray();
				
				//writer initialisieren und mit Header beschreiben
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
				writer.write(header);
				writer.append(System.getProperty("line.separator"));
				
				//Alle Daten aus dem File holen und abspeichern
				while(index < dataArray.size()){
					JsonObject object = dataArray.get(index).asObject();
					
					//Strings aus dem Json Object holen
					String name = object.get("dateTime").toString();
					String valueString =  object.get("value").toString();
					
					//Strings von "" bereinigen
					name = name.substring(1, name.length()-1);
					valueString = valueString.substring(1, valueString.length()-1);
					
					//Strings in Datei speichern
					writer.write(name+Properties.SEPERATION_LETTER+valueString);
					writer.append(System.getProperty("line.separator"));
					
					index++;
				}
			}
			
			writer.close();
			br.close();
			System.out.println(objectName+" Datei wurde gespeichert");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clearMinuteData(File sleepFile){
		String path = sleepFile.getPath();
		try {
			// File Einlesen
			br = new BufferedReader(new FileReader(sleepFile));

			// Die eine Zeile die darin steht auslesen
			String string = br.readLine();
			
			//Wenn der String null ist, also fehlerhafte Datenvorliegen einfach ausnullen und wegschreiben
			if(string == null){
				log.logInfo(string);
				path = path.replace(sleepFile.getName(),Properties.FOLDER_NAME_PARSED+"\\"+sleepFile.getName());
				util.createNullMinuteDataFile(path);
			}else{
				// In ein JSON Array Konvertieren
				JsonObject jsonObject = Json.parse(string).asObject();
		
				JsonValue value = jsonObject.get("sleep");
				JsonArray data = value.asArray();
				if(value != null && data.size() >= 1){
					
					JsonObject dataObject = data.get(0).asObject();
					
					JsonValue dataSet = dataObject.get("minuteData");
					JsonArray dataArray = dataSet.asArray();
					
					path = path.replace(sleepFile.getName(),Properties.FOLDER_NAME_PARSED+"\\"+sleepFile.getName());
					saveData(dataArray, path);
				}else{
					log.logInfo("Value: "+value+"Size: "+data.size());
					path = path.replace(sleepFile.getName(),Properties.FOLDER_NAME_PARSED+"\\"+sleepFile.getName());
					util.createNullMinuteDataFile(path);
				}
			}
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}

	}
	
	public void saveData(JsonArray array, String path) throws IOException,InterruptedException {
		ArrayList<String> time = new ArrayList<String>();
		ArrayList<String> value = new ArrayList<String>();
		int index = 0;
		int timeIndex = 0;
		int valueIndex = 0;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}

		while (index < array.size()) {
			JsonObject data = array.get(index).asObject();
			time.add(data.get("dateTime").asString());
			String valueString = data.get("value").toString();
			valueString = valueString.substring(1, valueString.length()-1);
			value.add(valueString);
			index++;
		}

		while (timeIndex < time.size()) {
			writer.write(time.get(timeIndex));
			writer.write(Properties.SEPERATION_LETTER);
			timeIndex++;
		}
		writer.append(System.getProperty("line.separator"));

		while (valueIndex < value.size()) {
			writer.write(value.get(valueIndex));
			writer.write(Properties.SEPERATION_LETTER);
			valueIndex++;
		}
		writer.close();
		File nameFile = new File(path);
		System.out.println(nameFile.getPath()+" wurde bereinigte abgelegt");
	}
	
}
