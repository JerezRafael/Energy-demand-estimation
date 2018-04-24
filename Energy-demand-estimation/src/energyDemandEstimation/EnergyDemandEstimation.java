package energyDemandEstimation;

import energyDemandEstimation.data.*;

import energyDemandEstimation.ELM.elm;
import no.uib.cipr.matrix.NotConvergedException;

public class EnergyDemandEstimation {

	final static int nIterations = 100;

	public static void main(String[] args) throws NotConvergedException {

		Data data = new Data();
		Constructive constructive;
		elm elm;
		Solution sol = null;
		Solution bestSol;
		double bestAccuracy;
		GRASP grasp = new GRASP(data, 100);

		System.out.println("-----Random Constructive-----");
		constructive = new CRandom();

		bestSol = null;
		bestAccuracy = 0;

		for (int i = 0; i < nIterations; i++) {
			sol = constructive.generateSolution();

			// Se prueba la posible solución
			elm = new elm(0, 20, "sig");
			double[][] trainData = data.getTrainData(sol.getSelectedVars());
			elm.train(trainData);

			if (elm.getTrainingAccuracy() > bestAccuracy) { // Si es mejor se guarda
				bestAccuracy = elm.getTrainingAccuracy();
				bestSol = sol;
			}
		}

		System.out.println("La mejor ejecución del train ha tenido una accuracy de " + bestAccuracy);

		elm = new elm(0, 20, "sig");
		elm.train(data.getTrainData(bestSol.getSelectedVars()));
		elm.testOut(data.getTestData(bestSol.getSelectedVars()));

		System.out.println("-----Votos Constructive-----");
		constructive = new CVotos(nIterations);

		bestSol = null;
		bestAccuracy = 0;

		for (int i = 0; i < nIterations; i++) {
			sol = constructive.generateSolution();

			// Se prueba la posible solución
			elm = new elm(0, 20, "sig");
			double[][] trainData = data.getTrainData(sol.getSelectedVars());
			elm.train(trainData);

			if (elm.getTrainingAccuracy() > bestAccuracy) { // Si es mejor se guarda
				bestAccuracy = elm.getTrainingAccuracy();
				bestSol = sol;
			}
		}

		System.out.println("La mejor ejecución del train ha tenido una accuracy de " + bestAccuracy);

		elm = new elm(0, 20, "sig");
		elm.train(data.getTrainData(sol.getSelectedVars()));
		elm.testOut(data.getTestData(sol.getSelectedVars()));

	}

}