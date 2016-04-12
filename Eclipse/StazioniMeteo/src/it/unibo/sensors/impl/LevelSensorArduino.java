package it.unibo.sensors.impl;

import it.unibo.debugger.OnBoardDebugger;
import it.unibo.interfaces.SensorArduino;
import it.unibo.util.IMeasure;

/**
 * Water presence sensor to effect conductive - This sensor uses an electronic
 * circuit which measures the conductivity of the liquid around the electrodes.
 * This system is much more reliable than the float system but with slightly
 * higher costs due to the presence of the electronic circuit.
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 00:36:36
 *
 */
public class LevelSensorArduino extends SensorArduino {
	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 * @param deb
	 */
	public LevelSensorArduino(String name, String type, OnBoardDebugger deb) {
		super(name, type, deb);
	}

	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 */
	public LevelSensorArduino(String name, String type) {
		super(name, type);
	}

	/**
	 * All physical sensors must implement getSurvey () Depending on the type of
	 * data that is required have all the same iter: OPEN port => SEND request
	 * => GET answer
	 */
	@Override
	public synchronized IMeasure getSurvey() {
		// arduino serial com setup
		if (initSerialCom(this)) {
			// keyword arduino request
			arduino.sendData("getLevel=");
			// used for attend sensor respond for N seconds
			attendSerialCom();
			return getIntValue();
		} else
			return null;

	}

	public static void main(String[] args) {
		LevelSensorArduino testSens = new LevelSensorArduino("WSECO", "WL");
		System.out.println("Create new SENSOR name:" + testSens.getName()
				+ " with dataType:" + testSens.getDatatype());
		System.out.println("Test simulated measure:"
				+ (testSens.getSurvey()).getValue());

	}

}