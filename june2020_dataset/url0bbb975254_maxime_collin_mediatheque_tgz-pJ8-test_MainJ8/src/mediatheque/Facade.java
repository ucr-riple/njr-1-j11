package mediatheque;

import java.util.Date;

public interface Facade {

	
	public Boolean addMedia(int idCours, int idGenre, int idMedia, String n, String a, String r, int d);
	
	public Boolean setMedia(int idCours, int idGenre, int idMedia, String n, String a, String r, int d);
	
	public Boolean removeMedia(int idMedia);
	
	public String getMedia();
	
	public String getMedia(int idMedia);
	
		
	public Boolean addGenre(int idGenre, String n, int de);
	
	public Boolean setGenre(int idGenre, String n, int de);
	
	public Boolean removeGenre(int idGenre);
	
	public String getGenre();
	
	public String getGenre(int idGenre);
	
	
	public Boolean addUser(int idUser, String n, String p, String t, int nbEM);
	
	public Boolean setUser(int idUser, String n, String p, String t, int nbEM);
	
	public Boolean removeUser(int idUser);
	
	public String getUser();
	
	public String getUser(int idUser);
		
	
	public Boolean addAdmin(int idAdmin, String n, String p);
	
	public Boolean setAdmin(int idAdmin, String n, String p);
	
	public Boolean removeAdmin(int idAdmin);
	
	public String getAdmin();
	
	public String getAdmin(int idAdmin);
	
	
	public Boolean addCours(int idCycle, int idMatiere, int idEnseignant, int idCours, String n);
	
	public Boolean setCours(int idCycle, int idMatiere, int idEnseignant, int idCours, String n);
	
	public Boolean removeCours(int idCours);
	
	public String getCours();
	
	public String getCours(int idCours);
	
	public Boolean inscrireUserCours(int idUser, int idCours);
	
	public Boolean desinscrireUserCours(int idUser, int idCours);
	
	
	public Boolean addCycle(int idCycle, String n);
	
	public Boolean setCycle(int idCycle, String n);
	
	public Boolean removeCycle(int idCycle);
	
	public String getCycle();
	
	public String getCycle(int idCycle);
	
	
	public Boolean addMatiere(int idMatiere, String n);
	
	public Boolean setMatiere(int idMatiere, String n);
	
	public Boolean removeMatiere(int idMatiere);
	
	public String getMatiere();
	
	public String getMatiere(int idMatiere);
	
	
	public Boolean addProjet(int idCours, int idProjet, String n, String d, Date dd, Date df);
	
	public Boolean setProjet(int idCours, int idProjet, String n, String d, Date dd, Date df);
	
	public Boolean removeProjet(int idProjet);
	
	public String getProjet();
	
	public String getProjet(int idProjet);
	
	public Boolean addEtudiantProjet(int idEtudiant, int idProjet);
	
	public Boolean removeEtudiantProjet(int idEtudiant, int idProjet);
	
	
	public Boolean addStage(int idProjet, int i, String n, Date dd, Date df);
	
	public Boolean setStage(int idProjet, int i, String n, Date dd, Date df);
	
	public Boolean removeStage(int idStage);
	
	public String getStage();
	
	public String getStage(int idStage);
	
	
	public Boolean emprunter(int idUser, int idMedia, Date de, Date dr);
	
	public Boolean emprunter(int idUser, int idMedia, Date de, Date dr, int nbJours);
	
	public Boolean commenter(int idUser, int idMedia, int n, String c);
	
}
