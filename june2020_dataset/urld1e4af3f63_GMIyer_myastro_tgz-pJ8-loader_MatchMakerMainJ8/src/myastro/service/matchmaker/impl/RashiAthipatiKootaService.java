package myastro.service.matchmaker.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import myastro.constants.Graha;
import myastro.constants.Rashi;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.IRashiKootaService;

public class RashiAthipatiKootaService implements IRashiKootaService {

	private static final RashiAthipatiKootaService RASHI_ATHIPATI_KOOTA_SERVICE = new RashiAthipatiKootaService();

	public static IRashiKootaService getCheckRashiAthipatiKootaService() {
		return RASHI_ATHIPATI_KOOTA_SERVICE;
	}

	private final Map<Graha, HashMap<Graha, GrahaRelation>> rashiAthipatiDetailsCache;

	private RashiAthipatiKootaService() {
		rashiAthipatiDetailsCache = getRashiAthipatiDetails();
	}

	@Override
	public MatchResult checkKoota(Rashi boyRashi, Rashi girlRashi) {
		MatchResult matchResult = MatchResult.ADHAMA;

		GrahaRelation boyToGirlGrahaRelation = rashiAthipatiDetailsCache.get(boyRashi.getRashiAthipati()).get(girlRashi.getRashiAthipati());
		GrahaRelation girlToBoyGrahaRelation = rashiAthipatiDetailsCache.get(girlRashi.getRashiAthipati()).get(boyRashi.getRashiAthipati());

		if (boyToGirlGrahaRelation.equals(GrahaRelation.FRIEND) && girlToBoyGrahaRelation.equals(GrahaRelation.FRIEND)) {
			matchResult = MatchResult.UTTAMA;
		}
		if ((boyToGirlGrahaRelation.equals(GrahaRelation.FRIEND) && girlToBoyGrahaRelation.equals(GrahaRelation.EQUAL)) || (boyToGirlGrahaRelation.equals(GrahaRelation.EQUAL) && girlToBoyGrahaRelation.equals(GrahaRelation.FRIEND))) {
			matchResult = MatchResult.UTTAMA;
		}
		if ((boyToGirlGrahaRelation.equals(GrahaRelation.FRIEND) && girlToBoyGrahaRelation.equals(GrahaRelation.ENEMY)) || (boyToGirlGrahaRelation.equals(GrahaRelation.ENEMY) && girlToBoyGrahaRelation.equals(GrahaRelation.FRIEND))) {
			matchResult = MatchResult.MADHYAMA;
		}

		return matchResult;
	}

	@Override
	public String getImportance() {
		return null;
	}

	private enum GrahaRelation {
		FRIEND, EQUAL, ENEMY;
	}

	private Map<Graha, HashMap<Graha, GrahaRelation>> getRashiAthipatiDetails() {

		Map<Graha, HashMap<Graha, GrahaRelation>> rashiAthipatiDetails = new LinkedHashMap<Graha, HashMap<Graha, GrahaRelation>>();
		rashiAthipatiDetails.put(Graha.SURYA, getSuryaGrahaRelations());
		rashiAthipatiDetails.put(Graha.CHANDRA, getChandraGrahaRelations());
		rashiAthipatiDetails.put(Graha.MANGAL, getMangalGrahaRelations());
		rashiAthipatiDetails.put(Graha.BUDHA, getBudhaGrahaRelations());
		rashiAthipatiDetails.put(Graha.BRIHASPATI, getBrihaspatiGrahaRelations());
		rashiAthipatiDetails.put(Graha.SHUKRA, getShukraGrahaRelations());
		rashiAthipatiDetails.put(Graha.SHANI, getShaniGrahaRelations());
		rashiAthipatiDetails.put(Graha.RAHU, getRahuGrahaRelations());
		rashiAthipatiDetails.put(Graha.KETU, getKetuGrahaRelations());
		return rashiAthipatiDetails;
	}

	private HashMap<Graha, GrahaRelation> getSuryaGrahaRelations() {
		HashMap<Graha, GrahaRelation> grahaRelation = new HashMap<Graha, GrahaRelation>();
		grahaRelation.put(Graha.SURYA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.CHANDRA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.MANGAL, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.BUDHA, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.BRIHASPATI, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.SHUKRA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.SHANI, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.RAHU, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.KETU, GrahaRelation.ENEMY);
		return grahaRelation;
	}

