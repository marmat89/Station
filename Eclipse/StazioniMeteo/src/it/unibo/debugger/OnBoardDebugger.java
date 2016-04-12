package it.unibo.debugger;

import it.unibo.interfaces.*;
import it.unibo.sensors.impl.*;
import it.unibo.station.impl.*;
import it.unibo.system.Communicator;
import it.unibo.system.DbCom;
import it.unibo.util.Coordinate;

/**
 * 
 * Each sensor can be equipped with a Debugger on-board, on Board debugger is
 * used to send data relating to the errors detected by the sensors. We have two
 * types of errors, we can comunicere on a port that is already occupied,
 * therefore can not be delivered to the robot our demands. Or we could wait too
 * long to receive a response, this indicates that the microcontroller is not
 * working properly.
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 05/feb/2015 23:00:24
 *
 */
public class OnBoardDebugger {

	private StationRPI station;

	/**
	 * OnBoardDebugger Constructor
	 * 
	 * @param station
	 *            need know station where run debugger
	 */
	public OnBoardDebugger(StationRPI station) {
		super();
		this.station = station;
	}

	/**
	 * Used for add error record to DB, we need to know station ID and Sensor
	 * NAME because is PrimaryKey on DB we send an S.D.E. (Sensor Detection
	 * Error) that memorize DATA and DETTAILS of error detection Record is
	 * delivered on DB with DbCom an adHoc DataBase Communication Manager is
	 * important colose connection at the end of operation PROBLEM:Sensor not
	 * detected from embedded machine
	 * 
	 * @param sen
	 *            Sensor where we have detect problem
	 */
	public void SerialReplyProblem(Sensor sen) {
		System.out.println("Sensor:" + sen.getName()
				+ " no replay to embedded machine");
		// OPEN CONNECTION
		DbCom sm = new DbCom();
		String erDesc = "Error Detected form \nStation:" + station.getName()
				+ "\nSensor:" + sen.getName()
				+ "\nError:Sensor no replay to embedded machine";
		// Query
		sm.sendErr(Integer.toString(station.ID), sen.getName(), "SDE", erDesc);
		// CLOSE CONNECTION
		sm.turnOffConnection();
	}

	/**
	 * Used for add error record to DB, we need to know station ID and Sensor
	 * NAME because is PrimaryKey on DB we send an S.C.E. (Serial Connection
	 * Error) that memorize DATA and DETTAILS of error detection Record is
	 * delivered on DB with DbCom an adHoc DataBase Communication Manager is
	 * important colose connection at the end of operation PROBLEM:Serial Port
	 * is Occupated
	 * 
	 * @param sen
	 *            Sensor where we have detect problem
	 */
	public void SerialOccupatedProblem(Sensor sen) {
		// OPEN CONNECTION
		System.out.println("SensorComPort of " + sen.getName()
				+ "  is occupated on embedded machine");
		DbCom sm = new DbCom();
		String erDesc = "Error Detected form \nStation:" + station.getName()
				+ "\nSensor:" + sen.getName()
				+ "\nError:Serial Occupated on embedded machine";
		// Query
		sm.sendErr(Integer.toString(station.ID), sen.getName(), "SCE", erDesc);
		// CLOSE CONNECTION
		sm.turnOffConnection();
	}

	public static void main(String[] args) {
		AirMonitor ar = new AirMonitor("testAirMonitor", new Coordinate(0, 0),
				3);
		OnBoardDebugger SRP = new OnBoardDebugger(ar);
		SRP.SerialReplyProblem(new RainSensorArduino("RAINECO", "RL"));
		SRP.SerialOccupatedProblem(new RainSensorArduino("RAINECO", "RL"));

	}

}
