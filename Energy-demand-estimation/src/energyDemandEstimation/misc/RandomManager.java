package energyDemandEstimation.misc;

import java.util.Random;

public class RandomManager {

	private static Random rnd;

	public static void setSeed(int seed) {
		rnd = new Random(seed);
	}

	public static Random getRandom() {
		return rnd;
	}
}
