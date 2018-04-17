package energyDemandEstimation.data;

import energyDemandEstimation.Solution;
import energyDemandEstimation.ELM.elm;
import energyDemandEstimation.misc.RandomManager;
import no.uib.cipr.matrix.NotConvergedException;

public class CRandom implements Constructive {

	@Override
	public Solution generateSolution(Data data) throws NotConvergedException {

		RandomManager.setSeed(1234);
		boolean[] selectedVars = new boolean[14];
		elm elm;
		boolean[] bestSol = null;
		double bestAccuracy = 0;

		for (int i = 0; i < 100; i++) {

			for (int j = 0; j < 14; j++) { // bucle que genera una posible solucion aleatoriamente
				selectedVars[j] = RandomManager.getRandom().nextBoolean();
			}

			elm = new elm(0, 20, "sig");
			double[][] trainData = data.getTrainData(selectedVars);
			elm.train(trainData);

			if (elm.getTrainingAccuracy() > bestAccuracy) {
				bestAccuracy = elm.getTrainingAccuracy();
				bestSol = selectedVars;
			}
		}

		System.out.println("La mejor ejecución ha tenido una accuracy de " + bestAccuracy);

		return new Solution(bestSol, data);
	}

}
