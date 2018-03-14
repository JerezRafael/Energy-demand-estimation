package energyDemandEstimation.data;

public class CVotos implements Constructive {

	@Override
	public double[][] crearDatosEntrada(double[][] selectedData) {

		boolean[] variables = elegirVariables();
		int numVariables = contarVariables(variables);
		double[][] returnData = new double[selectedData.length][numVariables + 1]; // datos de entrada determinados por
																					// las variables elegidas
		for (int i = 0; i < selectedData.length; i++) {
			returnData[i][0] = selectedData[i][0];
			for (int j = 0; j < returnData[i].length - 1; j++) {
				if (variables[j]) // cuando es una variable elegida, la copia
					returnData[i][j + 1] = selectedData[i][j + 1];
			}
		}
		return returnData;
	}

	@Override
	public double[][] crearDatosEntrada(double[][] selectedData, boolean[] variables) {

		int numVariables = contarVariables(variables);
		double[][] returnData = new double[selectedData.length][numVariables + 1]; // datos de entrada determinados por
																					// las variables elegidas
		for (int i = 0; i < selectedData.length; i++) {
			returnData[i][0] = selectedData[i][0];
			for (int j = 0; j < returnData[i].length - 1; j++) {
				if (variables[j]) // cuando es una variable elegida, la copia
					returnData[i][j + 1] = selectedData[i][j + 1];
			}
		}
		return returnData;
	}

	@Override
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
