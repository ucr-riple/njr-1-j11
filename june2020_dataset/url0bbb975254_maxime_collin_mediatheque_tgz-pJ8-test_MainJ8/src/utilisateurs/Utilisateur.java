package utilisateurs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import medias.Media;

public abstract class Utilisateur {
	
	protected ArrayList<scolarite.Cours> cours;
	protected ArrayList<emprunt.Emprunt> emprunts;
	
	private int id;
	private String nom;
	private String prenom;
	private String type;
	private int nbEmpruntMax;
	private int nbEmpruntNonCommente;
	private int nbEmpruntEnCours;
	
	public Utilisateur(int i, String n, String p, String t, int nbEM) {
		id = i;
		nom = n;
		prenom = p;
		type = t;
		nbEmpruntMax = nbEM;
		nbEmpruntNonCommente = 0;
		nbEmpruntEnCours = 0;
		
		cours = new ArrayList<scolarite.Cours>();
		emprunts = new ArrayList<emprunt.Emprunt>();
	}
	
	public ArrayList<scolarite.Cours> getCours() {
		return cours;
	}

	public void setCours(ArrayList<scolarite.Cours> cours) {
		this.cours = cours;
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

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int emprunter(Media media) {
		
		int valeurEligible = eligibilite(media);
		
		if(valeurEligible == 1)
		{
			// -> Création de l'emprunt
			
			// Récupère la date courante
			Date datedeb = new Date();
			Date datefin;
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, media.getGenre().getDureeEmprunt());
			datefin = cal.getTime();
			
			faireUnEmprunt(media, datedeb, datefin);			
						
			return 1;
		}
		else // Sinon
		{
			return valeurEligible;
		}		
	}

	public int emprunter(Media media, int nbJour) {
		
		int valeurEligible = eligibilite(media);
		
		if(valeurEligible == 1)
		{
			// -> Création de l'emprunt
			
			// Récupère la date courante
			Date datedeb = new Date();
			Date datefin;
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, nbJour);
			datefin = cal.getTime();
			
			faireUnEmprunt(media, datedeb, datefin);			
						
			return 1;
		}
		else // Sinon
		{
			return valeurEligible;
		}
	}
	
	protected abstract int eligibilite(medias.Media media);
	
	public abstract int commenter(int idEmprunt, int n, String com);
	
	public abstract void VerifierLesEmprunts();
	
	public int getNbEmpruntMax() {
		return nbEmpruntMax;
	}

	public void setNbEmpruntMax(int nbEmpruntMax) {
		this.nbEmpruntMax = nbEmpruntMax;
	}

	public int getNbEmpruntNonCommente() {
		return nbEmpruntNonCommente;
	}

	public void setNbEmpruntNonCommente(int nbEmpruntNonCommente) {
		this.nbEmpruntNonCommente = nbEmpruntNonCommente;
	}

	public ArrayList<emprunt.Emprunt> getEmprunts() {
		return emprunts;
	}

	public void setEmprunts(ArrayList<emprunt.Emprunt> emprunts) {
		this.emprunts = emprunts;
	}

	public int getNbEmpruntEnCours() {
		return nbEmpruntEnCours;
	}

	public void setNbEmpruntEnCours(int nbEmpruntEnCours) {
		this.nbEmpruntEnCours = nbEmpruntEnCours;
	}
	
	public void incrementerNbEmpruntEnCours(){
		this.nbEmpruntEnCours ++;
	}
	
	public void decrementerNbEmpruntEnCours(){
		this.nbEmpruntEnCours --;
	}
	
	public void incrementerNbEmpruntNonCommente(){
		this.nbEmpruntNonCommente ++;
	}
	
	public void decrementerNbEmpruntNonCommente(){
		this.nbEmpruntNonCommente --;
	}
	
	public void addEmprunts(emprunt.Emprunt emprunt)
	{
		this.emprunts.add(emprunt);
	}
	
	public emprunt.Emprunt getEmprunt(int idEmp)
	{
		emprunt.Emprunt resultat = null;
		for(emprunt.Emprunt tmp : emprunts)
		{
			if(tmp.getId() == idEmp)
				resultat = tmp;
		}
		return resultat;
	}
	
	protected void faireUnEmprunt(medias.Media media, Date deb, Date fin)
	{
		this.incrementerNbEmpruntEnCours();
		this.incrementerNbEmpruntNonCommente();
		
		this.addEmprunts(new emprunt.Emprunt(this,media,deb,fin));
		//media.setDisponible(false);
	}

	public String toString() {
		String res = "";
		
		res += getId() + " ";
		res += getNom() + " ";
		res += getPrenom() + " ";
		res += getType() + "\n";
		
		res += "cours : ";
		for (int i = 0; i < getCours().size(); i++)
			res += getCours().get(i) + " ";
		res += "\n";
		
		res += "emprunts : ";
		for (int i = 0; i < getEmprunts().size(); i++)
			res += getEmprunts().get(i) + " ";
		res += "\n";
		
		res += "nbEmpruntMax : " + getNbEmpruntMax() + "\n";
		res += "nbEmpruntEnCours : " + getNbEmpruntEnCours() + "\n";
		res += "nbEmpruntNonCommente : " + getNbEmpruntNonCommente() + "\n";
		
		return res;
	}

}
