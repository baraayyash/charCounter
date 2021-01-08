package nokia.offline.charactercounter;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class CacheCenter {

	private static BlockingQueue<CharReader> charReaderQueue = new LinkedBlockingQueue<CharReader>(100);
	
	private static volatile boolean isAllFilesCollected = false;
	
	private static AtomicReferenceArray<BigInteger> charsCountArray = new AtomicReferenceArray<BigInteger>(26);

	static {
		for (int i=0; i<26;i++) {
			charsCountArray.set(i, BigInteger.ZERO);
		}
	}
	
	public static boolean isAllFilesCollected() {
		return isAllFilesCollected;
	}

	public static void setAllFilesCollected(boolean isAllFilesCollected) {
		CacheCenter.isAllFilesCollected = isAllFilesCollected;
	}
	
	public static AtomicReferenceArray<BigInteger> getCharsCountArray() {
		return charsCountArray;
	}

	public static BlockingQueue<CharReader> getCharReaderQueue() {
		return charReaderQueue;
	}
}
