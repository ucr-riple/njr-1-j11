package myastro.service.matchmaker.impl;

import myastro.constants.Nakshatra;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.INakshatraKootaService;
import myastro.service.matchmaker.MatchMakerCommonService;

public class SthreeDeerkhaKootaService implements INakshatraKootaService {

	private static final SthreeDeerkhaKootaService STHREE_DEERKHA_KOOTA_SERVICE = new SthreeDeerkhaKootaService();

	public static SthreeDeerkhaKootaService getCheckSthreeDeerkhaKootaService() {
		return STHREE_DEERKHA_KOOTA_SERVICE;
	}

	@Override
	public MatchResult checkKoota(Nakshatra boyStar, Nakshatra girlStar) {
		MatchResult matchResult = MatchResult.ADHAMA;
		int distance = MatchMakerCommonService.getGirlToBoyNakshatraDistance(boyStar, girlStar);

		if (distance >= 13) {
			matchResult = MatchResult.UTTAMA;
		}
		return matchResult;
	}

	@Override
	public String getImportance() {
		// TODO Auto-generated method stub
		return null;
	}

}
