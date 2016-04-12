package it.unibo.system;

import it.unibo.interfaces.StationRPI;
import it.unibo.util.AssembledList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class MongoCom implements Communicator{

	private String user;
	private String database;
	private char[] password;
	private MongoClient mongoClient;
	private DB dataBase;
	private DBCollection collection;
	
	
	public MongoCom(String user, String database, char[] password) {
		super();
		this.user = user;
		this.database = database;
		this.password = password;		
	}

	public void sendMes() {
		
		String json = "{'database' : 'mkyongDB','table' : 'hosting'," +
				  "'detail' : {'records' : 99, 'index' : 'vps_index1', 'active' : 'true'}}}";

				DBObject dbObject = (DBObject)JSON.parse(json);
						
				collection.insert(dbObject);
	}	


	

	public boolean testConnection(String DataBaseName){
		if(dataBase.getCollection(DataBaseName) != null){
			System.out.println("Connected to DataBase:" + DataBaseName);
			return true;
			}
		System.err.println("Connection Failed to DataBase:" + DataBaseName);
		return false;
	}
	
	public static void main(String[] args) {

		MongoCom mc= new MongoCom("marmat89","esn_nosql","28mamprenar".toCharArray());
		
		mc.turnOnConnection();
		
		mc.testConnection("test");


	}

	@Override
	public void sendMes(StationRPI stat, List<AssembledList> lastUpdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnOffConnection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnOnConnection() {
		MongoCredential credential = MongoCredential.createCredential(user,
                database,
                password);
		
		mongoClient = new MongoClient(new ServerAddress(
				"ds019480.mlab.com", 19480),
				Arrays.asList(credential));

		dataBase = mongoClient.getDB("esn_nosql");
	}	
}
