package ELM;

import no.uib.cipr.matrix.NotConvergedException;

public class main {

	/**
	 * @param args
	 * @throws NotConvergedException
	 */
	public static void main(String[] args) throws NotConvergedException {
		// TODO Auto-generated method stub
		elm ds1 = new elm(0, 20, "sig");
		ds1.train("src\\ELM\\sinc_train");
		ds1.test("src\\ELM\\sinc_test");

		elm ds2 = new elm(1, 20, "sig");
		ds2.train("src\\ELM\\diabetes_train");
		ds2.test("src\\ELM\\diabetes_test");

		elm ds3 = new elm(1, 20, "sig");
		ds3.train("src\\ELM\\AND_train");
		ds3.test("src\\ELM\\AND_test");

		System.out.println("TrainingTime:" + ds1.getTrainingTime());
		System.out.println("TrainingAcc:" + ds1.getTrainingAccuracy());
		System.out.println("TestingTime:" + ds1.getTestingTime());
		System.out.println("TestAcc:" + ds1.getTestingAccuracy());
		System.out.println();

		System.out.println("TrainingTime:" + ds2.getTrainingTime());
		System.out.println("TrainingAcc:" + ds2.getTrainingAccuracy());
		System.out.println("TestingTime:" + ds2.getTestingTime());
		System.out.println("TestAcc:" + ds2.getTestingAccuracy());
		System.out.println();

		System.out.println("TrainingTime:" + ds3.getTrainingTime());
		System.out.println("TrainingAcc:" + ds3.getTrainingAccuracy());
		System.out.println("TestingTime:" + ds3.getTestingTime());
		System.out.println("TestAcc:" + ds3.getTestingAccuracy());
		System.out.println();

	}

}