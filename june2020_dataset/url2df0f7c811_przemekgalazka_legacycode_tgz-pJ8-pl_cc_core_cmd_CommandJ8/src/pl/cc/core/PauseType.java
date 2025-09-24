package pl.cc.core;
import static pl.cc.Localization.getText;

/**
 * Tryb pauzy agenta. Specjalne pauzy: - adm - administracyjna - dostępna tylko
 * dla supervisorów - def - domyślna pauza dla agenta
 * 
 * @since 2009-06-02
 */
public class PauseType {
	public  static final int ID_AUTO = 0;
	public  static final int ID_ADMINISTRATIVE = 1; 
	public  static final int ID_DEFAULT = 2;
	
	public  static final int PAUSE_FOREVER = -1;

	private int id;
	private String name;
	private String displayName;


	public PauseType(int id, String name) {
		this.id = id;
		this.name = name;
		this.displayName=getText(name);
	}

	/**
	 * @return true jeśli to pauza typu administracyjnego - user nie może wejść w nią sam 
	 */
	public boolean isAdministrative() {
		return id==ID_ADMINISTRATIVE || id==ID_AUTO; 
	}

	public boolean isAuto() {
		return id==ID_AUTO;
	}
	
	public boolean isDefault() {
		return id==ID_DEFAULT;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PauseType other = (PauseType) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
