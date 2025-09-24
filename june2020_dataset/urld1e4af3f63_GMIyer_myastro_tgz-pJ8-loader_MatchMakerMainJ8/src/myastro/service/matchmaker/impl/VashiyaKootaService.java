package myastro.service.matchmaker.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import myastro.constants.Rashi;
import myastro.constants.matchmaker.MatchResult;
import myastro.service.matchmaker.IRashiKootaService;

public class VashiyaKootaService implements IRashiKootaService {

	private static final VashiyaKootaService VASHIYA_KOOTA_SERVICE = new VashiyaKootaService();

	public static VashiyaKootaService getCheckVashiyaKootaService() {
		return VASHIYA_KOOTA_SERVICE;
	}

	private final Map<Rashi, ArrayList<Rashi>> vashiyaDetailsCache;

	private VashiyaKootaService() {
		vashiyaDetailsCache = getVashiyaDetails();
	}

	@Override
	public MatchResult checkKoota(Rashi boyRashi, Rashi girlRashi) {
		MatchResult matchResult = MatchResult.ADHAMA;
		if (vashiyaDetailsCache.get(girlRashi).contains(boyRashi)) {
			matchResult = MatchResult.UTTAMA;
		}
		return matchResult;
	}

	@Override
	public String getImportance() {
		return null;
	}

	private Map<Rashi, ArrayList<Rashi>> getVashiyaDetails() {
		Map<Rashi, ArrayList<Rashi>> rashiAthipatiDetails = new LinkedHashMap<Rashi, ArrayList<Rashi>>();

		rashiAthipatiDetails.put(Rashi.MESA, getMesaRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.VRISABHA, getVrisabhaRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.MITHUNA, getMithunaRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.KARKATA, getKarkataRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.SIMHA, getSimhaRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.KANYA, getKanyaRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.TULA, getTulaRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.VRISHCIKA, getVrishcikaRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.DHANUS, getDhanusRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.MAKARA, getMakaraRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.KUMBHA, getKumbhaRashiVashiyaDetails());
		rashiAthipatiDetails.put(Rashi.MINA, getMinaRashiVashiyaDetails());

		return rashiAthipatiDetails;
	}

	private ArrayList<Rashi> getMesaRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.SIMHA);
		rashiList.add(Rashi.VRISHCIKA);
		return rashiList;
	}

	private ArrayList<Rashi> getVrisabhaRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.KARKATA);
		rashiList.add(Rashi.TULA);
		return rashiList;
	}

	private ArrayList<Rashi> getMithunaRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.KANYA);
		return rashiList;
	}

	private ArrayList<Rashi> getKarkataRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.VRISHCIKA);
		rashiList.add(Rashi.DHANUS);
		return rashiList;
	}

	private ArrayList<Rashi> getSimhaRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.MAKARA);
		return rashiList;
	}

	private ArrayList<Rashi> getKanyaRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.VRISABHA);
		rashiList.add(Rashi.MINA);
		return rashiList;
	}

	private ArrayList<Rashi> getTulaRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.MAKARA);
		return rashiList;
	}

	private ArrayList<Rashi> getVrishcikaRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.KARKATA);
		rashiList.add(Rashi.KANYA);
		return rashiList;
	}

	private ArrayList<Rashi> getDhanusRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.MINA);
		return rashiList;
	}

	private ArrayList<Rashi> getMakaraRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.KUMBHA);
		return rashiList;
	}

	private ArrayList<Rashi> getKumbhaRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.MINA);
		return rashiList;
	}

	private ArrayList<Rashi> getMinaRashiVashiyaDetails() {
		ArrayList<Rashi> rashiList = new ArrayList<Rashi>();
		rashiList.add(Rashi.MAKARA);
		return rashiList;
	}

}
