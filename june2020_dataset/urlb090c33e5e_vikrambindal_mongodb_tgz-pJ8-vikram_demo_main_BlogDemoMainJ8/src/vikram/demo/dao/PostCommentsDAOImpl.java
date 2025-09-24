package vikram.demo.dao;

import java.util.ArrayList;
import java.util.List;

import vikram.demo.domain.Post;
import vikram.demo.domain.PostComments;
import vikram.demo.factory.MongoDBFactory;
import vikram.demo.main.Constants;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class PostCommentsDAOImpl {

	MongoDBFactory mongoDBFactory;
	DBCollection commentCollection;
	
	public PostCommentsDAOImpl(MongoDBFactory mongoDBFactory) {
		this.mongoDBFactory = mongoDBFactory;
		commentCollection = this.mongoDBFactory.getDBCollection(Constants.COMMENT_TABLE);
	}

	public void createComment(PostComments comments) {

		DBObject dbObject = new BasicDBObject();
		dbObject.put(PostComments.DB_AUTHOR, comments.getAuthor());
		dbObject.put(PostComments.DB_MAILADD, comments.getMailAdd());
		dbObject.put(PostComments.DB_CREATED, comments.getCreated());
		dbObject.put(PostComments.DB_POST, comments.getPostId());
		dbObject.put(PostComments.DB_COMMENT, comments.getComment());
		commentCollection.save(dbObject);
	}
	
	public List<PostComments> findCommentsByPost(Post post){
		
		List<PostComments> postComments = new ArrayList<PostComments>();
		DBObject dbObject = new BasicDBObject();
		dbObject.put(PostComments.DB_POST, post.getId());
		DBCursor resultCursor = commentCollection.find(dbObject);
		while(resultCursor.hasNext()){
			DBObject resultObj = resultCursor.next();
			PostComments comment = new PostComments().convert(resultObj);
			postComments.add(comment);
		}
		return postComments;
		
	}

}
