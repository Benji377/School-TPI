package net.tfobz.logger;

public class LoggerFactory {
	public enum LoggerType {FILE, CONSOLE, UDP}
	
	public static Logger getLogger(LoggerType loggerType) {
		Logger logger;
		
	    switch (loggerType) {
	      case FILE:
	        logger = new FileLogger();
	        break;
	      case CONSOLE:
	    	  logger = new ConsoleLogger();
	    	  break;
	      case UDP:
	    	  logger = new UDPLogger();
	    	  break;
	      default:
	        logger = new ConsoleLogger();
	    }
	    return logger;
	}
}
