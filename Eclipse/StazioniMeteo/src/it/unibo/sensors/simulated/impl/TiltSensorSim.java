package it.unibo.sensors.simulated.impl;

import it.unibo.interfaces.SensorSim;
import it.unibo.util.*;

/**
 * Tilt Sensor Simulator
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 01:06:27
 *
 */
public class TiltSensorSim extends SensorSim {
	public TiltSensorSim(String name, String type) {
		super(name, type);
	}

	/**
	 * Each sensor simulated must invoke a method that allows him to get a
	 * plausible value
	 */
	public BooleanMeasure getSurvey() {
		takeMeasure();
		return (BooleanMeasure) measures.get(measures.size() - 1);
	}

	/**
	 * each sensor uses simulated takeMeasure () to get the random values to be
	 * returned to getSurvey, each sensor requires different formats
	 */
	void takeMeasure() {
		BooleanMeasure mes = new BooleanMeasure(true, false);
		measures.add(mes);
	}

	public static void main(String[] args) {
		TiltSensorSim testSens = new TiltSensorSim("TLTECO", "ALL");
		System.out.println("Create new SENSOR name:" + testSens.getName()
				+ " with dataType:" + testSens.getDatatype());
		System.out.println("Test simulated measure:"
				+ (testSens.getSurvey()).getValue());

	}
}
