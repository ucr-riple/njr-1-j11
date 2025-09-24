package scolarite;

import java.util.ArrayList;
import java.util.Date;

public class Projet {

	private Stage stage;
	private Cours cours;
	private ArrayList<utilisateurs.Etudiant> etudiants;

	
	private int id;
	private String nom;
	private String description;
	private Date dateDebut;
	private Date dateFin;
	
	public Projet(Cours c, ArrayList<utilisateurs.Etudiant> e, int i, String n, String d, Date dd, Date df) {
		cours = c;
		etudiants = e;
		
		id = i;
		nom = n;
		description = d;
		dateDebut = dd;
		dateFin = df;
		
		etudiants = new ArrayList<utilisateurs.Etudiant>();
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}

	public ArrayList<utilisateurs.Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(ArrayList<utilisateurs.Etudiant> etudiants) {
		this.etudiants = etudiants;
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;

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
		res += description + " ";
		res += dateDebut + "->";
		res += dateFin + " ";
		res += cours.getNom() + " ";
		
		res += "etudiants : ";
		for (int i = 0; i < etudiants.size(); i++)
			res += etudiants.get(i).getNom();
		res += "\n";
		
		return res;
	}
}
