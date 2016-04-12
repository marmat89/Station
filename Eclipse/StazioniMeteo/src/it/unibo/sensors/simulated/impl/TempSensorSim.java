package it.unibo.sensors.simulated.impl;

import java.util.Random;
import it.unibo.interfaces.SensorSim;
import it.unibo.util.FloatMeasure;

/**
 * Temp Sensor Simulator
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 01:05:58
 *
 */
public class TempSensorSim extends SensorSim {
	public TempSensorSim(String name, String type) {
		super(name, type);

	}

	/**
	 * Each sensor simulated must invoke a method that allows him to get a
	 * plausible value
	 */
	public FloatMeasure getSurvey() {
		takeMeasure();
		return (FloatMeasure) measures.get(measures.size() - 1);
	}

	/**
	 * each sensor uses simulated takeMeasure () to get the random values to be
	 * returned to getSurvey, each sensor requires different formats
	 */
	private void takeMeasure() {
		Random r = new Random();
		float temp = (float) (r.nextGaussian() * 10 + 20);
		FloatMeasure mes = new FloatMeasure(true, temp);
		measures.add(mes);
	}

	public static void main(String[] args) {
		TempSensorSim testSens = new TempSensorSim("DS18B20", "TMP");
		System.out.println("Create new SENSOR name:" + testSens.getName()
				+ " with dataType:" + testSens.getDatatype());
		System.out.println("Test simulated measure:"
				+ (testSens.getSurvey()).getValue());

	}
}
