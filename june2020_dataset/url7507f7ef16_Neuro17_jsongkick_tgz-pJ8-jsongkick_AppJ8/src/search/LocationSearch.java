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
import entity.FullLocation;

public class LocationSearch extends SongkickConnector {
	private static final Logger log = LogManager.getLogger(LocationSearch.class);
	private String currentLocation;

	public LocationSearch() {
		page = 1;
		pages = 1;
		currentLocation = null;
		gson = new GsonBuilder().setPrettyPrinting().create();
		uriBld = new URIBuilder();
	}
	
	private void search(String locationName) throws URISyntaxException{
		buildURI();
		
		uri = query(locationName);
		
		executeRequest(uri);
	}
	
	public FullLocation firstLocation(String locationName) throws URISyntaxException{ 
		FullLocation l;
		JsonObject response = null;
		JsonElement firstLocationAsJson = null;
		JsonElement metroAreaAsJson = null;
		JsonElement cityAsJson = null;
		
		log.trace("Retrieving first location");
		
		search(locationName);
		
		if(!checkResponse()){
			l = null;
		}
		
		response = getJsonResponse();
		firstLocationAsJson = response.getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location").get(0);

		metroAreaAsJson = firstLocationAsJson.getAsJsonObject().getAsJsonObject("metroArea");
		cityAsJson = firstLocationAsJson.getAsJsonObject().getAsJsonObject("city");
	
		//	da: http://www.songkick.com/developer/location-search		
		
		l = new FullLocation(	Extractor.extractMetroArea(metroAreaAsJson),
								Extractor.extractCity(cityAsJson));
		clearQuery();
		log.trace("Successfully retrieved location");	
	
		return l;
	}

	public ArrayList<FullLocation> list(String locationName) throws URISyntaxException{
		log.trace("Retrieving location list");
		JsonObject response = null;
		JsonElement locationsAsJson = null;
		JsonElement cityAsJson = null;
		JsonElement metroAreaAsJson = null;
		FullLocation fullLocation = null;
		ArrayList<FullLocation> locations = new ArrayList<FullLocation>();
		
		if(currentLocation == null || !currentLocation.equals(locationName)){
			currentLocation = locationName;
			setPage(1);
		}

		search(locationName);
		
		if(!checkResponse()){
			locations = null;
		}
		//da : http://www.songkick.com/developer/location-search

		response = getJsonResponse();
		locationsAsJson = response.getAsJsonObject("resultsPage").getAsJsonObject("results").getAsJsonArray("location");
		
		if(response.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() > response.getAsJsonObject("resultsPage").get("perPage").getAsInt())
			setPages(response.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() / response.getAsJsonObject("resultsPage").get("perPage").getAsInt());
		else
			setPages(1);
		
		JsonArray locationsArray = locationsAsJson.getAsJsonArray();
		
		for(JsonElement location : locationsArray){
		
			metroAreaAsJson = location.getAsJsonObject().getAsJsonObject("metroArea");
			cityAsJson = location.getAsJsonObject().getAsJsonObject("city");
			
			fullLocation = new FullLocation(Extractor.extractMetroArea(metroAreaAsJson),
											Extractor.extractCity(cityAsJson));
			locations.add(fullLocation);
		}
		
		clearQuery();
		
		log.trace("Succesfully retrieved locations");

		return locations;
	}
	
	@Override
	protected URI query(String locationName){
		URI uri = null;
		
		try {
			uri = uriBld.setPath(SongkickConfig.getLocationPath())
						.setParameter("query", locationName)
						.setParameter("apikey", SongkickConfig.getApiKey())
						.setParameter("page", getPage().toString())
						.build();
		} catch (URISyntaxException e) {
			log.error(e.getCause());
			log.error(e.getMessage());
		}
		return uri;
	}
	
	public ArrayList<FullLocation> nextPage() throws URISyntaxException{
		if(hasNextPage()){
			setPage(getPage()+1);
			return list(currentLocation);
		}
		else
			log.debug("no more records");
		return null;
		
	}

}
