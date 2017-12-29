package fitbitData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		Util util = new Util();
		
//################		Pulsdaten Parsen     #################
		
		PulseDataParser pulseParser = new PulseDataParser();
//		
//		File rawFolder = new File(Properties.FOLDER_PULSE);
//		File[] rawFileArr = rawFolder.listFiles();
//		
//		for(File file: rawFileArr){
//			if(file.isFile()){
//				pulseParser.parseDataPulse(file);
//				pulseParser.parsePulseMetaData(file);
//			}
//		}
//////################		Pulsdaten Bereinigen     #################	
////		Leeres File mit ZeitHeader erstellen
//		File clearData = util.createTimeLineSheet(Properties.FOLDER_PULSE);
//		System.out.println(clearData.getPath());
//		
//		File parsedFolder = new File(Properties.FOLDER_PULSE+Properties.FOLDER_NAME_PARSED);
//		File[] parsedFileArr = parsedFolder.listFiles();
//		
//		
//		for(File file: parsedFileArr){
//			if(file.isFile()){
//				util.createOneSheetClear(file, clearData);
//			}
//		}
//				
////################		Schlafdaten Parsen     #################
//		
//		/////////////////////////////////////////////////////////////
//		//// Dies einkommentieren zum parsen und clearen	////////
//		////  Der Schlaf Daten								////////
//		////////////////////////////////////////////////////////////
//
//		
//		//Meta Daten
		SleepDataParser sleepDataParser = new SleepDataParser();
		String folderResult = Properties.FOLDER_SLEEP+Properties.FOLDER_NAME_PARSED;

//		//Anzahl aufgewacht
//		String awakeSleepObjectName = "sleep-awakeningsCount";
//		String awakeSleepHeader = "Datum"+","+"Wie oft aufgewacht";
//		String awakeSleepPath = Properties.FOLDER_SLEEP+"awakeningsCount07_20_15.txt";
//		String awakeSleepResult = folderResult+"AnzahlAufgewacht20_07-02_09.txt";
//		File awakeSleepFile = new File(awakeSleepPath);
//
//		sleepDataParser.clearSimpleSleepData(awakeSleepFile,awakeSleepResult,awakeSleepObjectName,awakeSleepHeader);
//		
//		//Schlaf Effizienz
//		String efficiencySleepObjectName = "sleep-efficiency";
//		String efficiencySleepHeader = "Datum"+","+"Schlaf Effizienz";
//		String efficiencySleepPath = Properties.FOLDER_SLEEP+"efficiency07_20_15.txt";
//		String efficiencySleepResult = folderResult+"SchlafEffizienz20_07-02_09.txt";
//		File efficiencySleepFile = new File(efficiencySleepPath);
//		
//		sleepDataParser.clearSimpleSleepData(efficiencySleepFile, efficiencySleepResult,efficiencySleepObjectName,efficiencySleepHeader);
// 
//		//Minuten nach dem Aufwachen
//		String minutesAfterWakeupObjectName = "sleep-minutesAfterWakeup";
//		String minutesAfterWakeupHeader = "Datum"+","+"Minuten nach dem Auwachen";
//		String minutesAfterWakeupPath = Properties.FOLDER_SLEEP+"minutesAfterWakeup07_20_15.txt";
//		String minutesAfterWakeupResult = folderResult+"MinutenNachAufwachen20_07-02_09.txt";
//		File minutesAfterWakeupFile = new File(minutesAfterWakeupPath);
//		
//		sleepDataParser.clearSimpleSleepData(minutesAfterWakeupFile, minutesAfterWakeupResult,minutesAfterWakeupObjectName,minutesAfterWakeupHeader);
// 
//		//Geschlafene Minuten
//		String minutesAsleepObjectName = "sleep-minutesAsleep";
//		String minutesAsleepHeader = "Datum"+","+"Geschlafene Minuten";
//		String minutesAsleepPath = Properties.FOLDER_SLEEP+"minutesAsleep07_20_15.txt";
//		String minutesAsleepResult = folderResult+"GeschlafeneMinuten20_07-02_09.txt";
//		File minutesAsleepFile = new File(minutesAsleepPath);
//				
//		sleepDataParser.clearSimpleSleepData(minutesAsleepFile, minutesAsleepResult,minutesAsleepObjectName,minutesAsleepHeader);
//		
//		//Minuten wach
//		String minutesAwakeObjectName = "sleep-minutesAwake";
//		String minutesAwakeHeader = "Datum"+","+"Minuten Wach";
//		String minutesAwakePath = Properties.FOLDER_SLEEP+"minutesAwake07_20_15.txt";
//		String minutesAwakeResult = folderResult+"MinutenWach20_07-02_09.txt";
//		File minutesAwakeFile = new File(minutesAwakePath);
//						
//		sleepDataParser.clearSimpleSleepData(minutesAwakeFile, minutesAwakeResult,minutesAwakeObjectName,minutesAwakeHeader);
//		
//		
//		//Minuten zum Einschlafen
//		String minutesToFallAsleepObjectName = "sleep-minutesToFallAsleep";
//		String minutesToFallAsleepHeader = "Datum"+","+"Minuten zum Einschlafen";
//		String minutesToFallAsleepPath = Properties.FOLDER_SLEEP+"minutesToFallAsleep07_20_15.txt";
//		String minutesToFallAsleepResult = folderResult+"MinutenZumEinschlafen20_07-02_09.txt";
//		File minutesToFallAsleepFile = new File(minutesToFallAsleepPath);
//						
//		sleepDataParser.clearSimpleSleepData(minutesToFallAsleepFile, minutesToFallAsleepResult,minutesToFallAsleepObjectName,minutesToFallAsleepHeader);
//		
//		//Startzeit
//		String startTimeSleepObjectName = "sleep-startTime";
//		String startTimeSleepHeader = "Datum"+","+"Startzeit";
//		String startTimeSleepPath = Properties.FOLDER_SLEEP+"starteTime07_20_15.txt";
//		String startTimeSleepResult = folderResult+"StartZeitSchlaf20_07-02_09.txt";
//		File startTimeSleepFile = new File(startTimeSleepPath);
//						
//		sleepDataParser.clearSimpleSleepData(startTimeSleepFile, startTimeSleepResult,startTimeSleepObjectName,startTimeSleepHeader);
//		
//		//Minuten im Bett
//		String timeInBedObjectName = "sleep-timeInBed";
//		String timeInBedHeader = "Datum"+","+"Minuten im Bett";
//		String timeInBedPath = Properties.FOLDER_SLEEP+"timeInBed07_20_15.txt";
//		String timeInBedResult = folderResult+"ZeitImBett20_07-02_09.txt";
//		File timeInBedFile = new File(timeInBedPath);
//						
//		sleepDataParser.clearSimpleSleepData(timeInBedFile, timeInBedResult,timeInBedObjectName,timeInBedHeader);
//	
//	
	//Minuten Daten
		
		File sleepFolder = new File(Properties.FOLDER_SLEEP+Properties.FOLDER_NAME_MINUTEDATA);
		File[] sleepFileArr = sleepFolder.listFiles();
		
		for(File file: sleepFileArr){
			if(file.isFile()){
				sleepDataParser.clearMinuteData(file);
			}
		}

		
