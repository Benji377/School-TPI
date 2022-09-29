package net.tfobz.logger;

import net.tfobz.logger.LoggerFactory.LoggerType;

public class Hauptprogramm {
	
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(LoggerType.FILE);
		logger.log("Written In File");
		
		logger = LoggerFactory.getLogger(LoggerType.CONSOLE);
		logger.log("Written in Cosnole");
		
		logger = LoggerFactory.getLogger(LoggerType.UDP);
		logger.log("Written to UDP");
	}
}
