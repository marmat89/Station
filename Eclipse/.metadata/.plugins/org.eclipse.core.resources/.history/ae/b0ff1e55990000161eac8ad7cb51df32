package it.unibo.station.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.debugger.OnBoardDebugger;
import it.unibo.interfaces.StationRPI;
import it.unibo.interfaces.Sensor;
import it.unibo.sensors.impl.*;
import it.unibo.sensors.simulated.impl.*;
import it.unibo.util.*;

public class PMPHMonitor extends StationRPI {

	public PMPHMonitor(String name, Coordinate position, int ID) {
		super(name, position, ID);
		System.out.println("BUILD:AirMonitor=>" + this.getName() + " | "
				+ this.getPosition().getLat() + " , "
				+ this.getPosition().getLat());
		mesList = new ArrayList<AssembledList>();
	}

	private Sensor temperature;
	private Sensor humidity;
	private Sensor speed;
	private Sensor light;
	private Sensor rain;
	private IMeasure lastMes;

	@Override
	public List<Sensor> getSensorList() {
		List<Sensor> sensors = new ArrayList<Sensor>();
			sensors.add(temperature);
			sensors.add(humidity);
			sensors.add(speed);
			sensors.add(light);
		sensors.add(rain);
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

	public void addSpeedSensor(Sensor speed) {
		this.speed = speed;
		System.out.println("addSpeedSensor=>" + speed.getName());

	}

	public void addLightSensor(Sensor light) {
		this.light = light;
		System.out.println("addLightSensor=>" + light.getName());

	}

	public void addRainSensor(Sensor rain) {
		this.rain = rain;
		System.out.println("addRainSensor=>" + rain.getName());

	}

	public List<AssembledList> monitorUpdates() {

		mesList.clear();
		List<Sensor> sensors = this.getSensorList();
		Iterator itSen = sensors.iterator();
		while (itSen.hasNext()) {
			Sensor sen = (Sensor) itSen.next();
			if (sen != null) {
			lastMes = sen.getSurvey();
			if (sen != null && lastMes != null) {
				mesList.add(new AssembledList(sen, lastMes));
				System.out.println("UPDATE | " + sen.getName() + " => "
						+ lastMes.getValue() + "" + lastMes.getUOM());
			} else {
				System.err.println("ALLERT | no " + sen.getName()
						+ "monitor find");
			}
			}
		}

		// lastMes = temperature.getSurvey();
		// if (temperature != null && lastMes != null) {
		// mesList.add(new AssembledList(temperature, lastMes));
		// System.out.println("UPDATE | " + temperature.getName() + " => "
		// + lastMes.getValue() + "" + lastMes.getUOM());
		// } else {
		// System.err.println("ALLERT | no temperature monitor find");
		// }
		// lastMes = humidity.getSurvey();

		// if (humidity != null && lastMes != null) {
		// mesList.add(new AssembledList(humidity, lastMes));
		// System.out.println("UPDATE | " + humidity.getName() + " => "
		// + lastMes.getValue() + "" + lastMes.getUOM());
		// } else {
		// System.err.println("ALLERT | no humidity monitor find");
		// }
		// lastMes = speed.getSurvey();
		// if (speed != null && lastMes != null) {
		// mesList.add(new AssembledList(speed, lastMes));
		// System.out.println("UPDATE | " + speed.getName() + " => "
		// + lastMes.getValue() + "" + lastMes.getUOM());
		// } else {
		// System.err.println("ALLERT | no speed monitor find");
		// }
		// lastMes = light.getSurvey();
		// if (light != null && lastMes != null) {
		// mesList.add(new AssembledList(light, lastMes));
		// System.out.println("UPDATE | " + light.getName() + " => "
		// + lastMes.getValue() + "" + lastMes.getUOM());
		// } else {
		// System.err.println("ALLERT | no light monitor find");
		// }
		// lastMes = rain.getSurvey();
		// if (rain != null && lastMes != null) {
		// mesList.add(new AssembledList(rain, lastMes));
		// System.out.println("UPDATE | " + rain.getName() + " => "
		// + lastMes.getValue() + "" + lastMes.getUOM());
		// } else {
		// System.err.println("ALLERT | no rain monitor find");
		// }

		return mesList;
	}

	public static void main(String[] args) {
		PMPHMonitor testAir = new PMPHMonitor("testAirMonitor", new Coordinate(0,
				0), 3);
		OnBoardDebugger deb = new OnBoardDebugger(testAir);

		System.out.println("Create new STATION name:" + testAir.name);
		testAir.addTemperatureSensor(new TempSensorSim("DHT11", "TMP"));
		testAir.addHumiditySensor(new HumidSensorArduino("DHT11", "HMD", deb));
		testAir.addLightSensor(new LightSensorArduino("LDRGL5528", "LGH", deb));
		testAir.addRainSensor(new RainSensorArduino("RAINECO", "RL"));
		// testAir.addSpeedSensor(new SpeedSensorArduino("HC020K", "SPD",deb));
		System.out.println(testAir.monitorUpdates());
	}
}
