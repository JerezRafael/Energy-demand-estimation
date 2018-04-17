package energyDemandEstimation;

import energyDemandEstimation.data.*;
import energyDemandEstimation.misc.RandomManager;

import java.util.Arrays;

import energyDemandEstimation.ELM.elm;
import no.uib.cipr.matrix.NotConvergedException;

public class EnergyDemandEstimation {

	public static void main(String[] args) throws NotConvergedException {

		// puerta AND
//		System.out.println("\nTest AND:");
//		elm ds3 = new elm(0, 20, "sig");
//		double[][] traindata = new double[][] { { 1, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0 }, { 1, 1, 1 } };
//		ds3.train(traindata);
//		double[][] inpt = new double[][] { { 5000, 0, 0 }, { 5000, 0, 1 }, { 5000, 1, 0 }, { 5001, 1, 1 } };
//		double[] salida = ds3.testOut(inpt);
//		System.out.println(Arrays.toString(salida));

		RandomManager.setSeed(1234);

		double mejorError = 99999;
		Data data = new Data();
		Constructive constructive = new CRandom();

		double[][][] allData = constructive.crearDatosEntrada(data);
		double[][] trainData = allData[0];
		double[][] testData = allData[1];

		elm consumoEnergia = new elm(0, 20, "sig");
		consumoEnergia.train(trainData);

		for (int i = 0; i < 100; i++) {
//			consumoEnergia = new elm(0, 20, "sig");
//			consumoEnergia.train(trainData);
			consumoEnergia.testOut(testData);

			if (consumoEnergia.getTestingAccuracy() < mejorError) {
				mejorError = consumoEnergia.getTestingAccuracy();
			}
		}

		System.out.println("La mejor ejecución ha tenido una accuracy de " + mejorError);

	}

}