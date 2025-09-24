package search;

import http.SongkickConnector
;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import config.SongkickConfig;
import entity.Artist;

public class ArtistSearch extends SongkickConnector {
	private static final Logger log = LogManager.getLogger(ArtistSearch.class);
	
	private Artist currentArtist;
	
	public ArtistSearch(){
		page = 1;
		pages = 1;
		currentArtist = null;
		gson = new GsonBuilder().setPrettyPrinting().create();
		uriBld = new URIBuilder();
	}
	
	private void search(String artistName) throws URISyntaxException{
		buildURI();
		
		uri = query(artistName);
		
		executeRequest(uri);
	}
		
	/**
	 * Extracts the first artist received by songkick response.
	 * @throws URISyntaxException 
	 */
	public Artist firstArtist(String artistName) throws URISyntaxException{
		String name;
		String id;
		Artist artist;
		JsonElement firstArtist = null;
		
		log.trace("Retrieving first artist");
		
		search(artistName);
		
		if(!checkResponse()){
			artist = null;
		}
	
		firstArtist = getJsonResponse().getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("artist").get(0);
		
		name = firstArtist.getAsJsonObject().get("displayName").getAsString();
		id = firstArtist.getAsJsonObject().get("id").getAsString();
		
		log.trace("Successfully retrieved artist");
		artist = new Artist(name, id);
		return artist;
		
	}
	
	public ArrayList<Artist> list(String artistName) throws URISyntaxException{
		log.trace("Retrieving artists list");
		String name;
		String id;
		JsonElement artistsAsJson = null;
		ArrayList<Artist>  artists = new ArrayList<Artist>();
		JsonObject response = new JsonObject();
		
		if(currentArtist == null || !currentArtist.getName().equals(artistName)){
			currentArtist = new Artist();
			currentArtist.setName(artistName);
			setPage(1);
		}
		
		search(artistName);
		
		if(!checkResponse()){
			return artists;
		}
		
		response = getJsonResponse();
		artistsAsJson = response.getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("artist");
		
		if(response.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() > response.getAsJsonObject("resultsPage").get("perPage").getAsInt())
			setPages(response.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() / response.getAsJsonObject("resultsPage").get("perPage").getAsInt());
		else
			setPages(1);
		
		for(JsonElement artist : artistsAsJson.getAsJsonArray() ){
			name = artist.getAsJsonObject().get("displayName").getAsString();
			id = artist.getAsJsonObject().get("id").getAsString();
			
			log.debug(gson.toJson(name));
			log.debug(gson.toJson(id));
			
			artists.add(new Artist(	name, id));
		}
		
		clearQuery();
		
		log.trace("Successfully retrieved artists");
		return artists;
	}
	
	@Override
	protected URI query(String artistName) throws URISyntaxException{
//		return uriBld.setPath(SongkickConfig.getArtistPath()).setParameter("query", artistName).setParameter("apikey", SongkickConfig.getApiKey()).build();
		log.trace("setting parameters to query");
		URI uri;
		try {
			uri = uriBld.setPath(SongkickConfig.getArtistPath())
						 .setCustomQuery("&query=" + artistName +"&apikey="+SongkickConfig.getApiKey()+"&page="+getPage()).build();
			log.debug(uri);
			return uri;
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}

	public ArrayList<Artist> nextPage() throws URISyntaxException{
		if(hasNextPage()){
			setPage(getPage()+1);
			return list(currentArtist.getName());
		}
		else
			log.debug("no more records");
		return null;
		
	}
	
}