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
import java.util.Iterator;
import java.util.List;

import com.eclipsesource.json.*;

public class PulseDataParser {
	Util util = new Util();
	BufferedReader br = null;
	Writer writer = null;

	Logger log = new Logger("/Users/Maria/Documents/HAW/Sommersemester 2015_AI6/Bachelor/fitbitDaten/Log/fitbitPulseParserLog.txt");

	/**
	 * Die Methoden holt den Timestamp und das Value aus den 
	 * Rohdaten und gibt sie unter dem gleichen Namen im geparset Ordner aus
	 * Muss das Format [{"time":"hh:mm:ss","value":},...] haben
	 * @param pulseFile das Roh File das geparst werden soll
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void parseDataPulse(File pulseFile) throws IOException,InterruptedException {
		String path = pulseFile.getPath();
		// File Einlesen
		br = new BufferedReader(new FileReader(pulseFile));

		// Die eine Zeile die darin steht auslesen
		String string = br.readLine();

		//Wenn der String null ist, also fehlerhafte Datenvorliegen einfach ausnullen und wegschreiben
		if(string == null){
			path = path.replace(pulseFile.getName(),Properties.FOLDER_NAME_PARSED+"/"+pulseFile.getName());
			util.createNullMinuteDataFile(path);
		}else{
			// In ein JSON Array Konvertieren
			JsonObject jsonObject = Json.parse(string).asObject();
			
			//Erstes Object auslesen
			JsonValue heartValue = jsonObject.get("activities-heart");
			JsonArray heartArray = heartValue.asArray();
			JsonObject heartObject = heartArray.get(0).asObject();
//			System.out.println(heartArray.get(0));
////			System.out.println(heartValue.);
			
			String name = heartObject.get("dateTime").toString();
			name = name.substring(1, name.length()-1);
			path = path.replace(pulseFile.getName(),Properties.FOLDER_NAME_PARSED+"/"+name+"_pulse.txt");
			
			//Daten Object auslesen
			JsonValue value = jsonObject.get("activities-heart-intraday");
			JsonObject dataObject = value.asObject();
	
			//Die Metadaten wegfallen lassen und nur den eigentlichen Dataset auslesen
			JsonValue dataSet = dataObject.get("dataset");
			JsonArray data = dataSet.asArray();
	
			saveData(data, path);
		}
	}

	/**
	 * Speichert das gegeben Aray unter im gegebenen Pfad 
	 * @param array das JsonArray der Daten die in die neue Dateien sollen, bestehend aus Zeit und Value 
	 * @param path
	 * @throws IOException
	 * @throws InterruptedException
	 */
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
			time.add(data.get("time").asString());
			String valueString = data.get("value").toString();
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
		System.out.println("Daten gespeichert");
	}


	public void parsePulseMetaData(File pulseFile){
		try {
			// File Einlesen
			br = new BufferedReader(new FileReader(pulseFile));
	
			// Die eine Zeile die darin steht auslesen
			String string = br.readLine();

			// In ein JSON Object Konvertieren
			JsonObject jsonObject = Json.parse(string).asObject();
	
			JsonValue value = jsonObject.get("activities-heart");
			JsonArray dataArray = value.asArray();
			JsonObject dateTimeObject = dataArray.get(0).asObject();
			JsonValue date = dateTimeObject.get("dateTime");
			
			JsonObject valueObject = dateTimeObject.get("value").asObject();
			JsonArray heartRateZones = valueObject.get("heartRateZones").asArray();
			JsonValue restingHeartRate = valueObject.get("restingHeartRate");
			
			JsonObject outOfRange = heartRateZones.get(0).asObject();
			JsonObject fatBurn = heartRateZones.get(1).asObject();
			JsonObject cardio = heartRateZones.get(2).asObject();
			JsonObject peak = heartRateZones.get(3).asObject();
			
			String  header = "Name,Minuten,min,max,caloriesOut";
			String  outOfRangeString = heartRateZonesAsString(outOfRange);
			String  fatBurnString = 	heartRateZonesAsString(fatBurn);
			String  cardioString = 	heartRateZonesAsString(cardio);
			String  peakString = heartRateZonesAsString(peak);
			
			String name = date.asString();
			String path = pulseFile.getPath().replace(pulseFile.getName(),Properties.FOLDER_NAME_PARSED+"/"+Properties.FOLDER_NAME_META+"/"+name+".txt");
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
			writer.write(header);
			writer.append(System.getProperty("line.separator"));
			writer.write(outOfRangeString);
			writer.append(System.getProperty("line.separator"));
			writer.write(fatBurnString);
			writer.append(System.getProperty("line.separator"));
			writer.write(cardioString);
			writer.append(System.getProperty("line.separator"));
			writer.write(peakString);
			writer.append(System.getProperty("line.separator"));
			writer.append("Ruhepuls:"+Properties.SEPERATION_LETTER+restingHeartRate);
			
			writer.close();
			br.close();
			System.out.println(name+" wurde unter MetaDaten gespeichert");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Hilfsfunktion für parsePulseMetaData die Meta 
	 * die die angegebene Herzzone in einen String in einer Zeile kommasepariert zurück
	 * gibt
	 * @param zone
	 * @return
	 */
	public String heartRateZonesAsString(JsonObject zone){
		String result = null;
		String name = zone.get("name").toString();
		
		//Anfürhungszeichen am Zonen Namen Abschneiden
		name = name.substring(1, name.length()-1);
		String minutes = zone.get("minutes").toString();
		String min = zone.get("min").toString();
		String max = zone.get("max").toString();
		String caloriesOut = zone.get("caloriesOut").toString();
		
		result = name+Properties.SEPERATION_LETTER+minutes+Properties.SEPERATION_LETTER+min+Properties.SEPERATION_LETTER+max+Properties.SEPERATION_LETTER+caloriesOut;
		return result;
	}

}
