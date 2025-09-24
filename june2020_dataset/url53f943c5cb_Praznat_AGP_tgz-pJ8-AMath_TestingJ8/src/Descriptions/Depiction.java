package Descriptions;

import Defs.Misc;
import Government.Order;
import Shirage.Shire;

public class Depiction {
	public static final byte CONQUER = 0;
	public static final byte DEFEAT = 1;
	public static final byte RESTING = 2;
	public static final byte SEXING = 3;
	public static final byte ADDRESSING = 4;

	public static final byte FEMALE = 0;
	public static final byte MALE = 1;
	public static final byte ORDERFEMALE = 2;
	public static final byte ORDERMALE = 3;
	public static final byte SHIRE = 4;
	
	byte subject1, subject2, subjectType; //same subject, 2 bytes
	byte object1, object2, objectType; //same object, 2 bytes
	byte type;
	
	public Depiction(byte s1, byte s2, byte o1, byte o2, byte t) {
		subject1 = s1; subject2 = s2; object1 = o1; object2 = o2; type = t;
		switch (t) {
			// set subType and objType here
		}
	}
	
	private String translate(byte b1, byte b2, byte t) {
		if (t == SHIRE) return Shire.getName(b1, b2);
		else if (t == ORDERFEMALE || t == ORDERMALE) return Order.getName(b1, b2, t == ORDERFEMALE ? Misc.FEMALE : Misc.MALE);
		else return GobName.firstName(b1, b2, t == FEMALE ? Misc.FEMALE : Misc.MALE);
	}

	private String subject() {return translate(subject1, subject2, subjectType);}
	private String gobject() {return translate(object1, object2, objectType);}
	
	public String depict() {
		switch (type) {
		case CONQUER: return "The army of " + subject() + " conquering " + gobject();
		case DEFEAT: return "The army of " + subject() + " vanquishing the army of " + gobject();
		case RESTING: return subject() + " sleeping";
		case SEXING: return subject() + " mating with " + gobject();
		case ADDRESSING: return subject() + " mating with " + gobject();
		default: return "";
		}
	}
	
	
	@Override
	public String toString() {return depict();}
}
