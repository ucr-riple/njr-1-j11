package myastro.service.matchmaker;

import myastro.constants.Nakshatra;
import myastro.constants.Rashi;

public class MatchMakerCommonService {

	public static Integer getGirlToBoyNakshatraDistance(Nakshatra boyStar, Nakshatra girlStar) {
		int boyNumber = boyStar.ordinal() + 1;
		int girlNumber = girlStar.ordinal() + 1;
		int distance = 0;

		if (boyNumber > girlNumber) {
			distance = boyNumber - (girlNumber - 1);
		}
		if (boyNumber < girlNumber) {
			distance = (boyNumber + 28) - girlNumber;
		}
		if (boyNumber == girlNumber) {
			distance = 1;
		}
		return distance;
	}

	public static Integer getGirlToBoyRashiDistance(Rashi boyRashi, Rashi girlRashi) {
		int boyNumber = boyRashi.ordinal() + 1;
		int girlNumber = girlRashi.ordinal() + 1;
		int distance = 0;

		if (boyNumber > girlNumber) {
			distance = boyNumber - (girlNumber - 1);
		}
		if (boyNumber < girlNumber) {
			distance = (boyNumber + 13) - girlNumber;
		}
		if (boyNumber == girlNumber) {
			distance = 1;
		}
		return distance;
	}

	public static Integer getBoyToGirlRashiDistance(Rashi boyRashi, Rashi girlRashi) {
		int boyNumber = boyRashi.ordinal() + 1;
		int girlNumber = girlRashi.ordinal() + 1;
		int distance = 0;

		if (boyNumber < girlNumber) {
			distance = girlNumber - (boyNumber - 1);
		}
		if (boyNumber > girlNumber) {
			distance = (girlNumber + 13) - boyNumber;
		}
		if (boyNumber == girlNumber) {
			distance = 1;
		}
		return distance;
	}

}
