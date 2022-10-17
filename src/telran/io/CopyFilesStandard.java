package telran.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Application for copying files based on static method copy of class Files
 * files may be very big (several Gbytes ) args[0] - source file path args[1] -
 * destination file path args[2] - if exists "overwritten" then destination may
 * be overwritten otherwise may not be Output one of the following: Files have
 * been copied (<amount of bytes> <time of copying>) Source file doesn't exist
 * Destination can not be overwritten
 *
 */
public class CopyFilesStandard {

	public static void main(String[] args) {
		args = new String[] { "d:\\\\Test\\1.txt", "d:\\\\Test\\3.txt", "overwritten" };
		try {
			copyFiles(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void copyFiles(String[] args) throws Exception {
		Instant start = Instant.now();
		long copiedSize = 0;
		Path source = Path.of(args[0]);
		if (!source.isAbsolute()) {
			throw new IllegalArgumentException("Source file " + args[0] + " does not exist");
		}
		File destFile = new File(args[1]);
		if (destFile.exists() && (args.length < 3 || args[2].compareTo("overwritten") != 0)) {
			throw new IllegalArgumentException("Destination " + args[1] + " cannot be overwritten");
		}
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(destFile));
		} catch (FileNotFoundException e) {
			throw new Exception("Destination " + destFile.getPath() + " has non-existed directory in the path");
		}
		copiedSize = Files.copy(source, out);
		out.close();
		Instant finish = Instant.now();
		System.out.print(
				"Copied " + copiedSize + " bytes for " + ChronoUnit.MILLIS.between(start, finish) + " milliseconds");
	}

}
