package DATA;

import java.io.Serializable;

public class Time implements Serializable {

	private  byte day, hour, min;
	
	public Time (byte d, byte h, byte m)	{
	
		this.day = d;
		this.hour = h;
		this.min = m;
		
		/*
		if (this.day > 6 || this.hour > 23 || this.min > 59)
			System.out.println("Error while creating a Time : " + this);
		*/
		
		this.format();
		//System.out.println("New Time : " + this);
	}
	
	public Time (String s)	{
	
		this ((byte)0, (byte)0, (byte)0);
		
		if (s.length() >= 3)	{
			this.day = (byte) s.charAt(0);
			this.hour = (byte) s.charAt(1);
			this.min = (byte) s.charAt(2);
		}
		
		else
			System.out.println("Error while creating Time from string : " + s);
		//System.out.println("New Time : " + this);
	}
	
	public Time(int i)	{
		this((byte)(i/10000), (byte)((i%10000)/100), (byte)(i%100));
		//ex : new Time(52359);
		//			   5d 23h 59min
	}
 	
	public Time ()	{
		this(0);
	}
	
	public Time clone()	{
		return new Time(this.day, this.hour, this.min);
	}
	
	public Time add (Time t)	{
	
		//System.out.println("Time.add() : " + this + " " + t);
		
		Time temp = new Time((byte)(this.day + t.getDay()), (byte)(this.hour + t.hour), (byte)(this.min + t.getMin()));
		
		temp.format();
		
		if (temp.day > 6)
			System.out.println("Time.add() : changed week" + temp + " " + t);
		
		temp.day = (byte)(temp.day % 7);
		
		return temp;
	}
	
	public Time substract(Time t)	{
	

		int min = this.toMin() - t.toMin();
		
		Time temp = new Time((byte)(min / 1440), (byte)(min % 1440 / 60), (byte)(min % 60));
		
		temp.format();
		
		//System.out.println("Time.substract() : " + this + " - " + t + " = " + temp);
		
		return temp;
	}
	
	public Time multiplyBy(double x)	{

		double min = (this.toMin() * x);
		
		Time t = new Time((byte)(min / 1440), (byte)(min % 1440 / 60), (byte)(min % 60));
		
		t.format();
		
		//System.out.println("Time.multiplyBy() : " + this + " x " + x + " = " + t);
		
		return t;
	}
	
	public Time divideBy(double x)	{
		
		if (x == 0)
			return null;

		//System.out.print("Time.divideBy(double x) : " + this + " / " + x);
		
		int min = (int)(this.toMin() / x);
		
		//System.out.print(" --> " + this.toMin() + " ; " + min);
		
		Time t = new Time((byte)(min / 1440), (byte)(min % 1440 / 60), (byte)(min % 60));
		
		//System.out.println(" ==> " + t);
		
		if (x < 1)
			t.format();
		
		return t;
	}
	
	public double divideBy(Time t)	{
		
		//System.out.println("Time.divideBy(Time t) : " + this + " / " + t + " --> " + this.toMin() + " / " + t.toMin() + " = " + (double)this.toMin() / t.toMin());
		
		if (t.equals(new Time()))
			return 0.0;
		return (double)(this.toMin()) / (double)t.toMin();
	}
	
	public Time modulo(Time t)	{
		
		//	 res = 		this - 		(this * 		Ent(this / t)	)
		Time res = t.substract( t.multiplyBy( (int)t.divideBy(this) ) );
		//System.out.println("Time.modulo() : " + this + " % " + t + " = " + this.multiplyBy((this.divideBy(t) % 1)) + " // " + new Time((byte)(min / 1440), (byte)(min%1440/60), (byte)(min%60)) + " // " + res);
		return res;
	}
	
	public boolean isLessThan(Time t)	{
		return (this.toMin() < t.toMin());
	}

	public boolean isntMoreThan(Time t)	{
		return this.toMin() <= t.toMin();
	}
	
	public boolean isMoreThan(Time t)	{
		return (this.toMin() > t.toMin());
	}
	
	public boolean isntLessThan(Time t)	{
		return this.toMin() >= t.toMin();
	}
	
	public boolean equals (Time t)	{
		this.format();
		t.format();
		return this.day == t.day && this.hour == t.hour && this.min == t.min;
	}
	
	//Gère les exceptions du type 25h --> 1day 1hour ; 92min --> 1h32min
	private void format()	{
		this.hour += this.min / 60;
		this.min = (byte) (this.min % 60);
		this.day += this.hour / 24;
		this.hour = (byte) (this.hour % 24);
		this.day = (byte) (this.day % 7);
	}
	
	//Renvoie l'équivalent de this en minutes.
	public int toMin()	{
		return this.day * 1440 + this.hour * 60 + this.min;
	}
	
	//Renvoie this dans le format utilisé par le constructeur new Time(int i);
	private int toInt()	{
		return this.day * 10000 + this.hour * 100 + this.min;
	}
	
	public String toString()	{
	
		return ((this.day == 0 ? "Mon " : (this.day == 1 ? "Tue " : (this.day == 2 ? "Wed " : (this.day == 3 ? "Thu " : (this.day == 4 ? "Fri " : (this.day == 5 ? "Sat " : (this.day == 6 ? "Sun " : "Unknown " + this.day + " "))))))) + (int)this.hour + "h" + (int)this.min + "m");												
	}
	
	public byte getMin()	{
		return this.min;
	}
	
	public byte getHour()	{
		return this.hour;
	}
	
	public byte getDay()	{
		return this.day;
	}
}
