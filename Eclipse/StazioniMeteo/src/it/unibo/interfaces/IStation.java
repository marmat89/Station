package it.unibo.interfaces;

import it.unibo.util.AssembledList;
import it.unibo.util.Coordinate;

import java.util.List;

/**
 * 
 * 
 * A station is a center of detection of data, has the aim to provide data to
 * the database based on sensors installed on board, which interrogates, and
 * that provide data of different nature: temperature, speed, radiation etc.
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 05/feb/2015 17:45:44
 * 
 */
public interface IStation {

	/**
	 * usually refers to the primary key in DB
	 * 
	 * @return Station Name
	 *
	 */
	public String getName();

	/**
	 * geoReference of Station
	 * 
	 * @return
	 */
	public Coordinate getPosition();

	// GET: list of onBoard Sensor
	public List<Sensor> getSensorList();

	// GET: refresh LastUpdates
	public List<AssembledList> monitorUpdates();

	// GET: list of lastUpdate
	public List<AssembledList> lastUpdate();
}