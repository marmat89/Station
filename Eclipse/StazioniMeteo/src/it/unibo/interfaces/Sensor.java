package it.unibo.interfaces;

import java.util.ArrayList;
import java.util.List;
import it.unibo.util.Measure;

/**
 * 
 * 
 * Physical sensor implemented on real hardware, add var for Name, Reliability
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since  05/feb/2015 17:55:29
 *
 */
public abstract class Sensor implements ISensor {

	/**
	 * 
	 * Used to describe a sensor primitive data
	 * 
	 */
	protected String name;
	protected static Boolean reliability;

	 /**
	 * On DB => "Datatype_code"
	 */
	 protected String datatype;

	 
	/**
	 * All measures detected by sensor
	 */
	 protected List<Measure> measures = new ArrayList<Measure>();

		/**
	 * init Sensor with value on the DB
	 * on DB => name:"idSen"
	 * 			Type:"Datatype_code"
	 * 			reliability: true if use a simulated Sensor
	 * 
	 * @param name on DB is primary key of Sensor
	 * @param reliability indicates the reliability of the sensor in terms of quality of the data returned
	 * @param type represents the type of reality measured
	 */
	 public Sensor(String name, Boolean reliability, String type) {
		super();
		this.name = name;
		Sensor.reliability = reliability;
		this.datatype = type;
	}


	/**
	 *  GET: sensor name which is also the primary key on DB
	 *  
	 */
	 public String getName() {
		return name;
	}

	/**
	 *  GET:controls the response of the sensor
	 * @return TRUE if sensor is implementeted on Phisical Hardware, FALSE   if sensor is implementeted on Virtual Hardware
	 */
	 public Boolean getReliability() {
		return reliability;
	}


	/**
	 * GET: data type of sensor which is also the key to the DB
	 * @return String name Type
	 */
	 public String getDatatype() {
		return datatype;
	}

}
