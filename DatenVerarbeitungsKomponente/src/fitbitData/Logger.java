package fitbitData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class Logger {

	String path = null;
	Writer writer = null;
	//true alles loggen false nur Warnings
	Boolean mode = true;
	
	public Logger(String path) {
		this.path = path;
	}
	
	public void logInfo(String log){
		if(mode){
			try {
				File result = new File(path);
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(result,true), "utf-8"));
				writer.write("Info: "+log+"\n");
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void logWarning(String log){
		try {
			File result = new File(path);
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(result,true), "utf-8"));
			writer.write("Warning: " + log+"\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setLogModeWarning(){
		mode = false;
	}
	
	public void setLogModeInfo(){
		mode = true;
	}
}
