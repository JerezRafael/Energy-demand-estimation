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
	public Solution improve(Solution solution, int[] mostUsedVars, int añoBuscado, int nLSIterations) throws NotConvergedException {

		ArrayList<Integer> roullete = new ArrayList<Integer>();

		for (int i = 0; i < mostUsedVars.length; i++) {
			mostUsedVars[i]++;
		}

		int l = 0;

		for (int i = 0; i < mostUsedVars.length; i++) {
			l = 0;
			while (l < mostUsedVars[i]) {
				roullete.add(i);
				l++;
			}
		}

		double currentError, minError = Double.MAX_VALUE;
		double[] output;
		double[][] trainData, testData;
		boolean removing, exception;
		boolean[] bestVars = solution.getSelectedVars();
		boolean[] vars;
		int n, var, numVars, year;
		elm elm;
		Solution newSolution, currentSol = solution;
		final ArrayList<Integer> finalRoullete = (ArrayList<Integer>) roullete.clone();

		for (int i = 0; i < 100; i++) {

			roullete = (ArrayList<Integer>) finalRoullete.clone();

			vars = new boolean[14];
			n = 1 + RandomManager.getRandom().nextInt(14); // numero de variables que cogeremos

			l = 0;
			while (l < n) { // escoger nuevas variables

				var = roullete.get(RandomManager.getRandom().nextInt(roullete.size()));
				vars[var] = true;
				removing = true;
				while (removing) {
					removing = roullete.remove((Integer) var);
				}

				l++;
			}
			elm = new elm(0, 20, "sig");
			trainData = data.getTrainData(vars);
			elm.train(trainData);

			// Función objetivo
			currentError = 0;

			currentSol = new Solution(vars);

			numVars = 0;
			for (int j = 0; j < currentSol.getSelectedVars().length; j++) {
				if (currentSol.getSelectedVars()[j])
					numVars++;
			}
			trainData = new double[añoBuscado - 1981][numVars];
			year = 1981;
			for (int j = 0; j < trainData.length; j++) {
				trainData[j] = data.getYear(year, currentSol.getSelectedVars());
				year++;
			}

			testData = new double[2][];
			testData[0] = data.getYear(year, currentSol.getSelectedVars());
			testData[1] = data.getYear(year, currentSol.getSelectedVars());

			for (int j = 0; j < nLSIterations; j++) {

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

				numVars = 0;
				for (int k = 0; k < currentSol.getSelectedVars().length; k++) {
					if (currentSol.getSelectedVars()[k])
						numVars++;
				}
				output = elm.testOut(testData);

				//System.out.println(testData[0][0] + " - " + output[0]);

				currentError += Math.abs(testData[0][0] - output[0]);

			}

			currentError = currentError / 100;

			if (currentError < minError) { // Si es mejor se guarda
				minError = currentError;
				bestVars = vars;
			}

		}

		newSolution = new Solution(bestVars);

		return newSolution;

	}
}
