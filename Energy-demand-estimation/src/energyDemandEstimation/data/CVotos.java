package energyDemandEstimation.data;

import energyDemandEstimation.Solution;
import energyDemandEstimation.ELM.elm;
import energyDemandEstimation.misc.RandomManager;
import no.uib.cipr.matrix.NotConvergedException;

public class CVotos implements Constructive {

	@Override
	public Solution generateSolution(Data data) throws NotConvergedException {

		final int nIterations = 100;

		boolean[] selectedVars = new boolean[14];
		elm elm;
		boolean[] bestSol = null;
		double bestAccuracy = 0;
		int[] wheel;
		int nVars, random, total, spin;
		boolean[] selectedAlready;

		wheel = new int[14]; // se inicializa la ruleta con el numero de variables y cada una a 100
		for (int j = 0; j < 14; j++) {
			wheel[j] = nIterations;
		}

		for (int i = 0; i < nIterations; i++) {

			selectedAlready = new boolean[14];

			nVars = RandomManager.getRandom().nextInt(14) + 1; // numero de variables que se van a elegir esta iteracion
			for (int j = 0; j < nVars; j++) { // eligiendo variables por la ruleta

				total = 0;
				for (int k = 0; k < wheel.length; k++) { // cogiendo el numero total de la ruleta
					total += wheel[k];
				}

				random = RandomManager.getRandom().nextInt(total);
				spin = 0;
				for (int k = 0; k < wheel.length; k++) {
					spin += wheel[k];
					if (random < spin & !selectedAlready[k]) {
						selectedVars[k] = true;
						selectedAlready[k] = true;
						wheel[k]--;
						break;
						// es correcto poner un break? o mejor poner
						// selectedVars[k] = random > wheel[k] & random < wheel[k + 1]
					}
				}
			}

			// Se prueba la posible solución
			elm = new elm(0, 20, "sig");
			double[][] trainData = data.getTrainData(selectedVars);
			elm.train(trainData);

			if (elm.getTrainingAccuracy() > bestAccuracy) {
				bestAccuracy = elm.getTrainingAccuracy();
				bestSol = selectedVars;
			}
		}

		System.out.println("La mejor ejecución del train ha tenido una accuracy de " + bestAccuracy);

		return new Solution(bestSol, data);
	}

}
