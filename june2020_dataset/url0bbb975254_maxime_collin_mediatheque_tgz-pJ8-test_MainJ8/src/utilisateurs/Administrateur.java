package utilisateurs;

public class Administrateur {

	private int id;
	private String nom;
	private String prenom;
	
	public Administrateur(int i, String n, String p) {
		id = i;
		nom = n;
		prenom = p;
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
	
	public String toString() {
		String res = "";
		
		res += getId() + " ";
		res += getNom() + " ";
		res += getPrenom() + "\n";
		
		return res;
	}
}
