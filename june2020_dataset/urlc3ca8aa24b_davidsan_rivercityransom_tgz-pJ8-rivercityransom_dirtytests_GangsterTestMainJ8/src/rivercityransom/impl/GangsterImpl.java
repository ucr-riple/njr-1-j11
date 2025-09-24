package rivercityransom.impl;

import rivercityransom.services.BlocService;
import rivercityransom.services.GangsterService;
import rivercityransom.services.ObjetService;

public class GangsterImpl implements GangsterService {
	private String nom;
	private int largeur;
	private int hauteur;
	private int profondeur;
	private int force;
	private int pointsDeVie;
	private int x;
	private int y;
	private int z;
	private ObjetService chose;

	public GangsterImpl() {
		super();
	}

	@Override
	public void init(String nom, int largeur, int hauteur, int profondeur,
			int force, int pointsDeVie, int x, int y, int z, ObjetService obj) {
		this.nom = nom;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.profondeur = profondeur;
		this.force = force;
		this.pointsDeVie = pointsDeVie;
		this.x = x;
		this.y = y;
		this.z = z;
		this.chose = obj;
	}

	@Override
	public String nom() {
		return nom;
	}

	@Override
	public int largeur() {
		return largeur;
	}

	@Override
	public int hauteur() {
		return hauteur;
	}

	@Override
	public int profondeur() {
		return profondeur;
	}

	@Override
	public int force() {
		return force;
	}

	@Override
	public int pointsDeVie() {
		return pointsDeVie;
	}

	@Override
	public boolean estVaincu() {
		return pointsDeVie <= 0;
	}

	@Override
	public boolean estEquipe() {
		return laChoseEquipee() != null;
	}

	@Override
	public ObjetService laChoseEquipee() {
		return chose;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getZ() {
		return z;
	}

	/* Operators */

	@Override
	public void depot(int pointsDeVie) {
		this.pointsDeVie = this.pointsDeVie + pointsDeVie;
	}

	@Override
	public void retrait(int pointsDeVie) {
		this.pointsDeVie = this.pointsDeVie - pointsDeVie;
	}

	@Override
	public void jeter(BlocService b) {
		b.setObjet(this.chose);
		this.chose = null;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void setZ(int z) {
		this.z = z;
	}

}