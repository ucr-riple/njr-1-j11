package rivercityransom.services;

import java.util.List;

import rivercityransom.enumerations.Commande;

public interface GestionCombatService {
	/** Observateur : Terrain du jeu */
	TerrainService getTerrain();

	/** Observateur : Ryan */
	PersonnageService ryan();

	/** Observateur : Alex */
	PersonnageService alex();

	/** Observateur : Slick */
	GangsterService slick();

	/** Observateur : Liste des gangsters */
	List<GangsterService> gangster();

	/** Observateur : vrai si l'entité (personnage ou gangster) est frappée */
	boolean estFrappe(String nomPersonnage);

	/** Observateur : vrai si l'entité (personnage ou gangster) est gelé */
	boolean estGele(String nomPersonnage);

	/**
	 * Observateur : rend le gangster en collision avec le personnage, s'il
	 * existe, sinon null
	 */
	List<GangsterService> collisionPerso(PersonnageService p);

	/**
	 * Observateur : rend le personnage en collision avec le gangster, s'il
	 * existe, sinon null
	 */
	List<PersonnageService> collisionGangster(GangsterService g);

	/**
	 * Initialisation
	 * pre:
				largeurTerrain > 0
				&& hauteurTerrain > 0
				&& profondeurTerrain > 0
	 * post:
	 	Terrain::nbBlocLargeur(getTerrain(init(x,y,z))) = x		
		Terrain::nbBlocHauteur(getTerrain(init(x,y,z))) = y
		Terrain::nbBlocProfondeur(getTerrain(init(x,y,z))) = z
		alex(init(x,y,z)) = Personnage::init("Alex",16,51,16,50,1001,0,0,0)
		ryan(init(x,y,z)) = Personnage::init("Ryan",16,51,16,50,1001,1,0,0)
		slick(init(x,y,z)) = Gangster::init("Slick",16,51,16,55,100,
			Terrain::nbBlocLargeur(getTerrain(init(x,y,z)))-1,0,0)

		\foreach g \in gangster 
		g = Gangster::init(name,16,51,16,10,50, randomX, 0, randomZ)

		\foreach entity \in {alex, ryan, slick, gangster} 
		estFrappe(init(x,y,z), name(entity)) = false
		estGele(init(x,y,z), name(entity)) = false
	 */
	void init(int largeurTerrain, int hauteurTerrain, int profondeurTerrain);

