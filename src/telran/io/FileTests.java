package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.io.*;

class FileTests {
	File file;

	@BeforeEach
	void setUp() {
		file = new File("dir1/dir2");
		file.delete();
	}

	@Test
	void newDirectoryTest() {

		assertFalse(file.exists());
		file.mkdirs();
		assertTrue(file.exists());
	}

	@Test
	void printDirectoryContent() {
		printDirectory("d:\\\\Test", -1);
		printDirectory("d:\\\\Test", 0);
		printDirectory("d:\\\\Test", 1);
		printDirectory("d:\\\\Test", 2);
		printDirectory("d:\\\\Test", 3);
		printDirectory("d:\\\\Test", 4);
	}

	/**
	 * 
	 * @param pathName - name of path to initial directory
	 * @param level    - level of sub-directories printing example, level = 1
	 *                 printing only first level of the initial directory content
	 *                 level = 2 content + sub-directories content '''''''' level =
	 *                 -1 printing all levels
	 */
	private void printDirectory(String pathName, int level) {
		File path = new File(pathName);
		if (level == -1) {
			printDirectory(path, 0);
		} else {
			printDirectory(path, 0, level);
		}
		System.out.println();
		System.out.println("**************************************");

	}

	private void printDirectory(File path, int indent, int level) {
		if (level < 0) {
			return;
		}
		for (int i = 0; i < indent; i++) {
			System.out.print(' ');
		}
		boolean isDirectory = path.isDirectory();
		if (isDirectory) {
			System.out.println("<dir> type = " + path.getName());
		} else {
			System.out.println("<file> type = " + path.getName());
		}
		--level;
		if (isDirectory) {
			File[] pathContents = path.listFiles();
			for (File i : pathContents) {
				printDirectory(i, indent + 4, level);
			}
		}
	}
	private void printDirectory(File path, int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print(' ');
		}
		boolean isDirectory = path.isDirectory();
		if (isDirectory) {
			System.out.println("<dir> type = " + path.getName());
		} else {
			System.out.println("<file> type = " + path.getName());
		}
		if (isDirectory) {
			File[] pathContents = path.listFiles();
			for (File i : pathContents) {
				printDirectory(i, indent + 4);
			}
		}
	}
}

