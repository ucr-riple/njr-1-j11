package rivercityransom.contracts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rivercityransom.decorators.GestionCombatDecorator;
import rivercityransom.enumerations.Commande;
import rivercityransom.enumerations.Tresor;
import rivercityransom.error.InvariantError;
import rivercityransom.error.PostconditionError;
import rivercityransom.error.PreconditionError;
import rivercityransom.services.GangsterService;
import rivercityransom.services.GestionCombatService;
import rivercityransom.services.PersonnageService;
import rivercityransom.services.TerrainService;

public class GestionCombatContract extends GestionCombatDecorator {

	public GestionCombatContract(GestionCombatService delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		PersonnageService[] goodguys = { ryan(), alex() };
		List<GangsterService> badguys = new ArrayList<GangsterService>(
				gangster());
		badguys.add(slick());

		// collisionPerso
		for (PersonnageService p : goodguys) {
			List<GangsterService> g2 = collisionPerso(p);
			if (g2.isEmpty()) {
				continue;
			}
			for (GangsterService g : g2) {
				if (!((Math.abs(p.getX() - g.getX()) <= 1)
						&& (Math.abs(p.getY() - g.getY()) <= 1) && (Math.abs(p
						.getZ() - g.getZ()) <= 1))) {
					throw new InvariantError("collisionPerso");
				}
			}
		}
		// collisionGangster
		for (GangsterService g : badguys) {
			List<PersonnageService> p2 = collisionGangster(g);
			if (p2.isEmpty()) {
				continue;
			}
			for (PersonnageService p : p2) {
				if (!((Math.abs(g.getX() - p.getX()) <= 1)
						&& (Math.abs(g.getY() - p.getY()) <= 1) && (Math.abs(g
						.getZ() - p.getZ()) <= 1))) {
					throw new InvariantError("collisionGangster");
				}
			}
		}
	}

	@Override
	public void init(int largeurTerrain, int hauteurTerrain,
			int profondeurTerrain) {
		if (!(largeurTerrain > 0)) {
			throw new PreconditionError("init");
		}
		if (!(hauteurTerrain > 0)) {
			throw new PreconditionError("init");
		}
		if (!(profondeurTerrain > 0)) {
			throw new PreconditionError("init");
		}
		super.init(largeurTerrain, hauteurTerrain, profondeurTerrain);
		checkInvariant();
		if (!(getTerrain().nbBlocLargeur() == largeurTerrain)) {
			throw new PostconditionError("init");
		}
		if (!(getTerrain().nbBlocHauteur() == hauteurTerrain)) {
			throw new PostconditionError("init");
		}
		if (!(getTerrain().nbBlocProfondeur() == profondeurTerrain)) {
			throw new PostconditionError("init");
		}
		if (!(estFrappe("Alex") == false)) {
			throw new PostconditionError("init");
		}
		if (!(estFrappe("Ryan") == false)) {
			throw new PostconditionError("init");
		}
		if (!(estFrappe("Slick") == false)) {
			throw new PostconditionError("init");
		}

		for (GangsterService g : gangster()) {
			if (!(estFrappe(g.nom()) == false)) {
				throw new PostconditionError("init");
			}
			if (!(estGele(g.nom()) == false)) {
				throw new PostconditionError("init");
			}
		}
		if (!(estGele("Alex") == false)) {
			throw new PostconditionError("init");
		}
		if (!(estGele("Ryan") == false)) {
			throw new PostconditionError("init");
		}
		if (!(estGele("Slick") == false)) {
			throw new PostconditionError("init");
		}
	}

