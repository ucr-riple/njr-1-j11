package Sentiens;

import java.util.*;

import AMath.Calc;
import Defs.*;
import Descriptions.*;
import Descriptions.GobLog.Book;
import Descriptions.GobLog.Reportable;
import Game.*;
import Government.Order;
import Markets.*;
import Questing.Quest;
import Questing.Might.FormArmyQuest;
import Questing.Wealth.*;
import Sentiens.Law.Commandments;
import Sentiens.Stress.*;
import Shirage.Shire;

public class Clan implements Misc, Blameable {
	public static int DMC = 3; //daily millet consumption
	public static final int MIN_DMC_RESERVE = 15;
	protected static final int MUTATION_PCT = 2;
	//protected static final int MEMORY = 8;
	//bio
	protected byte[] name = new byte[2];
	protected String nameOverride = null;
	protected boolean gender;
	private int age;
	private int ageInherited; //at what age did it start "playing" (ancestor died)
	private int heritageLength; //number of ancestors remembered

	protected Clan suitor; //ID of suitor
	protected byte[] firstMateTraits;
	protected int numSpawns;
	protected int firstSpawnAgeDiff; //age when first child was born
	private int splendor;
	private int holiness;
	private int timesPrayed = 1;
	private int knowledgeAttribution;
	
	protected int ID;
	protected Shire homeShire, currentShire, governedShire;
	protected Job job, aspiration, backupJob;
	protected int[] assets;
	protected int[] inventory; //OBSOLETE
	private short specialweapon;
	protected int expIncome;
	protected Clan boss;
	protected Order order;
	protected int myB;
	protected int minionB;
	protected int subminionB;
	protected int minionN;
	protected int subminionN;
	protected int pointsBN;
	//serfs farm, mine, cut trees, and build
	//serfs revolt against master if not fed
	//in case of revolt, master replaced with SERF CREED, all other serfs become family
	
	private boolean active;
	protected boolean lastSuccess;
	//protected int act;
	protected int cumIncome;
	public Ideology FB;
	public Amygdala AB;
	public Memory MB;
	public Legacy LB;
	protected int lastBeh, lastBehN;
	protected Book goblog = new Book();
	protected Collection<DeathListener> deathListeners = new ArrayList<DeathListener>();
	
	public Clan() {}
	public Clan(Shire place, int id) {
		homeShire = place;
		currentShire = homeShire;
		ID = id;
		boss = this;
		suitor = null;
		assets = new int[numAssets];
		assets[millet] = 100;
//		inventory = recalcInv();
//		expTerms = new int[][] {{0}, {}, {}, {}};
		backupJob = Job.HUNTERGATHERER;
		job = Job.FARMER;  setRandomJob();
		cumIncome = 0;
		age = (AGPmain.rand.nextInt(100) * 365);
		ageInherited = age;
		specialweapon = (short) AGPmain.rand.nextInt(); //XWeapon.NULL;
		
		byte[] r = new byte[2];
		AGPmain.rand.nextBytes(r);
		name[0] = r[0]; name[1] = r[1];
		gender = AGPmain.rand.nextBoolean();
		FB = new Ideology(this);
		AB = new Amygdala(this);
		MB = new Memory();
		LB = new Legacy(this);
	}
	

	public void pursue() {
		daily();
		if (isActive()) {
			if (MB.QuestStack.empty()) {
				Q_ q = FB.randomValueInPriority().pursuit();
				Quest quest = Quest.QtoQuest(this, q);
				MB.QuestStack.add(quest);
			}
			Quest questToPursue = MB.QuestStack.peek();
			// BUG CHECK:
			if (questToPursue.getDoer() != this) throw new IllegalStateException(this + " has " + questToPursue.getDoer() + " 's quest");
			MB.QuestStack.peek().pursueQuest();
		}
		setActive(true);
	}
	
	public int getID() {return ID;}
	public int getShireID() {return myShire().getX() + myShire().getY() * AGPmain.getShiresX();}
	public Shire myShire() {return homeShire;}
	public void setHomeShire(Shire s) {homeShire = s;}
	public Shire currentShire() {return currentShire;}
	public void setCurrentShire(Shire s) {
		if (currentShire != s) goblog.addReport(GobLog.moveCurrentShire(currentShire, s));
		currentShire = s;
	}
	public void moveTowards(Shire target) {
		// TODO set current shire one step closer to target
		setCurrentShire(target);
	}
	public Shire getGovernedShire() {
		return governedShire;
	}
	public void setGovernedShire(Shire governedShire) {
		this.governedShire = governedShire;
	}
	/** market in current shire not home shire */
	public MktAbstract myMkt(int g) {return currentShire().getMarket(g);}
	public Library getRelevantLibrary() {return homeShire.getLibrary();}
	public int getAge() {return age / 365;}
	public int getHeritageLength() {
		return heritageLength;
	}
	public void setHeritageLength(int heritageLength) {
		this.heritageLength = heritageLength;
	}
	public boolean getGender() {return gender;}
	public void setGender(boolean g) {gender = g;}

