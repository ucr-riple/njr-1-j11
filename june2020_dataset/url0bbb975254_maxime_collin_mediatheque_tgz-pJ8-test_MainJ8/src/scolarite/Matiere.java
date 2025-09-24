package scolarite;

public class Matiere {

	private int id;
	private String nom;
	
	public Matiere(int i, String n) {
		id = i;
		nom = n;
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
	
	public String toString() {
		String res = "";
	
		res += id + " ";
		res += nom + " ";
		
		return res;
	}
}
