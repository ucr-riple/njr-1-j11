package myastro.service.matchmaker.impl;

import java.util.ArrayList;

import myastro.constants.Nakshatra;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.INakshatraKootaService;

public class NaadiKootaService implements INakshatraKootaService {

	private static final NaadiKootaService NAADI_KOOTA_SERVICE = new NaadiKootaService();

	public static NaadiKootaService getCheckNaadiKootaService() {
		return NAADI_KOOTA_SERVICE;
	}

	private final ArrayList<Integer[]> naadiDetailsCache;

	private NaadiKootaService() {
		naadiDetailsCache = getNaadiDetails();
	}

	@Override
	public String getImportance() {
		return null;
	}

	@Override
	public MatchResult checkKoota(Nakshatra boyStar, Nakshatra girlStar) {
		MatchResult matchResult = MatchResult.ADHAMA;
		int boyStarNaadiPosition = getNakshatraNaadiPosition(boyStar);
		int girlStarNaadiPosition = getNakshatraNaadiPosition(girlStar);

		if (boyStarNaadiPosition != girlStarNaadiPosition) {
			matchResult = MatchResult.UTTAMA;
		}
		return matchResult;
	}

	private int getNakshatraNaadiPosition(Nakshatra nakshatra) {
		int naadiPosition = 0;
		int starPosition = nakshatra.ordinal() + 1;
		for (Integer[] nadiPositionGroup : naadiDetailsCache) {
			int position = 1;
			for (Integer nadiPosition : nadiPositionGroup) {
				if (nadiPosition == starPosition) {
					naadiPosition = position;
					break;
				}
				position++;
			}
			if (naadiPosition > 0) {
				break;
			}
		}
		return naadiPosition;
	}

	private ArrayList<Integer[]> getNaadiDetails() {

		ArrayList<Integer[]> naadiNakshatraPositions = new ArrayList<Integer[]>();

		int num = 1;
		for (int i = 1; i <= 9; i++) {
			if (i % 2 != 0) {
				naadiNakshatraPositions.add(new Integer[] { num, num + 1, num + 2 });
				num += 3;
			} else {
				naadiNakshatraPositions.add(new Integer[] { num + 2, num + 1, num });
				num += 3;
			}
		}
		return naadiNakshatraPositions;
	}
}
