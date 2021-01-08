package nokia.offline.charactercounter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.math.BigInteger;

import org.junit.Test;


/**
 * Unit test for character count application.
 */
public class CharacterCounterAppTest 
{

	/**
	 * Verify that the char counts are correct
	 *
	 * @throws Exception
	 */
	@Test
	public void testCharacterCount() throws InterruptedException {
		ClassLoader loader = CharacterCounterAppTest.class.getClassLoader();
		File folder = new File(loader.getResource("test2").getPath());

		FilesCollector filesCollector = new FilesCollector(folder);
		filesCollector.produceCharReaders();
		CharReadersConsumer charReadersConsumer = new CharReadersConsumer();
		charReadersConsumer.run();

		assertEquals(BigInteger.valueOf(19), CacheCenter.getCharsCountArray().get(0));
		assertEquals(BigInteger.valueOf(15), CacheCenter.getCharsCountArray().get(1));		
	}

	/**
	 * Verify that all threads in the queue has been submitted
	 *
	 * @throws Exception
	 */
	@Test
	public void testCharReaderQueue() {
		assertEquals(CacheCenter.getCharReaderQueue().size(), 0);		
	}

	/**
	 * Verify that invalid directory path will be rejected
	 *
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDirectory() throws Exception {
		String[] arguments = {"invalidPath/invlidfile"};
		CharacterCounterApp.main(arguments);
	}


	/**
	 * Verify that a file path will be rejected
	 *
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFilePassedAsDirectory() throws Exception {
		ClassLoader loader = CharacterCounterAppTest.class.getClassLoader();
		String[] arguments = {loader.getResource("test2/file1.txt").getPath()};
		CharacterCounterApp.main(arguments);
	}
}