	public int getNumGoods() {return Goods.numGoods;}
	public Clan getSuitor() {return suitor;}
	public void setSuitor(Clan C) {
		if (C == this) throw new IllegalStateException("illegal suitor");
		if (C != null && suitor != C) Commandments.INSTANCE.Adultery.getFor(C).commit();
		suitor = C;
	}
	public int getNumOffspring() {return numSpawns;}
	public byte[] getNameBytes() {return name;}
	public void overrideName(String newName) {nameOverride = newName;}
	public String getFirstName() {return nameOverride != null ? nameOverride : GobName.firstName(name[0], name[1], gender);}
	public String getNomen() {return GobName.fullName(this);}
	public String getSancName() {return FB.getDeusName();}
	public Job getJob() {return job;}
	public void setJob(Job j) {backupJob = job; job = j;}
	public Job getAspiration() {return aspiration;}
	public void setAspiration(Job j) {aspiration = j;}
	public Book getGoblog() {return goblog;}
	
	public void daily() {
		age++;
		final boolean starve = !eat();
		if (starve) {
			if (numSpawns > 0) {numSpawns--; addReport(GobLog.lostChild());} else {die(); addReport(GobLog.died());}
		}
		if (isHungry()) {
			addReport(GobLog.hungry());
			Quest currQ = !MB.QuestStack.isEmpty() ? MB.QuestStack.peek() : null;
			if (currQ != null) {
				if (currQ instanceof IntelligentLaborQuest) return; //currentlyHustling
				if (currQ instanceof FormArmyQuest) { // should be just having patron? belonging to order?
					Order o = myOrder();
					if (o != null) { // army of one...
						 if (o.requestFeed(this)) return;
					}
				}
			}
			MB.newQ(new IntelligentLaborQuest(this, Job.GatherMillet));
		}
	}
	public boolean isHungry() {
		return getMillet() < (MIN_DMC_RESERVE + FB.getBeh(M_.PARANOIA)) * DMC;
	}
	public void breed(Clan mate) {
		this.setSuitor(mate);
		mate.setSuitor(this);
		// FIRST child
		if (firstMateTraits == null) {
			firstMateTraits = mate.FB.copyFs();
			firstSpawnAgeDiff = age;
		}
		numSpawns++;
	}
	public void die() {
		// stuff happens
		boolean couldBecomeHeir = becomeHeir();
		
		for (DeathListener dl : deathListeners) {dl.onDeathOf(this);}
		// remove from populations
	}
	private boolean becomeHeir() {
		//stuff that happens anyway
		holiness = 0; timesPrayed = 1;
		
		if (firstMateTraits == null) {return false;} // unable to continue bloodline
		createHeir(this.FB, null);
		firstMateTraits = null;
		return true;
	}
	public void createHeir(Ideology fb, byte[] hypotheticalMateFs) {
		// lower prs by youth...
		final double prsMult = ((double)firstSpawnAgeDiff / age);
		for (P_ p : P_.values()) {fb.setPrs(p, (int)Math.round(prsMult * fb.getPrs(p)));}
		knowledgeAttribution *= prsMult;
		cumIncome = 0;
		age -= firstSpawnAgeDiff;
		ageInherited = age;
		// TODO mutate mems
		// sexual reproduction for F traits
		for (F_ f : F_.values()) {
			int newF;
			if (Calc.pPercent(MUTATION_PCT)) {
				newF = AGPmain.rand.nextInt(16);
				continue;
			}
			if (AGPmain.rand.nextBoolean()) {newF = FB.getFac(f);} // mine
			else if (hypotheticalMateFs != null) {newF = getFacMate(hypotheticalMateFs, f);}
			else {newF = getFacMate(firstMateTraits, f);} // mate's
			fb.setFac(f, newF);
		}
	}
	private int getFacMate(byte[] fs, F_ f) {
		return Ideology.getVar(f.ordinal(), fs);
	}
	
	public int getFamilySize() {return 1 + numSpawns;}
	
