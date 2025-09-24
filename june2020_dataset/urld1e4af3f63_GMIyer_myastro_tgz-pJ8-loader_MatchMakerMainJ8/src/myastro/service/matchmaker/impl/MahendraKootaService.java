package myastro.service.matchmaker.impl;

import myastro.constants.Nakshatra;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.INakshatraKootaService;
import myastro.service.matchmaker.MatchMakerCommonService;

public class MahendraKootaService implements INakshatraKootaService {

	private static final MahendraKootaService MAHENDRA_KOOTA_SERVICE = new MahendraKootaService();

	public static MahendraKootaService getMahendraKootaService() {
		return MAHENDRA_KOOTA_SERVICE;
	}

	@Override
	public MatchResult checkKoota(Nakshatra boyStar, Nakshatra girlStar) {
		MatchResult matchResult = MatchResult.ADHAMA;
		int distance = MatchMakerCommonService.getGirlToBoyNakshatraDistance(boyStar, girlStar);
		switch (distance) {
		case 4:
		case 7:
		case 10:
		case 13:
		case 16:
		case 19:
		case 22:
		case 25:
			matchResult = MatchResult.UTTAMA;
			break;
		}
		return matchResult;
	}

	@Override
	public String getImportance() {
		return null;
	}

}
