package nokia.offline.charactercounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class CharReader implements Runnable {

	private String fileDir;

	public CharReader(String fileDir) {
		this.fileDir = fileDir;
	}

	private int charsCount[] = new int[26];

	public void run() {

		File file = new File(fileDir);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			readFile(br);
		} catch (IOException e) {
			e.printStackTrace();
		}

		publishResult();
	}

	private void readFile(BufferedReader br) throws IOException {
		// This will use about 16 KB regardless of the size of the file.
		char[] chars = new char[8192];
		for (; br.read(chars) > 0;) {
			pushCharacterToArray(chars);
		}
	}

	private void pushCharacterToArray(char[] chars) {
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] > 96 && chars[i] < 123) {
				// 97 = a = first index in the array = 0
				++charsCount[chars[i] - 97];
			}
		}
	}

	private void publishResult() {
		for (int i = 0; i < 26; i++) {
			if (charsCount[i] > 0) {
				final int index = i;
				CacheCenter.getCharsCountArray().getAndUpdate(i,
						current -> current.add(BigInteger.valueOf(charsCount[index])));
			}
		}
	}
}
