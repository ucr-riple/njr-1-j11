package myastro.service.matchmaker.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import myastro.constants.Nakshatra;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.INakshatraKootaService;

public class YoniKootaService implements INakshatraKootaService {

	private static final YoniKootaService YONI_KOOTA_SERVICE = new YoniKootaService();

	public static INakshatraKootaService getCheckYoniKootaService() {
		return YONI_KOOTA_SERVICE;
	}

	private final HashMap<YoniAnimal, YoniAnimal> yoniAnimalEnimityCache;
	private final HashMap<Nakshatra, NakshatraYoniDetails> nakshatraYoniDetailsCache;

	private YoniKootaService() {
		yoniAnimalEnimityCache = getYoniAnimalEnimity();
		nakshatraYoniDetailsCache = getNakshatraYoniDetails();
	}

	@Override
	public MatchResult checkKoota(Nakshatra boyStar, Nakshatra girlStar) {
		MatchResult matchResult = MatchResult.ADHAMA;
		Boolean enemity = Boolean.FALSE;
		NakshatraYoniDetails boyNakshatraYoniDetails = nakshatraYoniDetailsCache.get(boyStar);
		NakshatraYoniDetails girlNakshatraYoniDetails = nakshatraYoniDetailsCache.get(girlStar);

		if (girlNakshatraYoniDetails.getYoniAnimal().equals(yoniAnimalEnimityCache.get(boyNakshatraYoniDetails.getYoniAnimal()))) {
			enemity = Boolean.TRUE;
		}

		if ((boyNakshatraYoniDetails.getYoniGender().equals(YoniGender.MALE) && girlNakshatraYoniDetails.getYoniGender().equals(YoniGender.FEMALE))) {
			if (boyNakshatraYoniDetails.getYoniAnimal().equals(girlNakshatraYoniDetails.getYoniAnimal())) {
				matchResult = MatchResult.UTTAMA;
			}
			if (!boyNakshatraYoniDetails.getYoniAnimal().equals(girlNakshatraYoniDetails.getYoniAnimal()) && !enemity) {
				matchResult = MatchResult.MADHYAMA;
			}
			if (!boyNakshatraYoniDetails.getYoniAnimal().equals(girlNakshatraYoniDetails.getYoniAnimal()) && enemity) {
				matchResult = MatchResult.ADHAMA;
			}
		}
		if ((boyNakshatraYoniDetails.getYoniGender().equals(YoniGender.MALE) && girlNakshatraYoniDetails.getYoniGender().equals(YoniGender.MALE)) || (boyNakshatraYoniDetails.getYoniGender().equals(YoniGender.FEMALE) && girlNakshatraYoniDetails.getYoniGender().equals(YoniGender.FEMALE))) {
			if (boyNakshatraYoniDetails.getYoniAnimal().equals(girlNakshatraYoniDetails.getYoniAnimal())) {
				matchResult = MatchResult.MADHYAMA;
			}
			if (!boyNakshatraYoniDetails.getYoniAnimal().equals(girlNakshatraYoniDetails.getYoniAnimal()) && !enemity) {
				matchResult = MatchResult.MADHYAMA;
			}
			if (!boyNakshatraYoniDetails.getYoniAnimal().equals(girlNakshatraYoniDetails.getYoniAnimal()) && enemity) {
				matchResult = MatchResult.ADHAMA;
			}
		}

		return matchResult;
	}

	@Override
	public String getImportance() {
		return null;
	}

	private enum YoniAnimal {

		HORSE, ELEPHANT, GOAT, SNAKE, DOG, CAT, RAT, COW, BUFFALO, TIGER, DEER, MONKEY, LION, MONGOOSE;
	}

	private enum YoniGender {

		MALE, FEMALE;

	}

	private class NakshatraYoniDetails {

		private YoniGender yoniGender;
		private YoniAnimal yoniAnimal;

		private NakshatraYoniDetails(YoniGender yoniGender, YoniAnimal yoniAnimal) {
			this.yoniGender = yoniGender;
			this.yoniAnimal = yoniAnimal;
		}

		public YoniGender getYoniGender() {
			return yoniGender;
		}

		public YoniAnimal getYoniAnimal() {
			return yoniAnimal;
		}

	}

