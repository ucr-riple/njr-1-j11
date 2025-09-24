package clarion.system;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collection;

public class TimeStampCollection extends LinkedList <Long> {
	private static final long serialVersionUID = 6277351424232165280L;
	
	/**The number of slots in the time stamp collection.*/
	public static int GLOBAL_CAPACITY = 10;
	/**The number of slots in the time stamp collection.*/
	public int CAPACITY = GLOBAL_CAPACITY;
	
	public boolean add (Long TimeStamp)
	{
		if(CAPACITY > 0)
		{
			if(size() == CAPACITY)
			{
				Iterator <Long> i = iterator();
				Long oldest = i.next();
				while(i.hasNext())
				{
					Long check = i.next();
					if(check < oldest)
						oldest = check;
				}
				remove(oldest);
			}
		}
		return super.add(TimeStamp);
	}
	
	public boolean addAll (Collection <? extends Long> TimeStamps)
	{
		boolean check = false;
		for(Long o : TimeStamps)
		{
			if(check != true)
				check = add(o);
			else
				add(o);
		}
		
		return check;
	}
	
	public TimeStampCollection clone()
	{
		TimeStampCollection a = new TimeStampCollection ();
		a.CAPACITY = CAPACITY;
		for(Long t : this)
		{
			a.add(new Long (t));
		}
		return a;
	}
	
}
