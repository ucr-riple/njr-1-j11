package myastro.service.matchmaker.impl;

import myastro.constants.Nakshatra;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.INakshatraKootaService;
import myastro.service.matchmaker.MatchMakerCommonService;

public class DinaKootaService implements INakshatraKootaService {

	private static final DinaKootaService DINA_KOOTA_SERVICE = new DinaKootaService();

	public static INakshatraKootaService getCheckDinaKootaService() {
		return DINA_KOOTA_SERVICE;
	}

	@Override
	public MatchResult checkKoota(Nakshatra boyStar, Nakshatra girlStar) {
		MatchResult matchResult = MatchResult.ADHAMA;

		if (boyStar.equals(girlStar)) {

			switch (boyStar) {
			case ROHINI:
			case ARDRA:
			case MAGHA:
			case VISHAKHA:
			case SHRAVANA:
			case HASTA:
			case UTTARA_BHADRAPADA:
			case REVATI:
				matchResult = MatchResult.UTTAMA;
				break;

			case ASHVINI:
			case KRITTIKA:
			case MRIGASHIRSHA:
			case PUNARVASU:
			case PUSHYA:
			case PURVA_PHALGUNI:
			case UTTARA_PHALGUNI:
			case CHITRA:
			case PURVA_ASHADHA:
			case UTTARA_ASHADHA:
				matchResult = MatchResult.UTTAMA;
				break;

			case BHARANI:
			case ASHLESHA:
			case ANURADHA:
			case DHANISHTA:
			case SHATABHISHA:
			case SVATI:
			case MULA:
			case PURVA_BHADRAPADA:
			case JYESHTHA:
				matchResult = MatchResult.UTTAMA;
				break;
			}
			matchResult = MatchResult.UTTAMA;
		}

		int distance = MatchMakerCommonService.getGirlToBoyNakshatraDistance(boyStar, girlStar);

		switch (distance) {
		case 2:
		case 4:
		case 6:
		case 8:
		case 9:
		case 11:
		case 13:
		case 15:
		case 18:
		case 20:
		case 24:
		case 26:
			matchResult = MatchResult.UTTAMA;
			break;

		default:
			break;
		}
		return matchResult;
	}

	@Override
	public String getImportance() {
		return null;
	}

}
