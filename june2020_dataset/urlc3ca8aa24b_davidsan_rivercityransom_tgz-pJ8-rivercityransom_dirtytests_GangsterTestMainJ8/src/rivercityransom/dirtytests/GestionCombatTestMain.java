package rivercityransom.dirtytests;

import rivercityransom.contracts.GestionCombatContract;
import rivercityransom.enumerations.Commande;
import rivercityransom.impl.GestionCombatImpl;
import rivercityransom.services.GestionCombatService;

public class GestionCombatTestMain {

	public static void test1() {
		GestionCombatService gc = new GestionCombatContract(
				new GestionCombatImpl());
		gc.init(20, 10, 10);
		System.out.println(gc.toString());
		for (int i = 0; i < 4; i++) {
			gc.gerer(Commande.DROITE, Commande.BAS);
			System.out.println(gc.toString());
		}
		for (int i = 0; i < 10; i++) {
			gc.gerer(Commande.GAUCHE, Commande.DROITE);
			System.out.println(gc.toString());
		}

		gc.gerer(Commande.FRAPPE, Commande.FRAPPE);
		System.out.println(gc.toString());

		for (int i = 0; i < 8; i++) {
			gc.gerer(Commande.BAS, Commande.HAUT);
			System.out.println(gc.toString());
		}
		for (int i = 0; i < 10; i++) {
			gc.gerer(Commande.BAS, Commande.DROITE);
			System.out.println(gc.toString());
		}
		gc.gerer(Commande.FRAPPE, Commande.FRAPPE);
		System.out.println(gc.toString());

		for (int i = 0; i < 4; i++) {
			gc.gerer(Commande.HAUT, Commande.BAS);
			System.out.println(gc.toString());
		}

		gc.gerer(Commande.FRAPPE, Commande.FRAPPE);
		System.out.println(gc.toString());

		// for (int i = 0; i < 1000; i++) {
		// // int choix = RandomSingleton.getInstance().nextInt(5);
		// Commande cmd = Commande.RIEN;
		// gc.gerer(cmd, cmd);
		// System.out.println(gc.toString());
		// }
	}

	public static void main(String[] args) {
		test1();
	}
}
