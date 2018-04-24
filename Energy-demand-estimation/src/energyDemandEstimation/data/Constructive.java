package energyDemandEstimation.data;

import energyDemandEstimation.Solution;
import no.uib.cipr.matrix.NotConvergedException;

public interface Constructive {
	
	public Solution generateSolution() throws NotConvergedException;
}
