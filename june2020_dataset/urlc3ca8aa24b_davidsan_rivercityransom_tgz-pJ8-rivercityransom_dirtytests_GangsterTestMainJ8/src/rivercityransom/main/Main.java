package rivercityransom.main;

import java.util.Scanner;

import rivercityransom.contracts.MoteurJeuContract;
import rivercityransom.enumerations.Commande;
import rivercityransom.impl.MoteurJeuImpl;
import rivercityransom.services.MoteurJeuService;

public class Main {

	private static Scanner sc;

	public static void main(String[] args) {
		boolean DEBUG = true;
		sc = new Scanner(System.in);
		MoteurJeuService engine = new MoteurJeuContract(new MoteurJeuImpl());
		engine.init(20, 10, 10);
		String input;
		System.out.println("Manuel");
		System.out.println("z : haut");
		System.out.println("s : bas");
		System.out.println("q : gauche");
		System.out.println("d : droite");
		System.out.println("f : frappe");
		System.out.println("e : ramasse");
		System.out.println("g : jet");
		System.out.println("autre : rien");
		System.out.println(engine.combat().toString());
		while (!engine.estFini()) {
			Commande cmd;
			Commande[] cmds = new Commande[2];
			for (int i = 0; i < cmds.length; i++) {
				input = sc.nextLine();
				if (input.length() == 0) {
					input = " ";
				}
				switch (input.charAt(0)) {
				case 'z':
					cmd = Commande.HAUT;
					if (DEBUG)
						System.err.println("up");
					break;
				case 's':
					cmd = Commande.BAS;
					if (DEBUG)
						System.err.println("down");
					break;
				case 'q':
					cmd = Commande.GAUCHE;
					if (DEBUG)
						System.err.println("left");
					break;
				case 'd':
					cmd = Commande.DROITE;
					if (DEBUG)
						System.err.println("right");
					break;
				case 'f':
					cmd = Commande.FRAPPE;
					if (DEBUG)
						System.err.println("hit");
					break;
				case 'e':
					cmd = Commande.RAMASSE;
					if (DEBUG)
						System.err.println("pick");
					break;
				case 'g':
					cmd = Commande.JET;
					if (DEBUG)
						System.err.println("drop");
					break;
				default:
					cmd = Commande.RIEN;
				}
				cmds[i] = cmd;
			}
			engine.pasDeJeu(cmds[0], cmds[1]);
			System.out.println(engine.combat().toString());
		}

		System.out.println("Résultat de la partie : " + engine.resulatFinal());

	}
}
