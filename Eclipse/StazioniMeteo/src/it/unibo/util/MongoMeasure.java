package it.unibo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class MongoMeasure {
	private String StationName;
	private int StationID;
	private double StationPositionLat;
	private double StationPositionLon;
	private String SensorName;
	private String SensorDataType;
	private Object SensorValue;
	private String SensorUOM;
	private String MeasureTime;
	public MongoMeasure(String stationName, int stationID, double stationPositionLat, double stationPositionLon,
			String sensorName, String sensorDataType, Object sensorValue, String UOM) {
		super();
		StationName = stationName;
		StationID = stationID;
		StationPositionLat = stationPositionLat;
		StationPositionLon = stationPositionLon;
		SensorName = sensorName;
		SensorDataType = sensorDataType;
		SensorValue = sensorValue;
	      Date dNow = new Date( );
	      SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss");

	    MeasureTime = ft.format(dNow);
		SensorUOM=UOM;
	}
}
