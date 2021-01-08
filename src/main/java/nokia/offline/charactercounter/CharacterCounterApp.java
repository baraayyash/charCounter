package nokia.offline.charactercounter;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

		ExecutorService executor = Executors.newFixedThreadPool(1);
		// one producer to read files and submits them
		executor.submit(new CharReadersConsumer());

		FilesCollector filesCollector = new FilesCollector(folder);

		// This method will collect the files and push them into a queue
		filesCollector.produceCharReaders();
		
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.DAYS);

		printRsult();
	}

	private static void printRsult() {
		for (int i = 0; i < 26; i++) {
			System.out.println((char) (i + 97) + "     " + CacheCenter.getCharsCountArray().get(i));
		}
	}
}
