package energyDemandEstimation;

import energyDemandEstimation.data.*;
import energyDemandEstimation.misc.RandomManager;
import energyDemandEstimation.misc.Solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

import energyDemandEstimation.ELM.elm;
import energyDemandEstimation.GRASP.CRandom;
import energyDemandEstimation.GRASP.CVotos;
import energyDemandEstimation.GRASP.Constructive;
import energyDemandEstimation.GRASP.LocalSearch;
import no.uib.cipr.matrix.NotConvergedException;

public class EnergyDemandEstimation {

	final static int referenceYear = 1989;
	final static int nExecutions = 100;
	final static int nIterations = 100;
	final static int nLSObjectiveFunction = 100;

	public static void main(String[] args) throws NotConvergedException {

		RandomManager.setSeed(1234);
		Data data = new Data();
		Constructive constructive;
		elm elm = null;
		Solution sol = null;
		Solution bestSol;
		double minError, currentError;
		LocalSearch localSearch = new LocalSearch(data);
		int year, numVars = 0;
		int[] mostUsedVars = new int[14];
		double[] output;
		double[][] testData, trainData;
		boolean exception;
		long startTime, endTime, durationCR, durationTCR, durationCV, durationTCV;
		String sbS;

		System.out.println("Año buscado: " + referenceYear);

		/* CSV */
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File(referenceYear + " - " + nExecutions + ".csv"));
			StringBuilder sb = new StringBuilder();
			sb.append(referenceYear);
			sb.append(";");
			sb.append("CRandom");
			sb.append(";");
			sb.append("Total CR");
			sb.append(";");
			sb.append("CVotos");
			sb.append(";");
			sb.append("Total CV");
			sb.append(";");
			sb.append("Tiempo CR");
			sb.append(";");
			sb.append("Tiempo Total CR");
			sb.append(";");
			sb.append("Tiempo CV");
			sb.append(";");
			sb.append("Tiempo Total CV");
			sb.append("\n");

			// Bucle para obtener nEjecuciones resultados y exportarlos a un csv
			for (int n = 0; n < nExecutions; n++) {

				sb.append(n + 1);
				sb.append(";");
				System.out.println("\nn = " + (n + 1));

				startTime = System.nanoTime();

				System.out.println("\n-----Random Constructive-----");

				constructive = new CRandom();

				bestSol = null;
				minError = Double.MAX_VALUE;

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
					year = 1981;
					for (int j = 0; j < trainData.length; j++) {
						trainData[j] = data.getYear(year, sol.getSelectedVars());
						year++;
					}

					// Se prueba la posible solución
					currentError = calculateError(nLSObjectiveFunction, trainData, trainData, sol);

					if (currentError < minError) { // Si es mejor se guarda
						minError = currentError;
						bestSol = sol;
						for (int j = 0; j < mostUsedVars.length; j++) {
							if (sol.getSelectedVars()[j])
								mostUsedVars[j]++;
						}
					}
				}

				endTime = System.nanoTime();
				durationCR = (endTime - startTime) / 1000000;