//################		Schlafdaten Bereinigen     #################	
//		
//	File sleepSummaryFile = util.createTimeLineSheet(Properties.FOLDER_SLEEP);
//	File sleepMinuteClearedFolder = new File(Properties.FOLDER_SLEEP+Properties.FOLDER_NAME_MINUTEDATA+"/"+Properties.FOLDER_NAME_PARSED);
//	File[] sleepMinuteClearedFileArr = sleepMinuteClearedFolder.listFiles();
//		
//	for(File sleepFile: sleepMinuteClearedFileArr){
//		if(sleepFile.isFile()){
//			util.createOneSheetClear(sleepFile,sleepSummaryFile);
//		}
//	}
		
//################		Aktivitätsdaten Parsen     #################
/////////////////////////////////////////////////////////////
////Dies einkommentieren zum parsen und clearen		////////
////Der Aktivitäts Daten							////////
////////////////////////////////////////////////////////////
		
////Meta Daten
//ActivityDataParser activityData = new ActivityDataParser();
//String activityFolderResult = Properties.FOLDER_ACTIVITY+Properties.FOLDER_NAME_PARSED;
//
////Durch Aktivitäten verbrauchte Calorien
//String activityCaloriesObjectName = "activities-activityCalories";
//String activityCaloriesHeader = "Datum"+","+"Durch Aktivitäten verbrauchte Kalorien";
//String activityCaloriesPath = Properties.FOLDER_ACTIVITY+"activityCalories.txt";
//String activityCaloriesResult = activityFolderResult+"aktivitaetenKalorien.txt";
//File activityCaloriesFile = new File(activityCaloriesPath);
//
//activityData.clearSimpleActivityData(activityCaloriesFile,activityCaloriesResult, activityCaloriesObjectName, activityCaloriesHeader);
//
////Verbrauchte Kalorien
//String caloriesObjectName = "activities-calories";
//String caloriesHeader = "Datum"+","+"Schlaf Effizienz";
//String caloriesPath = Properties.FOLDER_ACTIVITY+"calories.txt";
//String caloriesResult = activityFolderResult+"VerbrauchteKalorien.txt";
//File caloriesFile = new File(caloriesPath);
//
//activityData.clearSimpleActivityData(caloriesFile, caloriesResult,caloriesObjectName,caloriesHeader);
//
////Kalorien Grundumsatz
//String caloriesBMRObjectName = "activities-caloriesBMR";
//String caloriesBMRHeader = "Datum"+","+"Kalorien Grundumsatz";
//String caloriesBMRPath = Properties.FOLDER_ACTIVITY+"caloriesBMR.txt";
//String caloriesBMRResult = activityFolderResult+"KalorienGrundumsatz.txt";
//File caloriesBMRFile = new File(caloriesBMRPath);
//
//activityData.clearSimpleActivityData(caloriesBMRFile, caloriesBMRResult,caloriesBMRObjectName,caloriesBMRHeader);
//
////Distanz
//String distanceObjectName = "activities-distance";
//String distanceHeader = "Datum"+","+"Distanz zurück gelegt";
//String distancePath = Properties.FOLDER_ACTIVITY+"distance.txt";
//String distanceResult = activityFolderResult+"Distanz.txt";
//File distanceFile = new File(distancePath);
//		
//activityData.clearSimpleActivityData(distanceFile, distanceResult, distanceObjectName, distanceHeader);
//
////Höhe
//String elevationObjectName = "activities-elevation";
//String elevationHeader = "Datum"+","+"Höhe";
//String elevationPath = Properties.FOLDER_ACTIVITY+"elevation.txt";
//String elevationResult = activityFolderResult+"Hoehe.txt";
//File elevationFile = new File(elevationPath);
//				
//activityData.clearSimpleActivityData(elevationFile, elevationResult, elevationObjectName, elevationHeader);
//
////Etagen
//String floorsObjectName = "activities-floors";
//String floorsHeader = "Datum"+","+"Etagen";
//String floorsPath = Properties.FOLDER_ACTIVITY+"floors.txt";
//String floorsResult = activityFolderResult+"Etagen.txt";
//File floorsFile = new File(floorsPath);
//				
//activityData.clearSimpleActivityData(floorsFile, floorsResult, floorsObjectName, floorsHeader);
//
////Minuten sehr aktiv
//String minutesFairlyActiveObjectName = "activities-minutesFairlyActive";
//String minutesFairlyActiveHeader = "Datum"+","+"Minuten sehr aktiv";
//String minutesFairlyActivePath = Properties.FOLDER_ACTIVITY+"minutesFairlyActive.txt";
//String minutesFairlyActiveResult = activityFolderResult+"MinutenSehrAktiv.txt";
//File minutesFairlyActiveFile = new File(minutesFairlyActivePath);
//				
//activityData.clearSimpleActivityData(minutesFairlyActiveFile, minutesFairlyActiveResult, minutesFairlyActiveObjectName, minutesFairlyActiveHeader);
//
////Minuten leicht aktiv
//String minutesLightlyActiveObjectName = "activities-minutesLightlyActive";
//String minutesLightlyActiveHeader = "Datum"+","+"Minuten leicht aktiv";
//String minutesLightlyActivePath = Properties.FOLDER_ACTIVITY+"minutesLightlyActive.txt";
//String minutesLightlyActiveResult = activityFolderResult+"MinutenLeichtAktiv.txt";
//File minutesLightlyActiveFile = new File(minutesLightlyActivePath);
//				
//activityData.clearSimpleActivityData(minutesLightlyActiveFile, minutesLightlyActiveResult, minutesLightlyActiveObjectName, minutesLightlyActiveHeader);
//
////Minuten sitzend
//String minutesSedentaryObjectName = "activities-minutesSedentary";
//String minutesSedentaryHeader = "Datum"+","+"Minuten sitzend";
//String minutesSedentaryPath = Properties.FOLDER_ACTIVITY+"minutesSedentary.txt";
//String minutesSedentaryResult = activityFolderResult+"MinutenSitzend.txt";
//File minutesSedentaryFile = new File(minutesSedentaryPath);
//				
//activityData.clearSimpleActivityData(minutesSedentaryFile, minutesSedentaryResult, minutesSedentaryObjectName, minutesSedentaryHeader);
//
////Schritte am Tag
//String stepsObjectName = "activities-steps";
//String stepsHeader = "Datum"+","+"Schritte";
//String stepsPath = Properties.FOLDER_ACTIVITY+"steps.txt";
//String stepsResult = activityFolderResult+"Schritte.txt";
//File stepsFile = new File(stepsPath);
//				
//activityData.clearSimpleActivityData(stepsFile, stepsResult, stepsObjectName, stepsHeader);
//
////Minuten Daten
//File activityFolder = new File(Properties.FOLDER_ACTIVITY+Properties.FOLDER_NAME_MINUTEDATA);
//File[] activityFileArr = activityFolder.listFiles();
//
//for(File activityFile: activityFileArr){
//	if(activityFile.isFile()){
//		activityData.parseMinuteData(activityFile);
//	}
//}
//		
////################		Aktivitätsdaten Bereinigen     #################	
//		
//File activityMinuteClearedFolder = new File(Properties.FOLDER_ACTIVITY+Properties.FOLDER_NAME_MINUTEDATA+"/"+Properties.FOLDER_NAME_PARSED);
//File[] activityMinuteClearedFileArr = activityMinuteClearedFolder.listFiles();
//
//File activitySummaryFile = util.createTimeLineSheet(Properties.FOLDER_ACTIVITY);
//
//for(File acticityMinuteFile: activityMinuteClearedFileArr){
//	if(acticityMinuteFile.isFile()){
//		util.createOneSheetClear(acticityMinuteFile, activitySummaryFile);
//	}
//}


	}
}
