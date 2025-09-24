package rivercityransom.impl;

import rivercityransom.enumerations.Tresor;
import rivercityransom.services.ObjetService;

public class ObjetImpl implements ObjetService {

	private Tresor tresor;

	@Override
	public boolean isEquipable() {
		return tresor == Tresor.CHAINEDEVELO
				|| tresor == Tresor.POUBELLEMETALLIQUE;
	}

	@Override
	public boolean isValeurMarchande() {
		return tresor == Tresor.UNDOLLAR || tresor == Tresor.CINQUANTECENTIMES;
	}

	@Override
	public Tresor getTresor() {
		return tresor;
	}

	@Override
	public void init(Tresor tresor) {
		this.tresor = tresor;
	}

}
