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
public class PM1SensorArduino extends SensorArduino {
	
	
	
	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 * @param deb
	 */
	public PM1SensorArduino(String name, String type, String UOM, OnBoardDebugger deb) {
		super(name, type, UOM,deb);
	}

	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 */
	public PM1SensorArduino(String name, String type, String UOM) {
		super(name, type, UOM);			
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
			arduino.sendData("getPM1=");
			// used for attend sensor respond for N seconds
			attendSerialCom();
			IMeasure mes=getFloatValue();
			mes.setUOM(getUOM());
			return mes;
		} else
			return null;
	}

	

	public static void main(String[] args) {
		PM1SensorArduino testSens = new PM1SensorArduino("DSM501A", "PM_","cfp");
		System.out.println("Create new SENSOR name:" + testSens.getName()
				+ " with dataType:" + testSens.getDatatype());
		System.out.println("Test simulated measure:"
				+ (testSens.getSurvey()).getValue());

	}

}