				// Probamos antes cuando daría sin utilizar la busqueda local
				numVars = 0;
				for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
					if (bestSol.getSelectedVars()[i])
						numVars++;
				}
				trainData = new double[referenceYear - 1981][numVars];
				year = 1981;
				for (int i = 0; i < trainData.length; i++) {
					trainData[i] = data.getYear(year, bestSol.getSelectedVars());
					year++;
				}

				testData = new double[2][];
				testData[0] = data.getYear(year, bestSol.getSelectedVars());
				testData[1] = data.getYear(year, bestSol.getSelectedVars());
				System.out.println("SIN MEJORA");

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
				for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
					if (bestSol.getSelectedVars()[i])
						numVars++;
				}
				output = elm.testOut(testData);

				System.out.println(Arrays.toString(output));

				sb.append(output[0]);
				sb.append(";");

				// Busqueda local y resultados
				startTime = System.nanoTime();

				exception = true;
				while (exception) {
					exception = false;
					try {
						bestSol = localSearch.improve(bestSol, mostUsedVars, referenceYear, nLSObjectiveFunction);
					} catch (Exception e) {
						exception = true;
					}
				}

				numVars = 0;
				for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
					if (bestSol.getSelectedVars()[i])
						numVars++;
				}
				trainData = new double[referenceYear - 1981][numVars];
				year = 1981;
				for (int i = 0; i < trainData.length; i++) {
					trainData[i] = data.getYear(year, bestSol.getSelectedVars());
					year++;
				}

				testData = new double[2][];
				testData[0] = data.getYear(year, bestSol.getSelectedVars());
				testData[1] = data.getYear(year, bestSol.getSelectedVars());
				System.out.println("CON MEJORA");

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
				for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
					if (bestSol.getSelectedVars()[i])
						numVars++;
				}
				output = elm.testOut(testData);

				endTime = System.nanoTime();
				durationTCR = (endTime - startTime) / 1000000;

				System.out.println(Arrays.toString(output));

				sb.append(output[0]);
				sb.append(";");

				////////////////////////////////////////////////////////////////////////////

				startTime = System.nanoTime();

				System.out.println("\n-----Votos Constructive-----");

				constructive = new CVotos(nIterations);

				bestSol = null;
				minError = Double.MAX_VALUE;

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
					year = 1981;
					for (int j = 0; j < trainData.length; j++) {
						trainData[j] = data.getYear(year, sol.getSelectedVars());
						year++;
					}

					// Se prueba la posible solución
					currentError = calculateError(nLSObjectiveFunction, trainData, trainData, sol);

					if (currentError < minError) { // Si es mejor se guarda
						minError = currentError;
						bestSol = sol;
						for (int j = 0; j < mostUsedVars.length; j++) {
							if (sol.getSelectedVars()[j])
								mostUsedVars[j]++;
						}
					}
				}

				endTime = System.nanoTime();
				durationCV = (endTime - startTime) / 1000000;

				// Probamos antes cuando daría sin utilizar la busqueda local
				numVars = 0;
				for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
					if (bestSol.getSelectedVars()[i])
						numVars++;
				}
				trainData = new double[referenceYear - 1981][numVars];
				year = 1981;
				for (int i = 0; i < trainData.length; i++) {
					trainData[i] = data.getYear(year, bestSol.getSelectedVars());
					year++;
				}

				testData = new double[2][];
				testData[0] = data.getYear(year, bestSol.getSelectedVars());
				testData[1] = data.getYear(year, bestSol.getSelectedVars());
				System.out.println("SIN MEJORA");

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
				for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
					if (bestSol.getSelectedVars()[i])
						numVars++;
				}
				output = elm.testOut(testData);

				System.out.println(Arrays.toString(output));

				sb.append(output[0]);
				sb.append(";");

				// Busqueda local y resultados
				startTime = System.nanoTime();

				exception = true;
				while (exception) {
					exception = false;
					try {
						bestSol = localSearch.improve(bestSol, mostUsedVars, referenceYear, nLSObjectiveFunction);
					} catch (Exception e) {
						exception = true;
					}
				}

				numVars = 0;
				for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
					if (bestSol.getSelectedVars()[i])
						numVars++;
				}
				trainData = new double[referenceYear - 1981][numVars];
				year = 1981;
				for (int i = 0; i < trainData.length; i++) {
					trainData[i] = data.getYear(year, bestSol.getSelectedVars());
					year++;
				}

				testData = new double[2][];
				testData[0] = data.getYear(year, bestSol.getSelectedVars());
				testData[1] = data.getYear(year, bestSol.getSelectedVars());
				System.out.println("CON MEJORA");

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
				for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
					if (bestSol.getSelectedVars()[i])
						numVars++;
				}

				output = elm.testOut(testData);

				endTime = System.nanoTime();
				durationTCV = (endTime - startTime) / 1000000;

				System.out.println(Arrays.toString(output));

				sb.append(output[0]);
				sb.append(";");
				sb.append(durationCR);
				sb.append(";");
				sb.append(durationTCR);
				sb.append(";");
				sb.append(durationCV);
				sb.append(";");
				sb.append(durationTCV);
				sb.append("\n");
			}

			sbS = sb.toString();
			sbS = sbS.replace(".", ",");

			pw.write(sbS);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/* CSV */
		System.out.println("\nAño buscado: " + referenceYear);
	}

	public static double calculateError(int nLSIterations, double[][] trainData, double[][] testData, Solution currentSol) {
		
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
	
}
