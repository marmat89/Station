package it.unibo.util;
/**
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since  05/feb/2015 22:16:38
 *
 */
public class Coordinate {

	private double latitude;
	private double longitude;
/**
 * 
 * @param latitude
 * @param longitude
 */
	public Coordinate(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * 
	 * @return
	 */
	public double getLat() {
		return latitude;
	}

	/**
	 * 
	 * @param latitude
	 */
	public void setLat(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * 
	 * @return
	 */
	public double getLon() {
		return longitude;
	}

	/**
	 * 
	 * @param longitude
	 */
	public void setLon(double longitude) {
		this.longitude = longitude;
	}
}
