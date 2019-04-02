package energyDemandEstimation.GRASP;

import energyDemandEstimation.ELM.elm;
import energyDemandEstimation.data.*;
import energyDemandEstimation.misc.RandomManager;
import energyDemandEstimation.misc.Solution;
import no.uib.cipr.matrix.NotConvergedException;

public class LocalSearch {

	private Data data;

	public LocalSearch(Data data) {
		this.data = data;
	}

	public Solution improve(Solution solution, int kMax) throws NotConvergedException {

		double bestError, currentError;
		double[][] trainData;
		boolean improve = true, exception;
		boolean[] bestVars, currentVars;
		int k, feature;
		elm elm = null;
		Solution newSolution;

		bestVars = solution.getSelectedVars().clone();
		currentVars = solution.getSelectedVars().clone();

		// Calculamos el error con la solución actual
		trainData = data.getTrainData(bestVars);

		exception = true;
		while (exception) {
			exception = false;
			try {
				elm = new elm(0, 20, "sig");
				elm.train(trainData);
			} catch (Exception e) {
				exception = true;
			}
		}

		bestError = elm.getTrainingAccuracy();

		// Ahora empezamos la busqueda local

		while (improve) {
			improve = false;
			k = 1;
			while (k <= kMax) {
				for (int i = 1; i < k; i++) {

					feature = RandomManager.getRandom().nextInt(14);
					currentVars[feature] = !currentVars[feature];

				}

				// Función objetivo
				trainData = data.getTrainData(currentVars);

				exception = true;
				while (exception) {
					exception = false;
					try {
						elm = new elm(0, 20, "sig");
						elm.train(trainData);
					} catch (Exception e) {
						exception = true;
					}
				}

				currentError = elm.getTrainingAccuracy();

				if (currentError < bestError) { // Si es mejor se guarda
					bestError = currentError;
					bestVars = currentVars.clone();
				} else {
					k++;
				}
			}
		}

		newSolution = new Solution(bestVars);

		return newSolution;

	}
}
