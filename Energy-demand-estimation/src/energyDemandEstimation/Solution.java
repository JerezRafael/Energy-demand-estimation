package energyDemandEstimation;

public class Solution {

	private boolean[] selectedVars;

	public Solution(boolean[] solucion) {
		this.selectedVars = solucion;
	}

	public boolean[] getSelectedVars() {
		return selectedVars;
	}

}
