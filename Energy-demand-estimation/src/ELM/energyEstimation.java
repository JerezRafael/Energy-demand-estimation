package ELM;

import java.util.Random;

import data.Data;
import no.uib.cipr.matrix.NotConvergedException;

public class energyEstimation {

	public static void main(String[] args) throws NotConvergedException {

		double mejorError = 0;
		elm consumoEnergia = new elm(0, 20, "sig");
		Data data = new Data();

		double[][] selectedData = new double[][] { data.getYear(1981), data.getYear(1982), data.getYear(1985),
				data.getYear(1988), data.getYear(1991), data.getYear(1994), data.getYear(1997), data.getYear(2000),
				data.getYear(2001), data.getYear(2002), data.getYear(2003), data.getYear(2004), data.getYear(2005),
				data.getYear(2006), data.getYear(2007), data.getYear(2008), data.getYear(2009), data.getYear(2010),
				data.getYear(2011) };

		boolean[] variables = elegirVariablesRandom();
		double[][] trainData = crearDatosEntrada(selectedData, variables);

		for (int i = 0; i < 100; i++) {
			consumoEnergia = new elm(0, 20, "sig");
			consumoEnergia.train(trainData);

			if (consumoEnergia.getTrainingAccuracy() > mejorError) {
				mejorError = consumoEnergia.getTrainingAccuracy();
			}
		}

		System.out.println("La mejor ejecución ha tenido una accuracy de " + mejorError);

	}

	// Metodo para elegir variables de manera aleatoria
	private static boolean[] elegirVariablesRandom() {

		boolean[] variables = new boolean[14]; // array que elige las variables aleatoriamente
		for (int i = 0; i < 14; i++) {
			Random random = new Random();
			variables[i] = random.nextBoolean();
		}
		return variables;

	}

	// Metodo para contar las variables elegidas
	public static int contarVariables(boolean[] variables) {

		int numVariables = 0; // contador de variables que vamos a utilizar
		for (int i = 0; i < variables.length; i++) {
			if (variables[i])
				numVariables++;
		}
		return numVariables;

	}

	// Metodo para crear un conjunto de datos de entrada con las variables pasadas
	private static double[][] crearDatosEntrada(double[][] selectedData, boolean[] variables) {

		int numVariables = contarVariables(variables);
		double[][] trainData = new double[selectedData.length][numVariables + 1]; // datos de entrada determinados por
																					// las variables elegidas
		for (int i = 0; i < selectedData.length; i++) {
			trainData[i][0] = 5000;
			int k = 0; // contador para recorrer las variables elegidas
			for (int j = 1; j < trainData[i].length; j++) {
				while (!variables[k]) { // si no es una variable elegida (es false) pasa a la siguiente
					k++;
				}
				if (variables[j]) { // cuando es una variable elegida, la copia
					trainData[i][j] = selectedData[i][k];
				}
				k++;
			}
		}
		return trainData;
	}

}