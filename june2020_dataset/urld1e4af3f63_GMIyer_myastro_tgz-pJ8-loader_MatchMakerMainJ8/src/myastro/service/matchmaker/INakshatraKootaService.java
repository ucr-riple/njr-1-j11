package myastro.service.matchmaker;

import myastro.constants.Nakshatra;
import myastro.constants.matchmaker.MatchResult;

public interface INakshatraKootaService extends IKootaService {	

	public MatchResult checkKoota(Nakshatra boyStar, Nakshatra girlStar);

}
