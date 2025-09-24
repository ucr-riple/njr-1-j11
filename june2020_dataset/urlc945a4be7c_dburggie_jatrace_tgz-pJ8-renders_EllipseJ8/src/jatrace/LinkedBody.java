package jatrace;

/** This class provides a means to keep track of a collection of objects with
 *  the Body interface through a linked list. Each link will have a unique ID
 *  integer that can be used to find and or remove it from the collection. */
public class LinkedBody
{

	private LinkedBody nextLink;
	private LinkedBody prevLink;
	private Body body;
	
	
	/** Instantiates a new link containing the specified Body interface. */
	public LinkedBody(Body b)
	{
		body = b;
		nextLink = null;
		prevLink = null;
	}
	
	/** Fetches a reference to the Body object this link wraps. */
	public Body b()
	{
		return body;
	}
	
	/** Fetches the next link down from caller. A null reference will be
	 *  returned if the caller is the tail object. */
	public LinkedBody next()
	{
		return nextLink;
	}
	
	/** Removes object from its linked list and inserts it before the argument.
	 *  This function safely handles null references. */
	public void insertBefore(LinkedBody lb)
	{
		//clear this objects ends
		remove();
		
		if (lb != null)
		{
			prevLink = lb.prevLink;
			lb.prevLink = this;
			nextLink = lb;
			if (prevLink != null)
			{
				prevLink.nextLink = this;
			}
		}
	}
	
	/** Removes object from its linked list and inserts it after the argument.
	 *  This function safely handles null references. */
	public void insertAfter(LinkedBody lb)
	{
		remove();
		
		if (lb != null)
		{
			nextLink = lb.nextLink;
			lb.nextLink = this;
			prevLink = lb;
			if (nextLink != null)
			{
				nextLink.prevLink = this;
			}
		}
	}
	
	/** Removes object from linked list, linking the ends together. */
	public void remove()
	{
		if (prevLink != null)
		{
			prevLink.nextLink = nextLink;
		}
		
		if (nextLink != null)
		{
			nextLink.prevLink = null;
		}
		
		nextLink = null; prevLink = null;
	}


}
