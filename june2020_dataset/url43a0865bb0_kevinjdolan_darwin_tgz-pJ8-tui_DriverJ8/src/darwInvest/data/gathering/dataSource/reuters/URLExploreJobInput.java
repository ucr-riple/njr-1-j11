package darwInvest.data.gathering.dataSource.reuters;

import java.net.URL;

/**
 * This is the input for the URLExploreJob object
 * 
 * @author Kevin Dolan
 */
public class URLExploreJobInput {

	/**
	 * The title of the article
	 */
	public String title;
	
	/**
	 * The article's URL
	 */
	public URL exploreurl;
	
	/**
	 * The date of the article
	 */
	public long time;
	
}
