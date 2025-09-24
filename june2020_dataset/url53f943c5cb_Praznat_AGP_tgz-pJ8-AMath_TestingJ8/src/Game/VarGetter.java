package Game;

import Defs.*;
import Descriptions.Naming;
import Ideology.Values;
import Markets.MktO;
import Sentiens.*;
import Shirage.Shire;

public class VarGetter implements Misc {
	//types
	public static final int SHIREVARS = 0;
	public static final int MARKETVARS = 1;
	public static final int CLANBASIC = 2;
	public static final int CLANDISCS = 3;
	public static final int CLANRANKSANCS = 4;
	public static final int CLANSANCPCTS = 5;
	public static final int CLANPRESTS = 6;
	public static final int CLANBEHS = 7;

	//shire vars
	public static final int LONGITUDE = 0;
	public static final int LATITUDE = 1;
	public static final int ALTITUDE = 2;
	public static final int FERTILITY = 3;
	//market vars
	public static final int GOODNAME = 0;
	public static final int BID = 1;
	public static final int ASK = 2;
	public static final int BIDSZ = 3;
	public static final int ASKSZ = 4;
	public static final int LAST = 5;
	public static final int STAVG = 6;
	public static final int LTAVG = 7;
	//clan vars
	public static final int CLANNAME = 0;
	public static final int CLANJOB = 1;
	public static final int CLANQUEST = 2;
	public static final int SHIRENAME = 3;
	public static final int CLANORDER = 4;
	public static final int CLANASPIRATION = 5;
	public static final int CLANBOSS = 6;
	public static final int CLANCREED = 7;
	
	
	private String varName;
	private int type, var;
	
	private VarGetter(String n, int t, int v) {
		varName = n; type = t; var = v;
	}
	public static VarGetter[] popVGs() {
		VarGetter[] all = new VarGetter[13 + Values.All.length + P_.values().length - 1];
		int k = 0;
		all[k++] = new VarGetter("Name", CLANBASIC, CLANNAME);
		all[k++] = new VarGetter("Job", CLANBASIC, CLANJOB);
		all[k++] = new VarGetter("Quest", CLANBASIC, CLANQUEST);
		all[k++] = new VarGetter("Location", CLANBASIC, SHIRENAME);
		all[k++] = new VarGetter("Order", CLANBASIC, CLANORDER);
		all[k++] = new VarGetter("Creed", CLANBASIC, CLANCREED);
		all[k++] = new VarGetter("Liege", CLANBASIC, CLANBOSS);
		all[k++] = new VarGetter("Aspiration", CLANBASIC, CLANASPIRATION);
		all[k++] = new VarGetter("1stValue", CLANRANKSANCS, 0);
		all[k++] = new VarGetter("2ndValue", CLANRANKSANCS, 1);
		all[k++] = new VarGetter("3rdValue", CLANRANKSANCS, 2);
		all[k++] = new VarGetter("4thValue", CLANRANKSANCS, 3);
		all[k++] = new VarGetter("5thValue", CLANRANKSANCS, 4);
		for(int i = 0; i < Values.All.length; i++) {
			all[k++] = new VarGetter(Values.All[i].toString(), CLANSANCPCTS, i);
		}
		for(int i = 1; i < P_.length(); i++) {all[k++] = new VarGetter(Naming.prestName(P_.values()[i]), CLANPRESTS, i);}
		return all;
	}

	public static VarGetter[] mktVGs() {
		VarGetter[] all = new VarGetter[8];
		int k = 0;
		all[k++] = new VarGetter("Good", MARKETVARS, GOODNAME);
		all[k++] = new VarGetter("Bid", MARKETVARS, BID);
		all[k++] = new VarGetter("Offer", MARKETVARS, ASK);
		all[k++] = new VarGetter("Buyers", MARKETVARS, BIDSZ);
		all[k++] = new VarGetter("Sellers", MARKETVARS, ASKSZ);
		all[k++] = new VarGetter("Last", MARKETVARS, LAST);
		all[k++] = new VarGetter("STA", MARKETVARS, STAVG);
		all[k++] = new VarGetter("LTA", MARKETVARS, LTAVG);
		return all;
	}
	public String getVGName() {return varName;}
	public int getType() {return type;}
	public int getVar() {return var;}
	public int getVarInt() {return getVarInt(null, null, -1);}
	public int getVarInt(Clan dude) {return getVarInt(dude, null, -1);}
	public int getVarInt(Shire shire) {return getVarInt(null, shire, -1);}
	public int getVarInt(int good) {return getVarInt(null, null, good);}
	public int getVarInt(Clan dude, Shire shire, int good) {
		switch(type) {
		case MARKETVARS:
			switch(var) {
			case BID: return shire.getMarket(good).bestBid();
			case ASK: return shire.getMarket(good).bestOffer();
			case BIDSZ: return shire.getMarket(good).getBidSz();
			case ASKSZ: return shire.getMarket(good).getAskSz();
			case LAST: return shire.getMarket(good).lastPrice();
			case STAVG: return shire.getMarket(good).stAvg();
			case LTAVG: return shire.getMarket(good).ltAvg();
			}
		case CLANBASIC:
			switch(var) {
			case CLANNAME: dude.getID();
			case SHIRENAME: dude.myShire().getID();
			}
		case CLANSANCPCTS: return dude.FB.getSancPct(Values.All[var]);
		case CLANPRESTS: return dude.FB.getPrs(var);
		case CLANBEHS: return dude.FB.getBeh(var);
		default: return 0;
		}
	}
	public String getVarString() {return getVarString(null, null, -1);}
	public String getVarString(Clan dude) {return getVarString(dude, null, -1);}
	public String getVarString(Shire shire) {return getVarString(null, shire, -1);}
	public String getVarString(int good) {return getVarString(null, null, good);}
	public String getVarString(Clan dude, Shire shire, int good) {
		switch(type) {
		case MARKETVARS:
			switch(var) {
			case GOODNAME: return Naming.goodName(good, true, true);
			case BID: return MktO.bidString(getVarInt(dude, shire, good));
			case ASK: return MktO.offerString(getVarInt(dude, shire, good));
			case LAST:case STAVG:case LTAVG:  int p = getVarInt(dude, shire, good); return (p == -1 ? "-" : p+"");
			default: int i = getVarInt(dude, shire, good); return (i != 0 ? i + "" : "-");
			}
		case CLANBASIC:
			switch(var) {
			case CLANNAME: return dude.getNomen();
			case CLANJOB: return dude.getJob().getDesc(dude);
			case CLANBOSS: Clan boss = dude.getBoss(); return boss == dude ? "none" : boss.getNomen();
			case CLANCREED: return dude.FB.getDeusName();
			case CLANASPIRATION: return dude.getAspiration().getDesc();
			case CLANQUEST: return (dude.MB.QuestStack.empty() ? "none" : dude.MB.QuestStack.peek().shortName());
			case CLANORDER: return (dude.myOrder() == null ? "none" : dude.myOrder().getFollowerName());
			case SHIRENAME: return dude.myShire().getName();
			}
		case CLANRANKSANCS: return dude.FB.valueInPriority(var).toString();
		case CLANSANCPCTS: return getVarInt(dude) + "%";
		case CLANPRESTS: case CLANBEHS: return (int) Math.round((double)getVarInt(dude) / 16) + "%";
		}
		return getVarInt(dude, shire, good) + "";
	}
	

}
