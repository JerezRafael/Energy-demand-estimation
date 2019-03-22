package energyDemandEstimation.data;

public class Data {

	private double[][] data;
	private double[][] trainData;
	private double[][] testData;

	public Data() {
		data = new double[][] {
				{ 0.08, 0.03, 0.30, 0.03, 0.03, 0.11, 0.11, 0.08, 0.07, 0.61, 0.64, 0.08, 0.17, 0.20, 0.03 }, // 1981
				{ 0.10, 0.04, 0.31, 0.04, 0.04, 0.18, 0.07, 0.08, 0.10, 0.56, 0.62, 0.09, 0.19, 0.32, 0.03 }, // 1982
				{ 0.13, 0.05, 0.31, 0.06, 0.04, 0.24, 0.13, 0.09, 0.14, 0.48, 0.62, 0.09, 0.17, 0.40, 0.05 }, // 1983
				{ 0.16, 0.05, 0.32, 0.07, 0.05, 0.32, 0.13, 0.10, 0.15, 0.41, 0.60, 0.11, 0.18, 0.47, 0.05 }, // 1984
				{ 0.18, 0.07, 0.32, 0.07, 0.06, 0.43, 0.15, 0.11, 0.17, 0.33, 0.49, 0.13, 0.15, 0.59, 0.06 }, // 1985
				{ 0.22, 0.08, 0.33, 0.09, 0.07, 0.48, 0.18, 0.13, 0.18, 0.30, 0.47, 0.14, 0.13, 0.65, 0.07 }, // 1986
				{ 0.24, 0.10, 0.33, 0.10, 0.08, 0.54, 0.19, 0.14, 0.20, 0.27, 0.42, 0.14, 0.14, 0.63, 0.07 }, // 1987
				{ 0.27, 0.12, 0.34, 0.12, 0.09, 0.53, 0.20, 0.16, 0.23, 0.30, 0.40, 0.16, 0.15, 0.60, 0.07 }, // 1988
				{ 0.29, 0.15, 0.34, 0.13, 0.10, 0.61, 0.25, 0.18, 0.23, 0.29, 0.35, 0.18, 0.16, 0.56, 0.15 }, // 1989
				{ 0.32, 0.20, 0.35, 0.15, 0.11, 0.76, 0.20, 0.21, 0.22, 0.26, 0.32, 0.21, 0.23, 0.47, 0.18 }, // 1990
				{ 0.35, 0.23, 0.35, 0.15, 0.12, 0.76, 0.27, 0.22, 0.25, 0.28, 0.32, 0.23, 0.24, 0.42, 0.19 }, // 1991
				{ 0.36, 0.27, 0.35, 0.16, 0.13, 0.75, 0.29, 0.24, 0.26, 0.32, 0.33, 0.24, 0.27, 0.43, 0.21 }, // 1992
				{ 0.34, 0.29, 0.36, 0.18, 0.15, 0.73, 0.30, 0.24, 0.27, 0.35, 0.35, 0.26, 0.31, 0.52, 0.22 }, // 1993
				{ 0.38, 0.25, 0.36, 0.19, 0.16, 0.70, 0.30, 0.24, 0.30, 0.33, 0.32, 0.25, 0.26, 0.72, 0.23 }, // 1994
				{ 0.41, 0.25, 0.37, 0.21, 0.16, 0.68, 0.31, 0.26, 0.30, 0.39, 0.34, 0.27, 0.29, 0.78, 0.24 }, // 1995
				{ 0.42, 0.28, 0.37, 0.22, 0.17, 0.65, 0.34, 0.27, 0.30, 0.44, 0.36, 0.30, 0.34, 0.73, 0.27 }, // 1996
				{ 0.46, 0.31, 0.38, 0.26, 0.20, 0.69, 0.33, 0.30, 0.34, 0.40, 0.33, 0.32, 0.30, 0.70, 0.30 }, // 1997
				{ 0.51, 0.32, 0.39, 0.34, 0.25, 0.66, 0.27, 0.36, 0.33, 0.46, 0.37, 0.37, 0.37, 0.63, 0.31 }, // 1998
				{ 0.53, 0.35, 0.40, 0.37, 0.29, 0.68, 0.44, 0.37, 0.35, 0.48, 0.36, 0.40, 0.40, 0.54, 0.38 }, // 1999
				{ 0.59, 0.38, 0.41, 0.39, 0.34, 0.62, 0.48, 0.41, 0.35, 0.54, 0.39, 0.46, 0.47, 0.41, 0.43 }, // 2000
				{ 0.64, 0.43, 0.42, 0.49, 0.44, 0.66, 0.47, 0.47, 0.37, 0.55, 0.38, 0.52, 0.53, 0.33, 0.47 }, // 2001
				{ 0.64, 0.47, 0.43, 0.52, 0.45, 0.72, 0.42, 0.51, 0.40, 0.53, 0.37, 0.56, 0.53, 0.18, 0.52 }, // 2002
				{ 0.70, 0.51, 0.45, 0.53, 0.46, 0.66, 0.47, 0.53, 0.44, 0.58, 0.40, 0.60, 0.59, 0.22, 0.55 }, // 2003
				{ 0.74, 0.56, 0.46, 0.56, 0.49, 0.70, 0.60, 0.59, 0.44, 0.58, 0.38, 0.63, 0.62, 0.21, 0.61 }, // 2004
				{ 0.77, 0.61, 0.48, 0.60, 0.57, 0.69, 0.66, 0.66, 0.46, 0.61, 0.40, 0.69, 0.69, 0.20, 0.66 }, // 2005
				{ 0.75, 0.66, 0.50, 0.64, 0.65, 0.61, 0.73, 0.71, 0.50, 0.65, 0.43, 0.74, 0.73, 0.12, 0.70 }, // 2006
				{ 0.78, 0.71, 0.52, 0.72, 0.74, 0.65, 0.70, 0.73, 0.60, 0.63, 0.42, 0.76, 0.70, 0.08, 0.75 }, // 2007
				{ 0.74, 0.79, 0.55, 0.79, 0.82, 0.61, 0.71, 0.75, 0.65, 0.66, 0.43, 0.77, 0.75, 0.08, 0.79 }, // 2008
				{ 0.66, 0.82, 0.57, 0.81, 0.81, 0.61, 0.73, 0.78, 0.73, 0.64, 0.40, 0.78, 0.65, 0.21, 0.81 }, // 2009
				{ 0.68, 0.78, 0.60, 0.67, 0.56, 0.61, 0.67, 0.72, 0.79, 0.60, 0.36, 0.72, 0.52, 0.52, 0.74 }, // 2010
				{ 0.64, 0.78, 0.63, 0.80, 0.67, 0.75, 0.79, 0.74, 0.77, 0.73, 0.29, 0.74, 0.47, 0.61, 0.71 } // 2011
		};
		trainData = new double[][] {
				{ 0.08, 0.03, 0.30, 0.03, 0.03, 0.11, 0.11, 0.08, 0.07, 0.61, 0.64, 0.08, 0.17, 0.20, 0.03 }, // 1981
				{ 0.10, 0.04, 0.31, 0.04, 0.04, 0.18, 0.07, 0.08, 0.10, 0.56, 0.62, 0.09, 0.19, 0.32, 0.03 }, // 1982
				{ 0.18, 0.07, 0.32, 0.07, 0.06, 0.43, 0.15, 0.11, 0.17, 0.33, 0.49, 0.13, 0.15, 0.59, 0.06 }, // 1985
				{ 0.27, 0.12, 0.34, 0.12, 0.09, 0.53, 0.20, 0.16, 0.23, 0.30, 0.40, 0.16, 0.15, 0.60, 0.07 }, // 1988
				{ 0.35, 0.23, 0.35, 0.15, 0.12, 0.76, 0.27, 0.22, 0.25, 0.28, 0.32, 0.23, 0.24, 0.42, 0.19 }, // 1991
				{ 0.38, 0.25, 0.36, 0.19, 0.16, 0.70, 0.30, 0.24, 0.30, 0.33, 0.32, 0.25, 0.26, 0.72, 0.23 }, // 1994
				{ 0.46, 0.31, 0.38, 0.26, 0.20, 0.69, 0.33, 0.30, 0.34, 0.40, 0.33, 0.32, 0.30, 0.70, 0.30 }, // 1997
				{ 0.51, 0.32, 0.39, 0.34, 0.25, 0.66, 0.27, 0.36, 0.33, 0.46, 0.37, 0.37, 0.37, 0.63, 0.31 }, // 1998
				{ 0.59, 0.38, 0.41, 0.39, 0.34, 0.62, 0.48, 0.41, 0.35, 0.54, 0.39, 0.46, 0.47, 0.41, 0.43 }, // 2000
				{ 0.64, 0.47, 0.43, 0.52, 0.45, 0.72, 0.42, 0.51, 0.40, 0.53, 0.37, 0.56, 0.53, 0.18, 0.52 }, // 2002
				{ 0.70, 0.51, 0.45, 0.53, 0.46, 0.66, 0.47, 0.53, 0.44, 0.58, 0.40, 0.60, 0.59, 0.22, 0.55 }, // 2003
				{ 0.77, 0.61, 0.48, 0.60, 0.57, 0.69, 0.66, 0.66, 0.46, 0.61, 0.40, 0.69, 0.69, 0.20, 0.66 }, // 2005
				{ 0.75, 0.66, 0.50, 0.64, 0.65, 0.61, 0.73, 0.71, 0.50, 0.65, 0.43, 0.74, 0.73, 0.12, 0.70 }, // 2006
				{ 0.74, 0.79, 0.55, 0.79, 0.82, 0.61, 0.71, 0.75, 0.65, 0.66, 0.43, 0.77, 0.75, 0.08, 0.79 }, // 2008
				{ 0.68, 0.78, 0.60, 0.67, 0.56, 0.61, 0.67, 0.72, 0.79, 0.60, 0.36, 0.72, 0.52, 0.52, 0.74 }, // 2010
		};
		testData = new double[][] {
				{ 0.13, 0.05, 0.31, 0.06, 0.04, 0.24, 0.13, 0.09, 0.14, 0.48, 0.62, 0.09, 0.17, 0.40, 0.05 }, // 1983
				{ 0.16, 0.05, 0.32, 0.07, 0.05, 0.32, 0.13, 0.10, 0.15, 0.41, 0.60, 0.11, 0.18, 0.47, 0.05 }, // 1984
				{ 0.22, 0.08, 0.33, 0.09, 0.07, 0.48, 0.18, 0.13, 0.18, 0.30, 0.47, 0.14, 0.13, 0.65, 0.07 }, // 1986
				{ 0.24, 0.10, 0.33, 0.10, 0.08, 0.54, 0.19, 0.14, 0.20, 0.27, 0.42, 0.14, 0.14, 0.63, 0.07 }, // 1987
				{ 0.29, 0.15, 0.34, 0.13, 0.10, 0.61, 0.25, 0.18, 0.23, 0.29, 0.35, 0.18, 0.16, 0.56, 0.15 }, // 1989
				{ 0.32, 0.20, 0.35, 0.15, 0.11, 0.76, 0.20, 0.21, 0.22, 0.26, 0.32, 0.21, 0.23, 0.47, 0.18 }, // 1990
				{ 0.36, 0.27, 0.35, 0.16, 0.13, 0.75, 0.29, 0.24, 0.26, 0.32, 0.33, 0.24, 0.27, 0.43, 0.21 }, // 1992
				{ 0.34, 0.29, 0.36, 0.18, 0.15, 0.73, 0.30, 0.24, 0.27, 0.35, 0.35, 0.26, 0.31, 0.52, 0.22 }, // 1993
				{ 0.41, 0.25, 0.37, 0.21, 0.16, 0.68, 0.31, 0.26, 0.30, 0.39, 0.34, 0.27, 0.29, 0.78, 0.24 }, // 1995
				{ 0.42, 0.28, 0.37, 0.22, 0.17, 0.65, 0.34, 0.27, 0.30, 0.44, 0.36, 0.30, 0.34, 0.73, 0.27 }, // 1996
				{ 0.53, 0.35, 0.40, 0.37, 0.29, 0.68, 0.44, 0.37, 0.35, 0.48, 0.36, 0.40, 0.40, 0.54, 0.38 }, // 1999
				{ 0.64, 0.43, 0.42, 0.49, 0.44, 0.66, 0.47, 0.47, 0.37, 0.55, 0.38, 0.52, 0.53, 0.33, 0.47 }, // 2001
				{ 0.74, 0.56, 0.46, 0.56, 0.49, 0.70, 0.60, 0.59, 0.44, 0.58, 0.38, 0.63, 0.62, 0.21, 0.61 }, // 2004
				{ 0.78, 0.71, 0.52, 0.72, 0.74, 0.65, 0.70, 0.73, 0.60, 0.63, 0.42, 0.76, 0.70, 0.08, 0.75 }, // 2007
				{ 0.66, 0.82, 0.57, 0.81, 0.81, 0.61, 0.73, 0.78, 0.73, 0.64, 0.40, 0.78, 0.65, 0.21, 0.81 }, // 2009
				{ 0.64, 0.78, 0.63, 0.80, 0.67, 0.75, 0.79, 0.74, 0.77, 0.73, 0.29, 0.74, 0.47, 0.61, 0.71 }, // 2011
		};
	}

