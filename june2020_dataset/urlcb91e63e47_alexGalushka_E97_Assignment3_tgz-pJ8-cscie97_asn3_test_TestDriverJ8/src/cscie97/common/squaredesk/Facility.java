package cscie97.common.squaredesk;


/**
 * The Class Facility.
 */
public class Facility
{
	
	/** The category. */
	private String category;
	
	/** The type. */
	private String type;
	
	/**
	 * Instantiates a new facility.
	 *
	 * @param category the category
	 * @param type the type
	 */
	public Facility ( String category, String type )
	{
		this.category = category;
		this.category = type;
	}
	
	public Facility ()
	{
		category = "";
		category = "";
	}
	
	/**
	 * mutator method for category attribute.
	 *
	 * @param category the new category
	 */
	public void setCategory ( String category )
	{
		this.category = category;
	}
	
	/**
	 * accessor method for category attribute.
	 *
	 * @return String
	 */
	public String getCategory ()
	{
		return this.category;
	}
	
	/**
	 * mutator method for type attribute.
	 *
	 * @param type the new type
	 */
	public void setType ( String type )
	{
		this.type = type;
	}
	
	/**
	 * accessor method for type attribute.
	 *
	 * @return String
	 */
	public String getType ()
	{
		return this.type;
	}
	

	/**
	 * translator of the category and type to the format of <facilityType> and <facilityType_category> , 
	 * packaged in the String[2], where first element <facilityType> , second - <facilityType_category>
	 * @return String[]
	 */
	public String[] getTraslatedCategoryAndType()
	{
		String[] result = {"",""};
		result[0] = type;
		if (!category.equals("") || category != null )
		{
			String[] arrayCaterory;
			arrayCaterory= category.split(" ");
			if (arrayCaterory.length == 1)
			{
			    result[1] = category; 
			}
			else
			{
				String tempStringCateg = "";
				for (int i = 0; i<arrayCaterory.length; i++)
				{
					if ( i == arrayCaterory.length - 1)
					{
					    tempStringCateg += arrayCaterory[i];
					}
					else
					{
						tempStringCateg += arrayCaterory[i]+"_";
					}		
				}
				result[1] = tempStringCateg;
			}
		}
		return result;
	}
	
}
