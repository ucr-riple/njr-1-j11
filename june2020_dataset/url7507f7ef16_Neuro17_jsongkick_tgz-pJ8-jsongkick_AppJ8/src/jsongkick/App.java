package jsongkick;

import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import search.ArtistSearch;
import search.EventSearch;
import search.LocationSearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Artist;
import entity.Concert;
import entity.FullLocation;


public class App {
	
	private static final Logger log = LogManager.getLogger(App.class);

	public static void run() throws URISyntaxException{
		

		ArtistSearch artistSearch = new ArtistSearch();
		LocationSearch locationSearch = new LocationSearch();
		EventSearch eventSearch = new EventSearch();
		ArrayList <Artist> artists = new ArrayList<Artist>();
		Artist art;
//		art = artistSearch.firstArtist("Metallica");
//		log.debug(art.toString());
	
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
//		test firstLocation
		log.debug("test firstLocation -------------------------------------------");
		FullLocation fl = locationSearch.firstLocation("rome");
		log.debug("location found : " + fl.getMetroarea() + " - " + fl.getCity());
		
//		Test eventsListByLocationId
		log.debug("Test eventsListByLocationId -------------------------------------------");
		FullLocation fullLocation = locationSearch.firstLocation("new york");
		ArrayList<Concert> events = eventSearch.eventsListByLocationId(fullLocation.getMetroarea().getId());
		log.debug(fullLocation.getCity().getName() + " concerts");
		for(Concert event : events){
			if(event.getPopularity() > 0.3)
				log.debug(event.toString());
		}
		
//		test nextPage EventSearch by location
		log.debug("Test nextPage EventSearch by location -------------------------------------------");
		int size = 10;
		events = eventSearch.nextPage(fullLocation.getMetroarea().getId());
		if(events.size() < 10)
			size = events.size();
		log.debug(fullLocation.getCity().getName() + " concerts");
		for(Concert concert : events.subList(0, size)){
			log.debug(concert.toString());
		}

		
//		Test location list
		log.debug("Test location list -------------------------------------------");
		ArrayList<FullLocation> fullLocations = locationSearch.list("rome");
		
		for(FullLocation fullLoc : fullLocations)
			log.debug("location found : " + fullLoc.getMetroarea() + " - " + fullLoc.getCity());
		
//		Test nextPage locationSearch list
		log.debug("Test nextPage locationSearch list -------------------------------------------");
		fullLocations = locationSearch.nextPage();
		if(locationSearch.hasNextPage()){
			for(FullLocation fullLoc : fullLocations)
				log.debug("location found : " + fullLoc.getMetroarea() + " - " + fullLoc.getCity());
		}
//		test uri artist search
		log.debug("test uri artist search -------------------------------------------");
		art = artistSearch.firstArtist("2cellos");
		
		
//		Test eventsListByArtistId
		log.debug("Test eventsListByArtistId -------------------------------------------");
		ArrayList<Concert> concerts = eventSearch.eventsListByArtist(art);
		log.debug(art.getName() + " concerts");
		for(Concert concert : concerts.subList(0, 5)){
			log.debug(concert.toString());
		}
		
//		test nextPage EventSearch by artist
		log.debug("Test nextPage EventSearch by artist -------------------------------------------");
		size = 10;
		concerts = eventSearch.nextPage(art);
		
		if(eventSearch.hasNextPage()){
			if(concerts.size() < 10)
				size = concerts.size();
			log.debug(art.getName() + " concerts");
			for(Concert concert : concerts.subList(0, size)){
				log.debug(concert.toString());
			}
		}	
		
//		test artists list search
		log.debug("Test artistsList search -------------------------------------------");
		 artists = artistSearch.list("a");
		 for(Artist artist: artists.subList(0, 10)){
			 log.debug(artist.toString());
		 }
		 
//		 test nextPage ArtistSearch
		 log.debug("Test nextPage ArtistSearch -------------------------------------------");
		 artists = artistSearch.nextPage();
		 for(Artist artist: artists.subList(0, 10)){
			 log.debug(artist.toString());
		 }
	}	
	public static void main(String[] args){
		try {
			App.run();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
}
