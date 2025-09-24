package Sentiens;

import java.util.*;

import Sentiens.Law.Commandments.Commandment;

public class Law {
	public static class PersonalCommandment {
		private boolean active;
		private int transgressions;
		private Commandment ref;
		
		public PersonalCommandment(Commandment ref) {this.ref = ref;}
		public boolean isSinful() {return active;}
		public void setSinful(boolean active) {this.active = active;}
		public String desc() {return "THOU SHALT NOT " + getVerb();}
		public String getVerb() {return ref.verb;}
		public int getTransgressions() {return transgressions;}
		public void setTransgressions(int transgressions) {this.transgressions = transgressions;}
		public void commit() {this.transgressions++;}
		@Override
		public String toString() {
			return (active ? desc() : (getVerb() + " OK")) + ": " + transgressions + " transgressions";
		}
	}
	
	public static class Commandments {
		public static final Commandments INSTANCE = new Commandments();
		
		public final List<Commandment> defs = new ArrayList<Commandment>();
		private int k = 0;
		public final Commandment Murder = Murder();
		public final Commandment Theft = Theft();
		public final Commandment Adultery = Adultery();
		public final Commandment Heresy = Heresy();
		public final Commandment Deception = Deception();
		public final Commandment Witchcraft = Witchcraft();
		public final Commandment Carnivory = Carnivory();
		public final Commandment Xenophobia = Xenophobia();
		public final Commandment Tyranny = Tyranny();
		public final Commandment Treason = Treason();
		public PersonalCommandment[] generatePersonalCommandments() {
			PersonalCommandment[] result = new PersonalCommandment[defs.size()];
			for (int i = 0; i < result.length; i++) {result[i] = new PersonalCommandment(defs.get(i));}
			return result;
		}
		
		public abstract class Commandment {
			private final transient String verb;
			private final int id;
			public Commandment(String verb) {this.verb = verb; id = k++; defs.add(this);}
			public PersonalCommandment getFor(Clan c) {
				return c.FB.commandments[id];
			}
		}
		public final Commandment Murder() {
			return new Commandment("KILL") { };
		}
		public final Commandment Theft() {
			return new Commandment("STEAL") { };
		}
		public final Commandment Adultery() {
			return new Commandment("BE PROMISCUOUS") { };
		}
		public final Commandment Heresy() {
			return new Commandment("WORSHIP OTHER GODS") { };
		}
		public final Commandment Deception() {
			return new Commandment("LIE") { };
		}
		public final Commandment Witchcraft() {
			return new Commandment("PROPHECIZE") { };
		}
		public final Commandment Carnivory() {
			return new Commandment("EAT MEAT") { };
		}
		/**
		 * other sins do not count when they are against foreigners if this is inactive
		 */
		public final Commandment Xenophobia() {
			return new Commandment("TREAT HEATHENS UNEQUALLY") { };
		}
		/**
		 * lord shall not usurp citizen's assets (via ultra-high taxation for instance)
		 */
		public final Commandment Tyranny() {
			return new Commandment("TYRANNIZE SUBJECTS") { };
		}
		
		public final Commandment Treason() {
			return new Commandment("ABANDON THY LORD") { };
		}
	}
	

}
