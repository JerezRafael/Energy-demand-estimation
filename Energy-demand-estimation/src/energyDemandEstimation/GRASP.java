package energyDemandEstimation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import energyDemandEstimation.ELM.elm;
import energyDemandEstimation.data.*;
import energyDemandEstimation.misc.RandomManager;
import no.uib.cipr.matrix.NotConvergedException;

public class GRASP {

	private Data data;

	public GRASP(Data data) {
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public Solution improve(Solution solution, int[] mostUsedVars) throws NotConvergedException {

		ArrayList<Integer> roullete = new ArrayList<Integer>();

		for (int i = 0; i < mostUsedVars.length; i++) {
			mostUsedVars[i]++;
		}

		int j = 0;

		for (int i = 0; i < mostUsedVars.length; i++) {
			j = 0;
			while (j < mostUsedVars[i]) {
				roullete.add(i);
				j++;
			}
		}

		double bestAccuracy = Double.MAX_VALUE;
		boolean[] selectedVars = new boolean[14];
		boolean[] varsAux;
		int n, var;
		elm elm;
		double[][] trainData;
		final ArrayList<Integer> finalRoullete = (ArrayList<Integer>) roullete.clone();
		boolean removing;

		/* CSV */
		PrintWriter pw;
		String vars;
		try {
			pw = new PrintWriter(new File("var-error-GRASP.csv"));
			StringBuilder sb = new StringBuilder();
			sb.append("Variables");
			sb.append(";");
			sb.append("Accuracy");
			sb.append('\n');

			for (int i = 0; i < 100; i++) {

				roullete = (ArrayList<Integer>) finalRoullete.clone();

				varsAux = new boolean[14];
				n = 1 + RandomManager.getRandom().nextInt(14); // numero de variables que cogeremos

				// si en mostUsedVars hay 5 variables, n deberia poder salir mas de 5?

				j = 0;
				while (j < n) { // escoger nuevas variables

					var = roullete.get(RandomManager.getRandom().nextInt(roullete.size()));
					varsAux[var] = true;
					removing = true;
					while (removing) {
						removing = roullete.remove((Integer) var);
					}

					j++;
				}
				elm = new elm(0, 20, "sig");
				trainData = data.getTrainData(varsAux);
				elm.train(trainData);

				vars = "[";
				for (int k = 0; k < varsAux.length; k++) {
					if (varsAux[k])
						vars = vars + " " + k;
				}
				vars = vars + " ]";
				sb.append(vars);
				sb.append(';');
				sb.append(elm.getTrainingAccuracy());
				sb.append('\n');

				if (elm.getTrainingAccuracy() < bestAccuracy) { // Si es mejor se guarda
					bestAccuracy = elm.getTrainingAccuracy();
					selectedVars = varsAux;
				}

			}

			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/* CSV */

		Solution newSolution = new Solution(selectedVars);

		return newSolution;

	}
}
