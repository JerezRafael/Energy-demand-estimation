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
		System.out.println("\nTest AND:");
		elm ds3 = new elm(0, 20, "sig");
		double[][] traindata = new double[][] { { 1, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0 }, { 1, 1, 1 } };
		ds3.train(traindata);
		double[][] inpt = new double[][] { { 5000, 0, 0 }, { 5000, 0, 1 }, { 5000, 1, 0 }, { 5001, 1, 1 } };
		double[] salida = ds3.testOut(inpt);
		System.out.println(Arrays.toString(salida));

		// test AND + OR ((x AND y) OR z)
		System.out.println("\nTest AND+OR:");
		elm ds4 = new elm(0, 20, "sig");
		double[][] traindata2 = new double[][] { { 1, 0, 0, 0 }, { 1, 0, 0, 1 }, { 0, 0, 1, 0 }, { 1, 0, 1, 1 },
				{ 0, 1, 0, 0 }, { 1, 1, 0, 1 }, { 1, 1, 1, 0 }, { 1, 1, 1, 1 } };
		ds4.train(traindata2);
		double[][] inpt2 = new double[][] { { 5000, 0, 0, 0 }, { 5000, 0, 0, 1 }, { 5000, 0, 1, 0 }, { 5000, 0, 1, 1 },
				{ 5000, 1, 0, 0 }, { 5000, 1, 0, 1 }, { 5000, 1, 1, 0 }, { 5000, 1, 1, 1 } };
		double[] salida2 = ds4.testOut(inpt2);
		System.out.println(Arrays.toString(salida2));

		System.out.println("TrainingTime:" + ds3.getTrainingTime());
		System.out.println("TrainingAcc:" + ds3.getTrainingAccuracy());
		System.out.println("TestingTime:" + ds3.getTestingTime());
		System.out.println("TestAcc:" + ds3.getTestingAccuracy());
		System.out.println();

	}

}