	private HashMap<Nakshatra, NakshatraYoniDetails> getNakshatraYoniDetails() {

		HashMap<Nakshatra, NakshatraYoniDetails> nakshatraYoniDetails = new LinkedHashMap<Nakshatra, NakshatraYoniDetails>();

		nakshatraYoniDetails.put(Nakshatra.ASHVINI, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.HORSE));
		nakshatraYoniDetails.put(Nakshatra.BHARANI, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.ELEPHANT));
		nakshatraYoniDetails.put(Nakshatra.KRITTIKA, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.GOAT));
		nakshatraYoniDetails.put(Nakshatra.ROHINI, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.SNAKE));
		nakshatraYoniDetails.put(Nakshatra.MRIGASHIRSHA, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.SNAKE));
		nakshatraYoniDetails.put(Nakshatra.ARDRA, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.DOG));
		nakshatraYoniDetails.put(Nakshatra.PUNARVASU, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.CAT));
		nakshatraYoniDetails.put(Nakshatra.PUSHYA, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.GOAT));
		nakshatraYoniDetails.put(Nakshatra.ASHLESHA, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.CAT));
		nakshatraYoniDetails.put(Nakshatra.MAGHA, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.RAT));
		nakshatraYoniDetails.put(Nakshatra.PURVA_PHALGUNI, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.RAT));
		nakshatraYoniDetails.put(Nakshatra.UTTARA_PHALGUNI, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.COW));
		nakshatraYoniDetails.put(Nakshatra.HASTA, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.BUFFALO));
		nakshatraYoniDetails.put(Nakshatra.CHITRA, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.TIGER));
		nakshatraYoniDetails.put(Nakshatra.SVATI, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.BUFFALO));
		nakshatraYoniDetails.put(Nakshatra.VISHAKHA, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.TIGER));
		nakshatraYoniDetails.put(Nakshatra.ANURADHA, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.DEER));
		nakshatraYoniDetails.put(Nakshatra.JYESHTHA, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.DEER));
		nakshatraYoniDetails.put(Nakshatra.MULA, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.DOG));
		nakshatraYoniDetails.put(Nakshatra.PURVA_ASHADHA, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.MONKEY));
		nakshatraYoniDetails.put(Nakshatra.UTTARA_ASHADHA, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.MONGOOSE));
		nakshatraYoniDetails.put(Nakshatra.SHRAVANA, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.MONKEY));
		nakshatraYoniDetails.put(Nakshatra.DHANISHTA, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.LION));
		nakshatraYoniDetails.put(Nakshatra.SHATABHISHA, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.HORSE));
		nakshatraYoniDetails.put(Nakshatra.PURVA_BHADRAPADA, new NakshatraYoniDetails(YoniGender.MALE, YoniAnimal.LION));
		nakshatraYoniDetails.put(Nakshatra.UTTARA_BHADRAPADA, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.COW));
		nakshatraYoniDetails.put(Nakshatra.REVATI, new NakshatraYoniDetails(YoniGender.FEMALE, YoniAnimal.ELEPHANT));

		return nakshatraYoniDetails;
	}

	private HashMap<YoniAnimal, YoniAnimal> getYoniAnimalEnimity() {

		HashMap<YoniAnimal, YoniAnimal> yoniAnimalEnimity = new LinkedHashMap<YoniAnimal, YoniAnimal>();

		yoniAnimalEnimity.put(YoniAnimal.HORSE, YoniAnimal.BUFFALO);
		yoniAnimalEnimity.put(YoniAnimal.ELEPHANT, YoniAnimal.LION);
		yoniAnimalEnimity.put(YoniAnimal.GOAT, YoniAnimal.MONKEY);
		yoniAnimalEnimity.put(YoniAnimal.SNAKE, YoniAnimal.MONGOOSE);
		yoniAnimalEnimity.put(YoniAnimal.DOG, YoniAnimal.DEER);
		yoniAnimalEnimity.put(YoniAnimal.CAT, YoniAnimal.RAT);
		yoniAnimalEnimity.put(YoniAnimal.COW, YoniAnimal.TIGER);
		yoniAnimalEnimity.put(YoniAnimal.BUFFALO, YoniAnimal.HORSE);
		yoniAnimalEnimity.put(YoniAnimal.LION, YoniAnimal.ELEPHANT);
		yoniAnimalEnimity.put(YoniAnimal.MONKEY, YoniAnimal.GOAT);
		yoniAnimalEnimity.put(YoniAnimal.MONGOOSE, YoniAnimal.SNAKE);
		yoniAnimalEnimity.put(YoniAnimal.DEER, YoniAnimal.DOG);
		yoniAnimalEnimity.put(YoniAnimal.RAT, YoniAnimal.CAT);
		yoniAnimalEnimity.put(YoniAnimal.TIGER, YoniAnimal.COW);

		return yoniAnimalEnimity;
	}

}
