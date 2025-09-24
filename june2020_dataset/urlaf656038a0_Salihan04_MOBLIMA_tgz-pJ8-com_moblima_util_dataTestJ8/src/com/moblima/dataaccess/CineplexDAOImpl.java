package com.moblima.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.moblima.model.Cineplex;

public class CineplexDAOImpl implements CineplexDAO {
	
	private static CineplexDAO cineplexDAO;
	private SerializeDB serializeDB;
	private List<Cineplex> cineplexes;
	
	public static CineplexDAO getInstance() {
		if(cineplexDAO == null) {
			cineplexDAO = new CineplexDAOImpl();
		}
		return cineplexDAO;
	}
	
	private CineplexDAOImpl() {
		serializeDB = SerializeDB.getInstance();
		cineplexes = serializeDB.getCineplexes();
	}	

	public void createCineplex(Cineplex cineplex) {
		int cineplexID = serializeDB.getCineplexID();
		cineplex.setCineplexCode(cineplexID);
		serializeDB.setCineplexID(cineplexID+1);
		
		cineplexes.add(cineplex);
		
		serializeDB.saveData();
	}

	public void updateCineplex(Cineplex cineplex) {
		serializeDB.saveData();
	}

	public void deleteCineplex(Cineplex cineplex) {
		cineplexes.remove(cineplex);
		serializeDB.saveData();
	}

	public Cineplex getCineplex(int cineplexCode) {
		for(Cineplex cp: cineplexes) {
			if(cp.getCineplexCode() == (cineplexCode))
				return cp;
		}
		return null;
	}

	public List<Cineplex> getCineplexes() {
		return cineplexes;
	}

}
