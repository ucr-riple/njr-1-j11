package vikram.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import vikram.demo.domain.Post;
import vikram.demo.factory.MongoDBFactory;
import vikram.demo.main.Constants;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class PostDAOImpl {

	MongoDBFactory mongoDBFactory;
	DBCollection postCollection;
	
	public PostDAOImpl(MongoDBFactory mongoDBFactory) {
		this.mongoDBFactory = mongoDBFactory;
		postCollection = this.mongoDBFactory.getDBCollection(Constants.POST_TABLE);
	}

	public void createPost(Post post){
		DBObject dbObject = new BasicDBObject();
		dbObject.put(Post.DB_TITLE, post.getTitle());
		dbObject.put(Post.DB_TEXT, post.getText());
		dbObject.put(Post.DB_CREATED, post.getCreated());
		dbObject.put(Post.DB_AUTHORID, post.getAuthorId());
		dbObject.put(Post.DB_TAG, post.getTags());
		postCollection.save(dbObject);
	}
	
	public Post findPostByTitle(String title){
		Post post = null;
		DBObject dbObject = new BasicDBObject();
		dbObject.put(Post.DB_TITLE, java.util.regex.Pattern.compile(title));
		DBCursor resultCursor = postCollection.find(dbObject);
		while(resultCursor.hasNext()){
			DBObject resultObj = resultCursor.next();
			post = new Post().convert(resultObj);
		}
		return post;
	}

	public List<Post> findPostByAuthor(ObjectId authorId) {
		
		List<Post> postList = new ArrayList<Post>();
		DBObject dbObject = new BasicDBObject();
		dbObject.put(Post.DB_AUTHORID, authorId);
		DBCursor resultCursor = postCollection.find(dbObject);
		while(resultCursor.hasNext()){
			DBObject resultObj = resultCursor.next();
			Post post = new Post().convert(resultObj);
			postList.add(post);
		}
		return postList;
	}
	
	public long countPostByAuthor(ObjectId authorId){
		
		DBObject dbObject = new BasicDBObject();
		dbObject.put(Post.DB_AUTHORID, authorId);
		long numOfPosts = postCollection.count(dbObject);
		return numOfPosts;
	}
}
