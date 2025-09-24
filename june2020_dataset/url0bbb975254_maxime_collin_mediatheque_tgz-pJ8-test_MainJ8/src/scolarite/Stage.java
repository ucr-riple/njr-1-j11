package scolarite;

import java.util.Date;

public class Stage {

	private Projet projet;

	private int id;
	private String nom;
	private Date dateDebut;
	private Date dateFin;
	
	
	public Stage(Projet p, int i, String n, Date dd, Date df) {
		projet = p;
		
		id = i;
		nom = n;
		dateDebut = dd;
		dateFin = df;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	public Date getDateDebut() {
		return dateDebut;
	}


	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}


	public Date getDateFin() {
		return dateFin;
	}


	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	public String toString() {
		String res = "";
		
		res += id + " ";
		res += nom + " ";
		res += dateDebut + "->";
		res += dateFin + "\n";
	
		res += "projet : " + projet + "\n";
		res += "etudiants : ";
		for (int i = 0; i < projet.getEtudiants().size(); i++)
			res += projet.getEtudiants().get(i).getNom();
	
		return res;
	}
}
