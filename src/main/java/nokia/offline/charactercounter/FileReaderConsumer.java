package nokia.offline.charactercounter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class is a consumer for the FilesCollector.
 * Will fetch runnables from queue and submits them
 * 
 * @author Braa'
 *
 */
public class FileReaderConsumer implements Runnable {
	
	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	@Override
	public void run() {
		while( ! CacheCenter.getCharReaderQueue().isEmpty() || ! CacheCenter.isAllFilesCollected()) {
			try {
				// Wait 50 ms for the producer
				CharReader charReader = CacheCenter.getCharReaderQueue().poll(50, TimeUnit.MILLISECONDS);
				
				// waited 50 ms and didn't get, so try again
				if (charReader == null) {
					continue;
				}
				
				this.executor.submit(charReader);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.executor.shutdown();
		
		try {
			this.executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
