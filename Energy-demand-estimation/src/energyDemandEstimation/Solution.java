package energyDemandEstimation;

import energyDemandEstimation.data.*;

public class Solution {

	private boolean[] selectedVars;
	private Data data;

	public Solution(boolean[] solucion, Data data) {
		this.data = data;
		this.selectedVars = solucion;
	}

	public boolean[] getSelectedVars() {
		return selectedVars;
	}

}
