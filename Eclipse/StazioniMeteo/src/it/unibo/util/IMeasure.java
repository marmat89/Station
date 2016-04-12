package it.unibo.util;

import java.util.Date;

/**
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 05/feb/2015 22:17:40
 *
 */
public interface IMeasure {

	/**
	 * 
	 * @return
	 */
	public String getUOM();

	/**
	 * 
	 * @return
	 */
	public boolean isSimulated();

	/**
	 * 
	 * @return
	 */
	public Date getTime();

	/**
	 * 
	 * @return
	 */
	public Object getValue();

}