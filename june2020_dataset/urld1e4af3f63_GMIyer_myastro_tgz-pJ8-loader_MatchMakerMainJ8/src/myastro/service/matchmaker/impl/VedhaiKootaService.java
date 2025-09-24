package myastro.service.matchmaker.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import myastro.constants.Nakshatra;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.INakshatraKootaService;

public class VedhaiKootaService implements INakshatraKootaService {

	private static final VedhaiKootaService VEDHAI_KOOTA_SERVICE = new VedhaiKootaService();

	public static INakshatraKootaService getCheckVedhaiKootaService() {
		return VEDHAI_KOOTA_SERVICE;
	}

	private final Map<Nakshatra, Nakshatra> vedhaiDetailsCache;

	private VedhaiKootaService() {
		vedhaiDetailsCache = getVedhaiDetails();
	}

	@Override
	public String getImportance() {
		return null;
	}

	@Override
	public MatchResult checkKoota(Nakshatra boyStar, Nakshatra girlStar) {
		MatchResult matchResult = MatchResult.ADHAMA;

		if (vedhaiDetailsCache.get(boyStar) != null && !vedhaiDetailsCache.get(boyStar).equals(girlStar)) {
			matchResult = MatchResult.UTTAMA;
		}
		if (vedhaiDetailsCache.get(boyStar) == null) {
			if (!((boyStar.equals(Nakshatra.MRIGASHIRSHA) || boyStar.equals(Nakshatra.CHITRA) || boyStar.equals(Nakshatra.DHANISHTA)) && (girlStar.equals(Nakshatra.MRIGASHIRSHA) || girlStar.equals(Nakshatra.CHITRA) || girlStar.equals(Nakshatra.DHANISHTA)))) {
				matchResult = MatchResult.UTTAMA;
			}
		}
		return matchResult;
	}

	private Map<Nakshatra, Nakshatra> getVedhaiDetails() {
		Map<Nakshatra, Nakshatra> vedhaiDetails = new LinkedHashMap<Nakshatra, Nakshatra>();
		vedhaiDetails.put(Nakshatra.ASHVINI, Nakshatra.JYESHTHA);
		vedhaiDetails.put(Nakshatra.BHARANI, Nakshatra.ANURADHA);
		vedhaiDetails.put(Nakshatra.KRITTIKA, Nakshatra.VISHAKHA);
		vedhaiDetails.put(Nakshatra.ROHINI, Nakshatra.SVATI);
		vedhaiDetails.put(Nakshatra.ARDRA, Nakshatra.SHRAVANA);
		vedhaiDetails.put(Nakshatra.PUNARVASU, Nakshatra.UTTARA_ASHADHA);
		vedhaiDetails.put(Nakshatra.PUSHYA, Nakshatra.PURVA_ASHADHA);
		vedhaiDetails.put(Nakshatra.ASHLESHA, Nakshatra.MULA);
		vedhaiDetails.put(Nakshatra.MAGHA, Nakshatra.REVATI);
		vedhaiDetails.put(Nakshatra.PURVA_PHALGUNI, Nakshatra.UTTARA_BHADRAPADA);
		vedhaiDetails.put(Nakshatra.UTTARA_PHALGUNI, Nakshatra.PURVA_BHADRAPADA);
		vedhaiDetails.put(Nakshatra.HASTA, Nakshatra.SHATABHISHA);
		vedhaiDetails.put(Nakshatra.SVATI, Nakshatra.ROHINI);
		vedhaiDetails.put(Nakshatra.VISHAKHA, Nakshatra.KRITTIKA);
		vedhaiDetails.put(Nakshatra.ANURADHA, Nakshatra.BHARANI);
		vedhaiDetails.put(Nakshatra.JYESHTHA, Nakshatra.ASHVINI);
		vedhaiDetails.put(Nakshatra.MULA, Nakshatra.ASHLESHA);
		vedhaiDetails.put(Nakshatra.PURVA_ASHADHA, Nakshatra.PUSHYA);
		vedhaiDetails.put(Nakshatra.UTTARA_ASHADHA, Nakshatra.PUNARVASU);
		vedhaiDetails.put(Nakshatra.SHRAVANA, Nakshatra.ARDRA);
		vedhaiDetails.put(Nakshatra.SHATABHISHA, Nakshatra.HASTA);
		vedhaiDetails.put(Nakshatra.PURVA_BHADRAPADA, Nakshatra.UTTARA_PHALGUNI);
		vedhaiDetails.put(Nakshatra.UTTARA_BHADRAPADA, Nakshatra.PURVA_PHALGUNI);
		vedhaiDetails.put(Nakshatra.REVATI, Nakshatra.MAGHA);
		return vedhaiDetails;
	}

}