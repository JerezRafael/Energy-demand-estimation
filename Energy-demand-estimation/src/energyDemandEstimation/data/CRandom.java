package energyDemandEstimation.data;

import energyDemandEstimation.Solution;
import energyDemandEstimation.misc.RandomManager;
import no.uib.cipr.matrix.NotConvergedException;

public class CRandom implements Constructive {
	
	@Override
	public Solution generateSolution() throws NotConvergedException {

		RandomManager.setSeed(1234);
		boolean[] selectedVars = new boolean[14];

		for (int j = 0; j < 14; j++) { // bucle que genera una posible solucion aleatoriamente
			selectedVars[j] = RandomManager.getRandom().nextBoolean();
		}
		
		return new Solution(selectedVars);
	}

}