package net.tfobz.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger {
	File logFile = new File("logFile.txt");
	FileWriter writer;

	@Override
	public void log(String msg) {
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			System.out.println("Logging to "+ logFile.getAbsolutePath() + ": " + msg);
			writer = new FileWriter(logFile+"\n");
			writer.write(msg);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