	public Clan getBoss() {return boss;}
	public Clan getTopBoss() { //i think broken
		if (getBoss() == null) {Calc.p(""+1/0); return this;}
		if (this == getBoss()) {return this;}
		else {return getBoss().getTopBoss();}
	}
	public boolean isDirectBossOf(Clan him) {
		final Clan hisBoss = him.getBoss();
		return hisBoss != him && this == hisBoss;
	}
	public boolean isSomeBossOf(Clan him) {return isSomeBossOf(him, this);}  //false if self!
	private boolean isSomeBossOf(Clan him, Clan orig) {
		final Clan hisBoss = him.getBoss();
		if (hisBoss == orig) {return true;}
		else if (hisBoss == null) {Calc.p(""+1/0); return false;}
		else if (hisBoss == him) {return false;}
		else {return isSomeBossOf(hisBoss, orig);}
	}
	public int distanceFromTopBoss() {
		Clan boss = this; int k = 1;
		while (boss != boss.getBoss()) {
			boss = boss.getBoss(); k++;
		}	return k;
	}
	public int getMinionTotal() {return minionN + subminionN;}
	public int getMinionN() {return minionN;}
	public int getSubminionN() {return subminionN;}
	public int getMinionPoints() {return minionB + subminionB;}
	public int getPointsBN() {return pointsBN;}
	public Order myOrder() {return order;}
	public Order myOrder(boolean createIfNull) {
		if (order == null) {order = Order.createBy(this);}
		return order;
	}
	public void setOrder(Order o) {order = o;}
	public void joinOrder(Order newOrder) {
		if (newOrder == null) {Calc.p(""+1/0); return;}
		if (order == null) {newOrder.addMember(this);}
		else {order.moveTo(this, newOrder);}
		Shire govS = getGovernedShire();
		if (govS != null) {order.acquireShire(govS, this);}
	}
	public boolean join(Clan newBoss) {
		if (this.isSomeBossOf(newBoss)) {return false;}  //forget it if im already above him
		Clan oldBoss = this.getBoss();
		if (oldBoss == newBoss) {return true;} // already is boss
		if (oldBoss != this) {oldBoss.removeMinion(this);}
		this.boss = newBoss;
		if (newBoss != this) {newBoss.addMinion(this);}
		if(newBoss.myOrder() == null) {Order.createBy(newBoss);}
		joinOrder(newBoss.myOrder());
		return true;
	}
	private void addMinion(Clan minion) {
		minionN++;   chgSubMinionN(1 + minion.getMinionTotal(), true);
	}
	private void removeMinion(Clan minion) {
		minionN--;   chgSubMinionN(-1 - minion.getMinionTotal(), true);
	}
	private void chgSubMinionN(int n, boolean first) {
		subminionN += n - (first ? Math.signum(n) : 0);
//		n += (first && n != 0 ? (n > 0 ? 1 : -1) : 0);
		Clan Boss = getBoss();
		if (Boss != this) {Boss.chgSubMinionN(n, false);}
	}
	public void chgMyB(int b) {
		myB += b; ///WHAT IS B???  soldiers vanquished? people converted?
		Clan Boss = getBoss(); int id = Boss.getID();
		if (id != -1 && id != getID()) {Boss.chgMinionB(b);}
	}
	private void chgMinionB(int b) {
		minionB += b;   chgSubMinionB(b);
	}
	private void chgSubMinionB(int b) {
		subminionB += b;
		Clan Boss = getBoss(); int id = Boss.getID();
		if (id != -1 && id != getID()) {Boss.chgSubMinionB(b);}
	}
//	public int calcPoints(Clan hypoBoss) {
//		Clan hypoTopBoss = hypoBoss.getTopBoss();
//		double P = (double)hypoTopBoss.useBeh(M_.PYRAMIDALITY)/15;
//		double L = (double)hypoTopBoss.useBeh(M_.LEADERSHIP)/15;
//		double M = (double)hypoTopBoss.useBeh(M_.MERITOCRACITY)/15;
//		double adjB = L*(P*subminionB + (1.0-P)*minionB) + (1.0-L)*myB;
//		double adjN = L*(P*subminionN + (1.0-P)*minionN) + (1.0-L)*1;
//		return (int) (Math.pow(adjB, M) * Math.pow(adjN, 1.0-M));
//	}
//	public int estimateWinnings(Clan hypoBoss) {
//		Clan hypoTopBoss = hypoBoss.getTopBoss();
//		int Winnings = hypoTopBoss.getExpIncome();
//		int TotalPoints = hypoTopBoss.getPointsBN();
//		int pointsBN = calcPoints(hypoTopBoss);
//		return Winnings * pointsBN / TotalPoints;
//	}

