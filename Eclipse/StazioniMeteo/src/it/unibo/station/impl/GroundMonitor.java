package it.unibo.station.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.interfaces.StationRPI;
import it.unibo.interfaces.Sensor;
import it.unibo.sensors.impl.ShockSensorArduino;
import it.unibo.sensors.simulated.impl.TempSensorSim;
import it.unibo.sensors.simulated.impl.TiltSensorSim;
import it.unibo.sensors.simulated.impl.HumidSensorSim;
import it.unibo.util.*;

public class GroundMonitor extends StationRPI {

	public GroundMonitor(String name, Coordinate position, int ID) {
		super(name, position, ID);
		System.out.println("BUILD:AirMonitor=>" + this.getName() + " | "
				+ this.getPosition().getLat() + " , "
				+ this.getPosition().getLat());
		mesList = new ArrayList<AssembledList>();
	}

	private Sensor temperature;
	private Sensor humidity;
	private Sensor tilt;
	private Sensor dip;
	private IMeasure lastMes;

	public List<Sensor> getSensorList() {
		List<Sensor> sensors = new ArrayList<Sensor>();
		sensors.add(temperature);
		sensors.add(humidity);
		sensors.add(tilt);
		sensors.add(dip);
		return sensors;
	}

	public void addTemperatureSensor(Sensor temp) {
		temperature = temp;
		System.out.println("addTemperatureSensor=>" + temp.getName());
	}

	public void addHumiditySensor(Sensor hum) {
		humidity = hum;
		System.out.println("addHumiditySensor=>" + hum.getName());

	}

	public void addTiltSensor(Sensor tilt) {
		this.tilt = tilt;
		System.out.println("addSpeedSensor=>" + tilt.getName());

	}

	public void addDipSensor(Sensor dip) {
		this.dip = dip;
		System.out.println("addDipSensor=>" + dip.getName());

	}

	public List<AssembledList> monitorUpdates() {

		mesList.clear();
		List<Sensor> sensors = this.getSensorList();
		Iterator itSen = sensors.iterator();
		while (itSen.hasNext()) {
			Sensor sen = (Sensor) itSen.next();
			if (sen != null) {
				lastMes = sen.getSurvey();
				if (lastMes != null) {
					mesList.add(new AssembledList(sen, lastMes));
					System.out.println("UPDATE | " + sen.getName() + " => "
							+ lastMes.getValue() + "" + lastMes.getUOM());
				} else {
					System.err.println("ALLERT | no " + sen.getName()
							+ "monitor find");
				}
			}
		}
		// mesList = new ArrayList();
		// if (temperature != null) {
		// mesList.add((FloatMeasure) temperature.getSurvey());
		// System.out.println("UPDATE | "
		// + temperature.getName()
		// + " => "
		// + ((FloatMeasure) mesList.get(mesList.size() - 1))
		// .getValue() + ""
		// + mesList.get(mesList.size() - 1).getUOM());
		// } else {
		// System.err.println("ALLERT | no temperature monitor find");
		// }
		// if (humidity != null) {
		// mesList.add((IntMeasure) humidity.getSurvey());
		// System.out.println("UPDATE | "
		// + humidity.getName()
		// + " => "
		// + ((IntMeasure) mesList.get(mesList.size() - 1))
		// .getValue() + ""
		// + mesList.get(mesList.size() - 1).getUOM());
		// } else {
		// System.err.println("ALLERT | no humidity monitor find");
		// }
		// if (tilt != null) {
		// mesList.add((BooleanMeasure) tilt.getSurvey());
		// System.out.println("UPDATE | "
		// + tilt.getName()
		// + " => "
		// + ((BooleanMeasure) mesList.get(mesList.size() - 1))
		// .isChanged());
		// } else {
		// System.err.println("ALLERT | no tilt monitor find");
		// }
		// if (dip != null) {
		// mesList.add((BooleanMeasure) dip.getSurvey());
		// System.out.println("UPDATE | "
		// + dip.getName()
		// + " => "
		// + ((BooleanMeasure) mesList.get(mesList.size() - 1))
		// .isChanged());
		// } else {
		// System.err.println("ALLERT | no inclinazione monitor find");
		// }
		return mesList;
	}

	public static void main(String[] args) {
		GroundMonitor testGND = new GroundMonitor("testGroundMonitor",
				new Coordinate(0, 0), 2);
		System.out.println("Create new STATION name:" + testGND.name);
		testGND.addTemperatureSensor(new TempSensorSim("DS18B20", "TMP"));
		// testGND.addTiltSensor(new TiltSensorSim("TLTECO", "ALL"));
		testGND.addHumiditySensor(new HumidSensorSim("DHT11", "HMD"));
		testGND.addDipSensor(new ShockSensorArduino("FC01", "ALL"));
		testGND.monitorUpdates();
	}

}
