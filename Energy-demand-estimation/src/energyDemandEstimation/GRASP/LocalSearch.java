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
	public Solution improve(Solution solution, int[] mostUsedVars, int referenceYear, int nLSIterations) throws NotConvergedException {

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

		double currentError, minError;
		double[][] trainData, testData;
		boolean removing;
		boolean[] bestVars = solution.getSelectedVars();
		boolean[] vars;
		int n, var, year;
		elm elm;
		Solution newSolution, currentSol = solution;
		final ArrayList<Integer> finalRoullete = (ArrayList<Integer>) roullete.clone();
		
		// Calculamos el error con la solución actual
		trainData = createTrainData(solution, referenceYear);
		
		year = referenceYear - 1;
		
		testData = new double[2][];
		testData[0] = data.getYear(year, solution.getSelectedVars());
		testData[1] = data.getYear(year, solution.getSelectedVars());
		
		minError = calculateError(nLSIterations, trainData, testData, currentSol);

		// Ahora empezamos la busqueda local
		
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

			trainData = createTrainData(currentSol, referenceYear);
			
			year = referenceYear - 1;
			
			testData = new double[2][];
			testData[0] = data.getYear(year, currentSol.getSelectedVars());
			testData[1] = data.getYear(year, currentSol.getSelectedVars());
			
			currentError = calculateError(nLSIterations, trainData, testData, currentSol);

			if (currentError < minError) { // Si es mejor se guarda
				minError = currentError;
				bestVars = vars;
			}

		}

		newSolution = new Solution(bestVars);

		return newSolution;

	}
	
	private double calculateError(int nLSIterations, double[][] trainData, double[][] testData, Solution currentSol) {
		
		boolean exception;
		elm elm = null;
		double[] output;
		double currentError = 1;
		
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

			output = elm.testOut(testData);

			//System.out.println(testData[0][0] + " - " + output[0]);

			currentError += Math.abs(testData[0][0] - output[0]);

		}

		currentError = currentError / nLSIterations;
		return currentError;
	}
	
	private double[][] createTrainData(Solution solution, int referenceYear) {
		
		int numVars;
		double[][] trainData;
		
		numVars = 0;
		for (int j = 0; j < solution.getSelectedVars().length; j++) {
			if (solution.getSelectedVars()[j])
				numVars++;
		}
		trainData = new double[referenceYear - 1981 - 1][numVars];
		int year = 1981;
		for (int j = 0; j < trainData.length; j++) {
			trainData[j] = data.getYear(year, solution.getSelectedVars());
			year++;
		}
		
		return trainData;
	}
}
