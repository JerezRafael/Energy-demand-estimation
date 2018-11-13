package energyDemandEstimation;

import energyDemandEstimation.data.*;
import energyDemandEstimation.misc.RandomManager;
import energyDemandEstimation.misc.Solution;

import java.util.Arrays;

import GRASP.CRandom;
import GRASP.CVotos;
import GRASP.Constructive;
import GRASP.LocalSearch;
import energyDemandEstimation.ELM.elm;
import no.uib.cipr.matrix.NotConvergedException;

public class EnergyDemandEstimation {

	final static int nIterations = 100;

	public static void main(String[] args) throws NotConvergedException {

		RandomManager.setSeed(1234);

		// pruebas

		// puerta AND
		System.out.println("\nTest AND:");
		elm ds3 = new elm(0, 20, "sig");
		double[][] traindata = new double[][] { { 0, 0 }, { 1, 1 } };
		// double[][] traindata = new double[][] { { 1, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0
		// }, { 1, 1, 1 } };
		ds3.train(traindata);
		double[][] inpt = new double[][] { { 5000, 1 } };
		// double[][] inpt = new double[][] { { 5000, 0, 0 }, { 5000, 0, 1 }, { 5000, 1,
		// 0 }, { 5001, 1, 1 } };
		double[] salida = ds3.testOut(inpt);
		System.out.println(Arrays.toString(salida));

		System.out.println("TrainingTime:" + ds3.getTrainingTime());
		System.out.println("TrainingAcc:" + ds3.getTrainingAccuracy());
		System.out.println();

		// pruebas

		Data data = new Data();
		Constructive constructive;
		elm elm;
		Solution sol = null;
		Solution bestSol;
		double bestAccuracy;
		LocalSearch localSearch = new LocalSearch(data);
		int[] mostUsedVars = new int[14];

		System.out.println("-----Random Constructive-----");
		constructive = new CRandom();

		bestSol = null;
		bestAccuracy = Double.MAX_VALUE;

		for (int i = 0; i < nIterations; i++) {
			sol = constructive.generateSolution();

			// Se prueba la posible solución
			elm = new elm(0, 20, "sig");
			double[][] trainData = data.getTrainData(sol.getSelectedVars());
			elm.train(trainData);

			if (elm.getTrainingAccuracy() < bestAccuracy) { // Si es mejor se guarda
				bestAccuracy = elm.getTrainingAccuracy();
				bestSol = sol;
				for (int j = 0; j < mostUsedVars.length; j++) {
					if (sol.getSelectedVars()[j])
						mostUsedVars[j]++;
				}
			}
		}

		System.out.println("La mejor ejecución del train ha tenido una accuracy de " + bestAccuracy);

		bestSol = localSearch.improve(bestSol, mostUsedVars);

		elm = new elm(0, 20, "sig");
		elm.train(data.getTrainData(bestSol.getSelectedVars()));
		bestAccuracy = elm.getTrainingAccuracy();
		System.out.println("Despues del improve ha sido " + bestAccuracy);

		System.out.println("\n-----Votos Constructive-----");
		constructive = new CVotos(nIterations);

		bestSol = null;
		bestAccuracy = Double.MAX_VALUE;

		for (int i = 0; i < nIterations; i++) {
			sol = constructive.generateSolution();

			// Se prueba la posible solución
			elm = new elm(0, 20, "sig");
			double[][] trainData = data.getTrainData(sol.getSelectedVars());
			elm.train(trainData);

			if (elm.getTrainingAccuracy() < bestAccuracy) { // Si es mejor se guarda
				bestAccuracy = elm.getTrainingAccuracy();
				bestSol = sol;
				for (int j = 0; j < mostUsedVars.length; j++) {
					if (sol.getSelectedVars()[j])
						mostUsedVars[j]++;
				}
			}
		}

		System.out.println("La mejor ejecución del train ha tenido una accuracy de " + bestAccuracy);

		// sol = grasp.improve(sol, mostUsedVars);

		elm = new elm(0, 20, "sig");
		elm.train(data.getTrainData(sol.getSelectedVars()));
		bestAccuracy = elm.getTrainingAccuracy();
		System.out.println("Despues del improve ha sido " + bestAccuracy);

	}

}