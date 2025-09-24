package DATA;

import java.io.Serializable;
//import DATA.HashMap;
import java.util.Iterator;

public class Group extends Timeable implements People, Serializable {

	private String name;

	protected int effectif;
	public LinksList links;
	public HashMap<Field, Time> classes;
	protected HashMap<Field, Boolean> done;

	private Group parent;
	private Group[] children;
	
	
	public Group(String aName, int aEff)	{
		
		this.name = aName;
		this.effectif = aEff;
		this.classes = null;	//Implement
		this.links = new LinksList();
		this.classes = new HashMap<Field, Time>();
		this.done = new HashMap<Field, Boolean>();
	}
	
		//Renvoie la première matière dans classes qui n'est pas attribuée dans teachers
		//(Pour l'attribution des profs)
	public Field getNextUnattributedClass()	{
	
		//System.out.println("Group.getNextUnattributedClass() : " + this + " " + this.classes);
		
		Iterator<Field> iter = this.classes.keySet().iterator();

		Field f;
		
		while (iter.hasNext())	{
			
			f = iter.next();		
			if (this.links.getLinks(f).size() == 0)
				return f;
		}
		
		return null;
	}
	
	public boolean setTeacher (Teacher teach, Field f)	{
	
		//System.out.println("Group.setTeacher() : " + this + " " + teach + " " + f);
		
		if (teach != null && f != null && (this.links.getLinks(f).size() == 0) && teach.canTeach(f, this))	{
			Link l = new Link(teach, this, f);
			teach.addLink(l);
			this.links.add(l);
			return true;
		}
		
		System.out.println("Group.setTeacher() : " + this + " " + teach + " " + f + " " + this.links.getLinks(f) + " " + teach.canTeach(f, this));
		
		return false;
	}
	
	public Teacher getTeacher(Field f)	{
		//System.out.println("Group.getTeacher(" + f + ") " + this + " " + this.links.size() + " " + this.links.getLinks(f));
		return (this.links.getLinks(f).size() != 0 ? this.links.getLinks(f).get(0).getTeacher() : null);
	}
	
	public boolean addLink(Link l)	{
		if (this.classes.containsKey(l.getField()) && this == l.getGroup() && this.links.getLinks(l.getField()).size() == 0)	{
			this.links.add(l);
			l.getTeacher().addLink(l);
			return true;
		}
		return false;
	}
	
	public String getMail()	{
		return this.name.toLowerCase().replaceAll(" ", "") + "@insa-lyon.fr";
	}
	
	public int getEffectif()	{
		return this.effectif;
	}
	
	public LinksList getLinks()	{
		return this.links;
	}
	
	public HashMap<Field, Time> getClasses()	{
		return this.classes;
	}
	
	public Group setClasses(HashMap<Field, Time> aClasses)	{
		
		//System.out.println("Group.setClasses() : " + this + " " + aClasses);
		
		if (aClasses != null)
			this.classes = aClasses;
		
		for (Field f : this.classes.keySet())
			if (!this.done.containsKey(f))
				this.done.put(f, false);
		
		return this;
	}
	
	public Group setParent(Group aParent)	{
		
		this.parent = aParent;
		return this;
	}
	
	public Group getParent()	{
		return this.parent;
	}
	
	public String toString()	{
		
		return (this.parent == null ? "" : (this.parent.toString() + " ")) + this.name;
	}

	public Group[] getChildren()	{
		return this.children;
	}
	
	public Group setChildren(Group[] aChildren) {
		this.children = aChildren;
		return this;
	}

	public HashMap<Field, Boolean> getFieldsDone() {
		return this.done;
	}

}
