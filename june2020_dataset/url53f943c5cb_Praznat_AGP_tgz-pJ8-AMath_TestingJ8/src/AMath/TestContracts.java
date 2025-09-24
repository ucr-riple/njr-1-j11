package AMath;

import Defs.P_;
import Game.Contract;
import Ideology.Values;
import Questing.Quest.TargetQuest;
import Questing.Might.MightQuests;
import Sentiens.Clan;
import Shirage.Shire;

public class TestContracts extends Testing {

	private static Shire s;
	private static Clan a;
	private static Clan b;
	
	public static void doAllContractTests() {
		testChallengeMightQuest();
		testChallengeMightQuest();
		testChallengeMightQuest();
		testChallengeMightQuest();
		testChallengeMightQuest();
		testChallengeMightQuest();
		testDemandTribute();
		testDemandService();
		testOfferThreat();
		testDemandService();
		testOfferThreat();
		testDemandService();
		testOfferThreat();
		testDemandService();
		testOfferThreat();
	}
	
	private static void resetContracts() {
		reset();
		s = testRealm.getClan(0).myShire();
		a = s.getCensus(0);
		b = s.getCensus(1);
		a.setActive(true);
		b.setActive(true);
		a.setMillet(1000);
		b.setMillet(1000);
		a.FB.setPrs(P_.COMBAT, 0);
		b.FB.setPrs(P_.COMBAT, 0);
	}
	private static void testDemandTribute() {
		resetContracts();
		makePuritan(a, Values.WEALTH, Values.RIGHTEOUSNESS);
		final int originalMillet = 1000;
		a.setMillet(originalMillet);
		Contract.getNewContract(a, b);
		final int demandedMillet = 500;
		Contract.getInstance().demandTribute(demandedMillet);
		final double theoretical = a.FB.weightOfValue(Values.WEALTH) * Values.logComp(originalMillet - demandedMillet, originalMillet);
		affirm(Contract.getInstance().getDemandValue() == theoretical);
	}
	
	private static void testDemandService() {
		resetContracts();
		System.out.println(a.getNomen() + " vs " + b.getNomen());
		Contract.getNewContract(a, b);
		Contract.getInstance().demandService(Values.BEAUTY);
		System.out.println("service value: " + Contract.getInstance().getDemandValue());
	}
	
	private static void testOfferThreat() {
		resetContracts();
		System.out.println(a.getNomen() + " vs " + b.getNomen());
		Contract.getNewContract(a, b);
		Contract.getInstance().threatenMight();
		System.out.println("threaten might value: " + Contract.getInstance().getDemandValue());
		Contract.getNewContract(a, b);
		Contract.getInstance().threatenLife();
		System.out.println("threaten life value: " + Contract.getInstance().getDemandValue());
		Contract.getNewContract(a, b);
		Contract.getInstance().threatenLifeAndProperty();
		System.out.println("threaten life and property value: " + Contract.getInstance().getDemandValue());
		Contract.getNewContract(a, b);
		Contract.getInstance().threatenProperty();
		System.out.println("threaten property value: " + Contract.getInstance().getDemandValue());
		Contract.getNewContract(a, b);
		Contract.getInstance().threatenLineage();
		System.out.println("threaten lineage value: " + Contract.getInstance().getDemandValue());
	}
	

	private static void testChallengeMightQuest() {
		resetContracts();
		makePuritan(a, Values.WEALTH, Values.RIGHTEOUSNESS);
		makePuritan(b, Values.WEALTH, Values.RIGHTEOUSNESS);
		a.MB.newQ(new MightQuests.ChallengeMightQuest(a, Values.WEALTH));
		((TargetQuest)a.MB.QuestStack.peek()).setTarget(b);
		a.pursue();
		final double demandVal = Contract.getInstance().getPreCalcedDemandValue();
		final double offerVal = Contract.getInstance().getPreCalcedOfferValue();
		System.out.println("demand value: " + demandVal);
		System.out.println("offer value: " + offerVal);
		affirm(demandVal < 0);
		affirmSoft(offerVal > 10);
	}
	
}
