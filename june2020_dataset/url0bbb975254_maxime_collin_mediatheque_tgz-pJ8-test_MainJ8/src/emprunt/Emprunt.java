package emprunt;

import java.util.Date;

public class Emprunt {

	private utilisateurs.Utilisateur emprunteur;
	private medias.Media media;
	
	private int id;
	private int note;
	private String commentaire;
	private Date dateEmprunt;
	private Date dateRetour;
	private boolean finis;
	
	public Emprunt(utilisateurs.Utilisateur e, medias.Media m, Date de, Date dr) {
		emprunteur = e;
		media = m;
		
		note = -1;
		commentaire = null;
		dateEmprunt = de;
		dateRetour = dr;
	}

	public utilisateurs.Utilisateur getEmprunteur() {
		return emprunteur;
	}

	public void setEmprunteur(utilisateurs.Utilisateur emprunteur) {
		this.emprunteur = emprunteur;
	}

	public medias.Media getMedia() {
		return media;
	}

	public void setMedia(medias.Media media) {
		this.media = media;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Date getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt(Date dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	public Date getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isFinis() {
		return finis;
	}

	public void setFinis(boolean finis) {
		this.finis = finis;
	}
	
	public void faireUneNotification()
	{
		Date aujourdhui = new Date();
		if(this.dateRetour.compareTo(aujourdhui) <=0)
		{
			this.media.getCours().getProf().addAlerteEmprunt(this);
		}			
	}
	
	public String toString() {
		String res = "";
		
		res += id + " ";
		res += emprunteur.getNom() + " ";
		res += media.getNom() + " ";
		res += dateEmprunt + "->" + dateRetour + " ";
		res += "rendu : " + finis + "\n";
		res += commentaire + "\n";
		res += note + "\n";
		
		return res;
	}
}
