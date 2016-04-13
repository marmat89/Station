package it.unibo.system;

import it.unibo.debugger.OnBoardDebugger;
import it.unibo.interfaces.StationRPI;
import it.unibo.sensors.impl.*;
import it.unibo.sensors.simulated.impl.*;
import it.unibo.station.impl.*;
import it.unibo.util.*;

/**
 * the builder is used to define the weather stations on the device, usually a
 * weather station at a time, but we can also define a DEVICE that simulates
 * more than a weather station at a time weather each station needs a simulator
 * that allows the station to make requests to Sensors and does the upload on DB
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 07/feb/2015 02:54:54
 *
 */
public class Builder {
	public AirMonitor testAir;
	public GroundMonitor testGND;
	public WaterMonitor testWTR;

	/**
	 * 
	 * @param type
	 *            there are 3 selection possible, Air => new AirMonitor, Ground
	 *            => new GroundMonitor, Water => new WaterMonitor
	 * @param name
	 *            Station Name
	 * @param coord
	 *            Geolocation of Station
	 * @param key
	 *            DB PrimaryKey
	 */
	public void CreateMonitor(String type, String name, Coordinate coord,
			int key, String communication) {
		StationRPI stat = null;
		switch (type) {
		case "Air":
			testAir = new AirMonitor(name, coord, key);
			stat = testAir;
			addAirSensor();
			break;
		case "Ground":
			testGND = new GroundMonitor(name, coord, key);
			stat = testGND;

			addGroundSensor();
			break;
		case "Water":
			testWTR = new WaterMonitor(name, coord, key);
			stat = testWTR;

			addWaterSensor() ;
			break;
		default:
			System.out.println("Invalid month");
			break;
		}
		StationTH simStat=null;
		switch (communication) {
		case "SQL":
			 simStat = new StationTH(new DbCom());
			break;
		case "NOSQL":
			 simStat = new StationTH(new MongoCom("marmat89","esn_nosql","28mamprenar".toCharArray(),"test"));
			break;
		}
		simStat.ThreadCreation(stat);
	}

	/**
	 * Add Sensor to Air Station
	 */
	public void addAirSensor() {
		OnBoardDebugger deb = new OnBoardDebugger(testAir);
		testAir.addTemperatureSensor(new TempSensorArduino("DS18B20", "TMP","°",
				deb));
		testAir.addHumiditySensor(new HumidSensorArduino("DHT11", "HMD","%", deb));
		testAir.addLightSensor(new LightSensorArduino("LDRGL5528", "LGH","%", deb));
		testAir.addRainSensor(new RainSensorArduino("RAINECO", "RL","%", deb));
		testAir.addSpeedSensor(new SpeedSensorArduino("HC020K", "SPD","km/h", deb));
	}

	/**
	 * Add Sensor To Ground Station
	 */
	public void addGroundSensor() {
		
		OnBoardDebugger deb = new OnBoardDebugger(testGND);
		testGND.addTemperatureSensor(new TempSensorArduino("DS18B20", "TMP","°",
				deb));
		// testGND.addTiltSensor(new TiltSensorSim("TLTECO", "ALL"));
		testGND.addHumiditySensor(new HumidSensorArduino("HGMECO", "HMD","%", deb));
		testGND.addDipSensor(new ShockSensorArduino("TLTECO", "ALL","#", deb));

	}

	/**
	 * Add Sensor To Water Station
	 */
	public void addWaterSensor() {
		
		testWTR.addTemperatureSensor(new TempSensorSim("DS18B20", "TMP"));
		testWTR.addLevelSensor(new LevelSensorSim("WSECO", "WL"));
		testWTR.addSpeedSensor(new SpeedSensorSim("HC020K", "SPD"));
	}

	public static void main(String[] args) throws InterruptedException {
		
		Builder bl = new Builder();
		bl.CreateMonitor("Water", "SimulatedWaterStationCesena", new Coordinate(
				44.139307, 12.237057), 3, "NOSQL");
//		bl.CreateMonitor("Ground", "SimulatedGroundStationCesena",
//				new Coordinate(44.139307, 12.237057), 2);

//		bl.CreateMonitor("Water", "SimulatedWaterStationCesena",
//				new Coordinate(44.139307, 12.237057), 1);

		//
		// Thread.sleep(500);

	}

}
