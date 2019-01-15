package energyDemandEstimation;

import energyDemandEstimation.data.*;
import energyDemandEstimation.misc.RandomManager;
import energyDemandEstimation.misc.Solution;

import energyDemandEstimation.ELM.elm;
import energyDemandEstimation.GRASP.CRandom;
import energyDemandEstimation.GRASP.CVotos;
import energyDemandEstimation.GRASP.Constructive;
import energyDemandEstimation.GRASP.LocalSearch;
import no.uib.cipr.matrix.NotConvergedException;

public class EnergyDemandEstimation {

	final static int nIterations = 100;

	public static void main(String[] args) throws NotConvergedException {

		RandomManager.setSeed(12345);
		Data data = new Data();
		Constructive constructive;
		elm elm = null;
		Solution sol = null;
		Solution bestSol;
		double bestAccuracy;
		LocalSearch localSearch = new LocalSearch(data);
		int[] mostUsedVars = new int[14];
		long startTime, endTime, duration;
		
		System.out.println("-----Random Constructive-----");
		
		startTime = System.nanoTime();
		
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
		
		endTime = System.nanoTime();
		duration = (endTime - startTime)/1000000;
		
		System.out.println("La mejor ejecución del train ha tenido una accuracy de " + bestAccuracy + " y un tiempo de " + duration);

		bestSol = localSearch.improve(bestSol, mostUsedVars);

		elm = new elm(0, 20, "sig");
		elm.train(data.getTrainData(bestSol.getSelectedVars()));
		bestAccuracy = elm.getTrainingAccuracy();
		
		endTime = System.nanoTime();
		duration = (endTime - startTime)/1000000;
		
		System.out.println("Despues del improve ha sido " + bestAccuracy + " y un tiempo de " + duration);

		System.out.println("\n-----Votos Constructive-----");
		
		startTime = System.nanoTime();
		
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
		
		endTime = System.nanoTime();
		duration = (endTime - startTime)/1000000;

		System.out.println("La mejor ejecución del train ha tenido una accuracy de " + bestAccuracy + " y un tiempo de " + duration);

		sol = localSearch.improve(sol, mostUsedVars);

		elm = new elm(0, 20, "sig");
		elm.train(data.getTrainData(sol.getSelectedVars()));
		bestAccuracy = elm.getTrainingAccuracy();
		
		endTime = System.nanoTime();
		duration = (endTime - startTime)/1000000;
		
		System.out.println("Despues del improve ha sido " + bestAccuracy + " y un tiempo de " + duration);

	}

}