package energyDemandEstimation;

import energyDemandEstimation.data.*;

import energyDemandEstimation.ELM.elm;
import no.uib.cipr.matrix.NotConvergedException;

public class EnergyDemandEstimation {

	public static void main(String[] args) throws NotConvergedException {

		Data data = new Data();
		Constructive constructive = new CRandom();
		Solution sol = constructive.generateSolution(data);

		elm elm = new elm(0, 20, "sig");
		elm.train(data.getTrainData(sol.getSelectedVars()));
		elm.testOut(data.getTestData(sol.getSelectedVars()));

		System.out.println("Test con un accuracy de " + elm.getTestingAccuracy());

	}

}