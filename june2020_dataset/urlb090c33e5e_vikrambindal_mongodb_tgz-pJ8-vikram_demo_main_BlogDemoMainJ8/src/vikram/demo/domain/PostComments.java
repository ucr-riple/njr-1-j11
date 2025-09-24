package vikram.demo.domain;

import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class PostComments extends Blog{

	public static final String DB_AUTHOR = "author";
	public static final String DB_MAILADD = "mailAdd";
	public static final String DB_CREATED = "created";
	public static final String DB_POST = "postId";
	public static final String DB_COMMENT = "comment";
	
	private String author;
	private String mailAdd;
	private String comment;
	private Date created;
	private ObjectId postId;
	
	public PostComments(String author, String mailAdd, String comment, Date created, ObjectId postId) {
		this.author = author;
		this.mailAdd = mailAdd;
		this.created = created;
		this.postId = postId;
		this.comment = comment;
	}
	
	public PostComments() {
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getMailAdd() {
		return mailAdd;
	}
	public void setMailAdd(String mailAdd) {
		this.mailAdd = mailAdd;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public ObjectId getPostId() {
		return postId;
	}
	public void setPostId(ObjectId postId) {
		this.postId = postId;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "PostComments [author=" + author + ", mailAdd=" + mailAdd
				+ ", comment=" + comment + ", created=" + created + ", postId="
				+ postId + ", id=" + getId() + "]";
	}

	public PostComments convert(DBObject resultObj) {
		setAuthor((String)resultObj.get(DB_AUTHOR));
		setCreated((Date)resultObj.get(DB_CREATED));
		setMailAdd((String)resultObj.get(DB_MAILADD));
		setPostId((ObjectId)resultObj.get(DB_POST));
		setComment((String)resultObj.get(DB_COMMENT));
		return this;
	}		
}
