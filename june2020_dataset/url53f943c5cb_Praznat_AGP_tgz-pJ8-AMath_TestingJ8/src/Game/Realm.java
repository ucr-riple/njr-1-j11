package Game;

import java.util.*;

import AMath.*;
import Ideology.Value;
import Sentiens.*;
import Shirage.Shire;

public class Realm {
	protected int shiresX;
	protected int shiresY;
	protected int startPop;
	protected Shire[] shires;
	private Clan[] population;
	private List<Clan> waitingForImmigration = new ArrayList<Clan>();
	private int day;
	private Mem[] MemDefs;
	//	private Sanc[] SancDefs;
	private Value[] ValueDefs;
	private VarGetter[] PopVarGetters;
	private VarGetter[] MktVarGetters;

	//private int[][] jobs;
//	private Clan Avatar;

	public Realm(int pX, int pY, int cN) {
		shiresX = pX;
		shiresY = pY;
		startPop = cN;
	}
	public static Realm makeRealm(int pX, int pY, int cN) {
		Calc.p("making realm");
		Realm newRealm = new Realm(pX, pY, cN);
		newRealm.generateShires(pX,pY);
		newRealm.generatePopulation(cN);
		return newRealm;
	}
	public static Realm makeTestRealm(int pX, int pY, int cN) {
		Calc.p("making test realm");
		Realm newRealm = new TestRealm(pX, pY, cN);
		newRealm.generateShires(pX,pY);
		newRealm.generatePopulation(cN);
		return newRealm;
	}
	public void setupDefs() {
		MemDefs = Mem.MemDefs();
		//		SancDefs = Sanc.SancDefs();
		PopVarGetters = VarGetter.popVGs();
		MktVarGetters = VarGetter.mktVGs();
	}
	public void goOnce() {
		Calc.p("day " + day);
		day++;
		int[] order = Calc.randomOrder(popSize());
		for (int i = 0; i < 1; i++) {
			for (int p : order) {population[p].pursue();}
			for (Shire s : shires) {s.newDay();}
		}
		setNewImmigrations();
	}

	public void run() {
		while (AGPmain.isGoing) {goOnce();}
	}

	private void generatePopulation(int C) {
		population = new Clan[C];
		for (int i = 0; i < C; i++) {
			Shire candidateHL = shires[AGPmain.rand.nextInt(shiresX * shiresY)];;
			while (candidateHL == null || !candidateHL.isPopulateable()) {
				candidateHL = shires[AGPmain.rand.nextInt(shiresX * shiresY)];
			}
			population[i] = new Clan(candidateHL, i);
			//population[i] = new Clan(z % shiresX, (int)(z/shiresX), i);
		}
	}

	protected void generateShires(int H, int V) {
		shires = new Shire[H*V];
		for (int x = 0; x < H; x++) {
			for (int y = 0; y < V; y++) {
				shires[x + y*H] = new Shire(x, y);
				shires[x + y*H].linkToPlot(AGPmain.mainGUI.MD.getPlotXY(x*2 + (y%2), y*2 + 1));
			}
		}
	}

	public void doCensus() {
		Clan curClan;
		for (int i = popSize() - 1; i >= 0; i--) {
			curClan = population[i];
			curClan.myShire().addToCensus(curClan);
		}
	}
	
	public void addToWaitingForImmigration(Clan c) {
		waitingForImmigration.add(c);
	}
	public void setNewImmigrations() {
		for (Clan clan : waitingForImmigration) {
			//TODO add to shire, remove from old shire
			final Shire oldShire = clan.myShire();
			final Shire newShire = clan.currentShire();
			clan.setHomeShire(newShire);
			oldShire.removeFromCensus(clan);
			newShire.addToCensus(clan);
		}
		waitingForImmigration.clear();
	}

//	public Clan getAvatar() {return Avatar;}
//	public void setAvatar(Clan C) {Avatar = C;}
	public int getNumShires() {return shiresX*shiresY;}
	public Shire getShire(int x, int y) {return shires[x + y*shiresX];}
	public Shire getShire(int xy) {return shires[xy];}
	public Shire getShire(String name) {
		for (Shire s : shires) {
			if (s.getName().toLowerCase().equals(name.toLowerCase())) return s;
		} return null;
	}
	public Shire[] getShires() {return shires;}
	public Clan getClan(int ID) {return population[ID];}
	public Clan getClan(String name) {
		for (Clan c : population) {
			if (c.getFirstName().toLowerCase().equals(name.toLowerCase())) return c;
		} return null;
	}
	public Clan getRandClan() {return population[Calc.randBetween(0, popSize())];}
	public Clan[] getPopulation() {return population;}
	public int popSize() {return population.length;}
	public int getDay() {return day;}

	//public ProductDefs getProductDefs() {return products;}
	public Mem getMem(int m) {return MemDefs[m];}
	//	public Value getValue(int v) {return Values.All[v];} //{return ValueDefs[v];}
	//	public Value[] getValues() {return Values.All;} //{return ValueDefs;}
	//	public Sanc getSanc(int s) {return SancDefs[s];}
	public VarGetter[] getPopVarGetters() {return PopVarGetters;}
	public VarGetter getPopVarGetter(int i) {return PopVarGetters[i];}
	public VarGetter[] getMktVarGetters() {return MktVarGetters;}
	public VarGetter getMktVarGetter(int i) {return MktVarGetters[i];}


}