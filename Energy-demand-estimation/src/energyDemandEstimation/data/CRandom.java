package energyDemandEstimation.data;

import energyDemandEstimation.misc.RandomManager;

public class CRandom implements Constructive {

	@Override
	public double[][][] crearDatosEntrada(Data data) {

		boolean[] variables = elegirVariables();
		
		double[][][] returnData = new double[2][][];
		
		double[][] allTrainData = data.getTrainData();
		returnData[0] = new double[allTrainData.length][contarVariables(variables) + 1];
		for (int i = 0; i < returnData[0].length; i++) {
			returnData[0][i][0] = allTrainData[i][0];
			int k = 1;
			for (int j = 0; j < variables.length; j++) {
				if (variables[j]) { // cuando es una variable elegida, la copia
					returnData[0][i][k] = allTrainData[i][k];
					k++;
				}
			}
		}

		double[][] allTestData = data.getTestData();
		returnData[1] = new double[allTestData.length][contarVariables(variables) + 1];
		for (int i = 0; i < returnData[1].length; i++) {
			returnData[1][i][0] = allTestData[i][0];
			int k = 1;
			for (int j = 0; j < variables.length - 1; j++) {
				if (variables[j]) { // cuando es una variable elegida, la copia
					returnData[1][i][k] = allTestData[i][k];
					k++;
				}
			}
		}
		return returnData;
	}

	private boolean[] elegirVariables() {

		boolean[] variables = new boolean[14]; // array que elige las variables aleatoriamente
		for (int i = 0; i < 14; i++) {
			variables[i] = RandomManager.getRandom().nextBoolean();
		}
		return variables;
	}

	private int contarVariables(boolean[] variables) {

		int numVariables = 0; // contador de variables que vamos a utilizar
		for (int i = 0; i < variables.length; i++) {
			if (variables[i])
				numVariables++;
		}
		return numVariables;
	}

}