	private void setRandomJob() {   //just for sample purposes!
		int n = AGPmain.rand.nextInt(12);
		switch (n) {
		case 0: job = Job.HUNTERGATHERER; break;
		case 1: job = Job.TRADER; break;
		case 2: job = Job.HERDER; break;
		case 3: job = Job.MINER; break;
		case 4: job = Job.MASON; break;
		case 5: job = Job.CARPENTER; break;
		case 6: job = Job.SMITHY; break;
		default: job = Job.FARMER; break;
		}
		
	}
	
	public Act[] getJobActs() {return job.getActs();}
	public void setLastSuccess(boolean b) {lastSuccess = b;}
	public boolean getLastSuccess() {return lastSuccess;}
	public boolean isActive() {return active;}
	public void setActive(boolean a) {active = a;}
	public double getAvgIncome() {return (int) Math.round((double)cumIncome / (double)(10 + age - ageInherited));} // +10 too smooth out "newborns"
	public int getCumulativeIncome() {return cumIncome;}
	public void alterCumIncome(int m) {
		cumIncome += m;
	}

	public int getMillet() {return assets[millet];}
	public void setMillet(int m) {assets[millet] = m;}
	public boolean alterMillet(int c) {
		if ((long) assets[millet] + c > Integer.MAX_VALUE) {assets[millet] = Integer.MAX_VALUE; System.out.println("max millet reached " + ID);}
		else if (assets[millet] + c < 0) {
			assets[millet] = 0; System.out.println("millet below zero " + getNomen() + " the " + getJob() + " $" + assets[millet] + " altered by " + c);
//			throw new IllegalStateException(); // return false;
		}//System.out.println(1 / 0);}
		else {assets[millet] = assets[millet] + c; alterCumIncome(c); return true;}
		return false;
	}
	public long getNetAssetValue(Clan POV) {
		int sum = 0;   for (int g = 1; g < Misc.numAssets; g++) {
			int px = g != Misc.millet ? (POV != null ? POV.myMkt(g).sellablePX(POV) : this.myMkt(g).bestBid()) : 1;
			if (px != MktO.NOASK && px != MktO.NOBID) sum += getAssets(g) * px;
		}	return sum;
	}
	public int[] getAssets() {return assets;}
	public int getAssets(int g) {return assets[g];}
	public void incAssets(int g, int x) {if (x < 0) {System.out.println("error incAssets x is negative");}
		if (g == Misc.millet) throw new IllegalStateException("use alterMillet");
		assets[g] += x;
	}
	public void decAssets(int g, int x) {
		if (g == Misc.millet) throw new IllegalStateException("use alterMillet");
		if (assets[g] - x < 0) {
			System.out.println(Naming.goodName(g, false, false) + " error negative assets in decAssets for " + getNomen());
		}
		else {assets[g] -= x;}
	}
	public short getXWeapon() {return specialweapon;}
	public void setXWeapon(short w) {specialweapon = w;}
	public int[] recalcInv() {
		int count = 0; int k = 0;
		for (int i = 0; i < assets.length; i++) {if (assets[i] > 0) {count++;}}
		int[] INV = new int[count]; 
		for (int i = 0; i < assets.length; i++) {if (assets[i] > 0) {INV[k++] = i;}}
		return INV;
	}

	public int getSplendor() {return splendor;}
	public void chgSplendor(int i) {this.splendor += i;}
	public int getHoliness() {return holiness;}
	public void chgHoliness(int i) {this.holiness += i;}
	public int getTimesPrayed() {return timesPrayed;}
	public void incTimesPrayed() {timesPrayed++;}
	public int getKnowledgeAttribution() {return knowledgeAttribution;}
	public void incKnowledgeAttribution() {knowledgeAttribution++;}
	
	//OBSOLETE
	public int Buy(int good, int num) {
		int sum = 0;
		for (int i = 0; i < num; i++) {sum += Buy1(good);}
		// returns number bought successfully
		return sum;
	}
	public int Sell(int good, int num) {
		int sum = 0;
		for (int i = 0; i < num; i++) {sum += Sell1(good);}
		// returns number sold successfully
		return sum;
	}
	public void buyFail() {}
	public void sellFail() {}
	public int Buy1(int good) {return 0;}
	public int Sell1(int good) {return 0;}
	//end OBSOLETE


