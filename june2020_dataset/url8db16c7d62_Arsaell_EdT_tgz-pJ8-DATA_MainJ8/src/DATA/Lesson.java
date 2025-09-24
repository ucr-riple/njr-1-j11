package DATA;

import java.io.Serializable;

public class Lesson	extends Slot implements Serializable {
	
	private Teacher teacher;
	private Group students;
	private Field field;
	private Classroom place;
	
	public Lesson (Teacher aTeacher, Group aGroup, Field aField, Classroom aPlace, Slot aSlot)	{
	
		super(aSlot.getBegin(), aSlot.getEnd());
		this.teacher = aTeacher;
		this.students = aGroup;
		this.field = aField;
		this.place = aPlace;
		this.check();
	}
	
	private boolean check()	{
	
		boolean res = true;
		
		if (this.place.getType() != this.field.getType())	{
		
			if (res)
				System.out.println("Lesson.check() : incoherent data ");
			System.out.println("Place type doesn't match lesson type.");
			res = false;
		}
		
		if (!this.teacher.knows(this.field))	{
		
			if (res)
				System.out.println("Lesson.check() : incoherent data ");
			System.out.println("Teacher " + this.teacher + " can't teach " + this.field);
			res = false;
		}
		
		if (this.students.getEffectif() > this.place.getEffectif())	{
		
			if (res)
				System.out.println("Lesson.check() : incoherent data ");
			System.out.println("Classroom " + this.place + " is too small for group " + this.students);
			res = false;
		}
		
		/*
		Vérifier aussi :
			la disponibilité de la salle
			si les étudiants étudient bien cte matière.
		*/
		
		return res;
	}
	
	public ClassType getType() {
		return this.field.getType();
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public Group getStudents() {
		return students;
	}
	
	public Field getField() {
		return field;
	}
	
	public Classroom getPlace() {
		return place;
	}
	
	public Slot getSlot()	{
		return new Slot(this.getBegin(), this.getEnd());
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
		this.check();
	}
	
	public void setStudents(Group students) {
		this.students = students;
		this.check();
	}
	
	public void setField(Field field) {
		this.field = field;
		this.check();
	}
	
	public void setPlace(Classroom place) {
		this.place = place;
		this.check();
	}

	public String toString()	{
		return "Lesson :\n\t" + this.students + "\n\t" + this.field + "\n\t" + this.teacher + "\n\t" + this.place + "\n\t" + super.toString();
	}
}
