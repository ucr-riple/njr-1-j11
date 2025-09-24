package DATA;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Timeable extends Constrainable implements Serializable {

	protected WeekTable timeTable;
	
	public Timeable()	{
		this.timeTable = new WeekTable(WeekTable.getDefault(), this);
	}
	
	public ArrayList<Slot> getAllFreeSlots(Time duration)	{
		return this.timeTable.getAllFreeSlots(duration);
	}
	
	public boolean addLesson(Lesson l)	{
		return this.timeTable.addLesson(l);
	}
	
	public void removeLesson(Lesson l)	{
		int i = this.timeTable.getSlots().indexOf(l);
		this.timeTable.getSlots().remove(i);
		this.timeTable.getSlots().add(i, l.getSlot());
		this.timeTable.checkForDuplicates();
		if (this instanceof Group)	{
			((Group)this).done.put(l.getField(), false);
		}
	}
	
	public WeekTable getWeekTable()	{
		return this.timeTable;
	}
}
