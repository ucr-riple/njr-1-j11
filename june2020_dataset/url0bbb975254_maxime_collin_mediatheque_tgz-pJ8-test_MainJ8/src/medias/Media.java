package medias;

public class Media {
	
	private scolarite.Cours cours;
	private Genre genre;
	
	private int id;
	private String nom;
	private String auteur;
	private String resume;
	private int duree;
	private boolean disponible;

	
	public Media(scolarite.Cours c, Genre g, int i, String n, String a, String r, int d) {
		cours = c;
		genre = g;
		
		id = i;
		nom = n;
		auteur = a;
		resume = r;
		duree = d;
		disponible = true;
		
	}


	public scolarite.Cours getCours() {
		return cours;
	}


	public void setCours(scolarite.Cours cours) {
		this.cours = cours;
	}


	public Genre getGenre() {
		return genre;
	}


	public void setGenre(Genre genre) {
		this.genre = genre;
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


	public String getResume() {
		return resume;
	}


	public void setResume(String resume) {
		this.resume = resume;
	}


	public int getDuree() {
		return duree;
	}


	public void setDuree(int duree) {
		this.duree = duree;
	}


	public String getAuteur() {
		return auteur;
	}


	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}


	public boolean isDisponible() {
		return disponible;
	}


	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
	public String toString() {
		String res = "";
		
		res += id + " ";
		res += nom + " ";
		res += auteur + " ";
		res += genre.getNom() + " ";
		res += cours.getNom() + " ";
		res += duree + " ";
		res += "disponible : " + disponible + "\n";
		
		return res;
	}
	
}
