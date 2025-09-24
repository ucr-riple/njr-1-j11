package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import config.SongkickConfig;

public abstract class SongkickConnector{
	private static final Logger log = LogManager.getLogger(SongkickConnector.class);
	private JsonObject jsonResponse;
	protected Gson gson = new GsonBuilder().setPrettyPrinting().create();
	protected URIBuilder uriBld;
	protected URI uri;
	protected Integer page;
	protected int pages;
	
	protected Integer getPage() {
		return page;
	}

	protected void setPage(int page) {
		this.page = page;
	}
	
	public boolean hasNextPage() {
		return page < pages;
	}

	protected int getPages() {
		return pages;
	}

	protected void setPages(int pages) {
		this.pages = pages;
	}
	
	protected URI getUri() {
		return uri;
	}

	protected void setUri(URI uri) {
		this.uri = uri;
	}

	protected URL url;
	

	/**
	 * Builds a string representing response from songkick ready to be parse as JSON.
	 * 
	 * @param  response					HttpRespose received by HttpGet call.	
	 * @return StringBuffer				String representing the response. Ready to parse as JSON.
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	protected JsonObject parseResponseAsJson(InputStream response){
		log.trace("Entering parseResponseAsJson");
		BufferedReader rd = new BufferedReader(new InputStreamReader(response));
		
		JsonParser jsonParser = new JsonParser();
		
		StringBuffer result = new StringBuffer();
		
		String line = "";
		
		try {
			while ((line = rd.readLine()) != null) {
			    result.append(line);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		log.trace("Exiting parseResponseAsJson");
		return jsonParser.parse(result.toString()).getAsJsonObject();
	}
	
	/**
	 * Performs HTTP get given a valid URI, returns JSON object representation of response.	
	 * 
	 * @param uri
	 * @return JsonObject
	 */
	protected JsonObject executeRequest(URI uri){
		//TODO - capire come accedere ai risultati che sono oltre la prima pagina. 
		InputStream response;
		
		try {
			url = uri.toURL();
		} catch (MalformedURLException e1) {
			
		}
				
		try {
			response = url.openStream();
			
			log.debug(response.toString());
			log.debug(uri.toString());
			
			jsonResponse = this.parseResponseAsJson(response);
			
			log.debug("Number of entries: " + jsonResponse.getAsJsonObject("resultsPage").get("totalEntries").getAsString());
			log.debug("Number of results per page: " + jsonResponse.getAsJsonObject("resultsPage").get("perPage").getAsString());
			log.debug("Page number: " + jsonResponse.getAsJsonObject("resultsPage").get("page"));
			
//			log.debug(gson.toJson(jsonResponse));
			
		} catch (ClientProtocolException e) {
			log.error(e.getMessage());
			
		} catch (IOException e) {
			log.error(e.getMessage());
			jsonResponse =  null;
		}
		
		return jsonResponse;
	}
	
	protected boolean isNullResponse(){
		return jsonResponse == null;
	}
	
	protected boolean isEmptyResponse(){
		return jsonResponse.getAsJsonObject("resultsPage").get("totalEntries").getAsInt() == 0;
	}

	protected JsonObject getJsonResponse() {
		return jsonResponse;
	}
	
	protected boolean checkResponse(){
		if(isNullResponse()){
			log.error("Timeout scaduto");
			return false;
		}
			
		if(isEmptyResponse()){
			log.error("Resource not found");
			return false;
		}
		
		return true;
	}
	
	protected void buildURI(){
		log.trace("Building URI");
		
		uriBld.setScheme(SongkickConfig.getScheme()).setHost(SongkickConfig.getHost());		
		
		log.trace("Succesfully build:"); 
	}
	
	
	protected void clearQuery(){
		if(!hasNextPage()){
			setPage(1);
			log.debug("no other pages");
		}
	}
	
	protected abstract URI query(String param) throws URISyntaxException;
	
	//TODO - come faccio a creare un metodo astratto che può tornare una lista ma non conosco a priori il tipo di dato che contiene
//	public abstract GenericArrayType nextPage(Object obj) throws URISyntaxException;
}