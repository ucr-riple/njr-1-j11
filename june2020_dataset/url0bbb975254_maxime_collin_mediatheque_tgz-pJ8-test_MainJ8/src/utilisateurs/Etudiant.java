package utilisateurs;

import medias.Media;

public class Etudiant extends Utilisateur {
	
	public Etudiant(int i, String n, String p, String t, int nbEM) {
		super(i, n, p ,t, nbEM);		
	}
	

	@Override
	protected int eligibilite(Media media) {
		// Si le nombre d'emprunt en cours est au max
		if(this.getNbEmpruntEnCours() == this.getNbEmpruntMax())
		{
			return 2;
		} // Si le nombre d'emprunt non commenté est au max
		else if(this.getNbEmpruntNonCommente()==this.getNbEmpruntMax())
		{
			return 3 ;
		} // Si le media n'est pas disponible
		else if(media.isDisponible() == false)
		{
			return 4;
		}
		
		return 1;
	}

	@Override
	public int commenter(int idEmprunt, int n, String com) {
		emprunt.Emprunt empruntConcerne = this.getEmprunt(idEmprunt);
		empruntConcerne.setCommentaire(com);
		empruntConcerne.setNote(n);
		
		this.decrementerNbEmpruntEnCours();
		this.decrementerNbEmpruntNonCommente();
		empruntConcerne.getMedia().setDisponible(true);
		empruntConcerne.setFinis(true);
		
		return 1;
	}


	@Override
	public void VerifierLesEmprunts() {
		for(emprunt.Emprunt tmp : emprunts)
		{
			if(!tmp.isFinis())
			{
				tmp.faireUneNotification();
			}
		}
	}
	
}
