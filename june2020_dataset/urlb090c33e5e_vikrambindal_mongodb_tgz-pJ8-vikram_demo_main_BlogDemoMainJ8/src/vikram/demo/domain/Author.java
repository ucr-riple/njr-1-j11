package vikram.demo.domain;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class Author extends Blog{

	public static final String DB_FIRST_NAME = "firstName";
	public static final String DB_LAST_NAME = "lastName";
	public static final String DB_MAIL = "mailAdd";
	public static final String DB_ABOUT_ME = "aboutMe";
	
	private String firstName;
	private String lastName;
	private String mailAdd;
	private String aboutMe;
	
	public Author(String firstName, String lastName, String mailAdd, String aboutMe) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailAdd = mailAdd;
		this.aboutMe = aboutMe;
	}
	
	public Author() {
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMailAdd() {
		return mailAdd;
	}
	public void setMailAdd(String mailAdd) {
		this.mailAdd = mailAdd;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	@Override
	public String toString() {
		return "Author [firstName=" + firstName + ", lastName=" + lastName
				+ ", mailAdd=" + mailAdd + ", aboutMe=" + aboutMe
				+ ", id=" + getId() + "]";
	}

	public Author convert(DBObject resultObj) {
		setId((ObjectId)resultObj.get(Blog.ID));
		setFirstName((String)resultObj.get(Author.DB_FIRST_NAME));
		setLastName((String)resultObj.get(Author.DB_LAST_NAME));
		setAboutMe((String)resultObj.get(Author.DB_ABOUT_ME));
		setMailAdd((String)resultObj.get(Author.DB_MAIL));
		return this;
	}
}
