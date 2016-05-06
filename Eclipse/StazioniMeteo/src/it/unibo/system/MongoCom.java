package it.unibo.system;

import it.unibo.interfaces.StationRPI;
import it.unibo.util.AssembledList;
import it.unibo.util.MongoMeasure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static java.util.Arrays.asList;
import com.google.gson.Gson;

public class MongoCom implements Communicator {

	private String user;
	private String database;
	private String collectionName;
	private char[] password;
	private MongoClient mongoClient;
	private DB dataBase;
	private DBCollection collection;

	public MongoCom(String user, String database, char[] password, String collectionName) {
		super();
		this.user = user;
		this.database = database;
		this.password = password;
		this.collectionName = collectionName;

	}

	public boolean testConnection(String DataBaseName) {

		if (dataBase.getCollection(DataBaseName) != null) {
			System.out.println("Connected to DataBase:" + DataBaseName);
			System.out.println("SEND TO NOSQL");

			MongoMeasure mm = new MongoMeasure("Test Station", 1, 0, 0, "getName()", "getDatatype()", 0, "getUOM()");

//			Gson gson = new Gson();
//			String jsonString = gson.toJson(mm);
//			DBObject dbObject = (DBObject) JSON.parse(jsonString);

//			System.out.println(mm.MeasureTime);
			collection.insert(mm.object);

			System.out.println("Test Station Measure Update");

			return true;
		}
		System.err.println("Connection Failed to DataBase:" + DataBaseName);
		return false;
	}

	public static void main(String[] args) {

		MongoCom mc = new MongoCom("marmat89", "esn_nosql", "28mamprenar".toCharArray(), "debug");

		mc.turnOnConnection();

		mc.testConnection("debug");

	}

	@Override
	public void sendMes(StationRPI stat, List<AssembledList> lastUpdate) {

		Iterator it = lastUpdate.iterator();
		while (it.hasNext()) {
			AssembledList element = (AssembledList) it.next();
			MongoMeasure mm = new MongoMeasure(stat.getName(), stat.ID, stat.getPosition().getLat(),
					stat.getPosition().getLon(), element.sen.getName(), element.sen.getDatatype(),
					element.lastMes.getValue(), element.lastMes.getUOM());
			
			collection.insert(mm.object);
		}
	}

	@Override
	public void turnOffConnection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void turnOnConnection() {
		MongoCredential credential = MongoCredential.createCredential(user, database, password);
		mongoClient = new MongoClient(new ServerAddress("ds019480.mlab.com", 19480), Arrays.asList(credential));

		dataBase = mongoClient.getDB("esn_nosql");

		collection = dataBase.getCollection(collectionName);
	}
}
