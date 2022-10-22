package telran.util;

import java.time.Instant;
import java.time.ZoneId;

public class Logger {
	public enum Level {
		TRACE, DEBUG, INFO, WARN, ERROR
	}

	private Level level = Level.INFO;
	private Handler handler;
	private String name;

	public Logger(Handler handler, String name) {
		this.handler = handler;
		this.name = name;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void error(String message) {
		handler.publish(new LoggerRecord(Instant.now(), ZoneId.systemDefault().getId(), level, name, message));
	}

	public void warn(String message) {
		if (level != Level.ERROR) {
			handler.publish(new LoggerRecord(Instant.now(), ZoneId.systemDefault().getId(), level, name, message));
		}
	}

	public void info(String message) {
		if (level != Level.ERROR && level != Level.WARN)  {
			handler.publish(new LoggerRecord(Instant.now(), ZoneId.systemDefault().getId(), level, name, message));
		}
	}

	public void debug(String message) {
		if (level == Level.TRACE || level == Level.DEBUG) {
			handler.publish(new LoggerRecord(Instant.now(), ZoneId.systemDefault().getId(), level, name, message));
		}
	}

	public void trace(String message) {
		if (level == Level.TRACE) {
			handler.publish(new LoggerRecord(Instant.now(), ZoneId.systemDefault().getId(), level, name, message));
		}
	}

}
