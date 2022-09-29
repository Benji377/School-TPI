package net.tfobz.logger;

public class ConsoleLogger implements Logger {

	@Override
	public void log(String msg) {
		System.out.println("Logging to console: " + msg);		
	}

}
