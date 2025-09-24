package yeah.cstriker1407.design_patterns.observe;

import java.util.Observable;
import java.util.Observer;

public class ObserveTest
{
	public static void test()
	{
		MyObservable myObservable = new MyObservable();
		MyObserver myObserverA = new MyObserver("A");
		MyObserver myObserverB = new MyObserver("B");
		MyObserver myObserverC = new MyObserver("C");
		
		myObservable.addObserver(myObserverA);
		myObservable.addObserver(myObserverB);
		myObservable.addObserver(myObserverC);
		
		myObservable.changed();
	}
}

class MyObservable extends Observable
{
	public String str = "this is MyObservable";
	public void changed()
	{
		System.out.println("I am changing");
		setChanged();
		notifyObservers("Hello World");
	}
}

class MyObserver implements Observer
{
	public MyObserver(String name)
	{
		super();
		this.name = name;
	}

	private String name;
	
	@Override
	public void update(Observable o, Object arg)
	{
		System.out.print("Name:" + name +" ");
		System.out.print("observerable:" + ((MyObservable)o).str +" ");
		System.out.println("Object:" + arg.toString() );
	}
}