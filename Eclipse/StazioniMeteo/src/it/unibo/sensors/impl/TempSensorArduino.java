package it.unibo.sensors.impl;

import it.unibo.debugger.OnBoardDebugger;
import it.unibo.interfaces.SensorArduino;
import it.unibo.util.IMeasure;

/**
 * DS18B20 is best choice for thermal sensor This is an electronic thermometer
 * which has high accuracy over a wide range (accurate to �0.5�C over the range
 * of -10�C to +85�C) (Workable from -55�C to +125�C). You can locate these
 * thermometer chips up to 100M away from your Arduino. Shorter cables can be
 * just 2 wires. NOTE: There must be a pullup resistor of about 5K in all cases,
 * but the Brick versions have this included.
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 00:55:13
 *
 */
public class TempSensorArduino extends SensorArduino {
	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 * @param deb
	 */
	public TempSensorArduino(String name, String type, OnBoardDebugger deb) {
		super(name, type, deb);
	}

	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 */
	public TempSensorArduino(String name, String type) {
		super(name, type);

	}

	/**
	 * All physical sensors must implement getSurvey () Depending on the type of
	 * data that is required have all the same iter: OPEN port => SEND request
	 * => GET answer
	 * 
	 */
	@Override
	public synchronized IMeasure getSurvey() {
		// arduino serial com setup
		if (initSerialCom(this)) {
			// keyword arduino request
			arduino.sendData("getTemp=");
			// used for attend sensor respond for N seconds
			attendSerialCom();
			return getFloatValue();
		} else
			return null;
	}

	

	public static void main(String[] args) {
		TempSensorArduino testSens = new TempSensorArduino("DHT11", "HMD");
		System.out.println("Create new SENSOR name:" + testSens.getName()
				+ " with dataType:" + testSens.getDatatype());
		System.out.println("Test simulated measure:"
				+ (testSens.getSurvey()).getValue());

	}

}
