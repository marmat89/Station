package it.unibo.system;

import it.unibo.interfaces.StationRPI;
import it.unibo.util.AssembledList;

import java.util.List;

public interface Communicator {

	/**
	 * Send measure to DB need to know Sation and his last Update measure list
	 * read all measure and update them to DB
	 * 
	 * @param stat
	 *            Input station for data
	 * @param lastUpdate
	 *            Last measure received from sensor
	 */
	public abstract void sendMes(StationRPI stat, List<AssembledList> lastUpdate);

	/**
	 * Used for close DB communication
	 */
	public abstract void turnOffConnection();

	public abstract void turnOnConnection();

}