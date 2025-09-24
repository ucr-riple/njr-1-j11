package myastro.service.matchmaker.impl;

import myastro.constants.Rashi;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.IRashiKootaService;
import myastro.service.matchmaker.MatchMakerCommonService;

public class RashiKootaService implements IRashiKootaService {

	private static final RashiKootaService RASHI_KOOTA_SERVICE = new RashiKootaService();

	public static IRashiKootaService getCheckRashiKootaService() {
		return RASHI_KOOTA_SERVICE;
	}

	@Override
	public MatchResult checkKoota(Rashi boyRashi, Rashi girlRashi) {
		MatchResult matchResult = MatchResult.ADHAMA;
		int boyToGirlRashiDistance = MatchMakerCommonService.getBoyToGirlRashiDistance(boyRashi, girlRashi);
		int girlToBoyRashiDistance = MatchMakerCommonService.getGirlToBoyRashiDistance(boyRashi, girlRashi);

		if (boyRashi.equals(girlRashi)) {
			matchResult = MatchResult.UTTAMA;
		}
		if ((boyToGirlRashiDistance == 7 || girlToBoyRashiDistance == 7) && !(boyRashi.equals(Rashi.KUMBHA) || girlRashi.equals(Rashi.KUMBHA) || boyRashi.equals(Rashi.KARKATA) || girlRashi.equals(Rashi.KARKATA))) {
			matchResult = MatchResult.UTTAMA;
		}

		switch (girlToBoyRashiDistance) {
		case 3:
		case 4:
		case 5:
		case 9:
		case 10:
		case 11:
			matchResult = MatchResult.MADHYAMA;
			break;
		}

		switch (boyToGirlRashiDistance) {
		case 3:
		case 4:
		case 5:
		case 9:
		case 10:
		case 11:
			matchResult = MatchResult.MADHYAMA;
			break;
		}

		return matchResult;
	}

	@Override
	public String getImportance() {
		return null;
	}

}
