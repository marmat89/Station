package it.unibo.interfaces;

import it.unibo.util.AssembledList;
import it.unibo.util.Coordinate;

import java.util.List;

/**
 * Represents the Weather Station implemented on Raspberry PI B +, essentially
 * contains methods that allow you to geolocate
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 00:16:53
 *
 */
public abstract class StationRPI implements IStation {
	/**
	 * on DB: idStations statName position
	 */
	public int ID;
	protected String name;
	protected Coordinate position;

	public List<AssembledList> mesList;

	/**
	 * StationRPI Constructor
	 * @param name informal identification of station
	 * @param position geo coordinate of station
	 * @param ID PrimaryKey on DB for identify Station
	 */
	public StationRPI(String name, Coordinate position, int ID) {
		this.name = name;
		this.position = position;
		this.ID = ID;
	}

	// GETTER METHODS
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Coordinate getPosition() {
		return position;
	}

	@Override
	public List<AssembledList> lastUpdate() {
		return mesList;
	}
}
