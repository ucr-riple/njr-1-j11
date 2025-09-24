package config;

public class SongkickConfig {
	
	private static final String SCHEME = "http";
	
	private static final String HOST = "api.songkick.com/api/3.0";
	
	private static final String ARTIST_PATH = "/search/artists.json";
	
	private static final String ARTIST_PATH_FOR_EVENT = "/artists";
	
	private static final String ARTIST_PATH_FOR_EVENT_CALENDAR = "/calendar.json";
	
	private static final String CONCERT_PATH = "";
	
	private static final String LOCATION_PATH = "/search/locations.json";
	
	private static final String EVENT_PATH = "/events.json";

	private static String apiKey = "iF1N0jYrhI5wtG3n";
	
	public static String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		SongkickConfig.apiKey = apiKey;
	}

	public static String getLocationPath() {
		return LOCATION_PATH;
	}

	public static String getScheme() {
		return SCHEME;
	}

	public static String getHost() {
		return HOST;
	}

	public static String getArtistPath() {
		return ARTIST_PATH;
	}

	public static String getConcertPath() {
		return CONCERT_PATH;
	}

	public static String getEventPath() {
		return EVENT_PATH;
	}

	public static String getArtistPathForEvent() {
		return ARTIST_PATH_FOR_EVENT;
	}

	public static String getArtistPathForEventCalendar() {
		return ARTIST_PATH_FOR_EVENT_CALENDAR;
	}
	
}
