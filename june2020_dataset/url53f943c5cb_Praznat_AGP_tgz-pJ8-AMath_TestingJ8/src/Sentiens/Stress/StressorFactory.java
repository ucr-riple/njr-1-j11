package Sentiens.Stress;

import Defs.*;
import Descriptions.GobLog;
import Game.*;
import Ideology.*;
import Questing.ImmigrationQuests;
import Questing.Knowledge.KnowledgeBlock;
import Questing.Might.*;
import Questing.Might.MightQuests.ChallengeMightQuest;
import Sentiens.Clan;
import Shirage.Shire;

public class StressorFactory {

	/** blame shire, job, or trade behs 
	 * easiest to change behs, then job, then shire (leaving shire means leaving assets)
	 */
	@SuppressWarnings("rawtypes")
	public static Stressor createWealthStressor(Clan clan) {
		final Library library = clan.myShire().getLibrary();
		// TODO behs
		KnowledgeBlock jobKb = library.findKnowledge(K_.JOBS);
		if (jobKb != null) {
			return createJobStressor(clan.getJob(), jobKb);
		}
		KnowledgeBlock shireKb = library.findKnowledge(SK_.valToSK.get(Values.WEALTH));
		if (shireKb != null) {
			return createShireStressor(clan.myShire(), Values.WEALTH, shireKb);
		}
		return createJobStressor(clan.getJob());
	}

	public static Stressor createShireStressor(Shire blamee, final Value whySucks) {
		return createShireStressor(blamee, whySucks, null);
	}
	@SuppressWarnings("rawtypes")
	public static Stressor createShireStressor(Shire blamee, final Value whySucks, final KnowledgeBlock shireKb) {
		return new Stressor(Stressor.ANNOYANCE, blamee) {
			@Override
			public boolean respond(Clan responder) {
				if (blamee != responder.myShire()) {
					System.out.println("blamed non-home shire");
					// TODO this should only happen when you are blaming a different shire.
					return true;
				}
				KnowledgeBlock kb = shireKb != null ? shireKb : responder.myShire().getLibrary().findKnowledge(SK_.valToSK.get(whySucks));
				Shire newShire;
				if (kb != null) {
					kb.useKnowledge(responder, false);
					Shire bestShire = (Shire) kb.getXs()[0];
					if (bestShire == blamee) return false;
					else newShire = bestShire;
				} else {
					newShire = responder.myShire().getSomeNeighbor();
				}
				if (responder.myShire() != newShire) {responder.MB.newQ(new ImmigrationQuests.EmigrateQuest(responder, newShire));}
				return true;
			}
		};
	}

	public static Stressor createJobStressor(Job job) {
		return createJobStressor(job, null);
	}
	@SuppressWarnings("rawtypes")
	public static Stressor createJobStressor(Job job, final KnowledgeBlock jobKb) {
		return new Stressor(Stressor.ANNOYANCE, job) {
			@Override
			public boolean respond(Clan responder) {
				if (blamee != responder.getJob()) {
					System.out.println("blamed different job");
					return true;
				}
				KnowledgeBlock kb = jobKb != null ? jobKb : responder.myShire().getLibrary().findKnowledge(K_.JOBS);
				Job newJob;
				if (kb != null) {
					kb.useKnowledge(responder, false);
					newJob = (Job) kb.getXs()[0];
				} else {
					newJob = Job.HUNTERGATHERER;
				}
				if (newJob != blamee) {responder.setJob(newJob); return true;}
				else return false;
			}
		};
	}	
	
	
	public static Stressor createBloodVengeanceStressor(Clan blamee) {
		return new Stressor(Stressor.HATRED, blamee) {
			@Override
			public boolean respond(Clan responder) {
				// TODO dedicate to revenge!
				Clan target = (Clan) blamee;
				responder.addReport(GobLog.beginBloodVengeance(target));
				responder.MB.newQ(WarQuest.start(responder, target)); // TODO WarQuest needs to know this is to the death
				return false; // you never feel good cuz this just starts a long process of revenge
			}
		};
	}
	
	public static Stressor createPayTaxesStressor(Clan blamee) {
		return new Stressor(Stressor.ANNOYANCE, blamee) {
			@Override
			public boolean respond(Clan responder) {
				final Clan taxer = (Clan)blamee;
				final double pDefeat = 1 - MightQuests.expPvictory(responder, taxer, false);
				if (responder.FB.getSancPct(Values.WEALTH) > 100 * pDefeat) {
					ChallengeMightQuest cmq = new ChallengeMightQuest(responder);
					cmq.setTarget(taxer);
					responder.MB.newQ(cmq);
				} else {
					responder.AB.add(createShireStressor(responder.myShire(), Values.WEALTH));
				}
				return true;
			}
		};
	}
}
