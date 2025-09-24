package search;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import entity.Artist;
import entity.City;
import entity.Concert;
import entity.FullLocation;
import entity.SimpleLocation;
import entity.MetroArea;
import entity.Venue;

public class Extractor {
	
	private static final Logger log = LogManager.getLogger(Extractor.class);
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static Concert extractConcert(JsonElement item){
		Concert event = new Concert();
		JsonObject concertTmp = item.getAsJsonObject();
		Venue venueTmp;
		DateTime datetime;
		MetroArea metroAreaTmp;
		LocalDate localDate;
		JsonArray artistListTmp;
		ArrayList<Artist> artistList = new ArrayList<Artist>();
		
//		log.debug(gson.toJson(concertTmp));
		event.setPopularity(concertTmp.get("popularity").getAsDouble());
		event.setId(concertTmp.get("id").getAsString());
		
//		TODO - Implementare DateFormatter usando il pattern per la stringa da parsare.
		
//		log.debug("datetime null? "+ concertTmp.getAsJsonObject("start").get("datetime").isJsonNull());
		
		datetime = concertTmp.getAsJsonObject("start").get("datetime").isJsonNull() ? null : new DateTime(concertTmp.getAsJsonObject("start").get("datetime").getAsString());
		
		localDate = new LocalDate(concertTmp.getAsJsonObject("start").get("date").getAsString());
		
		//TODO - verificare perchè a volte viene creata una data corrente invece che la data del concerto reale
		
		event.setDateTime(new DateTime(datetime));
		event.setDate(localDate);		
		event.setLocation(extractLocation(concertTmp.get("location")));
		
		// memorizzo le performance su un JsonArray ed estraggo l'artista dal singolo JsonElement
		artistListTmp = concertTmp.getAsJsonArray("performance");
		for(JsonElement artistTmp : artistListTmp){
			artistList.add(extractArtist(artistTmp));
		}
		event.setPerformance(artistList);
		
		
		metroAreaTmp = extractMetroArea(concertTmp.getAsJsonObject("venue").getAsJsonObject("metroArea"));
		
//		log.debug(concertTmp.getAsJsonObject("venue"));
		
//		log.debug(concertTmp.getAsJsonObject("venue").get("lat"));
		if(!concertTmp.getAsJsonObject("venue").get("id").isJsonNull()){
			event.setVenue(extractVenue(concertTmp.getAsJsonObject("venue")));
		}
		
//		log.debug(event.toString());
		
		return event;
	}
	
	public static SimpleLocation extractLocation(JsonElement item){
		Double lat, lng;
		SimpleLocation location;
		JsonObject locTmp = item.getAsJsonObject();
		
		lat = locTmp.get("lat").isJsonNull() ? null : locTmp.get("lat").getAsDouble();
		lng = locTmp.get("lng").isJsonNull() ? null : locTmp.get("lng").getAsDouble();
		
		if(lat == null && lng == null){
			location = new SimpleLocation(locTmp.get("city").getAsString());
		}
		else {
			location = new SimpleLocation(lat, lng, locTmp.get("city").getAsString());
		}
		return location;
	}

	public static Venue extractVenue(JsonElement item){
		Double lat;
		Double lng;
		Venue venue;
		JsonObject vne = item.getAsJsonObject();
		
		lat = vne.get("lat").isJsonNull() ? null : vne.get("lat").getAsDouble();
		lng = vne.get("lng").isJsonNull() ? null : vne.get("lng").getAsDouble();
		
		if(lat == null && lng == null)
			venue = new Venue(	extractMetroArea(vne.get("metroArea")),
								vne.get("id").getAsString(), 
								vne.get("displayName").getAsString());
		else 
			venue = new Venue(	lat, 
								lng, 
								extractMetroArea(vne.get("metroArea")),
								vne.get("id").getAsString(),
								vne.get("displayName").getAsString());
		
		return venue;
	}
	
	public static MetroArea extractMetroArea(JsonElement item){
		MetroArea metroArea;
		JsonObject metroAreaTmp = item.getAsJsonObject();
		
		metroArea = new MetroArea(metroAreaTmp.getAsJsonObject("country").get("displayName").getAsString(),
				metroAreaTmp.get("id").getAsString(),
				metroAreaTmp.get("displayName").getAsString());
		
		return metroArea;
	}
	
	public static City extractCity(JsonElement item){
		String name;
		String country;
		Double lat;
		Double lng;
		JsonObject cityTmp = item.getAsJsonObject();
		City city = null;

		name = cityTmp.get("displayName").getAsString();
		country = cityTmp.getAsJsonObject("country").get("displayName").getAsString();
		
		lat = cityTmp.get("lat").isJsonNull() ? null : cityTmp.get("lat").getAsDouble();
		lng = cityTmp.get("lng").isJsonNull() ? null : cityTmp.get("lng").getAsDouble();
		
		if(lat == null && lng == null)
			city = new City(name,country);
		else 
			city = new City(name,country,lat,lng);;

		return city;
	}
	
	public static Artist extractArtist(JsonElement item){
		//DONE?
		Artist artist;
		JsonObject artistTmp = item.getAsJsonObject();
		
		artist = new Artist(artistTmp.get("displayName").getAsString(),artistTmp.get("id").getAsString());
		
		return artist;
	}
	
}
