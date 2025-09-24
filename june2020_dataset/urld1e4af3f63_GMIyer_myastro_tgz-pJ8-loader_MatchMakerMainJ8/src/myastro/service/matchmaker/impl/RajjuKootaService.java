package myastro.service.matchmaker.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import myastro.constants.Nakshatra;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.INakshatraKootaService;

public class RajjuKootaService implements INakshatraKootaService {

	private static final RajjuKootaService RAJJU_KOOTA_SERVICE = new RajjuKootaService();

	public static INakshatraKootaService getCheckRajjuKootaService() {
		return RAJJU_KOOTA_SERVICE;
	}

	private final HashMap<Nakshatra, Rajju> nakshatraRajjuDetailsCache;

	private RajjuKootaService() {
		nakshatraRajjuDetailsCache = getNakshatraRajjuDetails();
	}

	@Override
	public MatchResult checkKoota(Nakshatra boyStar, Nakshatra girlStar) {
		MatchResult matchResult = MatchResult.ADHAMA;
		Rajju boyRajju = nakshatraRajjuDetailsCache.get(boyStar);
		Rajju girlRajju = nakshatraRajjuDetailsCache.get(girlStar);

		if (!boyRajju.equals(girlRajju)) {
			matchResult = MatchResult.UTTAMA;
		}

		return matchResult;
	}

	@Override
	public String getImportance() {
		return null;
	}

	private enum Rajju {

		SHIRO, KANTA, UDHARA, OORU, PADHA;

	}

	private HashMap<Nakshatra, Rajju> getNakshatraRajjuDetails() {

		HashMap<Nakshatra, Rajju> nakshatraGanaDetails = new LinkedHashMap<Nakshatra, Rajju>();

		nakshatraGanaDetails.put(Nakshatra.ASHVINI, Rajju.PADHA);
		nakshatraGanaDetails.put(Nakshatra.BHARANI, Rajju.OORU);
		nakshatraGanaDetails.put(Nakshatra.KRITTIKA, Rajju.UDHARA);
		nakshatraGanaDetails.put(Nakshatra.ROHINI, Rajju.KANTA);
		nakshatraGanaDetails.put(Nakshatra.MRIGASHIRSHA, Rajju.SHIRO);
		nakshatraGanaDetails.put(Nakshatra.ARDRA, Rajju.KANTA);
		nakshatraGanaDetails.put(Nakshatra.PUNARVASU, Rajju.UDHARA);
		nakshatraGanaDetails.put(Nakshatra.PUSHYA, Rajju.OORU);
		nakshatraGanaDetails.put(Nakshatra.ASHLESHA, Rajju.PADHA);
		nakshatraGanaDetails.put(Nakshatra.MAGHA, Rajju.PADHA);
		nakshatraGanaDetails.put(Nakshatra.PURVA_PHALGUNI, Rajju.OORU);
		nakshatraGanaDetails.put(Nakshatra.UTTARA_PHALGUNI, Rajju.UDHARA);
		nakshatraGanaDetails.put(Nakshatra.HASTA, Rajju.KANTA);
		nakshatraGanaDetails.put(Nakshatra.CHITRA, Rajju.SHIRO);
		nakshatraGanaDetails.put(Nakshatra.SVATI, Rajju.KANTA);
		nakshatraGanaDetails.put(Nakshatra.VISHAKHA, Rajju.UDHARA);
		nakshatraGanaDetails.put(Nakshatra.ANURADHA, Rajju.OORU);
		nakshatraGanaDetails.put(Nakshatra.JYESHTHA, Rajju.PADHA);
		nakshatraGanaDetails.put(Nakshatra.MULA, Rajju.PADHA);
		nakshatraGanaDetails.put(Nakshatra.PURVA_ASHADHA, Rajju.OORU);
		nakshatraGanaDetails.put(Nakshatra.UTTARA_ASHADHA, Rajju.UDHARA);
		nakshatraGanaDetails.put(Nakshatra.SHRAVANA, Rajju.KANTA);
		nakshatraGanaDetails.put(Nakshatra.DHANISHTA, Rajju.SHIRO);
		nakshatraGanaDetails.put(Nakshatra.SHATABHISHA, Rajju.KANTA);
		nakshatraGanaDetails.put(Nakshatra.PURVA_BHADRAPADA, Rajju.UDHARA);
		nakshatraGanaDetails.put(Nakshatra.UTTARA_BHADRAPADA, Rajju.OORU);
		nakshatraGanaDetails.put(Nakshatra.REVATI, Rajju.PADHA);

		return nakshatraGanaDetails;
	}

}
