package DATA;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Cette classe représente un emploi du temps hebdomadaire.
 * Elle regroupe un propriétaire Timeable (Teacher, Group ou Classroom)
 * et une liste de Slots représentant tous les créneaux horaires de la semaine.
 * La variable static minDelta représente l'intervalle minimum entre des créneaux (exemple : une demi-heure à l'INSA),
 * De même, defaultWeek correspond à l'ensemble des créneaux travaillés de la semaine (exemple: 8h-12, 14h-18 du lundi au vendredi) 
 * @author arsaell
 *
 */

public class WeekTable implements Serializable {
	
	private ArrayList<Slot> slots;
	private Timeable owner;

	private static Time minDelta = new Time(1);
	private static WeekTable defaultWeek;
	
	/**
	 * Cette méthode est celle à appeler de préférence :
	 * Elle recopie les créneaux de l'instance statique defaultWeek
	 * afin que toutes les instances qui implémentent Timeable (autrement dit, qui sont associées à une instance de WeekTable)
	 * soient basées sur la même semaine de travail.
	 * @param aOwner
	 */
	public WeekTable(Timeable aOwner)	{
	
		this(defaultWeek, aOwner);
	}
	
	public WeekTable(ArrayList<Slot> aSlots, Timeable aOwner)	{
		this.slots = aSlots;
		this.owner = aOwner;
	}
	
	public WeekTable(WeekTable source, Timeable aOwner)	{
	
		this.owner = aOwner;
		this.slots = new ArrayList<Slot>();
		for (Slot s : source.slots)
			this.slots.add(s.clone());
	}
	
	public void setSlot(Slot aSlot)	{
	
	}
	
	public ArrayList<Slot> getAllFreeSlots (Time duration)	{
	
		//System.out.println("\n\tWeekTable.getAllFreeSlots() : " + duration + "\n" + this.slots);
		
		ArrayList<Slot> res = new ArrayList<Slot>();
		this.slots.get(0).getBegin();
		
		for (Slot s : this.slots)	{
			//System.out.println("WeekTable.getAllFreeSlots() : " + s);
			if (!(s instanceof Lesson) && s.getDuration().isntLessThan(duration))	{
				//System.out.println("\t\t\t\t\t\t\t was added");
				res.add(s);
			}
		}
		
		return res;
	}
	
	public ArrayList<Lesson> getSlotsConcerning(Constrainable c)	{
		
		ArrayList<Lesson> res = new ArrayList<Lesson>();
		
		for (Slot s : this.slots)
			if (s instanceof Lesson)	{
				Lesson l = (Lesson) s;
				if (l.getField().equals(c) || l.getPlace().equals(c) || l.getStudents().equals(c) || l.getTeacher().equals(c) || l.getType().equals(c))
					res.add(l);
				
			}
		/*
		for (Slot s : res)	{
			System.out.println("WeekTable.getSlotsConcerning( " + c + " ) --> " + s);
		}
		*/
		return res;
	}

	public boolean fieldHappensInDay(Field f, byte day)	{
		
		//System.out.print("WeekTable<" + this.owner + ">.fieldHappensInDay (" + f + ", " + day + ") ");
		for (Slot s : this.slots)	{
			if (s instanceof Lesson && ((Lesson) s).getField().equals(f) && s.getBegin().getDay() == day)	{
				//System.out.println("true.");
				return true;
			}
		}
		//System.out.println("false");
		return false;
	}
	
	public boolean addLesson(Lesson l)	{
		
		int i = this.indexForTime(l.getBegin());
		Slot s = this.slots.get(i);
		
		//System.out.println("WeekTable.addLesson() : " + this.owner + "\n" + l + "\n\n\t" + i + " " + s);
		
		if (!(s instanceof Lesson) && s.getEnd().isntLessThan(l.getEnd()))	{
			
			this.slots.remove(i);
			
			//System.out.println("WeekTable.addLesson() #1 : " + this.slots.size());
			
			if (!l.getBegin().equals(s.getBegin()))	{
				Slot begin = new Slot(s.getBegin(), l.getBegin());
				this.slots.add(i, begin);
				i++;
				
				//System.out.println("WeekTable.addLesson()  #2 : " + begin);
			}
			
			this.slots.add(i, l);
			
			if (l.getEnd().isLessThan(s.getEnd()))	{
				Slot end = new Slot(l.getEnd(), s.getEnd());
				this.slots.add(i + 1, end);
				
				//System.out.println("WeekTable.addLesson()  #3 : " + end);
			}
			
			//System.out.println("WeekTable.addLesson() : Lesson added ! " + this.slots.size());
			
			return true;
		}
		
		return false;
	}
	
	public int checkForDuplicates()	{
		int res = 0;
		for (int i = 1 ; i < this.slots.size() ; i++)
			if (!(this.slots.get(i - 1) instanceof Lesson) && !(this.slots.get(i) instanceof Lesson) && this.slots.get(i).getBegin().substract(this.slots.get(i - 1).getEnd()).isLessThan(new Time(2)))	{
				Slot s = new Slot(this.slots.get(i - 1).getBegin(), this.slots.get(i).getEnd());
				//System.out.println("WeekTable.checkForDuplicates() : " + this.slots.get(i - 1) + " + " + this.slots.get(i) + " = " + s);
				this.slots.remove(i);
				this.slots.remove(i - 1);
				this.slots.add(i - 1, s);
				res++;
			}
		return res;
	}
	
	private int  indexForTime(Time t)	{
		
		int i;
		
		for (i = 0 ; i < this.slots.size(); i++)	{
			if (this.slots.get(i).getBegin().isLessThan(t) && this.slots.get(i).getEnd().isMoreThan(t) || this.slots.get(i).getBegin().equals(t) || this.slots.get(i).getBegin().isMoreThan(t))
				return i;
		}
		return -1;
	}
	
	public static void setDefault(WeekTable aDefault)	{
	
		defaultWeek = aDefault;
	}
	
	public static WeekTable getDefault()	{
	
		return defaultWeek;
	}

	public static void setMinDelta(Time aMD)	{
		minDelta = aMD != null ? aMD : new Time();
	}
	
	public static Time getMinDelta()	{
		return minDelta;
	}

	public String toString() {
		
		String res = "\tWeektable : " + this.owner + "\n";
		int currentDay = 0;
		for (Slot s : this.slots)	{
			
			if ((int)s.getBegin().getDay() > currentDay)	{
				res += "\n";
				currentDay = (int) s.getBegin().getDay();
			}
			
			if (s instanceof Lesson)	{
				res += (((Lesson)s).getSlot().toString() + " : " + ((Lesson)s).getField().toString() + "\t");
			}
			else
				res += s.toString() + "\t";
			
		}
		return res;
	}
	
	public static Time getMWWH()	{
		if (defaultWeek == null)
			return null;
		Time res = new Time();
		for (Slot s : defaultWeek.slots)
			res = res.add(s.getDuration());
		return res;
	}
	
	public ArrayList<Slot> getSlots() {
		return this.slots;
	}
	
	public Timeable getOwner() {
		return this.owner;
	}
}