	/**
	 * Traite les commandes de Ryan et d'Alex
	 * post: 
	 * 
		estFrappe(gerer(C, cr, ca), nom(perso)) 
			= !estGele(C,nom(attacker))
			&& cmdAttacker = FRAPPE && attacker \in collisionPerso(perso)
		
		estFrappe(gerer(C, cr, ca), nom(gangster)) 
			= !estGele(C,nom(attacker))
			&& cmdAttacker = FRAPPE && attacker \in collisionGangster(gangster)

		estGele(gerer(C, cr, ca), nom(perso))
			= estFrappe(gerer(C, cr, ca), nom(perso))
			|| !estGele(C, nom(perso)) && cmdPerso = FRAPPE

		estGele(gerer(C, cr, ca), nom(gangster))
			= estFrappe(gerer(C, cr, ca), nom(gangster))
			|| !estGele(C, nom(gangster)) && cmdgangster = FRAPPE

		ryan(gerer(C, cr, ca)) 
			= ryan(C) si !estFrappe(gerer(C, cr, ca), Personnage::nom(ryan(C))) 
			= 
			\forall g in collisionPerso(C, ryan)
			Personnage::retrait(ryan(C), 
				Gangster::force(g)) 
				(si estEquipe(g) alors * 2)

			  et si Gangster::estEquipe(g),
			   => Gangster::jeter(g, 
			   			Terrain::getBloc(getTerrain(C), 
			   				Gangster::getX(g),
			   				 Gangster::getY(g),
			   				  Gangster::getZ(g)))

		alex(gerer(C, cr, ca)) 
			= alex(C) si !estFrappe(gerer(C, cr, ca), Personnage::nom(alex(C))) 
			= 
			\forall g in collisionPerso(C, alex)
			Personnage::retrait(alex(C), 
				Gangster::force(g)) 
				(si estEquipe(g) alors * 2)
			 
			  et si Gangster::estEquipe(g),
			   => Gangster::jeter(g, 
			   			Terrain::getBloc(getTerrain(C), 
			   				Gangster::getX(g),
			   				 Gangster::getY(g),
			   				  Gangster::getZ(g)))

		slick(gerer(C, cr, ca)) 
			= slick(C) si !estFrappe(gerer(C, cr, ca), Gangster::nom(slick(C))) 
			= 
			\forall p in collisionGangster(C, slick)
			Gangster::retrait(slick(C), 
				Personnage::force(p))
				(si estEquipe(p) alors * 2)

			  et si Personnage::estEquipe(p,
			   => Personnage::jeter(p, 
			   			Terrain::getBloc(getTerrain(C), 
			   				Personnage::getX(p,
			   				 Personnage::getY(p,
			   				  Personnage::getZ(p))

		\forall g \in gangster(C)
		g(gerer(C, cr, ca)) 
			= g(C) si !estFrappe(gerer(C, cr, ca), Gangster::nom(g(C))) 
			= 
			\forall p in collisionGangster(C, g)
			Gangster::retrait(g(C), 
				Personnage::force(p))
				(si estEquipe(p) alors * 2)

			  et si Personnage::estEquipe(p,
			   => Personnage::jeter(p, 
			   			Terrain::getBloc(getTerrain(C), 
			   				Personnage::getX(p,
			   				 Personnage::getY(p,
			   				  Personnage::getZ(p))

		ryan(gerer(C, cr, ca)) =>
			Personnage::getY(ryan(gerer(C, cr, ca))) = Personnage::getY(ryan(C)) 
			si !estFrappe(gerer(C, cr, ca))
				si cr=GAUCHE
					Personnage::getX(ryan(gerer(C, cr, ca))) 	
						= max(0,
						Personnage::getX(ryan(gerer(C))) - 1)
				si cr=DROITE
					Personnage::getX(ryan(gerer(C, cr, ca))) 
						= min(Terrain::nbBlocLargeur(getTerrain(C), 
						Personnage::getX(ryan(gerer(C))) + 1))
				si cr=HAUT
					Personnage::getZ(ryan(gerer(C, cr, ca))) 
						= max(0, 
						Personnage::getX(ryan(gerer(C))) - 1))
				si cr=BAS
					Personnage::getZ(ryan(gerer(C, cr, ca))) 
						= min(Terrain::nbBlocProfondeur(getTerrain(C), 
						Personnage::getX(ryan(gerer(C))) + 1))

			si estFrappe(gerer(C, cr, ca))
				alors calcul de l'offset sur X et Z 
				dans un tableau offset[2]
				Personnage::getX(ryan(gerer(C, cr, ca))) 
						= max(0,min(Terrain::nbBlocLargeur(getTerrain(C), 
						Personnage::getX(ryan(gerer(C))) + offset[0]))
				
				Personnage::getZ(ryan(gerer(C, cr, ca))) 
						= max(0,min(Terrain::nbBlocProfondeur(getTerrain(C), 
						Personnage::getZ(ryan(gerer(C))) + offset[1]))


		alex(gerer(C, cr, ca)) =>
			Personnage::getY(alex(gerer(C, cr, ca))) = Personnage::getY(alex(C)) 
			si !estFrappe(gerer(C, cr, ca))
				si cr=GAUCHE
					Personnage::getX(alex(gerer(C, cr, ca))) 	
						= max(0,
						Personnage::getX(alex(gerer(C))) - 1)
				si cr=DROITE
					Personnage::getX(alex(gerer(C, cr, ca))) 
						= min(Terrain::nbBlocLargeur(getTerrain(C), 
						Personnage::getX(alex(gerer(C))) + 1))
				si cr=HAUT
					Personnage::getZ(alex(gerer(C, cr, ca))) 
						= max(0, 
						Personnage::getX(alex(gerer(C))) - 1))
				si cr=BAS
					Personnage::getZ(alex(gerer(C, cr, ca))) 
						= min(Terrain::nbBlocProfondeur(getTerrain(C), 
						Personnage::getX(alex(gerer(C))) + 1))

			si estFrappe(gerer(C, cr, ca))
				alors calcul de l'offset sur X et Z 
				dans un tableau offset[2]
				Personnage::getX(alex(gerer(C, cr, ca))) 
						= max(0,min(Terrain::nbBlocLargeur(getTerrain(C), 
						Personnage::getX(alex(gerer(C))) + offset[0]))
				
				Personnage::getZ(alex(gerer(C, cr, ca))) 
						= max(0,min(Terrain::nbBlocProfondeur(getTerrain(C), 
						Personnage::getZ(alex(gerer(C))) + offset[1]))

		slick(gerer(C, cr, ca)) =>
			Gangster::getY(slick(gerer(C, cr, ca))) = Gangster::getY(slick(C)) 
			si !estFrappe(gerer(C, cr, ca))
				si cr=GAUCHE
					Gangster::getX(slick(gerer(C, cr, ca))) 	
						= max(0,
						Gangster::getX(slick(gerer(C))) - 1)
				si cr=DROITE
					Gangster::getX(slick(gerer(C, cr, ca))) 
						= min(Terrain::nbBlocLargeur(getTerrain(C), 
						Gangster::getX(slick(gerer(C))) + 1))
				si cr=HAUT
					Gangster::getZ(slick(gerer(C, cr, ca))) 
						= max(0, 
						Gangster::getX(slick(gerer(C))) - 1))
				si cr=BAS
					Gangster::getZ(slick(gerer(C, cr, ca))) 
						= min(Terrain::nbBlocProfondeur(getTerrain(C), 
						Gangster::getX(slick(gerer(C))) + 1))

			si estFrappe(gerer(C, cr, ca))
				alors calcul de l'offset sur X et Z 
				dans un tableau offset[2]
				Gangster::getX(slick(gerer(C, cr, ca))) 
						= max(0,min(Terrain::nbBlocLargeur(getTerrain(C), 
						Gangster::getX(slick(gerer(C))) + offset[0]))
				
				Gangster::getZ(slick(gerer(C, cr, ca))) 
						= max(0,min(Terrain::nbBlocProfondeur(getTerrain(C), 
						Gangster::getZ(slick(gerer(C))) + offset[1]))

		\forall g \in gangster(C)
		g(gerer(C, cr, ca)) =>
			Gangster::getY(g(gerer(C, cr, ca))) = Gangster::getY(g(C)) 
			si !estFrappe(gerer(C, cr, ca))
				si cr=GAUCHE
					Gangster::getX(g(gerer(C, cr, ca))) 	
						= max(0,
						Gangster::getX(g(gerer(C))) - 1)
				si cr=DROITE
					Gangster::getX(g(gerer(C, cr, ca))) 
						= min(Terrain::nbBlocLargeur(getTerrain(C), 
						Gangster::getX(g(gerer(C))) + 1))
				si cr=HAUT
					Gangster::getZ(g(gerer(C, cr, ca))) 
						= max(0, 
						Gangster::getX(g(gerer(C))) - 1))
				si cr=BAS
					Gangster::getZ(g(gerer(C, cr, ca))) 
						= min(Terrain::nbBlocProfondeur(getTerrain(C), 
						Gangster::getX(g(gerer(C))) + 1))

			si estFrappe(gerer(C, cr, ca))
				alors calcul de l'offset sur X et Z 
				dans un tableau offset[2]
				Gangster::getX(g(gerer(C, cr, ca))) 
						= max(0,min(Terrain::nbBlocLargeur(getTerrain(C), 
						Gangster::getX(g(gerer(C))) + offset[0]))
				
				Gangster::getZ(g(gerer(C, cr, ca))) 
						= max(0,min(Terrain::nbBlocProfondeur(getTerrain(C), 
						Gangster::getZ(g(gerer(C))) + offset[1]))
	 * 
	 */
	void gerer(Commande commandRyan, Commande commandAlex);

}