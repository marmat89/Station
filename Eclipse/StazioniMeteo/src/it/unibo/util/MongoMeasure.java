package it.unibo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

import com.mongodb.BasicDBObject;

import it.unibo.system.MongoCom;

public class MongoMeasure {
	private String StationName;
	private int StationID;
	private double StationPositionLat;
	private double StationPositionLon;
	private String SensorName;
	private String SensorDataType;
	private Object SensorValue;
	private String SensorUOM;
	public Date MeasureTime;
	public BasicDBObject object = new BasicDBObject();
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
		SensorUOM=UOM;
		MeasureTime=new java.util.Date();

		object.put("StationName",StationName );
		object.put("StationID", StationID);
		object.put("StationPositionLat", StationPositionLat);
		object.put("StationPositionLon",StationPositionLon);
		object.put("SensorName", SensorName);
		object.put("SensorDataType", SensorDataType);
		object.put("SensorValue", SensorValue);
		object.put("SensorUOM",SensorUOM);
		object.put("SensorUOM",SensorUOM);
        object.put("MeasureTime", MeasureTime);
	}
	public static void main(String[] args) {

		MongoMeasure mm = new MongoMeasure("Test Station", 1, 0, 0, "getName()", "getDatatype()", 0,"getUOM()");
	     System.out.println(mm.MeasureTime); 

	}
}
