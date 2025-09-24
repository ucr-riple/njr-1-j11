package test;

import java.util.ArrayList;

import medias.*;
import scolarite.*;
import utilisateurs.*;

public class Bdd {
	
	public static ArrayList<Genre> listGenres = new ArrayList<Genre> ();
	public static ArrayList<Media> listMedias = new ArrayList<Media> ();

	public static ArrayList<Cycle> listCycles = new ArrayList<Cycle> ();
	public static ArrayList<Matiere> listMatieres = new ArrayList<Matiere> ();
	public static ArrayList<Cours> listCours = new ArrayList<Cours> ();
	public static ArrayList<Projet> listProjets = new ArrayList<Projet> ();
	public static ArrayList<Stage> listStages = new ArrayList<Stage> ();

	public static ArrayList<Enseignant> listEnseignants = new ArrayList<Enseignant> ();
	public static ArrayList<Etudiant> listEtudiants = new ArrayList<Etudiant> ();
	public static ArrayList<Administrateur> listAdmins = new ArrayList<Administrateur> ();


	
	public static void main(String[] args) {
		
		//Création de genres
		Genre livre = new Genre(1,"Livre", 14);
		Genre film = new Genre(2,"Film", 7);
		Genre documentaire = new Genre(3,"Documentaire", 14);
		
		listGenres.add(livre);
		listGenres.add(film);
		listGenres.add(documentaire);
		
		//Création de cycle
		Cycle l3 = new Cycle(1,"L3");
		Cycle m1 = new Cycle(2,"M1");
		
		listCycles.add(l3);
		listCycles.add(m1);
		
		//Création de Matière
		Matiere idl = new Matiere(1, "IDL");
		Matiere occam = new Matiere(2, "OCCAM");
		
		listMatieres.add(idl);
		listMatieres.add(occam);
		
		//Création d'enseignant
		Enseignant fabiani = new Enseignant(0,"FABIANI","Erwan","Enseignant",10); 
		Enseignant gire = new Enseignant(1,"GIRE","Sophie","Enseignant",10);
		
		listEnseignants.add(fabiani);
		listEnseignants.add(gire);
		
		// Création d'utilisateurs
		Etudiant e1 = new Etudiant(1,"VISBECQ","Romain alias Le BG","Etudiant",5);
		Etudiant e2 = new Etudiant(2,"FRIANT","Jérémy","Etudiant",5);
		Etudiant e3 = new Etudiant(3,"MONNIER","Alexandre","Etudiant",5);
		Etudiant e4 = new Etudiant(4,"COLLIN","Maxime","Etudiant",5);
		
		listEtudiants.add(e1);
		listEtudiants.add(e2);
		listEtudiants.add(e3);
		listEtudiants.add(e4);
		
		//Création de cours
		Cours c1 = new Cours(m1,occam,fabiani,1,"Occam : algorithmes distribués");
		Cours c2 = new Cours(m1,idl,gire,2,"Compilation : JFlex");
		
		listCours.add(c1);
		listCours.add(c2);
		
		//Création de média
		Media med1 = new Media(c1,livre,1,"OCCAM : initiation","Erwan Fabiani","La vie passsionante que l'on peut avoir avec l'OCCAM !!!",0);
		Media med2 = new Media(c2,film,2,"Compilation","Gire","Les méfaits du bug sur la compil'",180);
		Media med3 = new Media(c2,documentaire,3,"Compilation2","Bob","resume2",0);
		Media med4 = new Media(c1,documentaire,4,"OCCAM2","Jacques","resume2",0);
		Media med5 = new Media(c2,documentaire,5,"Compilation3","Bob","resume3",0);
		Media med6 = new Media(c1,documentaire,6,"Compilation2","Bob","resume2",0);
		Media med7 = new Media(c2,documentaire,7,"Compilation2","Bob","resume2",0);
		
		listMedias.add(med1);
		listMedias.add(med2);
		listMedias.add(med3);
		listMedias.add(med4);
		listMedias.add(med5);
		listMedias.add(med6);
		listMedias.add(med7);
		
		//
		
		
		
	}

}
