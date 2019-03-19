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

	final static int nIterations = 100;
	final static int añoInicio = 1981;
	final static int añoBuscado = 1999;
	final static int añosTrain = añoBuscado - añoInicio;

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
		boolean exception;
		System.out.println("Año buscado: " + añoBuscado);

		/* CSV */
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File(añoBuscado + ".csv"));
			StringBuilder sb = new StringBuilder();
			sb.append(añoBuscado);
			sb.append(";");
			sb.append("CRandom");
			sb.append(";");
			sb.append("Total CR");
			sb.append(";");
			sb.append("CVotos");
			sb.append(";");
			sb.append("Total CV");
			sb.append("\n");

			// Bucle para obtener 10 resultados y exportarlos a un csv
			for (int n = 0; n < 10; n++) {
				
				sb.append(n + 1);
				sb.append(";");
				System.out.println("\nn = " + (n + 1));
				System.out.println("\n-----Random Constructive-----");
				
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

				// Probamos antes cuando daría sin utilizar la busqueda local
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
				System.out.println("SIN MEJORA");

				elm = new elm(0, 20, "sig");
				elm.train(trainData);
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
				exception = true;
				while (exception) {
					exception = false;
					try {
						bestSol = localSearch.improve(bestSol, mostUsedVars);
					} catch (Exception e) {
						exception = true;
					}
				}

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
				System.out.println("CON MEJORA");

				elm = new elm(0, 20, "sig");
				elm.train(trainData);
				numVars = 0;
				for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
					if (bestSol.getSelectedVars()[i])
						numVars++;
				}
				output = elm.testOut(testData);

				System.out.println(Arrays.toString(output));
				
				sb.append(output[0]);
				sb.append(";");

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

				// Probamos antes cuando daría sin utilizar la busqueda local
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
				System.out.println("SIN MEJORA");

				elm = new elm(0, 20, "sig");
				elm.train(trainData);
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
				exception = true;
				while (exception) {
					exception = false;
					try {
						bestSol = localSearch.improve(bestSol, mostUsedVars);
					} catch (Exception e) {
						exception = true;
					}
				}

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
				System.out.println("CON MEJORA");

				elm = new elm(0, 20, "sig");
				elm.train(trainData);
				numVars = 0;
				for (int i = 0; i < bestSol.getSelectedVars().length; i++) {
					if (bestSol.getSelectedVars()[i])
						numVars++;
				}

				output = elm.testOut(testData);

				System.out.println(Arrays.toString(output));
				
				sb.append(output[0]);
				sb.append("\n");
			}
			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/* CSV */
	}

}