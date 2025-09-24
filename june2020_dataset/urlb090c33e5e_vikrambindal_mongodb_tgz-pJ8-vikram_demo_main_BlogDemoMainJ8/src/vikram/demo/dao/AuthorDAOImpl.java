package vikram.demo.dao;

import vikram.demo.domain.Author;
import vikram.demo.factory.MongoDBFactory;
import vikram.demo.main.Constants;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class AuthorDAOImpl {

	MongoDBFactory mongoDBFactory;
	DBCollection authorCollection;
	
	public AuthorDAOImpl(MongoDBFactory mongoDBFactory) {
		this.mongoDBFactory = mongoDBFactory;
		authorCollection = this.mongoDBFactory.getDBCollection(Constants.AUTHOR_TABLE);
	}
	
	public void createAuthor(Author author){
		DBObject dbObject = new BasicDBObject();
		dbObject.put(Author.DB_FIRST_NAME, author.getFirstName());
		dbObject.put(Author.DB_LAST_NAME, author.getLastName());
		dbObject.put(Author.DB_MAIL, author.getMailAdd());
		dbObject.put(Author.DB_ABOUT_ME, author.getAboutMe());
		authorCollection.save(dbObject);
	}
	
	public Author findAuthorByFirstName(String firstName){
		
		Author author = null;
		DBObject dbObject = new BasicDBObject();
		dbObject.put(Author.DB_FIRST_NAME, java.util.regex.Pattern.compile(firstName));
		DBCursor resultCursor = authorCollection.find(dbObject);
		while(resultCursor.hasNext()){
			DBObject resultObj = resultCursor.next();
			author = new Author().convert(resultObj);
		}
		return author;
	}

}
