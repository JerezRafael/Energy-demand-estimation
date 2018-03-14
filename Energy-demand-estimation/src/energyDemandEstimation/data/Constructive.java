package energyDemandEstimation.data;

public interface Constructive {

	public double[][] crearDatosEntrada(double[][] selectedData);
	
	public double[][] crearDatosEntrada(double[][] selectedData, boolean[] variables);

	public boolean[] elegirVariables();
}
