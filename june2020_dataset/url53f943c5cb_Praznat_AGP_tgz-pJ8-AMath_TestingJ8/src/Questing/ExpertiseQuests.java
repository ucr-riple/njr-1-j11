package Questing;

import AMath.Calc;
import Avatar.SubjectiveType;
import Defs.*;
import Descriptions.GobLog;
import Game.AGPmain;
import Ideology.Values;
import Questing.Quest.PatronedQuest;
import Questing.Quest.PatronedQuestFactory;
import Sentiens.*;

public class ExpertiseQuests {
	public static PatronedQuestFactory getMinistryFactory() {return new PatronedQuestFactory(TeachQuest.class) {public Quest createFor(Clan c, Clan p) {return new TeachQuest(c, p);}};}

	public static final P_[] ALLSKILLS = {P_.CARPENTRY, P_.SMITHING, P_.MASONRY, P_.ARTISTRY, P_.PROSE, P_.LOBOTOMY,
		P_.COMBAT, P_.MARKSMANSHIP, P_.STRENGTH, P_.DANCE,
		P_.ARITHMETIC};
	

	public static class TeachQuest extends PatronedQuest {
		public TeachQuest(Clan P, Clan patron) {super(P, patron);}
		@Override
		public String description() {return "Teach Skill";}
		@Override
		public void pursue() {
			final P_ s = decideSkillToPractice(patron);
			final boolean success = practiceSkill(Me, null, s);
			if (success) {success(Values.EXPERTISE);}
			else {failure(Values.EXPERTISE);}
		}
	}
	
	
	public static class LearnQuest extends Quest {
		public LearnQuest(Clan P) {super(P);}
		@Override
		public String description() {return "Practice Skill";}

		private void pursue(P_ s, Clan teacher) {
			final boolean success = practiceSkill(Me, teacher, s);
			if (success) {success(Values.EXPERTISE);}
			else {failure(Values.EXPERTISE);}
		}
		
		@Override
		public void pursue() {
			final P_ s = decideSkillToPractice(Me);
			pursue(s, findTeacher(s, Me));
		}
		
		@Override
		public void avatarPursue() {
			avatarConsole().showChoices("Choose skill to practice", Me, ALLSKILLS, SubjectiveType.NO_ORDER, new Calc.Listener() {
				@Override
				public void call(Object arg) {
					final P_ s = ((P_) arg);
					pursue(s, findTeacher(s, Me));
				}
			});
		}
	}
	
	private static boolean isTeachingFor(Clan teacher, Clan student) {
		final QStack qs = teacher.MB.QuestStack;
		if (!qs.isEmpty()) {
			final Quest q = qs.peek();
			return (q instanceof TeachQuest && ((TeachQuest) q).getPatron().isSomeBossOf(student));
		}
		return false;
	}
	
	private static Clan findTeacher(P_ s, Clan student) {
		int bestPrs = 0; Clan bestC = null;
		for (Clan candidate : student.myShire().getCensus()) {
			if (isTeachingFor(candidate, student)) {
				final int prs = candidate.FB.getPrs(s);
				if (prs > bestPrs) {bestPrs = prs; bestC = candidate;}
			}
		}
		return bestC;
	}
	
	private static P_ decideSkillToPractice(Clan doer) {
		final P_[] relSkills = doer.FB.randomValueInPriority().getRelevantSkills();
		final int k = AGPmain.rand.nextInt(1 + relSkills.length + doer.FB.getBeh(M_.MADNESS) / 3);
		if (k < relSkills.length) {return relSkills[k];}
		else {return ALLSKILLS[AGPmain.rand.nextInt(ALLSKILLS.length)];}
	}

	public static boolean practiceSkill(Clan doer, P_ skill) {
		return practiceSkill(doer, null, skill);
	}
	private static boolean practiceSkill(Clan doer, Clan teacher, P_ skill) {
		final int meatModifier = (doer.eatMeat() ? 2 : 1);
		final int pctStrDown = 5 / meatModifier;
		final int pctStrUp = 15 * meatModifier;
		if (skill == null) {
			if (Calc.pPercent(pctStrDown)) {doer.FB.downPrest(P_.STRENGTH);}
			return false; // strength down
		}
		final int currPrs = doer.FB.getPrs(skill);
		int pSuccess = 15 - currPrs + (teacher == null ? 0 : Math.min(0, teacher.FB.getPrs(skill) - currPrs));
		boolean success = false;
		switch (skill) {
		case STRENGTH:
			if (Calc.pPercent(pctStrUp)) {
				success = true; doer.FB.upPrest(P_.STRENGTH);
			}
			break; // strength up
		case ARTISTRY:
		case PROSE:
		case LOBOTOMY:
			if (Calc.pPercent(pctStrDown)) {
				doer.FB.downPrest(P_.STRENGTH);
			} // strength down
			// no break
		case MARKSMANSHIP:
		case MASONRY:
		case CARPENTRY:
		case SMITHING:
		case DANCE:
		case COMBAT:
			if (Calc.pPercent(pSuccess)) {
				success = true; doer.FB.upPrest(skill);
			}
			break; // strength unchanged
		default: break;
		}
		doer.addReport(GobLog.practice(skill, success));
		return success;
	}
}
