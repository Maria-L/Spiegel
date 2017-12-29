package medisanaData;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CsvCardioParser {
    BufferedReader br = null;
    Writer writer = null;

    public void parseData(File file, String path) throws IOException, ParseException {

        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
        br = new BufferedReader(new FileReader("/Users/Maria/Downloads/blutdruck_ungeparsed.txt"));
        String header = br.readLine();
        br.readLine();
        String line = br.readLine();
        System.out.println(line);


          while((line = br.readLine()) != null){

            String diastole = null;
            String systole = null;
            String puls = null;
            String unixTimeStamp = null;

            String[] blood = line.split(";");
           //Das Datum heraus ziehen und zu unix umrechnen
            blood[0] = blood[0].substring(1, blood[0].length()-1);
            String string = "January 2, 2010";
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy - hh:mm", Locale.GERMANY);
            Date date = format.parse(blood[0]);
            Long unixTime = date.getTime()/1000;
            unixTimeStamp = unixTime.toString();
            System.out.println(date);

            //Systole heraus ziehen
            blood[1] = blood[1].substring(1, blood[1].length()-1);
            systole = blood[1].substring(0,blood[1].indexOf(" "));

            blood[2] = blood[2].substring(1, blood[2].length()-1);
            diastole = blood[2].substring(0,blood[2].indexOf(" "));

            blood[3] = blood[3].substring(1, blood[3].length()-1);
            puls = blood[3].substring(0,blood[3].indexOf(" "));

            String writeString = createInfluxDBLine(systole, diastole, puls, unixTimeStamp);
            System.out.println(blood[1]+" "+blood[2]);
            writer.write(writeString);
            writer.write(System.getProperty("line.separator"));

       }

        br.close();
        writer.close();

    }

    public String createInfluxDBLine(String sys, String dia, String puls, String time){

        return "bloodpresuere,user=01 systole="+sys+",diastole="+dia+",puls="+puls+" "+time;
    }
}
