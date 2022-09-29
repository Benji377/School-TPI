package net.tfobz.logger;

public class UDPLogger implements Logger {

	@Override
	public void log(String msg) {
		System.out.println("Logging to UDP: " + msg);		
	}

}
