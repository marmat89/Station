package it.unibo.interfaces;

/**
 * sensor simulated for testing prototype
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 05/feb/2015 19:01:18
 *
 */
public abstract class SensorSim extends Sensor {
	/**
	 * 
	 * sensor simulated for testing prototype
	 * 
	 * @param name
	 *            Sensors name
	 * @param type
	 *            data type of replay
	 * @param reliability
	 *            FALSE by default because sensor sim don't send reliable data
	 */
	public SensorSim(String name, String type) {
		super(type, reliability, type);
		this.name = name;
		this.reliability = false;
		this.datatype = type;
	}
	@Override
	public String getState() {
		return null;
	}
}
