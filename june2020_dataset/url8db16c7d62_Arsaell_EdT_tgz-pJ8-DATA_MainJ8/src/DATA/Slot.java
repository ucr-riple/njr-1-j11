package DATA;

import java.io.Serializable;
import java.util.ArrayList;


public class Slot implements Serializable {

	private Time begin, end;
	
	public Slot(Time b, Time e)	{
	
		this.begin = b;
		this.end = e;
		begin.getDay();
	}
	
	public Slot clone()	{
		return new Slot(this.begin.clone(), this.end.clone());
	}
	
	public boolean equals(Slot s)	{
		return (s.getBegin().equals(this.begin) && s.getEnd().equals(this.end));
	}
	
	public Time getDuration()	{
	
		//System.out.println("Slot.getDuration() : " + this.end.substract(this.begin));
		return this.end.substract(this.begin);
	}
	
	public Time getBegin()	{
		return this.begin;
	}
	
	public Time getEnd()	{
		return this.end;
	}
	
	public void setBegin(Time aBegin)	{
		this.begin = aBegin;
	}
	
	public void setEnd(Time aEnd)	{
		this.end = aEnd;
	}

	public boolean canFitIn(ArrayList<Slot> slots) {
		for (Slot s : slots)
			if (this.canFitIn(s))
				return true;
		
		return false;
	}
	
	public boolean canFitIn(Slot s)	{
		
		if (this.begin.isntLessThan(s.getBegin()) && this.begin.isLessThan(s.end) && this.end.isMoreThan(s.begin) && this.end.isntMoreThan(s.end))
			return true;
		
		return false;
	}

	public boolean intersects(Slot s) {
		//System.out.println("Slot.intersects() : " + this + " / " + s);
		return ( this.begin.isntLessThan(s.begin) && this.begin.isLessThan(s.getEnd()) ) || ( this.end.isMoreThan(s.getBegin()) && this.end.isntMoreThan(s.getEnd()) );
	}
	
	public Slot intersection(Slot s) {
		
		//System.out.print("Slot.intersection() : " + this + " " + s + " --> ");
		
		Time begin = this.begin.isMoreThan(s.begin) ? this.begin : s.begin;
		Time end = this.end.isLessThan(s.end) ? this.end : s.end;
		
		if (end.isMoreThan(begin))
			return new Slot(begin, end);
		
		return null;
		/*
		if (this.intersects(s))	{
			if (this.begin.isntLessThan(s.getBegin()) && this.begin.isLessThan(s.getEnd()))	{
				Slot res = new Slot(this.begin, s.getEnd());
				//System.out.println(res);
				return res;
			}
			
			else if (this.end.isMoreThan(s.getBegin()) && this.end.isntMoreThan(s.getEnd()))	{
				Slot res =  new Slot(s.getBegin(), this.end);
				//System.out.println(res);
				return res;
			}
		}
		//System.out.println("null");
		return null;
		*/
	}
	
	public String toString()	{
		return "[" + this.begin.toString() + " --> " + this.end.toString() + "]";
	}
}