	public double[] getYear(int year, boolean[] selectedVars) {
		double[] yearData = new double[contarVariables(selectedVars) + 1];
		yearData[0] = data[year % 1981][0];
		int j = 1;
		for (int i = 0; i < selectedVars.length; i++) {
			if (selectedVars[i]) { // cuando es una variable elegida, la copia y pasamos de posicion
				yearData[j] = data[year % 1981][j];
				j++;
			}
		}
		return yearData;
	}

	public double[][] getTrainData(boolean[] selectedVars) {

		double[][] trainDataSet = new double[trainData.length][contarVariables(selectedVars) + 1];
		for (int i = 0; i < trainDataSet.length; i++) {
			trainDataSet[i][0] = trainData[i][0];
			int k = 1;
			for (int j = 0; j < selectedVars.length; j++) {
				if (selectedVars[j]) { // cuando es una variable elegida, la copia y pasamos de posicion
					trainDataSet[i][k] = trainData[i][k];
					k++;
				}
			}
		}
		return trainDataSet;
	}

	public double[][] getTestData(boolean[] selectedVars) {

		double[][] testDataSet = new double[testData.length][contarVariables(selectedVars) + 1];
		for (int i = 0; i < testDataSet.length; i++) {
			testDataSet[i][0] = testData[i][0];
			int k = 1;
			for (int j = 0; j < selectedVars.length; j++) {
				if (selectedVars[j]) { // cuando es una variable elegida, la copia y pasamos de posicion
					testDataSet[i][k] = testData[i][k];
					k++;
				}
			}
		}
		return testDataSet;
	}

	private int contarVariables(boolean[] selectedVars) {

		int numVariables = 0; // contador de variables que vamos a utilizar
		for (int i = 0; i < selectedVars.length; i++) {
			if (selectedVars[i])
				numVariables++;
		}
		return numVariables;
	}
}
