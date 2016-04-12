package it.unibo.sensors.simulated.impl;

import java.util.Random;
import it.unibo.interfaces.SensorSim;
import it.unibo.util.IntMeasure;

/**
 * Level Sensor Simulator
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 00:56:15
 *
 */
public class LevelSensorSim extends SensorSim {
	public LevelSensorSim(String name, String type) {
		super(name, type);
	}

	/**
	 * Each sensor simulated must invoke a method that allows him to get a
	 * plausible value
	 */
	public IntMeasure getSurvey() {
		takeMeasure();
		return (IntMeasure) measures.get(measures.size() - 1);
	}

	/**
	 * each sensor uses simulated takeMeasure () to get the random values to be
	 * returned to getSurvey, each sensor requires different formats
	 */
	private void takeMeasure() {
		Random r = new Random();
		int temp = (int) (r.nextGaussian() * 10 + 20);
		IntMeasure mes = new IntMeasure(true, temp);
		measures.add(mes);
	}

	public static void main(String[] args) {
		LevelSensorSim testSens = new LevelSensorSim("WSECO", "WL");
		System.out.println("Create new SENSOR name:" + testSens.getName()
				+ " with dataType:" + testSens.getDatatype());
		System.out.println("Test simulated measure:"
				+ (testSens.getSurvey()).getValue());

	}

}