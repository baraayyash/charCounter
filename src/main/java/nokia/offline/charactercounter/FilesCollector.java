package nokia.offline.charactercounter;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The class is responsible for collecting the files and store them as runnable in queue
 * This class acts like a producer for the FileReaderConsumer
 * 
 * @author Braa'
 *
 */
public class FilesCollector {

	private File folder;
	private ExecutorService executor;

	public FilesCollector(File folder) {
		this.folder = folder;
		this.executor = Executors.newFixedThreadPool(1);
		this.executor.submit(new FileReaderConsumer());
	}

	public void collectAndRead() throws InterruptedException {
		listFilesForFolder(folder);
		CacheCenter.setAllFilesCollected(true);
		this.executor.shutdown();
		this.executor.awaitTermination(1, TimeUnit.DAYS);
	}

	public void listFilesForFolder(final File folder) throws InterruptedException {

		ArrayList<File> subDirectories = new ArrayList<File>();

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				subDirectories.add(fileEntry);
			} else {
				CacheCenter.getCharReaderQueue().offer(new CharReader(fileEntry.getAbsolutePath()), 10, TimeUnit.MINUTES);
			}
		}

		for (File subDir : subDirectories) {
			listFilesForFolder(subDir);
		}
	}
}