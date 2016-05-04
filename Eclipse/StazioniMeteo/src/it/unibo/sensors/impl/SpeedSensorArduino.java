package it.unibo.sensors.impl;

import it.unibo.debugger.OnBoardDebugger;
import it.unibo.interfaces.SensorArduino;
import it.unibo.util.IMeasure;

public class SpeedSensorArduino extends SensorArduino {
	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 * @param deb
	 */
	public SpeedSensorArduino(String name, String type, String UOM, OnBoardDebugger deb) {
		super(name, type, UOM,deb);
	}

	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 */
	public SpeedSensorArduino(String name, String type, String UOM) {
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
			arduino.sendData("getSpeed=");
			// used for attend sensor respond for N seconds
			attendSerialCom();

			IMeasure mes= getFloatValue();
			mes.setUOM(getUOM());
			return mes; 
		} else
			return null;
	}

	public static void main(String[] args) {
		SpeedSensorArduino testSens = new SpeedSensorArduino("HC020K", "SPD","kmh");
		System.out.println("Create new SENSOR name:" + testSens.getName()
				+ " with dataType:" + testSens.getDatatype());
		System.out.println("Test simulated measure:"
				+ (testSens.getSurvey()).getValue());
	}
}
