package telran.io;

import java.io.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


/**
 * Application for copying files based on FileInputStream and FileOutputStream
 * files may be very big (several Gbytes ) args[0] - source file path args[1] -
 * destination file path args[2] - if exists "overwritten" then destination may
 * be overwritten otherwise may not be Output one of the following: Files have
 * been copied (<amount of bytes> <time of copying>) Source file doesn't exist
 * Destination can not be overwritten
 *
 */
public class CopyFilesInputOutputStreams {

	public static void main(String[] args) {
		args = new String[]{"d:\\\\Test\\1.txt", "d:\\\\Test\\3.txt", "overwritten"};
		try {
			copyFiles(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void copyFiles(String[] args) throws Exception {
		Instant start = Instant.now();
		if(args.length < 2) {
			throw new IllegalArgumentException("Mandatory args are path name of a source file (source file must exist) "
					+ "and path name of a destination (destination may or may not exist).");
		}
		File sourceFile = new File(args[0]);
		if (!sourceFile.exists()) {
			throw new IllegalArgumentException("Source file " + args[0] + " does not exist");
		}
		File destFile = new File(args[1]);
		if (destFile.exists() && (args.length < 3 || args[2].compareTo("overwritten") != 0)) {
			throw new IllegalArgumentException("Destination " + args[1] + " cannot be overwritten");
		}
        long copiedSize = copyStream(sourceFile, destFile);
		Instant finish = Instant.now();
		System.out.print(
				"Copied " + copiedSize + " bytes for " + ChronoUnit.MILLIS.between(start, finish) + " milliseconds");
	}

	private static long copyStream(File sourceFile, File destFile)
			throws FileNotFoundException, IOException, Exception {
		long copiedSize = 0;
		InputStream in = null;
		OutputStream out = null;

		in = new BufferedInputStream(new FileInputStream(sourceFile));

		try {
			out = new BufferedOutputStream(new FileOutputStream(destFile));
		} catch (FileNotFoundException e) {
			in.close();
			throw new Exception("Destination " + destFile.getPath() + " has non-existed directory in the path");
		}

		byte[] buffer = new byte[1024];
		int length;

		while ((length = in.read(buffer)) > 0) {
			try {
				out.write(buffer, 0, length);
			} catch (Exception e) {
				in.close();
				out.close();
				throw new Exception("Unknown Error " + e.getMessage());
			}
			copiedSize += length;
		}
		in.close();
		out.close();
		return copiedSize;
	}

}
