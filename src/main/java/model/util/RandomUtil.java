package model.util;

import java.util.Random;

public class RandomUtil {
	private static RandomUtil randomUtil = null;
	private Random random = new Random();

	public static RandomUtil getInstance() {
		if (randomUtil == null)
			randomUtil = new RandomUtil();

		return randomUtil;
	}

	public int generateRandom(int threshold) {
		return random.nextInt(threshold);
	}
}
