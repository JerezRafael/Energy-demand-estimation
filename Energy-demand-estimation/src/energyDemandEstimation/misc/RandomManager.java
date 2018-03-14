package energyDemandEstimation.misc;

import java.util.Random;

public class RandomManager {

	private Random rnd;
	
	public RandomManager (int seed) {
		setSeed(seed);
	}
	
	public void setSeed(int seed) {
		rnd = new Random(seed);
	}
	
	public Random getRandom() {
		return rnd;
	}
}
