package telran.util;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SimpleStreamHandler implements Handler {
	
	private PrintStream printStream = null;
	
	public SimpleStreamHandler (PrintStream stream) {
		this.printStream = stream;
	}
	@Override
	public void publish(LoggerRecord loggerRecord) {
		String logEntry = new String("" + LocalDateTime.ofInstant(loggerRecord.timestamp, ZoneId.of(loggerRecord.zoneId)) 
		+ " " + loggerRecord.level.toString() +  " " + loggerRecord.loggerName + " " + loggerRecord.message);
		printStream.println(logEntry);		
	}

}
