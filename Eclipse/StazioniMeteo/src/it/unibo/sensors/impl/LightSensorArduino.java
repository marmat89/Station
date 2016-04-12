package it.unibo.sensors.impl;

import it.unibo.debugger.OnBoardDebugger;
import it.unibo.interfaces.SensorArduino;
import it.unibo.util.IMeasure;

/**
 * LDR GL5528 is photoresistor much diffuse for arduino
 * A photoresistor or light-dependent resistor (LDR) or photocell is a
 * light-controlled variable resistor. The resistance of a photoresistor
 * decreases with increasing incident light intensity; in other words, it
 * exhibits photoconductivity. A photoresistor can be applied in light-sensitive
 * detector circuits, and light- and dark-activated switching circuits.
 * 
 * A photoresistor is made of a high resistance semiconductor. In the dark, a
 * photoresistor can have a resistance as high as a few megaohms (MΩ), while in
 * the light, a photoresistor can have a resistance as low as a few hundred
 * ohms. If incident light on a photoresistor exceeds a certain frequency,
 * photons absorbed by the semiconductor give bound electrons enough energy to
 * jump into the conduction band. The resulting free electrons (and their hole
 * partners) conduct electricity, thereby lowering resistance. The resistance
 * range and sensitivity of a photoresistor can substantially differ among
 * dissimilar devices. Moreover, unique photoresistors may react substantially
 * differently to photons within certain wavelength bands
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 00:41:40
 *
 */
public class LightSensorArduino extends SensorArduino {
	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 * @param deb
	 */
	public LightSensorArduino(String name, String type, String UOM, OnBoardDebugger deb) {
		super(name, type, UOM,deb);
	}
	/**
	 * We must enter reference data on DB,we can have sensors with or without
	 * on-board debugger
	 * 
	 * @param name
	 * @param type
	 */
	public LightSensorArduino(String name, String type,String UOM) {
		super(name, type, UOM);
	}
	/**
	 * All physical sensors must implement getSurvey () Depending on the type of
	 * data that is required have all the same iter: OPEN port => SEND request
	 * => GET answer
	 * 
	 */
	@Override
	public synchronized IMeasure getSurvey() {
		// arduino serial com setup
		if (initSerialCom(this)) {
			// keyword arduino request
			arduino.sendData("getLight=");
			// used for attend sensor respond for N seconds
			attendSerialCom();
			return getIntValue();
		} else
			return null;

	}

	public static void main(String[] args) {
		LightSensorArduino testSens = new LightSensorArduino("LDRGL5528", "LGH","");
		System.out.println("Create new SENSOR name:" + testSens.getName()
				+ " with dataType:" + testSens.getDatatype());
		System.out.println("Test simulated measure:"
				+ (testSens.getSurvey()).getValue());

	}
}
