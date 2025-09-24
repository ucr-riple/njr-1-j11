package Descriptions;

import Ideology.*;

public class Comparative {

	public static String superior(Value v) {
		if (v == Values.COPULATION) {return "good looking";}
		if (v == Values.WEALTH) {return "wealthy";}
		if (v == Values.KNOWLEDGE) {return "learned";}
		if (v == Values.ALLEGIANCE) {return "faithful";}
		if (v == Values.HARMONY) {return "cool";}
		if (v == Values.BEAUTY) {return "grand";}
		if (v == Values.INFLUENCE) {return "popular";}
		if (v == Values.MIGHT) {return "strong";}
		if (v == Values.RIGHTEOUSNESS) {return "of good moral character";}
		if (v == Values.LEGACY) {return "high-born";}
		if (v == Values.EXPERTISE) {return "talented";}
		if (v == Values.SPIRITUALITY) {return "holy";}
		return "something";
	}
}
