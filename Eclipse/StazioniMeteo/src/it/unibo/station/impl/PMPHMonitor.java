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

	private Sensor PM;
	private Sensor PH;
	private IMeasure lastMeasure;

	@Override
	public List<Sensor> getSensorList() {
		List<Sensor> sensors = new ArrayList<Sensor>();
			sensors.add(PM);
			sensors.add(PH);
		return sensors;
	}

	public void addPMSensor(Sensor pm) {
		PM = pm;
		System.out.println("addPMSensor=>" + pm.getName());
	}

	public void addPHSensor(Sensor ph) {
		PH = ph;
		System.out.println("addPHSensor=>" + ph.getName());

	}

	public List<AssembledList> monitorUpdates() {
		mesList.clear();
		List<Sensor> sensors = this.getSensorList();
		Iterator itSen = sensors.iterator();
		while (itSen.hasNext()) {
			Sensor sen = (Sensor) itSen.next();
			if (sen != null) {
				lastMeasure = sen.getSurvey();
			if (sen != null && lastMeasure != null) {
				mesList.add(new AssembledList(sen, lastMeasure));
				System.out.println("UPDATE | " + sen.getName() + " => "
						+ lastMeasure.getValue() + "" + lastMeasure.getUOM());
			} else {
				System.err.println("ALLERT | no " + sen.getName()
						+ "monitor find");
			}
			}
		}
		return mesList;
	}

	public static void main(String[] args) {
		PMPHMonitor testAir = new PMPHMonitor("testAirMonitor", new Coordinate(0,
				0), 3);
		OnBoardDebugger deb = new OnBoardDebugger(testAir);

		System.out.println("Create new STATION name:" + testAir.name);
		testAir.addPMSensor(new PM1SensorArduino("DSM501A", "PM_","cfp"));
		testAir.addPHSensor(new PHSensorArduino("PHS", "PH_","ph", deb));
		System.out.println(testAir.monitorUpdates());
	}
}
