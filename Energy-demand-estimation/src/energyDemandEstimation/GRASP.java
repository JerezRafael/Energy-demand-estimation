package energyDemandEstimation;

import energyDemandEstimation.data.*;

public class GRASP {

	private int splitter;
	private Data data;

	public GRASP(Data data, int splitter) {
		this.data = data;
		this.splitter = splitter;
	}

	public Solution improve(Solution solution) {

		return solution;
	}
}
