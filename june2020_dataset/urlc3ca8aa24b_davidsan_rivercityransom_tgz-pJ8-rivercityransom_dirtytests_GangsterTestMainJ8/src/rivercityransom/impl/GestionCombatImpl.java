package rivercityransom.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rivercityransom.contracts.GangsterContract;
import rivercityransom.contracts.ObjetContract;
import rivercityransom.contracts.PersonnageContract;
import rivercityransom.contracts.TerrainContract;
import rivercityransom.enumerations.Commande;
import rivercityransom.enumerations.Tresor;
import rivercityransom.services.GangsterService;
import rivercityransom.services.GestionCombatService;
import rivercityransom.services.ObjetService;
import rivercityransom.services.PersonnageService;
import rivercityransom.services.TerrainService;
import rivercityransom.tools.RandomSingleton;

public class GestionCombatImpl implements GestionCombatService {

	private TerrainService terrain;
	private PersonnageService ryan;
	private PersonnageService alex;
	private GangsterService slick;
	private List<GangsterService> gangster;
	private HashMap<String, Boolean> estFrappeHash;
	private HashMap<String, Integer> estGeleHash;
	private List<String> noms;

	@Override
	public TerrainService getTerrain() {
		return this.terrain;
	}

	@Override
	public PersonnageService ryan() {
		return this.ryan;
	}

	@Override
	public PersonnageService alex() {
		return this.alex;
	}

	@Override
	public GangsterService slick() {
		return this.slick;
	}

	@Override
	public List<GangsterService> gangster() {
		return this.gangster;
	}

	@Override
	public boolean estFrappe(String nomPersonnage) {
		return estFrappeHash.get(nomPersonnage);
	}

	@Override
	public boolean estGele(String nomPersonnage) {
		return estGeleHash.get(nomPersonnage) > 0;
	}

	@Override
	public List<GangsterService> collisionPerso(PersonnageService p) {
		List<GangsterService> badguys = new ArrayList<GangsterService>(
				gangster());
		badguys.add(slick());
		List<GangsterService> attackers = new ArrayList<GangsterService>();
		for (GangsterService g : badguys) {
			int gx = g.getX();
			int gy = g.getY();
			int gz = g.getZ();
			if ((Math.abs(p.getX() - gx) <= 1)
					&& (Math.abs(p.getY() - gy) <= 1)
					&& (Math.abs(p.getZ() - gz) <= 1)) {
				attackers.add(g);
			}
		}
		return attackers;
	}

	@Override
	public List<PersonnageService> collisionGangster(GangsterService g) {
		List<PersonnageService> goodguys = new ArrayList<PersonnageService>();
		goodguys.add(ryan());
		goodguys.add(alex());
		List<PersonnageService> attackers = new ArrayList<PersonnageService>();
		for (PersonnageService p : goodguys) {
			int px = p.getX();
			int py = p.getY();
			int pz = p.getZ();
			if ((Math.abs(g.getX() - px) <= 1)
					&& (Math.abs(g.getY() - py) <= 1)
					&& (Math.abs(g.getZ() - pz) <= 1)) {
					attackers.add(p);
			}
		}
		return attackers;
	}

