package DATA;

import java.awt.Point;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import DATA.HashMap;
import DATA.Teacher;
import DATA.Time;

public class DataStore implements Serializable {
	
	private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
	private ArrayList<Classroom> classrooms= new ArrayList<Classroom>();
	private ArrayList<Group> groups = new ArrayList<Group>();
	private ArrayList<ClassType> types = new ArrayList<ClassType>();
	private ArrayList<Field> fields = new ArrayList<Field>();
	private Time MWWH;
	private WeekTable defaultWeek;
	
	public DataStore() {
		this.teachers = new ArrayList<Teacher>();
		this.classrooms = new ArrayList<Classroom>();
		this.groups = new ArrayList<Group>();
		this.types = new ArrayList<ClassType>();
		this.fields = new ArrayList<Field>();
		this.MWWH = WeekTable.getMWWH();
		//this.addFixtures();
	}
	public void addFixtures() {

		ArrayList<Slot> temp = new ArrayList<Slot>();

		temp.add(new Slot(new Time(800), new Time(1200)));
		temp.add(new Slot(new Time(1400), new Time(1800)));
		temp.add(new Slot(new Time(10800), new Time(11200)));
		temp.add(new Slot(new Time(11400), new Time(11800)));
		temp.add(new Slot(new Time(20800), new Time(21200)));
		temp.add(new Slot(new Time(21400), new Time(21800)));
		temp.add(new Slot(new Time(30800), new Time(31200)));
		temp.add(new Slot(new Time(40800), new Time(41200)));
		temp.add(new Slot(new Time(41400), new Time(41800)));
		
		WeekTable.setDefault(new WeekTable(temp, null));
		
		this.types.add(0, new ClassType("Cours", "Amphithéâtre", new Slot(new Time(100), new Time(130))));
		this.types.add(1, new ClassType("TD", "TD", new Slot(new Time(130), new Time(200))));
		this.types.add(2, new ClassType("TP Physique", "TP", new Slot(new Time(40), new Time(400))));
		this.types.add(3, new ClassType("TP Chimie", "Labo chimie", new Slot(new Time(300), new Time(400))));
		this.types.add(4, new ClassType("TP Construction", "TP", new Slot(new Time(400), new Time(400))));
		this.types.add(5, new ClassType("TD Conception", "TD", new Slot(new Time(130), new Time(200))));
		this.types.add(6, new ClassType("TD Info", "TD", new Slot(new Time(200), new Time(200))));
		this.types.add(7, new ClassType("Sport", "Gymnase", new Slot(new Time(200), new Time(200))));
		
		this.classrooms.add(new Classroom(types.get(1), "C9", 25, new Point(100, 100)));
		this.classrooms.add(new Classroom(types.get(1), "2.10", 25, new Point(110, 100)));
		this.classrooms.add(new Classroom(types.get(0), "Vannier", 100, new Point(100, 110)));
		//this.classrooms.add(new Classroom(types.get(0), "Turing", 100, new Point(500, 175)));
		this.classrooms.add(new Classroom(types.get(2), "Optique", 16, new Point(100, 120)));
		this.classrooms.add(new Classroom(types.get(3), "1", 16, new Point(120, 100)));
		this.classrooms.add(new Classroom(types.get(4), "Usinage", 32, new Point(120, 120)));
		this.classrooms.add(new Classroom(types.get(5), "Est", 25, new Point(100, 130)));
		this.classrooms.add(new Classroom(types.get(6), "Archie", 25, new Point(130, 100)));
		this.classrooms.add(new Classroom(types.get(7), "Piscine", 150, new Point(140, 100)));
		
		Field mathsa = (new Field(types.get(0), "Maths"));
		Field physiquea = (new Field(types.get(0), "Physique"));
		Field mathst = (new Field(types.get(1), "Maths"));
		Field physiquet = (new Field(types.get(1), "Physique"));
		Field concept = (new Field(types.get(5), "Conception"));
		Field contrucp = (new Field(types.get(4), "Usinage"));
		
		Field[] MaPtMt = {mathsa, physiquet, mathst};
		Field[] PaMtPt = {physiquea, mathst, physiquet};
		Field[] MtPtCt = {mathst, physiquet, concept};
		Field[] PtCtCp = {physiquet, concept, contrucp};
		Field[] Mt = {mathst};
		Field[] Pa = {physiquea};
		this.fields.add(mathsa);
		this.fields.add(mathst);
		this.fields.add(physiquea);
		this.fields.add(physiquet);
		this.fields.add(concept);
		this.fields.add(contrucp);
		
		this.MWWH = new Time (2500);
		
		this.teachers.add(new Teacher(0, "Twilight", "Sparkle", MaPtMt, MWWH));
		this.teachers.add(new Teacher(1, "Rarity", "", PaMtPt, MWWH));
		this.teachers.add(new Teacher(3, "Rainbow", "Dash", PtCtCp, MWWH));
		this.teachers.add(new Teacher(2, "Apple", "Jack", MtPtCt, MWWH));
		this.teachers.add(new Teacher(4, "Flutter", "Shy", Pa, MWWH));
		//this.teachers.add(new Teacher(5, "Pinkie", "Pie", Mt, MWWH));
		//this.teachers.add(new Teacher(6, "Spike", "", Mt, MWWH));
		//this.teachers.add(new Teacher(7, "Celestia", "", Mt, MWWH));
		//this.teachers.add(new Teacher(8, "Princess", "Luna", Mt, MWWH));
		//this.teachers.add(new Teacher(9, "Discord", "", Mt, MWWH));
		
		
		HashMap<Field, Time> classes = new HashMap<Field, Time>();
		
		classes.put(mathsa, new Time(300));
		classes.put(physiquea, new Time(300));
		classes.put(mathst, new Time(330));
		classes.put(physiquet, new Time(330));
		classes.put(concept, new Time(200));
		classes.put(contrucp, new Time(400));
		
		this.groups.add(new Group("Lanip", 100).setClasses(null).setParent(null));
		this.groups.add(new Group("g46", 25).setClasses(classes).setParent(groups.get(0)).setChildren(null));
		this.groups.add(new Group("g42", 25).setClasses(classes).setParent(groups.get(0)).setChildren(null));
		this.groups.add(new Group("g2116", 25).setClasses(classes).setParent(groups.get(0)).setChildren(null));
		//this.groups.add(new Group("gPi", 25).setClasses(classes).setParent(groups.get(1)).setChildren(null));
		
		Group[] gtab = {this.groups.get(1), this.groups.get(2)};
		this.groups.get(0).setChildren(gtab);
		
		//this.groups.add(new Group(3, 25).setClasses(classes));
		//this.groups.add(new Group(4, 25).setClasses(classes));
		/*
		this.groups.add(new Group(5, 16).setClasses(classes));
		this.groups.add(new Group(6, 16).setClasses(classes));
		this.groups.add(new Group(7, 16).setClasses(classes));
		this.groups.add(new Group(8, 16).setClasses(classes));
		this.groups.add(new Group(9, 16).setClasses(classes));
		this.groups.add(new Group(10, 16).setClasses(classes));
		*/
		
	}
	
