package entity;

public class Artist {
	private String name;
	private String id;
	
	private boolean empty;
	
	public Artist(){
		this.empty = true;
	}
	
	public Artist(String name, String id){
		this.name = name;
		this.id = id;
		this.empty = false;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isNull(){
		return this.empty;
	}

	@Override
	public String toString() {
		return "SongkickArtist [name=" + name + ", id=" + id + "]";
	}

}
