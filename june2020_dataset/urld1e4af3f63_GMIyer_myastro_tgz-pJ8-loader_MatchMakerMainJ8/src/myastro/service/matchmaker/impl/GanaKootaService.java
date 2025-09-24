package myastro.service.matchmaker.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import myastro.constants.Nakshatra;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.INakshatraKootaService;

public class GanaKootaService implements INakshatraKootaService {

	private static final GanaKootaService GANA_KOOTA_SERVICE = new GanaKootaService();

	public static INakshatraKootaService getGanaKootaService() {
		return GANA_KOOTA_SERVICE;
	}

	private final HashMap<Nakshatra, Gana> nakshatraGanaDetailsCache;

	private GanaKootaService() {
		nakshatraGanaDetailsCache = getNakshatraGanaDetails();
	}

	@Override
	public MatchResult checkKoota(Nakshatra boyStar, Nakshatra girlStar) {
		MatchResult matchResult = MatchResult.ADHAMA;
		Gana boyGana = nakshatraGanaDetailsCache.get(boyStar);
		Gana girlGana = nakshatraGanaDetailsCache.get(girlStar);

		if (boyGana.equals(girlGana) && !(boyGana.equals(Gana.RAKSHASA) && girlGana.equals(Gana.RAKSHASA))) {
			matchResult = MatchResult.UTTAMA;
		}
		if (girlGana.equals(Gana.DEVA) && boyStar.equals(Gana.MANUSHYA)) {
			matchResult = MatchResult.MADHYAMA;
		}
		if (girlGana.equals(Gana.DEVA) && boyStar.equals(Gana.RAKSHASA)) {
			matchResult = MatchResult.ADHAMA;
		}
		if (girlGana.equals(Gana.MANUSHYA) && boyStar.equals(Gana.RAKSHASA)) {
			matchResult = MatchResult.DEPENDS; // result will be moderate
		}

		return matchResult;
	}

	@Override
	public String getImportance() {
		return null;
	}

	private enum Gana {

		DEVA, MANUSHYA, RAKSHASA;

	}

	private HashMap<Nakshatra, Gana> getNakshatraGanaDetails() {

		HashMap<Nakshatra, Gana> nakshatraGanaDetails = new LinkedHashMap<Nakshatra, Gana>();

		nakshatraGanaDetails.put(Nakshatra.ASHVINI, Gana.DEVA);
		nakshatraGanaDetails.put(Nakshatra.BHARANI, Gana.MANUSHYA);
		nakshatraGanaDetails.put(Nakshatra.KRITTIKA, Gana.RAKSHASA);
		nakshatraGanaDetails.put(Nakshatra.ROHINI, Gana.MANUSHYA);
		nakshatraGanaDetails.put(Nakshatra.MRIGASHIRSHA, Gana.DEVA);
		nakshatraGanaDetails.put(Nakshatra.ARDRA, Gana.MANUSHYA);
		nakshatraGanaDetails.put(Nakshatra.PUNARVASU, Gana.DEVA);
		nakshatraGanaDetails.put(Nakshatra.PUSHYA, Gana.DEVA);
		nakshatraGanaDetails.put(Nakshatra.ASHLESHA, Gana.RAKSHASA);
		nakshatraGanaDetails.put(Nakshatra.MAGHA, Gana.RAKSHASA);
		nakshatraGanaDetails.put(Nakshatra.PURVA_PHALGUNI, Gana.MANUSHYA);
		nakshatraGanaDetails.put(Nakshatra.UTTARA_PHALGUNI, Gana.MANUSHYA);
		nakshatraGanaDetails.put(Nakshatra.HASTA, Gana.DEVA);
		nakshatraGanaDetails.put(Nakshatra.CHITRA, Gana.RAKSHASA);
		nakshatraGanaDetails.put(Nakshatra.SVATI, Gana.DEVA);
		nakshatraGanaDetails.put(Nakshatra.VISHAKHA, Gana.RAKSHASA);
		nakshatraGanaDetails.put(Nakshatra.ANURADHA, Gana.DEVA);
		nakshatraGanaDetails.put(Nakshatra.JYESHTHA, Gana.RAKSHASA);
		nakshatraGanaDetails.put(Nakshatra.MULA, Gana.RAKSHASA);
		nakshatraGanaDetails.put(Nakshatra.PURVA_ASHADHA, Gana.MANUSHYA);
		nakshatraGanaDetails.put(Nakshatra.UTTARA_ASHADHA, Gana.MANUSHYA);
		nakshatraGanaDetails.put(Nakshatra.SHRAVANA, Gana.DEVA);
		nakshatraGanaDetails.put(Nakshatra.DHANISHTA, Gana.RAKSHASA);
		nakshatraGanaDetails.put(Nakshatra.SHATABHISHA, Gana.RAKSHASA);
		nakshatraGanaDetails.put(Nakshatra.PURVA_BHADRAPADA, Gana.MANUSHYA);
		nakshatraGanaDetails.put(Nakshatra.UTTARA_BHADRAPADA, Gana.MANUSHYA);
		nakshatraGanaDetails.put(Nakshatra.REVATI, Gana.DEVA);

		return nakshatraGanaDetails;
	}

}
