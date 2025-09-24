package Sentiens;

import Defs.M_;
import Game.AGPmain;

public class Beliefs {
	
	public static interface ReligionType {
		public int summonPower(Clan clan);
	}
	
	public static final ReligionType ANIMISTIC = new ReligionType() {
		public int summonPower(Clan clan) {
			return 0; //TODO WILDERNESS!!
		}
	};
	public static final ReligionType SACRIFICIAL = new ReligionType() {
		public int summonPower(Clan clan) {
			return 0; //TODO sacrifice!
		}
	};
	public static final ReligionType RITUALISTIC = new ReligionType() {
		public int summonPower(Clan clan) {
			return AGPmain.rand.nextInt(clan.FB.getBeh(M_.OCD));
		}
	};
	public static final ReligionType SPIRITUAL = new ReligionType() {
		public int summonPower(Clan clan) {
			return AGPmain.rand.nextInt(clan.FB.getBeh(M_.OCD) + clan.FB.getBeh(M_.MADNESS));
		}
	};
	
	public static abstract class Ritual {
		private final String name;
		private boolean believed;
		private int fulfillments;

		public Ritual(String name) {
			this.name = name;
		}
		public boolean isBelieved() {return this.believed;}
		public void setBelieved(boolean b) {this.believed = b;}
		public void fulfill(int i) {this.fulfillments += i;}
		public void fulfill(double d) {fulfill(Math.round(d));}
		public int getFulfullments() {return this.fulfillments;}
		
		public void sacrificeGoblin() {}
		public void sacrificeDonkey() {}
		public void sacrificeBovad() {}
		public void donate(Clan receiver, double howMuch) {}
		public void prophecize(double priorProbSuccess, boolean success) {}
		
		@Override
		public String toString() {return (believed ? "Belief in " : "Doubt in ") + name + ": " + fulfillments + " fulfillments";}
	}

	public static class BeliefSystem {
		public final Ritual Meditation = Meditation();
	}
	
	public static Ritual Meditation() {
		return new Ritual("Meditation") {
			
		};
	}

	public static Ritual HumanSacrifice() {
		return new Ritual("Ritual Sacrifice") {
			@Override
			public void sacrificeGoblin() {fulfill(50);}
			@Override
			public void sacrificeDonkey() {fulfill(10);}
			@Override
			public void sacrificeBovad() {fulfill(5);}
		};
	}
	
	public static Ritual Charity() {
		return new Ritual("Charitable Giving") {
			@Override
			public void donate(Clan receiver, double howMuch) {
				final int multiplier = 10;
				fulfill(multiplier * howMuch / receiver.getMillet());
			}
		};
	}
	
	public static Ritual Prophecy() {
		return new Ritual("Prophecy") {
			@Override
			public void prophecize(double priorProbSuccess, boolean success) {
				final int multiplier = 10;
				fulfill(success ? (1.0-priorProbSuccess) * multiplier : priorProbSuccess * multiplier);
			}
		};
	}
}
