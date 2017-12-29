package medisanaData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
//import org.apache.commons.lang.time;


import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.eclipsesource.json.*;

import fitbitData.Logger;

public class BodyscaleParser {
	
	BufferedReader br = null;
	Writer writer = null;

	Logger log = new Logger("/Users/Maria/Documents/HAW/Sommersemester 2015_AI6/Bachelor/medisanaDaten/Log/bodyscaleParserLog.txt");

	public void parseData(File file) throws IOException{
		ArrayList<ArrayList<Object>> unsortedData = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> sortedData = new ArrayList<ArrayList<Object>>();
		
		String path = Properties.FOLDER_MEDISANA_BODYSCALE+Properties.FOLDER_NAME_PARSED+file.getName();
		
		// File Einlesen
		br = new BufferedReader(new FileReader(file));
		
		String line = br.readLine();
		// Die eine Zeile die darin steht auslesen
		while(line != null && line != "" && !line.isEmpty()){
			
			JsonArray jsonArray = Json.parse(line).asArray();
			
			for(JsonValue jsonValue: jsonArray){
				ArrayList<Object> list = new ArrayList<Object>();
				JsonObject jsonObject = jsonValue.asObject();
				
				//Datum auslesen und
				JsonValue dateValue = jsonObject.get("measurementDate");
				Long dateMilli = dateValue.asLong();
				Date date = new Date(dateMilli);
				list.add(date);
				
				list.add(jsonObject.get("bodyWeight"));
				list.add(jsonObject.get("bodyFat"));
				list.add(jsonObject.get("bmi"));
				list.add(jsonObject.get("muscleMass"));
				list.add(jsonObject.get("boneMass"));
				list.add(jsonObject.get("bodyWater"));
				list.add(jsonObject.get("kcal"));
				
				unsortedData.add(list);
				System.out.println("List: "+list);
				
			}
			line = br.readLine();
			
			System.out.println("Line:"+line+"!");
		}
		sortedData = sortDataOnDate(unsortedData);
		saveData(sortedData, path);
		br.close();
		
	}
	
	
	public void saveData(ArrayList<ArrayList<Object>> data,String path) throws IOException{
		String header = "Datum,kg,Fett %,BMI,MuskelMasse %,KnochenMasse,Wasseranteil %,kcal";
		
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
		writer.write(header+Properties.SEPERATION_LETTER);
		writer.append(System.getProperty("line.separator"));
		for(ArrayList<Object> list: data){
			
			writer.write(new SimpleDateFormat("yyyy-MM-dd").format(list.get(0))+Properties.SEPERATION_LETTER);
			writer.write(list.get(1)+Properties.SEPERATION_LETTER);
			writer.write(list.get(2)+Properties.SEPERATION_LETTER);
			writer.write(list.get(3)+Properties.SEPERATION_LETTER);
			writer.write(list.get(4)+Properties.SEPERATION_LETTER);
			writer.write(list.get(5)+Properties.SEPERATION_LETTER);
			writer.write(list.get(6)+Properties.SEPERATION_LETTER);
			writer.write(list.get(7).toString());
			
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