	public ArrayList<Teacher> getTeachers() {
		//System.out.println("DataStore.getTeachers() : " + this.teachers);
		return this.teachers;
	}

	public ArrayList<Classroom> getClassrooms() {
		return classrooms;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public ArrayList<ClassType> getTypes() {
		return types;
	}
	
	public ArrayList<Field> getFields() {
		return fields;
	}

	public Time getMWWH() {
		this.MWWH = WeekTable.getMWWH();
		return MWWH;
	}

	public WeekTable getDefaultWeek() {
		return WeekTable.getDefault();
	}

	public void setMWWH(Time mWWH) {
		MWWH = mWWH;
	}

	public void setDefaultWeek(WeekTable defaultWeek) {
		WeekTable.setDefault(defaultWeek);
	}

	public DataStore setTeachers(ArrayList<Teacher> teachers) {
		//System.out.println("DataStore.setTeachers() : " + teachers);
		if (teachers != null)
			this.teachers = teachers;
		else
			this.teachers = new ArrayList<Teacher>();
		return this;
	}

	public DataStore setClassrooms(ArrayList<Classroom> classrooms) {
		this.classrooms = classrooms;
		return this;
	}

	public DataStore setGroups(ArrayList<Group> groups) {
		this.groups = groups;
		return this;
	}

	public DataStore setTypes(ArrayList<ClassType> types) {
		this.types = types;
		return this;
	}
	
	public DataStore setFields(ArrayList<Field> fields) {
		this.fields = fields;
		return this;
	}
	
	public String toString()	{
		return "Datastore : " + this.classrooms.size() + " classrooms, " + this.fields.size() + " fields, " + this.groups.size() + " groups, " + this.teachers.size() + " teachers, " + this.types.size() + " types.";
	}
}
