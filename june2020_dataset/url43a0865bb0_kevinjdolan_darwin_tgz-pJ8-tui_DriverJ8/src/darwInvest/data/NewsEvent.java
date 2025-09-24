package darwInvest.data;

import java.io.Serializable;

/**
 * The news event object contains the content of a single
 * news story
 * @author Kevin Dolan, Jae Yong Sung, Andrew Perrault
 */
public class NewsEvent implements Serializable{
	
	private String title;
	private String content;
	private long date;

	private static final long serialVersionUID = 5945049073615009540L;

	public NewsEvent(String title, String content, long date) {
		this.title = title;
		this.content = content;
		this.date = date;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		if(content == null)
			return title;
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(long date) {
		this.date = date;
	}
	
	
	
}
