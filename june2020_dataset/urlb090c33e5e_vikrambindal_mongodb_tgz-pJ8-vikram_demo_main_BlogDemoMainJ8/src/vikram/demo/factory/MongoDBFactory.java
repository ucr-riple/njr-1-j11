package vikram.demo.factory;

import java.net.UnknownHostException;

import vikram.demo.main.Constants;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDBFactory {

	private static MongoDBFactory mongoDBFactory;
	private DB db;
	private Mongo mongoDB;
	
	private MongoDBFactory(String dbName) throws UnknownHostException, MongoException{
		
		mongoDB = new Mongo();
		db = mongoDB.getDB(dbName);
	}
	
	public static MongoDBFactory getInstance(){
		
		if(mongoDBFactory == null){
			try {
				mongoDBFactory = new MongoDBFactory(Constants.DB_NAME);
			} catch (Exception e) {
				mongoDBFactory = null;
				e.printStackTrace();
			}
		}
		return mongoDBFactory;
	}
	
	public DBCollection getDBCollection(String collectionName){
		return db.getCollection(collectionName);
	}
	
	public void dropDB(String dbName){
		mongoDB.dropDatabase(dbName);
	}
}
