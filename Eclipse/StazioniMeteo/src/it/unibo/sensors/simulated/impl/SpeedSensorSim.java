package it.unibo.sensors.simulated.impl;

import java.util.Random;
import it.unibo.interfaces.SensorSim;
import it.unibo.util.*;

/**
 * Speed Sensor Simulator
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 01:03:12
 *
 */
public class SpeedSensorSim extends SensorSim {
	public SpeedSensorSim(String name, String type) {
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
		int temp = (int) (r.nextGaussian() * 10 + 20);
		FloatMeasure mes = new FloatMeasure(true, temp);
		measures.add(mes);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpeedSensorSim testSpeedSen = new SpeedSensorSim("HC020K", "SPD");
		System.out.println("Create new SENSOR name:" + testSpeedSen.getName()
				+ " with output Data Type:" + testSpeedSen.getDatatype());
		System.out.println("Test simulated measure:"
				+ (float) (testSpeedSen.getSurvey()).getValue());

	}
}