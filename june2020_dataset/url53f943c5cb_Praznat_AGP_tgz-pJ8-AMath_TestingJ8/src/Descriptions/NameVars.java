package Descriptions;

import Government.Order;
import Sentiens.Clan;

public class NameVars {
	public interface NameVar {
		public String of(Clan clan);
	}

	public static final NameVar MINISTER_MILITARY = new NameVar() {
		@Override
		public String of(Clan clan) {
			Order order = clan.myOrder();  //switch on some aspect of the order
			return "Supreme Commander";
		}
	};
	public static final NameVar MINISTER_RELIGION = new NameVar() {
		@Override
		public String of(Clan clan) {
			Order order = clan.myOrder();  //switch on some aspect of the order
			return "High Priest";
		}
	};
	public static final NameVar MINISTER_ECONOMY = new NameVar() {
		@Override
		public String of(Clan clan) {
			Order order = clan.myOrder();  //switch on some aspect of the order
			return "Treasurer";
		}
	};
	public static final NameVar MINISTER_INTERIOR = new NameVar() {
		@Override
		public String of(Clan clan) {
			Order order = clan.myOrder();  //switch on some aspect of the order
			return "Grand Vizier";
		}
	};
}
