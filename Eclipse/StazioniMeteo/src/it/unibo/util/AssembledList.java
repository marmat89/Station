package it.unibo.util;

import it.unibo.interfaces.IStation;
import it.unibo.interfaces.Sensor;

/**
 * 
 * @author matteo.mariani11@studio.unibo.it utility
 * 
 *  class used to represent associations between sensor and measures
 *  is used for listing this association
 * @version 1.0.0
 * @since 05/feb/2015 21:20:10
 *
 */
public class AssembledList {

	public IMeasure lastMes;
	public Sensor sen;

	/**
	 * AssembledList Constructor
	 * 
	 * @param sen
	 *            Sensor
	 * @param lastMes
	 *            Measure
	 */
	public AssembledList(Sensor sen, IMeasure lastMes) {
		this.lastMes = lastMes;
		this.sen = sen;

	}
}