	@Override
	public void gerer(Commande commandRyan, Commande commandAlex) {

		HashMap<String, Boolean> estGele_atPre = new HashMap<>();
		estGele_atPre.put(ryan().nom(), super.estGele(ryan().nom()));
		estGele_atPre.put(alex().nom(), super.estGele(alex().nom()));
		estGele_atPre.put(slick().nom(), super.estGele(slick().nom()));
		for (GangsterService gangster : gangster()) {
			estGele_atPre.put(gangster.nom(), super.estGele(gangster.nom()));
		}

		HashMap<String, Boolean> estFrappe_atPre = new HashMap<>();
		estFrappe_atPre.put(ryan().nom(), super.estGele(ryan().nom()));
		estFrappe_atPre.put(alex().nom(), super.estGele(alex().nom()));
		estFrappe_atPre.put(slick().nom(), super.estGele(slick().nom()));
		for (GangsterService gangster : gangster()) {
			estFrappe_atPre.put(gangster.nom(), super.estGele(gangster.nom()));
		}

		checkInvariant();
		super.gerer(commandRyan, commandAlex);
		checkInvariant();

		// post 1 : estFrappe pour perso :: pas faisable (depend de la cmd de
		// l'attaqueur (donc du gangster))
		//
		// estFrappe(gerer(C, cr, ca), nom(perso))
		// = !estGele(C,nom(attacker))
		// && cmdAttacker=FRAPPE && attacker = collisionPerso(perso)
		//

		// post 2* : estFrappe pour gangster
		//
		// estFrappe(gerer(C, cr, ca), nom(gangster))
		// = !estGele(C,nom(attacker))
		// && cmdAttacker=FRAPPE && attacker = collisionGangster(gangster)
		//

		for (GangsterService gangster : gangster()) {
			if (super.estFrappe(gangster.nom())) {
				List<PersonnageService> attacker = super
						.collisionGangster(gangster);
				for (PersonnageService p : attacker) {
					Commande cmdAttacker = null;
					if (p == null)
						continue;
					if (p.nom().compareTo("Ryan") == 0) {
						cmdAttacker = commandRyan;
					} else {
						cmdAttacker = commandAlex;
					}
					if (cmdAttacker != Commande.FRAPPE) {
						throw new PostconditionError("Postcondtion numero 2");
					}
					if (estGele_atPre.get(p.nom())) {
						throw new PostconditionError("Postcondition numero 2");
					}
				}
			}
		}

		// post 3 : estGele pour perso :: pas faisable car pas d'accès au nombre
		// de gel restant
		//
		// estGele(gerer(C, cr, ca), nom(perso))
		// = estFrappe(gerer(C, cr, ca), nom(perso))
		// || !estGele(C, nom(perso)) && cmdPerso = FRAPPE
		//

		// post 4 : estGele pour gangster :: pas faisable (depend de la cmd du
		// gangster)
		//
		// estGele(gerer(C, cr, ca), nom(gangster))
		// = estFrappe(gerer(C, cr, ca), nom(gangster))
		// || !estGele(C, nom(gangster)) && cmdgangster = FRAPPE
		//

		// post 5 : ryan, faire retrait ou jeter si besoin :: pas faisable
		// (depend des cmd aléatoires)
		//
		// ryan(gerer(C, cr, ca))
		// = ryan(C) si !estFrappe(gerer(C, cr, ca), Personnage::nom(ryan(C)))
		// = Personnage::retrait(ryan(C),
		// Gangster::force(collisionPerso(C, ryan(C))))
		// (si estEquipe(collisionPerso(C)) alors * 2)
		//
		// et si Gangster::estEquipe(collisionPerso(C, ryan(C))),
		// => Gangster::jeter(collisionPerso(C, ryan(C)),
		// Terrain::getBloc(getTerrain(C), Gangster::getX(collisionPerso(C,
		// ryan(C))), Gangster::getY(collisionPerso(C, ryan(C))),
		// Gangster::getZ(collisionPerso(C, ryan(C)))))
		//

		// post 6 : alex, faire retrait ou jeter si besoin :: pas faisable
		// (depend des cmd aléatoires)
		//
		// alex(gerer(C, cr, ca))
		// = alex(C) si !estFrappe(gerer(C, cr, ca), Personnage::nom(alex(C)))
		// = Personnage::retrait(alex(C),
		// Gangster::force(collisionPerso(C, ryan(C))))
		// (si estEquipe(collisionPerso(C)) alors * 2)
		//
		// et si Gangster::estEquipe(collisionPerso(C, alex(C))),
		// => Gangster::jeter(collisionPerso(C, alex(C)),
		// Terrain::getBloc(getTerrain(C), Gangster::getX(collisionPerso(C,
		// alex(C))), Gangster::getY(collisionPerso(C, alex(C))),
		// Gangster::getZ(collisionPerso(C, alex(C)))))
		//

		// post 7* : slick, faire retrait ou jeter si besoin
		//
		// slick(gerer(C, cr, ca))
		// = slick(C) si !estFrappe(gerer(C, cr, ca), Gangster::nom(slick(C)))
		// = Gangster::retrait(slick(C),
		// Personnage::force(collisionPerso(C, slick(C))))
		// (si estEquipe(collisionGangster(C)) alors * 2)
		//
		// et si Personnage::estEquipe(collisionPerso(C, slick(C)),
		// => Personnage::jeter(collisionPerso(C, slick(C),
		// Terrain::getBloc(getTerrain(C), Personnage::getX(collisionPerso(C,
		// slick(C)), Personnage::getY(collisionPerso(C, slick(C)),
		// Personnage::getZ(collisionPerso(C, slick(C))))
		//

		// post 8* : pour chaque gangster, faire retrait ou jeter si besoin
		//
		// \foreach g \in gangster(C)
		// g(gerer(C, cr, ca))
		// = g(C) si !estFrappe(gerer(C, cr, ca), Gangster::nom(g(C)))
		// = Gangster::retrait(g(C),
		// Personnage::force(collisionPerso(C, g(C))))
		// et si Gangster::estEquipe(g),
		// => Gangster::jeter(g, Terrain::getBloc(getTerrain(C),
		// Gangster::getX(g), Gangster::getY(g), Gangster::getZ(g)))
		//

		// post 9 : ryan : traitement cmd :: pas faisable (depend de 1)
		//
		// ryan(gerer(C, cr, ca)) =>
		// Personnage::getY(ryan(gerer(C, cr, ca))) = Personnage::getY(ryan(C))
		// si !estFrappe(gerer(C, cr, ca))
		// si cr=GAUCHE
		// Personnage::getX(ryan(gerer(C, cr, ca)))
		// = max(0,
		// Personnage::getX(ryan(gerer(C))) - 1)
		// si cr=DROITE
		// Personnage::getX(ryan(gerer(C, cr, ca)))
		// = min(Terrain::nbBlocLargeur(getTerrain(C),
		// Personnage::getX(ryan(gerer(C))) + 1))
		// si cr=HAUT
		// Personnage::getZ(ryan(gerer(C, cr, ca)))
		// = max(0,
		// Personnage::getX(ryan(gerer(C))) - 1))
		// si cr=BAS
		// Personnage::getZ(ryan(gerer(C, cr, ca)))
		// = min(Terrain::nbBlocProfondeur(getTerrain(C),
		// Personnage::getX(ryan(gerer(C))) + 1))
		//
		// si estFrappe(gerer(C, cr, ca))
		// alors calcul de l'offset de déplacement sur X et Z
		// dans un tableau offset[2]
		// Personnage::getX(ryan(gerer(C, cr, ca)))
		// = max(0,min(Terrain::nbBlocLargeur(getTerrain(C),
		// Personnage::getX(ryan(gerer(C))) + offset[0]))
		//
		// Personnage::getZ(ryan(gerer(C, cr, ca)))
		// = max(0,min(Terrain::nbBlocProfondeur(getTerrain(C),
		// Personnage::getZ(ryan(gerer(C))) + offset[1]))
		//

		// post 10 : alex : traitement cmd :: pas faisable (depend de 1)
		//
		// alex(gerer(C, cr, ca)) =>
		// Personnage::getY(alex(gerer(C, cr, ca))) = Personnage::getY(alex(C))
		// si !estFrappe(gerer(C, cr, ca))
		// si cr=GAUCHE
		// Personnage::getX(alex(gerer(C, cr, ca)))
		// = max(0,
		// Personnage::getX(alex(gerer(C))) - 1)
		// si cr=DROITE
		// Personnage::getX(alex(gerer(C, cr, ca)))
		// = min(Terrain::nbBlocLargeur(getTerrain(C),
		// Personnage::getX(alex(gerer(C))) + 1))
		// si cr=HAUT
		// Personnage::getZ(alex(gerer(C, cr, ca)))
		// = max(0,
		// Personnage::getX(alex(gerer(C))) - 1))
		// si cr=BAS
		// Personnage::getZ(alex(gerer(C, cr, ca)))
		// = min(Terrain::nbBlocProfondeur(getTerrain(C),
		// Personnage::getX(alex(gerer(C))) + 1))
		//
		// si estFrappe(gerer(C, cr, ca))
		// alors calcul de l'offset de déplacement sur X et Z
		// dans un tableau offset[2]
		// Personnage::getX(alex(gerer(C, cr, ca)))
		// = max(0,min(Terrain::nbBlocLargeur(getTerrain(C),
		// Personnage::getX(alex(gerer(C))) + offset[0]))
		//
		// Personnage::getZ(alex(gerer(C, cr, ca)))
		// = max(0,min(Terrain::nbBlocProfondeur(getTerrain(C),
		// Personnage::getZ(alex(gerer(C))) + offset[1]))
		//

		// post 11 : slick : traitement cmd :: pas faisable
		//
		// slick(gerer(C, cr, ca)) =>
		// Gangster::getY(slick(gerer(C, cr, ca))) = Gangster::getY(slick(C))
		// si !estFrappe(gerer(C, cr, ca))
		// si cr=GAUCHE
		// Gangster::getX(slick(gerer(C, cr, ca)))
		// = max(0,
		// Gangster::getX(slick(gerer(C))) - 1)
		// si cr=DROITE
		// Gangster::getX(slick(gerer(C, cr, ca)))
		// = min(Terrain::nbBlocLargeur(getTerrain(C),
		// Gangster::getX(slick(gerer(C))) + 1))
		// si cr=HAUT
		// Gangster::getZ(slick(gerer(C, cr, ca)))
		// = max(0,
		// Gangster::getX(slick(gerer(C))) - 1))
		// si cr=BAS
		// Gangster::getZ(slick(gerer(C, cr, ca)))
		// = min(Terrain::nbBlocProfondeur(getTerrain(C),
		// Gangster::getX(slick(gerer(C))) + 1))
		//
		// si estFrappe(gerer(C, cr, ca))
		// alors calcul de l'offset de déplacement sur X et Z
		// dans un tableau offset[2]
		// Gangster::getX(slick(gerer(C, cr, ca)))
		// = max(0,min(Terrain::nbBlocLargeur(getTerrain(C),
		// Gangster::getX(slick(gerer(C))) + offset[0]))
		//
		// Gangster::getZ(slick(gerer(C, cr, ca)))
		// = max(0,min(Terrain::nbBlocProfondeur(getTerrain(C),
		// Gangster::getZ(slick(gerer(C))) + offset[1]))
		//

		// post 12 : pour chaque gangster, traitement cmd :: pas faisable
		//
		// \foreach g \in gangster(C)
		// g(gerer(C, cr, ca)) =>
		// Gangster::getY(g(gerer(C, cr, ca))) = Gangster::getY(g(C))
		// si !estFrappe(gerer(C, cr, ca))
		// si cr=GAUCHE
		// Gangster::getX(g(gerer(C, cr, ca)))
		// = max(0,
		// Gangster::getX(g(gerer(C))) - 1)
		// si cr=DROITE
		// Gangster::getX(g(gerer(C, cr, ca)))
		// = min(Terrain::nbBlocLargeur(getTerrain(C),
		// Gangster::getX(g(gerer(C))) + 1))
		// si cr=HAUT
		// Gangster::getZ(g(gerer(C, cr, ca)))
		// = max(0,
		// Gangster::getX(g(gerer(C))) - 1))
		// si cr=BAS
		// Gangster::getZ(g(gerer(C, cr, ca)))
		// = min(Terrain::nbBlocProfondeur(getTerrain(C),
		// Gangster::getX(g(gerer(C))) + 1))
		//
		// si estFrappe(gerer(C, cr, ca))
		// alors calcul de l'offset de déplacement sur X et Z
		// dans un tableau offset[2]
		// Gangster::getX(g(gerer(C, cr, ca)))
		// = max(0,min(Terrain::nbBlocLargeur(getTerrain(C),
		// Gangster::getX(g(gerer(C))) + offset[0]))
		//
		// Gangster::getZ(g(gerer(C, cr, ca)))
		// = max(0,min(Terrain::nbBlocProfondeur(getTerrain(C),
		// Gangster::getZ(g(gerer(C))) + offset[1]))
		//

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int ryanX, ryanZ, slickX, slickZ, alexX, alexZ;
		if (super.ryan() == null) {
			return sb.toString();
		}
		ryanX = super.ryan().getX();
		ryanZ = super.ryan().getZ();
		slickX = super.slick().getX();
		slickZ = super.slick().getZ();
		alexX = super.alex().getX();
		alexZ = super.alex().getZ();
		List<GangsterService> gangsters = super.gangster();
		TerrainService t = super.getTerrain();
		for (int z = 0; z < t.nbBlocProfondeur(); z++) {
			for (int x = 0; x < t.nbBlocLargeur(); x++) {

				if (x == ryanX && z == ryanZ) {
					if (ryan().estVaincu()) {
						sb.append("†");
					} else {
						sb.append("R");
					}
					continue;
				}
				if (x == slickX && z == slickZ) {
					if (slick().estVaincu()) {
						sb.append("†");
					} else {
						sb.append("S");
					}
					continue;
				}
				if (x == alexX && z == alexZ) {
					if (alex().estVaincu()) {
						sb.append("†");
					} else {
						sb.append("A");
					}
					continue;
				}

				int last = 0, cpt = 0;
				for (int i = 0; i < gangsters.size(); i++) {
					GangsterService g = gangsters.get(i);
					if (g.estVaincu()) {
						continue;
					}
					if (g.getX() == x && g.getZ() == z) {
						last = i;
						cpt++;
					}
				}
				if (cpt >= 1) {
					if (cpt == 1) {
						sb.append(last);
					} else {
						sb.append("#");
					}
					continue;
				}

				if (t.getBloc(x, 0, z).getObjet() == null) {
					sb.append(".");
					continue;
				}
				Tresor tres = t.getBloc(x, 0, z).getObjet().getTresor();
				switch (tres) {
				case POUBELLEMETALLIQUE:
					sb.append("∆");
					break;
				case CINQUANTECENTIMES:
					sb.append("¢");
					break;
				case UNDOLLAR:
					sb.append("$");
					break;
				case CHAINEDEVELO:
					sb.append("Ω");
					break;
				default:
					break;
				}
				sb.append("");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}