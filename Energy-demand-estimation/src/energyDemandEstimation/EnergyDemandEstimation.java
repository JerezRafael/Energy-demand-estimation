package energyDemandEstimation;

import energyDemandEstimation.data.*;
import energyDemandEstimation.misc.RandomManager;
import energyDemandEstimation.misc.Solution;

import java.util.Arrays;

import energyDemandEstimation.ELM.elm;
import energyDemandEstimation.GRASP.CRandom;
import energyDemandEstimation.GRASP.CVotos;
import energyDemandEstimation.GRASP.Constructive;
import energyDemandEstimation.GRASP.LocalSearch;
import no.uib.cipr.matrix.NotConvergedException;

public class EnergyDemandEstimation {

	final static int nIterations = 100;
	final static int añoInicio = 1981;
	final static int añosTrain = 4;
	final static int añoBuscado = añoInicio + añosTrain;

	public static void main(String[] args) throws NotConvergedException {

		RandomManager.setSeed(1234);
		Data data = new Data();
		Constructive constructive;
		elm elm = null;
		Solution sol = null;
		Solution bestSol;
		double bestAccuracy;
		LocalSearch localSearch = new LocalSearch(data);
		int[] mostUsedVars = new int[14];
		int year, numVars = 0;
		double[] output;
		double[][] testData, trainData = null;

		System.out.println("-----Random Constructive-----");

		constructive = new CRandom();

		bestSol = null;
		bestAccuracy = Double.MAX_VALUE;

		for (int i = 0; i < nIterations; i++) {
			sol = constructive.generateSolution();
			numVars = 0;

			// Contamos el número de variables escogidas
			for (int j = 0; j < sol.getSelectedVars().length; j++) {
				if (sol.getSelectedVars()[j])
					numVars++;
			}

			// Cogemos los años anteriores al que vamos a testear
			trainData = new double[4][numVars];
			year = añoInicio;
			for (int j = 0; j < trainData.length; j++) {
				trainData[j] = data.getYear(year, sol.getSelectedVars());
				year++;
			}

			// Se prueba la posible solución
			elm = new elm(0, 20, "sig");
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

		bestSol = localSearch.improve(bestSol, mostUsedVars);

		numVars = 0;
		for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
			if (bestSol.getSelectedVars()[i])
				numVars++;
		}
		trainData = new double[añosTrain][numVars];
		year = añoInicio;
		for (int i = 0; i < trainData.length; i++) {
			trainData[i] = data.getYear(year, bestSol.getSelectedVars());
			year++;
		}

		testData = new double[2][];
		testData[0] = data.getYear(year, bestSol.getSelectedVars());
		testData[1] = data.getYear(year, bestSol.getSelectedVars());
		System.out.println("Año buscado: " + añoBuscado);

		elm = new elm(0, 20, "sig");
		elm.train(trainData);
		numVars = 0;
		for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
			if (bestSol.getSelectedVars()[i])
				numVars++;
		}
		output = elm.testOut(testData);

		System.out.println(Arrays.toString(output));

////////////////////////////////////////////////////////////////////////////

		System.out.println("\n-----Votos Constructive-----");

		constructive = new CVotos(nIterations);

		bestSol = null;
		bestAccuracy = Double.MAX_VALUE;

		for (int i = 0; i < nIterations; i++) {
			sol = constructive.generateSolution();
			numVars = 0;

			// Contamos el número de variables escogidas
			for (int j = 0; j < sol.getSelectedVars().length; j++) {
				if (sol.getSelectedVars()[j])
					numVars++;
			}

			// Cogemos los años anteriores al que vamos a testear
			trainData = new double[4][numVars];
			year = añoInicio;
			for (int j = 0; j < trainData.length; j++) {
				trainData[j] = data.getYear(year, sol.getSelectedVars());
				year++;
			}

			// Se prueba la posible solución
			elm = new elm(0, 20, "sig");
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

		bestSol = localSearch.improve(bestSol, mostUsedVars);

		numVars = 0;
		for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
			if (bestSol.getSelectedVars()[i])
				numVars++;
		}
		trainData = new double[añosTrain][numVars];
		year = añoInicio;
		for (int i = 0; i < trainData.length; i++) {
			trainData[i] = data.getYear(year, bestSol.getSelectedVars());
			year++;
		}

		testData = new double[2][];
		testData[0] = data.getYear(year, bestSol.getSelectedVars());
		testData[1] = data.getYear(year, bestSol.getSelectedVars());
		System.out.println("Año buscado: " + añoBuscado);

		elm = new elm(0, 20, "sig");
		elm.train(trainData);
		numVars = 0;
		for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
			if (bestSol.getSelectedVars()[i])
				numVars++;
		}
		
		output = elm.testOut(testData);
		
		System.out.println(Arrays.toString(output));
	}
}