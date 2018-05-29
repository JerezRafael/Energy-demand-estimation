package energyDemandEstimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import energyDemandEstimation.ELM.elm;
import energyDemandEstimation.data.*;
import energyDemandEstimation.misc.RandomManager;
import no.uib.cipr.matrix.NotConvergedException;

public class GRASP {

	private int splitter;
	private Data data;

	public GRASP(Data data, int splitter) {
		this.data = data;
		this.splitter = splitter;
	}

	public Solution improve(Solution solution) throws NotConvergedException {

		double[][] testYears = data.getTestData(solution.getSelectedVars());

		// cojo un año al azar, debería hacer todo una vez por cada año?

		final double[] sample = testYears[RandomManager.getRandom().nextInt(testYears.length)];
		final double objetive = sample[sample.length - 1];
		double[] probe = new double[splitter + 1];
		for (int i = 0; i < probe.length; i++) {
			probe[i] = 1 / splitter; // No funciona
			probe[i] = probe[i] * i;
			// probe[i] = 1 / splitter * i;
		}

		List<Double> variables = new ArrayList<>();
		for (double e : sample) {
			variables.add(e);
		}

		elm elm = new elm(0, 20, "sig");
		double[][] trainData = data.getTrainData(solution.getSelectedVars());
		elm.train(trainData);

		double error;
		double bestError = 999;
		double improve = 1;

		while (improve > 0) { // bucle hasta que no mejore de ninguna manera
			Collections.shuffle(variables);
			for (int i = 0; i < variables.size() - 1; i++) { // bucle para recorrer cada dimension
				for (int j = 0; j < probe.length; j++) { // bucle para recorrer cada posicion del splitter
					List<Double> aux = variables;
					aux.set(i, probe[j]);

					// probar si ha mejorado con n;
					double[] output = elm.testOut(sample);
					error = objetive - output[0];
					improve = bestError - error;

					if (improve > 0) {
						bestError = error;
						break;
					}
				}
				if (improve > 0)
					break;
			}
		}

		return solution; // que devuelvo?
	}
}
