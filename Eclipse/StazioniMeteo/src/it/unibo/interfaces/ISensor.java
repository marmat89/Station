package it.unibo.interfaces;

import it.unibo.util.IMeasure;

/**
 * A sensor is a device that detects events or changes in quantities and
 * provides a corresponding output. ISensor is interface that represents a
 * sensor, the three characteristics of each sensor are the name, state, and
 * measurement. each sensor must be able to provide these features,
 * identification, working state, detection
 *
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 05/feb/2015 17:54:53
 *
 */
public interface ISensor {

	/**
	 * utility used to retrieve the name of the sensor, very useful to define an
	 * ID that links the sensor to a DB
	 *
	 * @return Sensor Name
	 */
	public String getName();

	/**
	 * 
	 * control used to manage malfunctions
	 * 
	 * @return Debug method used for check sensor working status
	 */
	public String getState();

	/**
	 * 
	 * base: allows to know a measurement of a given sensor
	 * 
	 * @return method used for interrogates sensor to obtain a measurement
	 */
	public IMeasure getSurvey();

}