    public boolean eat() {
    	assets[Misc.millet] -= DMC;
    	if (assets[Misc.millet] < 0) {
    		assets[Misc.millet] = 0; // just for now
    		return false;
    		//TODOstarvation!
    	}	return true;
    }
    public boolean eatMeat() {
    	assets[Misc.meat]--;
    	if (assets[Misc.meat] < 0) {
    		assets[Misc.meat] = 0;
    		return false;
    	}
    	Commandments.INSTANCE.Carnivory.getFor(this).commit();
    	return true;
    }
    
    
    
    public void displayProfile() {

    }

    public double confuse(double in) {
    	//returns number between 50%-150% of original number at min arithmetic + max madness
    	double x = Math.abs((16 - FB.getPrs(P_.ARITHMETIC) + useBeh(M_.MADNESS)) * in / 64);
    	return in + (x == 0 ? 0 : - x + AGPmain.rand.nextDouble()*x*2);
    }
    
	public boolean iHigherMem(int m, Clan other) {
		return iHigherPrest(mem(m).getPrestige(), other);
	}
    
	public boolean iHigherPrest(P_ p, Clan other) {
		boolean iHigherSanc = (FB.compareRespect(other) >= 0);
		if (p == P_.SANCP) {
			return iHigherSanc;
		}
		else {
			int adj = useBeh(M_.SUPERST);
			return (FB.getPrs(p) + (iHigherSanc?adj:-adj) - other.FB.getPrs(p) >= 0 ? true : false);
		}
	}
	
	public void prch(Clan patron, Clan other) {
		int[] euR = patron.FB.getValueRanks();
		int[] eleR = other.FB.getValueRanks();
		int max = 0; int k = 0; int cur = 0;
		for (int i = euR.length-1; i>=0; i--) {
			cur = Math.abs(eleR[i] - euR[i]);
			if (cur >= max) {
				max = cur; k = i;
			}
		}
		cur = eleR[k] - euR[k];
		if (cur > 0) {glorf(k, other);}
		else if (cur < 0) {dnounce(k, other);}
	}
//	public void raiseSanc(int s, Clan other) {
//		switch (s) {
//			case RX : other.FB.setDisc(LORD, FB.getDisc(LORD)); break;
//			case DE : other.FB.setDisc(CREED, FB.getDisc(CREED)); break;
//			case HL : int hl = FB.getDisc(HOMELAND);
//				if(hl == other.getShireXY()) {other.FB.setDisc(HOMELAND, FB.getDisc(HOMELAND));} break;
//			case JB : other.FB.setDisc(ASPIRATION, FB.getDisc(ASPIRATION)); break;
//			default : break;
//		}
//		other.FB.upSanc(s);
//	}
	private void glorf(int s, Clan other) {
		if (!other.iHigherPrest(P_.SANCP, this)) {
//			raiseSanc(s, other);   FB.upPrest(P_.PREACHP);
		}   else {FB.downPrest(P_.PREACHP);}
	}
	private void dnounce(int s, Clan other) {
		if (!other.iHigherPrest(P_.SANCP, this)) {
			other.FB.downSanc(s);   FB.upPrest(P_.PREACHP);
		}   else {FB.downPrest(P_.PREACHP);}
	}
	public void compSanc(boolean iHiSanc, Clan other) {
		if (iHiSanc) {
			FB.upPrest(P_.CONFP);
			other.FB.downPrest(P_.RSPCP);
		}
		else {
			FB.downPrest(P_.CONFP);
			other.FB.upPrest(P_.RSPCP);
		}
	}
	@Deprecated
	public void discourse(Clan other) {
		boolean iHi = iHigherMem(lastBeh, other);
		if (lastBeh == 0) {
			compSanc(iHi, other);
			other.compSanc(iHi, this);
		}
		else {
			if (iHi) {other.FB.setBeh(lastBeh, FB.getBeh(lastBeh));}
			else {FB.setBeh(lastBeh, other.FB.getBeh(lastBeh));}
		}
		lastBehN = 0;
	}
	public double conversation(Clan other) {
		if (this == other) {return 0;}
		double respect = FB.randomValueInPriority().compare(this, other, this);
		if (respect > 0) {FB.setBeh(lastBeh, other.FB.getBeh(lastBeh)); lastBehN = 0;}
		return respect;
	}
	
	
	public int useBeh(M_ m) {
		if (AGPmain.rand.nextDouble() < (double) 1 / ++lastBehN) {
			lastBeh = m.ordinal();
		}
		return FB.getBeh(m);
	}
	public Mem mem(int m) {return AGPmain.TheRealm.getMem(m);}
	
	public void addReport(Reportable R) {goblog.addReport(R);}
	public Reportable[] getLog() {return goblog.getBook();}
	
	@Override
	public String toString() {return getNomen();}
}
