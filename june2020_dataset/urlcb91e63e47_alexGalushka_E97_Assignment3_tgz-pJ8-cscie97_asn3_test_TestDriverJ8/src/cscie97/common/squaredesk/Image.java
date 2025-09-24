package cscie97.common.squaredesk;
import java.net.URI;


/**
 * The Class Image.
 */
public class Image
{
	
	/** The description. */
	private String description; 
	
	/** The name. */
	private String name;
	
	/** The uri. */
	private URI uri;
	
	/**
	 * Instantiates a new image.
	 *
	 * @param desciption the desciption
	 * @param name the name
	 * @param uri the uri
	 */
	public Image ( String desciption, String name, URI uri )
	{
		this.description = desciption;
		this.name = name;
		this.uri = uri;
	}
	
	/**
	 * mutator method for description attribute.
	 *
	 * @param description the new description
	 */
	public void setDescription ( String description )
	{
		this.description = description;
	}
	
	/**
	 * accessor method for description attribute.
	 *
	 * @return String
	 */
	public String getDescription ()
	{
		return this.description;
	}
	
	/**
	 * mutator method for name attribute.
	 *
	 * @param name the new name
	 */
	public void setName ( String name )
	{
		this.name = name;
	}
	
	/**
	 * accessor method for name attribute.
	 *
	 * @return String
	 */
	public String getName ()
	{
		return this.name;
	}
	
	/**
	 * mutator method for uri attribute.
	 *
	 * @param uri the new uri
	 */
	public void setUri ( URI uri )
	{
		this.uri = uri;
	}
	
	/**
	 * accessor method for uri attribute.
	 *
	 * @return URI
	 */
	public URI getUri ()
	{
		return this.uri;
	}
	
}
