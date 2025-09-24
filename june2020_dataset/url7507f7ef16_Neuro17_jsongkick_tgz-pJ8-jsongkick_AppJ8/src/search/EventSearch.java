package search;

import http.SongkickConnector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import config.SongkickConfig;
import entity.Artist;
import entity.Concert;

public class EventSearch extends SongkickConnector {
	private static final Logger log = LogManager.getLogger(EventSearch.class);
	
	private String currentlocationId;
	private Artist currentArtist;

	public EventSearch() {
		page = 1;
		pages = 1;
		currentArtist = null;
		currentlocationId = null;
		uriBld = new URIBuilder();
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	private Artist getCurrentArtist() {
		return currentArtist;
	}

	private void setCurrentArtist(Artist currentArtist) {
		this.currentArtist = currentArtist;
	}
	
	public String getLocationId() {
		return currentlocationId;
	}

	private void setLocationId(String locationId) {
		this.currentlocationId = locationId;
	}

	@Override
	protected URI query(String locationId){
		log.trace("setting parameters to query");
		
		try {
			return uriBld.setPath(SongkickConfig.getEventPath())
						 .setCustomQuery("location=sk:"+locationId+"&apikey="+SongkickConfig.getApiKey()+"&page="+getPage()).build();
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}
	
	private URI queryByArtistId(String artistId) throws URISyntaxException{		
		URI uri;
		try {
			uri = uriBld	.setPath(SongkickConfig.getArtistPathForEvent())
							.setPath(SongkickConfig.getArtistPathForEvent() + 
									"/" + artistId +
									SongkickConfig.getArtistPathForEventCalendar())
							.setCustomQuery("apikey=" + SongkickConfig.getApiKey() + "&page=" + getPage())
							.build();
			log.debug(uri);
			return uri;
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}
	
	public ArrayList<Concert> eventsListByLocationId(String locationId) throws URISyntaxException{	
		log.trace("entering eventList by location id");
		ArrayList<Concert> concerts = new ArrayList<Concert>();
		JsonObject events = new JsonObject();
		
		buildURI();
		
		if(getLocationId() == null || !getLocationId().equals(locationId)){
			setLocationId(locationId);
			setPage(1);
		}
		
		uri = query(getLocationId());
		
		log.debug("uri built: " + uri);
		
		executeRequest(uri);
		 
		events = getJsonResponse();
		if(events.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() > events.getAsJsonObject("resultsPage").get("perPage").getAsInt())
			setPages(events.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() / events.getAsJsonObject("resultsPage").get("perPage").getAsInt());
		else
			setPages(1);
		
//		log.debug(gson.toJson(events.toString()));:
		JsonArray listTmp = events.getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("event");
		
		for(JsonElement item : listTmp){
			concerts.add(Extractor.extractConcert(item));	
		}
	
//		log.debug(listTmp.get(0).toString());
//		concerts = events.getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("event");
		
		clearQuery();
		
		log.trace("exiting eventsListByLocationId");
		
		return concerts;
	}
	
	public ArrayList<Concert> eventsListByArtist(Artist artist) throws URISyntaxException{
		log.trace("entering eventLyst by artist id");
		
		ArrayList<Concert> concerts = new ArrayList<Concert>();
		JsonObject events = new JsonObject();
		
		if(getCurrentArtist() == null || !getCurrentArtist().getId().equals(artist.getId())){
			setCurrentArtist(artist);
			setPage(1);
		}
		
		buildURI();
		
		uri = queryByArtistId(currentArtist.getId());
		
		log.debug("http://api.songkick.com/api/3.0/artists/{artist_id}/calendar.json?apikey={your_api_key}");
		
		log.debug("uri built: " + uri);
		
		executeRequest(uri);

		events = getJsonResponse();
		
		log.debug(events.getAsJsonObject("resultsPage").get("page").getAsString());
		if(events.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() > events.getAsJsonObject("resultsPage").get("perPage").getAsInt())
			setPages(events.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() / events.getAsJsonObject("resultsPage").get("perPage").getAsInt());
		else
			setPages(1);
		
//		log.debug(events);
		JsonArray listTmp = events.getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("event");
//		log.debug(gson.toJson(listTmp));
		for(JsonElement item : listTmp){
			Concert concert = Extractor.extractConcert(item);
			concerts.add(concert);
		}
		
//		log.debug(concerts);
		
		clearQuery();
		log.trace("exiting eventsListByArtistId");
	
		return concerts;
	}

	public ArrayList<Concert> nextPage(Object obj) throws URISyntaxException{
		
		if(hasNextPage()){
			setPage(getPage()+1);
			if(obj instanceof Artist)
				return eventsListByArtist(getCurrentArtist());
			else if (obj instanceof String)
				return eventsListByLocationId(getLocationId());
		}
		else
			log.debug("no more records");
		return null;
	}
	
	public JsonObject toJson(){
		//TODO (DOMANDA) a cosa servirà in futuro convertire in json un concerto?
		//guardare : http://stackoverflow.com/questions/19618174/how-to-convert-complex-java-object-into-json
		return null;
	}

}