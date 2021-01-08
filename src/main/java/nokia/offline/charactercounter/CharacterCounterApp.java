package nokia.offline.charactercounter;

import java.io.File;

/**
 * Main entry into the application
 *
 */
public class CharacterCounterApp {
	public static void main(String[] args) throws Exception {

		if (args.length == 0) {
			throw new IllegalArgumentException("Please provide a path for the directory");
		}

		File folder = new File(args[0]);

		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("Please provide a path for a directory");
		}

		FilesCollector filesCollector = new FilesCollector(folder);
		// This method will collect the files and call threads to read them
		filesCollector.collectAndRead();

		printRsult();
	}

	private static void printRsult() {
		for (int i = 0; i < 26; i++) {
			System.out.println((char) (i + 97) + "     " + CacheCenter.getCharsCountArray().get(i));
		}
	}
}
