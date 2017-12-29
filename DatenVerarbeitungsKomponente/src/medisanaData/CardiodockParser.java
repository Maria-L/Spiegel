package medisanaData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import fitbitData.Logger;

public class CardiodockParser {
	BufferedReader br = null;
	Writer writer = null;

	Logger log = new Logger("/Users/Maria/Documents/HAW/Sommersemester 2015_AI6/Bachelor/medisanaDaten/Log/cardiodockParserLog.txt");

	public void parseData(File file) throws IOException{
		ArrayList<ArrayList<Object>> unsortedData = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> sortedData = new ArrayList<ArrayList<Object>>();
		String path = Properties.FOLDER_MEDISANA_CARDIODOCK+Properties.FOLDER_NAME_PARSED+file.getName();
		// File Einlesen
		
		br = new BufferedReader(new FileReader(file));
		
		String line = br.readLine();
		// Die eine Zeile die darin steht auslesen
		while(line != null){
			
			JsonArray jsonArray = Json.parse(line).asArray();
			
			for(JsonValue jsonValue: jsonArray){
				ArrayList<Object> list = new ArrayList<Object>();
				JsonObject jsonObject = jsonValue.asObject();
				
				JsonValue dateValue = jsonObject.get("measurementDate");
				Long dateMilli = dateValue.asLong();
				Date date = new Date(dateMilli);
				list.add(date);
				
				list.add(jsonObject.get("systole"));
				list.add(jsonObject.get("diastole"));
				list.add(jsonObject.get("pulse"));
				
				unsortedData.add(list);
				System.out.println("List: "+list);
				
			}
			line = br.readLine();
		}
				
		sortedData = sortDataOnDate(unsortedData);
		saveData(sortedData, path);
		br.close();
		
	}
	
	public void saveData(ArrayList<ArrayList<Object>> data,String path) throws IOException{
		String header = "Datum,Systole,Diastole,Puls";
		
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
		writer.write(header+Properties.SEPERATION_LETTER);
		writer.append(System.getProperty("line.separator"));
		
		for(ArrayList<Object> list: data){
			
			writer.write(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(list.get(0))+Properties.SEPERATION_LETTER);
			writer.write(list.get(1)+Properties.SEPERATION_LETTER);
			writer.write(list.get(2)+Properties.SEPERATION_LETTER);
			writer.write(list.get(3)+Properties.SEPERATION_LETTER);
			
			writer.append(System.getProperty("line.separator"));
		}
		
		writer.close();
	}
	
	public ArrayList<ArrayList<Object>> sortDataOnDate(ArrayList<ArrayList<Object>> data){
		ArrayList<ArrayList<Object>> sortedData = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> sortedDataWork = data;
		
		while(sortedDataWork.size()>0){
			ArrayList<Object> earliest = getEarliest(sortedDataWork);
			if(!sortedData.contains(earliest)){
				sortedData.add(earliest);
			}
			sortedDataWork.remove(earliest);
		}
		return sortedData;
	}
	
	public ArrayList<Object> getEarliest(ArrayList<ArrayList<Object>> unsortedData){
		ArrayList<Object> result = unsortedData.get(0);
		for(ArrayList<Object> array: unsortedData){
			if(!array.equals(result)){
				Date date1 = (Date) result.get(0);
				Date date2 = (Date) array.get(0);
				
				if(date2.before(date1)){
					result = array;
				}
			}
		}
			
		return result;
	}

}