	@Override
	public void init(int largeurTerrain, int hauteurTerrain,
			int profondeurTerrain) {
		this.terrain = new TerrainContract(new TerrainImpl());
		this.terrain.init(largeurTerrain, hauteurTerrain, profondeurTerrain);
		// find empty pos in map nearest to the left border for spamming the
		// good guys
		int positionAlexX = 0;
		int positionAlexY = 0;
		int positionAlexZ = 0;

		int positionRyanX = 1;
		int positionRyanY = 0;
		int positionRyanZ = 0;

		this.ryan = new PersonnageContract(new PersonnageImpl());
		this.ryan.init("Ryan", 16, 51, 16, 50, 1001, positionRyanX,
				positionRyanY, positionRyanZ);

		this.alex = new PersonnageContract(new PersonnageImpl());
		this.alex.init("Alex", 16, 51, 16, 50, 1001, positionAlexX,
				positionAlexY, positionAlexZ);

		// find empty pos in map nearest to the right border for spamming the
		// bad guys

		int positionSlickX = largeurTerrain - 1;
		int positionSlickY = 0;
		int positionSlickZ = 0;
		this.slick = new GangsterContract(new GangsterImpl());
		slick.init("Slick", 16, 51, 16, 55, 100, positionSlickX,
				positionSlickY, positionSlickZ, null);

		// liste des noms
		noms = new ArrayList<String>();
		noms.add("Ryan");
		noms.add("Alex");
		noms.add("Slick");

		// un certain nombre de gangsters aux noms poétiques
		// String[] poetes = { "Guillaume Appolinaire", "Charles Baudelaire",
		// "Paul Eluard", "Jacques Prévert", "Raymond Queneau",
		// "Paul Verlaine", "Victor Hugo", "Alfred De Musset",
		// "Arthur Rimbaud" };

		String[] poetes = { "Bot0" };

		gangster = new ArrayList<GangsterService>();
		for (int i = 0; i < poetes.length; i++) { // 10 gangsters + slick
			GangsterService g = new GangsterContract(new GangsterImpl());
			// int positionGangsterX = largeurTerrain - 2 - i;
			int positionGangsterX = RandomSingleton.getInstance().nextInt(
					largeurTerrain);
			int positionGangsterY = 0;
			int positionGangsterZ = RandomSingleton.getInstance().nextInt(
					profondeurTerrain);
			ObjetService obj = new ObjetContract(new ObjetImpl());
			if (i % 2 == 0) {
				obj.init(Tresor.CHAINEDEVELO);
			} else {
				obj.init(Tresor.POUBELLEMETALLIQUE);
			}
			g.init(poetes[i], 16, 51, 16, 10, 50, positionGangsterX,
					positionGangsterY, positionGangsterZ, obj);
			gangster.add(g);
			noms.add(poetes[i]);
		}

		// init hashes
		estFrappeHash = new HashMap<String, Boolean>();
		estGeleHash = new HashMap<String, Integer>();
		for (String nom : noms) {
			estFrappeHash.put(nom, false);
			estGeleHash.put(nom, 0);
		}
	}