	private HashMap<Graha, GrahaRelation> getChandraGrahaRelations() {
		HashMap<Graha, GrahaRelation> grahaRelation = new HashMap<Graha, GrahaRelation>();
		grahaRelation.put(Graha.SURYA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.CHANDRA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.MANGAL, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.BUDHA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.BRIHASPATI, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.SHUKRA, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.SHANI, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.RAHU, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.KETU, GrahaRelation.ENEMY);
		return grahaRelation;
	}

	private HashMap<Graha, GrahaRelation> getMangalGrahaRelations() {
		HashMap<Graha, GrahaRelation> grahaRelation = new HashMap<Graha, GrahaRelation>();
		grahaRelation.put(Graha.SURYA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.CHANDRA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.MANGAL, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.BUDHA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.BRIHASPATI, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.SHUKRA, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.SHANI, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.RAHU, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.KETU, GrahaRelation.ENEMY);
		return grahaRelation;
	}

	private HashMap<Graha, GrahaRelation> getBudhaGrahaRelations() {
		HashMap<Graha, GrahaRelation> grahaRelation = new HashMap<Graha, GrahaRelation>();
		grahaRelation.put(Graha.SURYA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.CHANDRA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.MANGAL, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.BUDHA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.BRIHASPATI, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.SHUKRA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.SHANI, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.RAHU, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.KETU, GrahaRelation.EQUAL);
		return grahaRelation;
	}

	private HashMap<Graha, GrahaRelation> getBrihaspatiGrahaRelations() {
		HashMap<Graha, GrahaRelation> grahaRelation = new HashMap<Graha, GrahaRelation>();
		grahaRelation.put(Graha.SURYA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.CHANDRA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.MANGAL, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.BUDHA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.BRIHASPATI, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.SHUKRA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.SHANI, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.RAHU, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.KETU, GrahaRelation.EQUAL);
		return grahaRelation;
	}

	private HashMap<Graha, GrahaRelation> getShukraGrahaRelations() {
		HashMap<Graha, GrahaRelation> grahaRelation = new HashMap<Graha, GrahaRelation>();
		grahaRelation.put(Graha.SURYA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.CHANDRA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.MANGAL, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.BUDHA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.BRIHASPATI, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.SHUKRA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.SHANI, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.RAHU, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.KETU, GrahaRelation.FRIEND);
		return grahaRelation;
	}

	private HashMap<Graha, GrahaRelation> getShaniGrahaRelations() {
		HashMap<Graha, GrahaRelation> grahaRelation = new HashMap<Graha, GrahaRelation>();
		grahaRelation.put(Graha.SURYA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.CHANDRA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.MANGAL, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.BUDHA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.BRIHASPATI, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.SHUKRA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.SHANI, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.RAHU, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.KETU, GrahaRelation.FRIEND);
		return grahaRelation;
	}

	private HashMap<Graha, GrahaRelation> getRahuGrahaRelations() {
		HashMap<Graha, GrahaRelation> grahaRelation = new HashMap<Graha, GrahaRelation>();
		grahaRelation.put(Graha.SURYA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.CHANDRA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.MANGAL, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.BUDHA, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.BRIHASPATI, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.SHUKRA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.SHANI, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.RAHU, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.KETU, GrahaRelation.FRIEND);
		return grahaRelation;
	}

	private HashMap<Graha, GrahaRelation> getKetuGrahaRelations() {
		HashMap<Graha, GrahaRelation> grahaRelation = new HashMap<Graha, GrahaRelation>();
		grahaRelation.put(Graha.SURYA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.CHANDRA, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.MANGAL, GrahaRelation.ENEMY);
		grahaRelation.put(Graha.BUDHA, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.BRIHASPATI, GrahaRelation.EQUAL);
		grahaRelation.put(Graha.SHUKRA, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.SHANI, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.RAHU, GrahaRelation.FRIEND);
		grahaRelation.put(Graha.KETU, GrahaRelation.FRIEND);
		return grahaRelation;
	}

}
