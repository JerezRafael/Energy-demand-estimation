package energyDemandEstimation.GRASP;

import java.util.ArrayList;

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

	@SuppressWarnings("unchecked")
	public Solution improve(Solution solution, int[] mostUsedVars) throws NotConvergedException {

		ArrayList<Integer> roullete = new ArrayList<Integer>();

		for (int i = 0; i < mostUsedVars.length; i++) {
			mostUsedVars[i]++;
		}

		int j = 0;

		for (int i = 0; i < mostUsedVars.length; i++) {
			j = 0;
			while (j < mostUsedVars[i]) {
				roullete.add(i);
				j++;
			}
		}

		double bestAccuracy = Double.MAX_VALUE;
		boolean[] selectedVars = new boolean[14];
		boolean[] varsAux;
		int n, var;
		elm elm;
		double[][] trainData;
		final ArrayList<Integer> finalRoullete = (ArrayList<Integer>) roullete.clone();
		boolean removing;

		for (int i = 0; i < 100; i++) {

			roullete = (ArrayList<Integer>) finalRoullete.clone();

			varsAux = new boolean[14];
			n = 1 + RandomManager.getRandom().nextInt(14); // numero de variables que cogeremos

			// si en mostUsedVars hay 5 variables, n deberia poder salir mas de 5?

			j = 0;
			while (j < n) { // escoger nuevas variables

				var = roullete.get(RandomManager.getRandom().nextInt(roullete.size()));
				varsAux[var] = true;
				removing = true;
				while (removing) {
					removing = roullete.remove((Integer) var);
				}

				j++;
			}
			elm = new elm(0, 20, "sig");
			trainData = data.getTrainData(varsAux);
			elm.train(trainData);

			if (elm.getTrainingAccuracy() < bestAccuracy) { // Si es mejor se guarda
				bestAccuracy = elm.getTrainingAccuracy();
				selectedVars = varsAux;
			}

		}

		Solution newSolution = new Solution(selectedVars);

		return newSolution;

	}
}
