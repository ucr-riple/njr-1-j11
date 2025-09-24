package vikram.demo.domain;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class Post extends Blog{

	public static final String DB_TITLE = "title";
	public static final String DB_TEXT = "text";
	public static final String DB_CREATED = "created";
	public static final String DB_TAG = "tags";
	public static final String DB_AUTHORID = "authorId";
	
	private String title;
	private String text;
	private Date created;
	private List<String> tags;
	private ObjectId authorId;
	
	public Post(String title, String text, Date created, ObjectId authorId, List<String> tags) {
		this.title = title;
		this.text = text;
		this.created = created;
		this.authorId = authorId;
		this.tags = tags;
	}
	
	public Post() {
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public ObjectId getAuthorId() {
		return authorId;
	}
	public void setAuthorId(ObjectId authorId) {
		this.authorId = authorId;
	}
	
	
	@Override
	public String toString() {
		return "Post [title=" + title + ", text=" + text + ", created="
				+ created + ", tags=" + tags + ", authorId=" + authorId
				+ ", id=" + getId() + "]";
	}
	
	public Post convert(DBObject resultObj) {
		setId((ObjectId)resultObj.get(Blog.ID));
		setTitle((String)resultObj.get(Post.DB_TITLE));
		setText((String)resultObj.get(Post.DB_TEXT));
		setTags((List<String>)resultObj.get(Post.DB_TAG));
		setCreated((Date)resultObj.get(Post.DB_CREATED));
		setAuthorId((ObjectId)resultObj.get(Post.DB_AUTHORID));
		return this;
	}
	
}
