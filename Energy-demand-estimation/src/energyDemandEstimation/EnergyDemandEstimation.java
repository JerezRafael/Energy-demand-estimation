package energyDemandEstimation;

import energyDemandEstimation.data.*;
import energyDemandEstimation.misc.RandomManager;
import energyDemandEstimation.ELM.elm;
import no.uib.cipr.matrix.NotConvergedException;

public class EnergyDemandEstimation {

	public static void main(String[] args) throws NotConvergedException {

		RandomManager.setSeed(1234);

		double mejorError = 0;
		elm consumoEnergia = new elm(0, 20, "sig");
		Data data = new Data();
		Constructive constructive = new CRandom();

		double[][][] allData = constructive.crearDatosEntrada(data);
		double[][] trainData = allData[0];
		double[][] testData = allData[1];

		consumoEnergia.train(trainData); // entrenamiento con el conjunto de datos de encima

		for (int i = 0; i < 100; i++) {
			consumoEnergia = new elm(0, 20, "sig");
			double[][] testDataTrasp = new double[testData[0].length][testData.length];
			for (int x = 0; x < testDataTrasp.length; x++) {
				for (int y = 0; y < testDataTrasp[0].length; y++) {
					testDataTrasp[x][y] = testData[y][x];
				}
			}
			consumoEnergia.testOut(testDataTrasp);

			if (consumoEnergia.getTestingAccuracy() > mejorError) {
				mejorError = consumoEnergia.getTestingAccuracy();
			}
		}

		System.out.println("La mejor ejecución ha tenido una accuracy de " + mejorError);

	}

}