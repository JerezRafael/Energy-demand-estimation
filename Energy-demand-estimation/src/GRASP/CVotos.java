package GRASP;

import energyDemandEstimation.misc.RandomManager;
import energyDemandEstimation.misc.Solution;
import no.uib.cipr.matrix.NotConvergedException;

public class CVotos implements Constructive {

	final int nIterations = 100;
	public int[] wheel;

	public CVotos(int nIterations) {
		wheel = new int[14]; // se inicializa la ruleta con el numero de variables y cada una a 100
		for (int j = 0; j < 14; j++) {
			wheel[j] = nIterations;
		}
	}

	@Override
	public Solution generateSolution() throws NotConvergedException {

		boolean[] selectedVars = new boolean[14];
		Solution bestSol = null;
		int nVars, random, total, spin;
		boolean[] selectedAlready;
		selectedAlready = new boolean[14];

		nVars = RandomManager.getRandom().nextInt(14) + 1; // numero de variables que se van a elegir esta iteracion

		for (int j = 0; j < nVars; j++) { // eligiendo variables por la ruleta

			total = 0;
			for (int k = 0; k < wheel.length; k++) { // cogiendo el numero total de la ruleta
				total += wheel[k];
			}

			random = RandomManager.getRandom().nextInt(total);
			spin = 0;
			for (int k = 0; k < wheel.length; k++) {
				spin += wheel[k];
				if (random < spin & !selectedAlready[k]) {
					selectedVars[k] = true;
					selectedAlready[k] = true;
					wheel[k]--;
					break;
					// es correcto poner un break? o mejor poner
					// selectedVars[k] = random > wheel[k] & random < wheel[k + 1]
				}
			}
		}
		bestSol = new Solution(selectedVars);
		return bestSol;
	}

}