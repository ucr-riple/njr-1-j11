package myastro.service.matchmaker;

import myastro.constants.Rashi;
import myastro.constants.matchmaker.MatchResult;

public interface IRashiKootaService extends IKootaService {

	public MatchResult checkKoota(Rashi boyRashi, Rashi girlRashi);

}