	@Override
	public void gerer(Commande commandRyan, Commande commandAlex) {
		// generation d'une hash commande qui donne la commande associé à un
		// personnage ou à un ennemi

		HashMap<String, Commande> commandes = new HashMap<String, Commande>();
		PersonnageService[] goodguys = { ryan(), alex() };
		commandes.put("Ryan", commandRyan);
		commandes.put("Alex", commandAlex);

		List<GangsterService> badguys = new ArrayList<GangsterService>(
				gangster());
		badguys.add(slick());
		RandomSingleton rs = RandomSingleton.getInstance();
		Commande[] enumCommande = Commande.class.getEnumConstants();
		for (GangsterService g : badguys) {
			Commande c = enumCommande[rs.nextInt(enumCommande.length)];
			commandes.put(g.nom(), c);
		}

		// mise à jour de la hash estFrappe
		for (PersonnageService p : goodguys) {
			if (p.estVaincu()) {
				continue; // ignorons les morts
			}
			// trouver le gangster avec qui il est en collision
			List<GangsterService> collidingGangster = collisionPerso(p);
			estFrappeHash.put(p.nom(), false);
			if (collidingGangster == null) {
				continue; // pas de collision
			} else {
				for (GangsterService coll : collidingGangster) {
					// il existe un gangster en collision
					// vérifions qu'il n'est pas gelé
					if (estGele(coll.nom())) {
						continue;
					}
					// vérifions qu'il est en train de taper
					if (commandes.get(coll.nom()) == Commande.FRAPPE) {
						int degat = coll.force();
						if (coll.estEquipe()) {
							degat = degat * 2;
						}
						// mise à jour des points de vie
						p.retrait(degat);
						estFrappeHash.put(p.nom(), true);
					}
				}
			}
		}

		// de manière identique pour les gangster
		for (GangsterService g : badguys) {
			if (g.estVaincu()) {
				continue; // ignorons les morts
			}
			// trouver le gangster avec qui il est en collision
			List<PersonnageService> collidingPersonnage = collisionGangster(g);
			estFrappeHash.put(g.nom(), false);
			if (collidingPersonnage == null) {
				continue; // pas de collision
			} else {
				for (PersonnageService coll : collidingPersonnage) {
					// il existe un gangster en collision
					// vérifions qu'il n'est pas gelé
					if (estGele(coll.nom())) {
						continue;
					}
					// vérifions qu'il est en train de taper
					if (commandes.get(coll.nom()) == Commande.FRAPPE) {
						int degat = coll.force();
						if (coll.estEquipe()) {
							degat = degat * 2;
						}
						// mise à jour des points de vie
						g.retrait(degat);
						estFrappeHash.put(g.nom(), true);
					}
				}
			}
		}

		// mise à jour de l'équipement des personnages frappés
		for (PersonnageService p : goodguys) {
			if (estFrappe(p.nom()) && p.estEquipe()) {
				p.jeter(getTerrain().getBloc(p.getX(), p.getY(), p.getZ()));
			}
		}
		for (GangsterService g : badguys) {
			if (estFrappe(g.nom()) && g.estEquipe()) {
				g.jeter(getTerrain().getBloc(g.getX(), g.getY(), g.getZ()));
			}
		}

		// mise à jour des positions
		for (PersonnageService p : goodguys) {
			if (estFrappe(p.nom())) {
				List<GangsterService> frap = collisionPerso(p);
				for (GangsterService frappeur : frap) {
					int[] offset = calculDeplacement(p.getX(), p.getZ(),
							frappeur.getX(), frappeur.getZ());
					int newX, newZ;
					newX = getBoundedPositionX(getTerrain(), offset[0] + p.getX());
					newZ = getBoundedPositionZ(getTerrain(), offset[1] + p.getZ());
					p.setX(newX);
					p.setZ(newZ);
				}
			} else if (!(estGele(p.nom()))) {
				// si pas gelé
				switch (commandes.get(p.nom())) {
				case HAUT:
					p.setZ(getBoundedPositionZ(getTerrain(), p.getZ() - 1));
					break;
				case BAS:
					p.setZ(getBoundedPositionZ(getTerrain(), p.getZ() + 1));
					break;
				case GAUCHE:
					p.setX(getBoundedPositionX(getTerrain(), p.getX() - 1));
					break;
				case DROITE:
					p.setX(getBoundedPositionX(getTerrain(), p.getX() + 1));
					break;
				case RAMASSE:
					// TODO
					break;
				case JET:
					// TODO
					break;
				default:
					break;
				}
			}
		}
		for (GangsterService g : badguys) {
			if (estFrappe(g.nom())) {
				List<PersonnageService> frap = collisionGangster(g);
				if (frap == null) {
					continue;
				}
				for (PersonnageService frappeur : frap) {
					int[] offset = calculDeplacement(g.getX(), g.getZ(),
							frappeur.getX(), frappeur.getZ());
					int newX, newZ;
					newX = getBoundedPositionX(getTerrain(), offset[0] + g.getX());
					newZ = getBoundedPositionZ(getTerrain(), offset[1] + g.getZ());
					// System.err.println("old : " + g.getX() + " / " + g.getZ());
					g.setX(newX);
					g.setZ(newZ);
					// System.err.println("offset : " + offset[0] + " / " +
					// offset[1]);
					// System.err.println("new : " + g.getX() + " / " + g.getZ());
				}
			} else if (!(estGele(g.nom()))) {
				// si pas gelé
				switch (commandes.get(g.nom())) {
				case HAUT:
					g.setZ(getBoundedPositionZ(getTerrain(), g.getZ() - 1));
					break;
				case BAS:
					g.setZ(getBoundedPositionZ(getTerrain(), g.getZ() + 1));
					break;
				case GAUCHE:
					g.setX(getBoundedPositionX(getTerrain(), g.getX() - 1));
					break;
				case DROITE:
					g.setX(getBoundedPositionX(getTerrain(), g.getX() + 1));
					break;
				default:
					break;
				}
			}
		}

		// mise à jour de la hash estGele
		// tout ce qui ont été frappé seront gelé pour 3 tours
		for (String s : noms) {
			if (estFrappe(s)) {
				// est frappé par qq1
				estGeleHash.put(s, 3);
			} else if (!estGele(s) && commandes.get(s) == Commande.FRAPPE) {
				// est en train de frapper qq1
				estGeleHash.put(s, 1);
			} else if (estGele(s)) {
				// dégelage progressif
				estGeleHash.put(s, estGeleHash.get(s) - 1);
			}
		}

		// ralentir les animations
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int getBoundedPositionZ(TerrainService t, int i) {
		return Math.max(0, (Math.min(i, t.nbBlocProfondeur() - 1)));
	}

	private int getBoundedPositionX(TerrainService t, int i) {
		return Math.max(0, (Math.min(i, t.nbBlocLargeur() - 1)));
	}

	private int[] calculDeplacement(int centerX, int centerZ, int fromX,
			int fromZ) {
		int[] res = { 3 * (centerX - fromX), 3 * (centerZ - fromZ) };
		return res;
	}
}
