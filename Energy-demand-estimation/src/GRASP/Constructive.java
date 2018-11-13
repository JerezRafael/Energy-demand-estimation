package GRASP;

import energyDemandEstimation.misc.Solution;
import no.uib.cipr.matrix.NotConvergedException;

public interface Constructive {

	public Solution generateSolution() throws NotConvergedException;
}
