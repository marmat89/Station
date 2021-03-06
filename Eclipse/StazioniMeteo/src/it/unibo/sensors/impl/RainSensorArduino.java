package it.unibo.sensors.impl;

import java.util.Observable;
import java.util.Observer;

import it.unibo.debugger.OnBoardDebugger;
import it.unibo.interfaces.SensorArduino;
import it.unibo.util.IMeasure;
import it.unibo.util.SerialCom;

/**
 * Water presence sensor to effect conductive - This sensor uses an electronic
 * circuit which measures the conductivity of the liquid around the electrodes.
 * This system is much more reliable than the float system but with slightly
 * higher costs due to the presence of the electronic circuit.
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 00:50:20
 *
 */
public class RainSensorArduino extends SensorArduino implements Observer {
	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 * @param deb
	 */
	public RainSensorArduino(String name, String type, String UOM, OnBoardDebugger deb) {
		super(name, type, UOM,deb);
		// TODO Auto-generated constructor stub
	}
	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 */
	public RainSensorArduino(String name, String type, String UOM) {
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
			arduino.sendData("getRain=");
			// used for attend sensor respond for N seconds
			attendSerialCom();
			IMeasure mes=getIntValue();
			mes.setUOM(getUOM());
			return mes; 
		} else
			return null;

	}

	@Override
	public synchronized void update(Observable o, Object arg) {
		value = ((SerialCom) o).getSomeVariable();
		System.out.println("Arduino received = " + value);
		this.notifyAll();
	}

	public static void main(String[] args) {
		RainSensorArduino testSens = new RainSensorArduino("RAINECO", "RL","");
		System.out.println("Create new SENSOR name:" + testSens.getName()
				+ " with dataType:" + testSens.getDatatype());
		System.out.println("Test simulated measure:"
				+ (testSens.getSurvey()).getValue());
	}

}