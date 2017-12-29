package medisanaData;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) throws IOException {
		BodyscaleParser bodyParser = new BodyscaleParser();
		CardiodockParser cardioParser = new CardiodockParser();
		CsvCardioParser csvParser = new CsvCardioParser();
		//String path = "/Users/Maria/Documents/HAW/Sommersemester 2015_AI6/Bachelor/medisanaDaten/Bodyscale.txt";
		String path = "/Users/Maria/Downloads/blutdruck_parsed.txt";
		String cardioPath = "/Users/Maria/Downloads/blutdruck_ungeparsed.txt";
		File file = new File(path);
		try {
			csvParser.parseData(file, path);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//bodyParser.parseData(file);
		//File cardioFile = new File(cardioPath);
		//cardioParser.parseData(cardioFile);

		
	}

}
