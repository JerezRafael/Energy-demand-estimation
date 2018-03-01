import no.uib.cipr.matrix.NotConvergedException;


public class main {

	/**
	 * @param args
	 * @throws NotConvergedException 
	 */
	public static void main(String[] args) throws NotConvergedException {
		// TODO Auto-generated method stub
		elm ds = new elm(0, 20, "sig");
		ds.train("sinc_train");		
		ds.test("sinc_test");
		
		/*elm ds = new elm(1, 20, "sig");
		ds.train("diabetes_train");
		ds.test("diabetes_test");
		*/
		/*
		elm ds = new elm(1, 20, "sig");
		ds.train("segment_train");
		ds.test("segment_test");
		*/

		System.out.println("TrainingTime:"+ds.getTrainingTime());
		System.out.println("TrainingAcc:"+ds.getTrainingAccuracy());
		System.out.println("TestingTime:"+ds.getTestingTime());
		System.out.println("TestAcc:"+ds.getTestingAccuracy());

	}

}
