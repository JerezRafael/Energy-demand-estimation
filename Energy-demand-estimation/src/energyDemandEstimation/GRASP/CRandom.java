package energyDemandEstimation.GRASP;

import energyDemandEstimation.misc.RandomManager;
import energyDemandEstimation.misc.Solution;
import no.uib.cipr.matrix.NotConvergedException;

public class CRandom implements Constructive {

	@Override
	public Solution generateSolution() throws NotConvergedException {

		boolean[] selectedVars = new boolean[14];

		for (int j = 0; j < 14; j++) { // bucle que genera una posible solucion aleatoriamente
			selectedVars[j] = RandomManager.getRandom().nextBoolean();
		}

		return new Solution(selectedVars);
	}

}