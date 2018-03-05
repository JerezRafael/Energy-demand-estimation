package ELM;

import data.Data;
import no.uib.cipr.matrix.NotConvergedException;

public class energyEstimation {

	public static void main(String[] args) throws NotConvergedException {

		double mejorError = 0;
		elm consumoEnergia;
		Data data = new Data();

		double[][] trainData = new double[][] { data.getYear(1981), data.getYear(1982), data.getYear(1985),
				data.getYear(1988), data.getYear(1991), data.getYear(1994), data.getYear(1997), data.getYear(2000),
				data.getYear(2001), data.getYear(2002), data.getYear(2003), data.getYear(2004), data.getYear(2005),
				data.getYear(2006), data.getYear(2007), data.getYear(2008), data.getYear(2009), data.getYear(2010),
				data.getYear(2011) };

		for (int i = 0; i < 100; i++) {
			consumoEnergia = new elm(0, 20, "sig");
			consumoEnergia.train(trainData);

			if (consumoEnergia.getTrainingAccuracy() > mejorError) {
				mejorError = consumoEnergia.getTrainingAccuracy();
			}
		}

		System.out.println("La mejor ejecución ha tenido una accuracy de " + mejorError);

	}
}