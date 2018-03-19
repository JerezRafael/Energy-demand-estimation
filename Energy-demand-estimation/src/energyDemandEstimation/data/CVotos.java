package energyDemandEstimation.data;

public class CVotos implements Constructive {

	@Override
	public double[][][] crearDatosEntrada(Data data) {

		boolean[] variables = elegirVariables();
		int numVariables = contarVariables(variables);
		double[][][] returnData = new double[2][][]; // datos de entrada determinados por las variables elegidas

		returnData[0] = new double[data.getTrainData().length][numVariables + 1];
		for (int i = 0; i < returnData[0].length; i++) {
			returnData[0][i][0] = returnData[0][i][0];
			for (int j = 0; j < returnData[i].length - 1; j++) {
				if (variables[j]) // cuando es una variable elegida, la copia
					returnData[0][i][j + 1] = returnData[0][i][j + 1];
			}
		}
		
		returnData[1] = new double[data.getTestData().length][numVariables + 1];
		for (int i = 0; i < returnData[1].length; i++) {
			returnData[1][i][0] = returnData[1][i][0];
			for (int j = 0; j < returnData[i].length - 1; j++) {
				if (variables[j]) // cuando es una variable elegida, la copia
					returnData[1][i][j + 1] = returnData[1][i][j + 1];
			}
		}
		return returnData;
	}

	public boolean[] elegirVariables() {
		// TODO Auto-generated method stub
		return null;
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
