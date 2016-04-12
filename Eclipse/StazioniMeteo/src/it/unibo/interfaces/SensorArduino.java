package it.unibo.interfaces;

import it.unibo.debugger.OnBoardDebugger;
import it.unibo.util.AttendRispThread;
import it.unibo.util.FloatMeasure;
import it.unibo.util.IntMeasure;
import it.unibo.util.SerialCom;

import java.util.Observable;
import java.util.Observer;

/**
 * Phisical sensor implemented on Arduino. The sensor implemented on the Arduino
 * uses serial comport to request data to microcontroller. Arudino interprets
 * commands and responds with the requested data. Using calls to the serial:
 * getHumidity= getTemp= getSpeed= getLight= getLevel=
 *
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 05/feb/2015 18:25:08
 *
 */
public abstract class SensorArduino extends Sensor implements Observer {

	/**
	 * for communication with arduino need value return and serial com driver
	 * serial com is not real time communication, arduino need time to elaborate
	 * request so need Thread that attend replay, and debugger for problem
	 */
	public String value;
	protected SerialCom arduino;
	private AttendRispThread attendRisp;
	private String UOM;
	public String getUOM() {
		return UOM;
	}

	public OnBoardDebugger deb;

	/**
	 * sensor without debugger
	 * 
	 * @param name
	 *            Code code assigned to all sensors Arduino
	 * @param type
	 *            Data type of replay
	 */
	public SensorArduino(String name, String type, String UOM) {
		super(type, reliability, type);
		this.name = name;
		this.reliability = true;
		this.datatype = type;
		this.UOM=UOM;
	}

	/**
	 * sensor with on-board debugger
	 * 
	 * @param name
	 *            Code code assigned to all sensors Arduino
	 * @param type
	 *            Data type of replay
	 * @param deb
	 *            Debugger component used for check problem with serial com
	 */
	public SensorArduino(String name, String type, String UOM, OnBoardDebugger deb) {
		super(type, reliability, type);
		this.name = name;
		this.reliability = true;
		this.datatype = type;
		this.deb = deb;
		this.UOM=UOM;
	}

	/**
	 * Used for initialization of COM port
	 * 
	 * @param sens
	 *            used for observe reply sensor
	 * @return TRUE if the initialization of the COM port is successful
	 */
	protected boolean initSerialCom(SensorArduino sens) {
		arduino = new SerialCom();
		if (!arduino.initialize()) {
			// no all sensor have debugger on board
			if (deb != null) {
				deb.SerialOccupatedProblem(this);
			}
			return false;
		} else {
			// used for attend COM reply
			// add observer for return variable
			// send data to Arduino port ready
			value = "";
			arduino.addObserver(sens);
			return true;
		}
	}

	/**
	 * For attend arduino replay on serial port need to launches a thread that
	 * allows to listen serial port for predefined period of time
	 */
	protected void attendSerialCom() {
		System.out.println("====Arduino attend reply====");
		attendRisp = new AttendRispThread();
		attendRisp.ThreadCreation(deb,this);
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		arduino.close();
	}

	/**
	 * Arduino's sensors can respond with data in different format intValue is
	 * mainly used for data rates, at the and of getting process we need parse
	 * value on correct format
	 * 
	 * @return IntMeasure represents the measurement through an integer value
	 */
	protected IntMeasure getIntValue() {
		if (value != "") {
			// add measure to return value and on on-board memory of sensor
			IntMeasure mes = new IntMeasure(false, Integer.parseInt(value));
			measures.add(mes);
			return mes;
		} else {
			// no all sensor i able to communicate data
			System.out.println("Sensor does not respond");
			return null;
		}
	}

	/**
	 * Arduino's sensors can respond with data in different format FloatMeasure
	 * is mainly used for real measure like Temperature or Speed, at the and of
	 * getting process we need parse value on correct format
	 * 
	 * @return FloatMeasure represents the measurement through an Float value
	 */
	protected FloatMeasure getFloatValue() {

		if (value != "") {
			// add measure to return value and on on-board memory of sensor
			FloatMeasure mes = new FloatMeasure(false, Float.parseFloat(value));
			measures.add(mes);
			return mes;
		} else {
			// no all sensor i able to communicate data
			System.out.println("Sensor does not respond");
			return null;
		}
	}

	/**
	 * For wait the response we observe updating a variable
	 */
	@Override
	public synchronized void update(Observable o, Object arg) {
		value = ((SerialCom) o).getSomeVariable();
		System.out.println("Arduino received = " + value);
		this.notifyAll();
	}

	/**
	 * Arduino sensor is not able to update State
	 */
	@Override
	public String getState() {
		return null;
	}

}
