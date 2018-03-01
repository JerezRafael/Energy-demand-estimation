package ELM;

import java.util.Arrays;

import no.uib.cipr.matrix.NotConvergedException;

public class main {

	/**
	 * @param args
	 * @throws NotConvergedException
	 */
	public static void main(String[] args) throws NotConvergedException {

		// puerta AND
		// System.out.println("Test AND:");
		// elm ds3 = new elm(0, 20, "sig");
		// double[][] traindata = new double[][] { { 1, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0
		// }, { 1, 1, 1 } };
		// ds3.train(traindata);
		// double[][] inpt = new double[][] { { 5000, 0, 0 }, { 5000, 0, 1 }, { 5000, 1,
		// 0 }, { 5001, 1, 1 } };
		// double[] salida = ds3.testOut(inpt);
		// System.out.println(Arrays.toString(salida));
		//
		// System.out.println();

		// test consumo energia
		System.out.println("Test consumo de energia: (1990 y 1991)");
		elm consumoEnergia = new elm(0, 20, "sig");
		double[][] traindata3 = new double[][] {
				{ 0.08, 0.03, 0.3, 0.03, 0.03, 0.11, 0.11, 0.08, 0.07, 0.61, 0.64, 0.08, 0.17, 0.2, 0.03 }, // 1981
				{ 0.1, 0.04, 0.31, 0.04, 0.04, 0.18, 0.07, 0.08, 0.1, 0.56, 0.62, 0.09, 0.19, 0.32, 0.03 }, // 1982
				{ 0.13, 0.05, 0.31, 0.06, 0.04, 0.24, 0.13, 0.09, 0.14, 0.48, 0.62, 0.09, 0.17, 0.4, 0.05 }, // 1983
				{ 0.16, 0.05, 0.32, 0.07, 0.05, 0.32, 0.13, 0.10, 0.15, 0.41, 0.60, 0.11, 0.18, 0.47, 0.05 }, // 1984
				{ 0.18, 0.07, 0.32, 0.07, 0.06, 0.43, 0.15, 0.11, 0.17, 0.33, 0.49, 0.13, 0.15, 0.59, 0.06 }, // 1985
				{ 0.22, 0.08, 0.33, 0.09, 0.07, 0.48, 0.18, 0.13, 0.18, 0.30, 0.47, 0.14, 0.13, 0.65, 0.07 }, // 1986
				{ 0.24, 0.10, 0.33, 0.1, 0.08, 0.54, 0.19, 0.14, 0.2, 0.27, 0.42, 0.14, 0.14, 0.63, 0.07 }, // 1987
				{ 0.27, 0.12, 0.34, 0.12, 0.09, 0.53, 0.20, 0.16, 0.23, 0.3, 0.4, 0.16, 0.15, 0.6, 0.07 }, // 1988
				{ 0.29, 0.15, 0.34, 0.13, 0.1, 0.61, 0.25, 0.18, 0.23, 0.29, 0.35, 0.18, 0.16, 0.56, 0.15 } }; // 1989
		consumoEnergia.train(traindata3);
		double[][] inpt3 = new double[][] {
				{ 5000, 0.20, 0.35, 0.15, 0.11, 0.76, 0.2, 0.21, 0.22, 0.26, 0.32, 0.21, 0.23, 0.47, 0.18 }, // 1990
				{ 5000, 0.23, 0.35, 0.15, 0.12, 0.76, 0.27, 0.22, 0.25, 0.28, 0.32, 0.23, 0.24, 0.42, 0.19 } }; // 1991
		double[] salida3 = consumoEnergia.testOut(inpt3);
		System.out.println(Arrays.toString(salida3));
		
		System.out.println();

		System.out.println("TrainingTime:" + consumoEnergia.getTrainingTime());
		System.out.println("TrainingAcc:" + consumoEnergia.getTrainingAccuracy());
		System.out.println("TestingTime:" + consumoEnergia.getTestingTime());
		System.out.println("TestAcc:" + consumoEnergia.getTestingAccuracy());
		System.out.println();

	}

}