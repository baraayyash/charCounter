package nokia.offline.charactercounter;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * The class is responsible for collecting the files and store them as runnable in queue
 * This class acts like a producer for the CharReadersConsumer
 * 
 * @author Braa'
 *
 */
public class FilesCollector {

	private File folder;

	public FilesCollector(File folder) {
		this.folder = folder;
	}

	
	/**
	 * This method will submit a CharReader threads for each file found 
	 * 
	 */
	public void produceCharReaders() throws InterruptedException {
		listFilesForFolder(folder);
		CacheCenter.setAllFilesCollected(true);
	}

	/**
	 * This method will loop through the directories to find files 
	 * 
	 */
	public void listFilesForFolder(final File folder) throws InterruptedException {

		ArrayList<File> subDirectories = new ArrayList<File>();

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				// loop into this later, give priority to pushing files to the queue
				subDirectories.add(fileEntry);
			} else {
				// Push a CharReader into the Queue, FileReaderConsumer will take it and submits it
				CacheCenter.getCharReaderQueue().offer(new CharReader(fileEntry.getAbsolutePath()), 10, TimeUnit.MINUTES);
			}
		}

		for (File subDir : subDirectories) {
			listFilesForFolder(subDir);
		}
	}
}