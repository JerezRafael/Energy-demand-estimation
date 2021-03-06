package energyDemandEstimation;

import energyDemandEstimation.data.*;
import energyDemandEstimation.misc.RandomManager;
import energyDemandEstimation.misc.Solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import energyDemandEstimation.ELM.elm;
import energyDemandEstimation.GRASP.CRandom;
import energyDemandEstimation.GRASP.CVotos;
import energyDemandEstimation.GRASP.Constructive;
import energyDemandEstimation.GRASP.LocalSearch;
import no.uib.cipr.matrix.NotConvergedException;

public class EnergyDemandEstimation {

	private static final int nExecutions = 100; // Deben cambiarse las formulas del excel
	private static final int nIterations = 100;
	private static final int kMax = 7;

	public static void main(String[] args) throws NotConvergedException {

		RandomManager.setSeed(1234);
		Data data = new Data();
		Constructive constructive;
		elm elm = null;
		Solution currentSolution = null;
		Solution bestSolution;
		double bestError, currentError;
		LocalSearch localSearch = new LocalSearch(data);
		int year, numVars = 0;
		double[] output;
		double[][] testData, trainData;
		long startTime, endTime, durationCR, durationTCR, durationCV, durationTCV;
		boolean exception;
		String sbS;
		PrintWriter pw;
		StringBuilder sb;
		int referenceYear = 0;

		try {
			sb = new StringBuilder();

			for (int y = 0; y < 16; y++) {

				switch (y) {
				case 0:
					referenceYear = 1985;
					break;
				case 1:
					referenceYear = 1986;
					break;
				case 2:
					referenceYear = 1988;
					break;
				case 3:
					referenceYear = 1989;
					break;
				case 4:
					referenceYear = 1992;
					break;
				case 5:
					referenceYear = 1993;
					break;
				case 6:
					referenceYear = 1994;
					break;
				case 7:
					referenceYear = 1997;
					break;
				case 8:
					referenceYear = 1998;
					break;
				case 9:
					referenceYear = 1999;
					break;
				case 10:
					referenceYear = 2000;
					break;
				case 11:
					referenceYear = 2002;
					break;
				case 12:
					referenceYear = 2003;
					break;
				case 13:
					referenceYear = 2008;
					break;
				case 14:
					referenceYear = 2010;
					break;
				case 15:
					referenceYear = 2011;
					break;
				}

				System.out.println("A�o buscado: " + referenceYear);

				/* CSV */

				// Bucle para obtener nEjecuciones resultados y exportarlos a un csv
				for (int n = 0; n < nExecutions; n++) {

					System.out.print(".");

					sb.append(n + 1);
					sb.append(";");

					startTime = System.nanoTime();

					constructive = new CRandom();

					bestSolution = null;
					bestError = Double.MAX_VALUE;

					for (int i = 0; i < nIterations; i++) {
						currentSolution = constructive.generateSolution();

						// Cogemos el conjunto de train
						trainData = data.getTrainData(currentSolution);

						// Se prueba la posible soluci�n

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
							bestSolution = currentSolution;
						}
					}

					endTime = System.nanoTime();
					durationCR = (endTime - startTime) / 1000000;

					// Probamos antes cuando dar�a sin utilizar la busqueda local
					numVars = 0;
					for (int i = 0; i < bestSolution.getSelectedVars().length; i++) {
						if (bestSolution.getSelectedVars()[i])
							numVars++;
					}
					trainData = new double[referenceYear - 1981][numVars];
					year = 1981;
					for (int i = 0; i < trainData.length; i++) {
						trainData[i] = data.getYear(year, bestSolution.getSelectedVars());
						year++;
					}

					testData = new double[2][];
					testData[0] = data.getYear(year, bestSolution);
					testData[1] = data.getYear(year, bestSolution);

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

					sb.append(output[0]);
					sb.append(";");

					// Busqueda local y resultados
					startTime = System.nanoTime();

					bestSolution = localSearch.improve(bestSolution, kMax);

					numVars = 0;
					for (int i = 0; i < bestSolution.getSelectedVars().length; i++) {
						if (bestSolution.getSelectedVars()[i])
							numVars++;
					}
					trainData = new double[referenceYear - 1981][numVars];
					year = 1981;
					for (int i = 0; i < trainData.length; i++) {
						trainData[i] = data.getYear(year, bestSolution);
						year++;
					}

					testData = new double[2][];
					testData[0] = data.getYear(year, bestSolution);
					testData[1] = data.getYear(year, bestSolution);

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

					endTime = System.nanoTime();
					durationTCR = (endTime - startTime) / 1000000;

					sb.append(output[0]);
					sb.append(";");

					////////////////////////////////////////////////////////////////////////////

					startTime = System.nanoTime();

					constructive = new CVotos(nIterations);

					bestSolution = null;
					bestError = Double.MAX_VALUE;

					for (int i = 0; i < nIterations; i++) {
						currentSolution = constructive.generateSolution();

						// Cogemos el conjunto de train
						trainData = data.getTrainData(currentSolution);

						// Se prueba la posible soluci�n

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
							bestSolution = currentSolution;
						}
					}

					endTime = System.nanoTime();
					durationCV = (endTime - startTime) / 1000000;

					// Probamos antes cuando dar�a sin utilizar la busqueda local
					numVars = 0;
					for (int i = 0; i < bestSolution.getSelectedVars().length; i++) {
						if (bestSolution.getSelectedVars()[i])
							numVars++;
					}
					trainData = new double[referenceYear - 1981][numVars];
					year = 1981;
					for (int i = 0; i < trainData.length; i++) {
						trainData[i] = data.getYear(year, bestSolution);
						year++;
					}

					testData = new double[2][];
					testData[0] = data.getYear(year, bestSolution);
					testData[1] = data.getYear(year, bestSolution);

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

					sb.append(output[0]);
					sb.append(";");

					// Busqueda local y resultados
					startTime = System.nanoTime();

					bestSolution = localSearch.improve(bestSolution, kMax);

					numVars = 0;
					for (int i = 0; i < bestSolution.getSelectedVars().length; i++) {
						if (bestSolution.getSelectedVars()[i])
							numVars++;
					}
					trainData = new double[referenceYear - 1981][numVars];
					year = 1981;
					for (int i = 0; i < trainData.length; i++) {
						trainData[i] = data.getYear(year, bestSolution);
						year++;
					}

					testData = new double[2][];
					testData[0] = data.getYear(year, bestSolution);
					testData[1] = data.getYear(year, bestSolution);

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

					endTime = System.nanoTime();
					durationTCV = (endTime - startTime) / 1000000;

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
				System.out.println();
			}

			sbS = sb.toString();
			sbS = sbS.replace(".", ",");

			pw = new PrintWriter(new File("export.csv"));
			pw.write(sbS);
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
