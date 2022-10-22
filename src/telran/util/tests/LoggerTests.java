package telran.util.tests;

import static org.junit.jupiter.api.Assertions.*;

import telran.util.Logger;
import telran.util.Logger.Level;
import telran.util.SimpleStreamHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoggerTests {
	File file = new File("file1.txt");

	@BeforeEach
	void setUp() throws Exception {
		file.delete();
	}

	@Test
	void errorLogTest() throws IOException {

		try (PrintStream printStream = new PrintStream(file);
				BufferedReader reader = new BufferedReader(new FileReader(file));) {
			Logger logger = new Logger(new SimpleStreamHandler(printStream), "ErrorLog");
			logger.setLevel(Level.ERROR);
			runAllLogMethods(logger);
			assertTrue(reader.readLine().contains("ERROR"));
		}
	}

	@Test
	void warnLogTest() throws IOException {
		try (PrintStream printStream = new PrintStream(file);
				BufferedReader reader = new BufferedReader(new FileReader(file));) {
			Logger logger = new Logger(new SimpleStreamHandler(printStream), "WarnLog");
			logger.setLevel(Level.WARN);
			runAllLogMethods(logger);
			assertTrue(reader.readLine().indexOf("ERROR", 0) >= 0 || reader.readLine().indexOf("WARN", 0) >= 0);
		}
	}

	@Test
	void infoLogTest() throws IOException {
		try (PrintStream printStream = new PrintStream(file);
				BufferedReader reader = new BufferedReader(new FileReader(file));) {
			Logger logger = new Logger(new SimpleStreamHandler(printStream), "InfoLog");
			logger.setLevel(Level.INFO);
			runAllLogMethods(logger);
			assertTrue(reader.readLine().indexOf("ERROR", 0) >= 0 || reader.readLine().indexOf("WARN", 0) >= 0
					|| reader.readLine().indexOf("INFO", 0) >= 0);
		}
	}
	
	@Test
	void debugLogTest() throws IOException {
		try (PrintStream printStream = new PrintStream(file);
				BufferedReader reader = new BufferedReader(new FileReader(file));) {
			Logger logger = new Logger(new SimpleStreamHandler(printStream), "DebugLog");
			logger.setLevel(Level.DEBUG);
			runAllLogMethods(logger);
			assertTrue(reader.readLine().indexOf("ERROR", 0) >= 0 || reader.readLine().indexOf("WARN", 0) >= 0
					|| reader.readLine().indexOf("INFO", 0) >= 0 || reader.readLine().indexOf("DEBUG", 0) >= 0);
		}
	}
	
	@Test
	void traceLogTest() throws IOException {
		try (PrintStream printStream = new PrintStream(file);
				BufferedReader reader = new BufferedReader(new FileReader(file));) {
			Logger logger = new Logger(new SimpleStreamHandler(printStream), "TraceLog");
			logger.setLevel(Level.TRACE);
			runAllLogMethods(logger);
			assertTrue(reader.readLine().indexOf("ERROR", 0) >= 0 || reader.readLine().indexOf("WARN", 0) >= 0
					|| reader.readLine().indexOf("INFO", 0) >= 0 || reader.readLine().indexOf("DEBUG", 0) >= 0 
					|| reader.readLine().indexOf("TRACE", 0) >= 0);
		}
	}
	
	@Test
	void printToConsole() throws FileNotFoundException, IOException {
		try (PrintStream printStream = new PrintStream(System.out);) {
			Logger logger = new Logger(new SimpleStreamHandler(printStream), "DebugLog");
			logger.setLevel(Level.TRACE);
			runAllLogMethods(logger);
		}
	}

	private void runAllLogMethods(Logger logger) {
		logger.error("ERROR message placeholder");
		logger.warn("WARNING message placeholder");
		logger.info("INFO message placeholder");
		logger.debug("DEBUG message placeholder");
		logger.trace("TRACE message placeholder");
	}

}
