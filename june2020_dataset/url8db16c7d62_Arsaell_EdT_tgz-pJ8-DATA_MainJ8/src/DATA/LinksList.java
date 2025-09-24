package DATA;

import java.io.Serializable;
import java.util.ArrayList;

public class LinksList extends ArrayList<Link> implements Serializable {
	
	/*
	 * Jusqu'à présent, cette classe n'a pas été utilisée.
	 * Ne pourrait-on pas s'en débarasser ?
	 * 	-- Rémi, entre les commit n°8 et 9.
	 */
	
	public LinksList()	{
		super();
	}

	public LinksList getLinks(Group g)	{
		
		LinksList ll = new LinksList();
		
		for (Link l : this)
			if (l.getGroup() == g)
				ll.add(l);
		
		return ll;
	}

	public LinksList getLinks(Field f)	{
		
		LinksList ll = new LinksList();
		
		for (Link l : this)
			if (l.getField() == f)
				ll.add(l);
		
		return ll;
	}

	public LinksList getLinks(Teacher t)	{
		
		LinksList ll = new LinksList();
		
		for (Link l : this)
			if (l.getTeacher() == t)
				ll.add(l);
		
		return ll;
	}
}
