package it.unibo.sensors.impl;

import it.unibo.debugger.OnBoardDebugger;
import it.unibo.interfaces.SensorArduino;
import it.unibo.util.IMeasure;

/**
 * 
 * A hygrometer is an instrument used for measuring the moisture content in the
 * atmosphere. Humidity measurement instruments usually rely on measurements of
 * some other quantity such as temperature, pressure, mass or a mechanical or
 * electrical change in a substance as moisture is absorbed. By calibration and
 * calculation, these measured quantities can lead to a measurement of humidity.
 * Modern electronic devices use temperature of condensation (the dew point), or
 * changes in electrical capacitance or resistance to measure humidity
 * differences. The first practical hygrometer was invented by polymath Johann
 * Heinrich Lambert in 1755.
 * 
 * The DHT11 is chosen because it is lab calibrated, accurate and stable and its
 * signal output is digital. Most important of all, it is relatively inexpensive
 * for the given performance. The DHT11 is a relatively cheap sensor for
 * measuring temperature and humidity. The DHT22 is similar to the DHT11 and has
 * greater accuracy. However, this library is not suitable for the DHT21 or
 * DHT22 as they have a different data format. Check DHTlib for support of these
 * sensors. This library is tested on a MEGA2560 and is confirmed working on an
 * Arduino 2009. Niesteszeck has made an interrupt-driven library for the DHT11
 * sensor. Andy Dalton has made a modified version. Difference is that the
 * DATAPIN is defined in the constructor, resulting in one dedicated object per
 * sensor. The DHT11 has three lines: GND, +5V and a single data line. By means
 * of a handshake, the values are clocked out over the single digital line.
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 00:22:24
 *
 */

public class HumidSensorArduino extends SensorArduino {
	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 */
	public HumidSensorArduino(String name, String type, String UOM) {
		super(name, type, UOM);
	}

	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 * @param deb
	 */
	public HumidSensorArduino(String name, String type,String UOM, OnBoardDebugger deb) {
		super(name, type, UOM, deb);
	}

	/**
	 * All physical sensors must implement getSurvey ()
	 * Depending on the type of data that is required have all the same iter:
	 * OPEN port => SEND request => GET answer
	 * 
	 */
	@Override
	public synchronized IMeasure getSurvey() {

		// arduino serial com setup
		if (initSerialCom(this)) {
			// keyword arduino request
			arduino.sendData("getHumidity=");
			// used for attend sensor respond for N seconds
			attendSerialCom();
			// For Level Sensor we return an int value from 0 to 100
			IMeasure mes=getIntValue();
			mes.setUOM(getUOM());
			return mes; 
		} else
			return null;

	}

	public static void main(String[] args) {
		HumidSensorArduino testSens = new HumidSensorArduino("DHT11", "HMD","%");
		System.out.println("Create new SENSOR name:" + testSens.getName()
				+ " with dataType:" + testSens.getDatatype());
		System.out.println("Arduino measure:"
				+ (testSens.getSurvey()).getValue()+ testSens.getSurvey().getUOM());

	}

}