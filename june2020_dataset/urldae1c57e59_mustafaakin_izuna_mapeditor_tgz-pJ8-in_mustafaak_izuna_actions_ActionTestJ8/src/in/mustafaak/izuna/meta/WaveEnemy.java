package in.mustafaak.izuna.meta;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * 
 * @author Mustafa
 */
@Root(name = "Enemy")
public class WaveEnemy {

	public void setPaths(List<WavePath> paths) {
		Paths = paths;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Attribute
	private String key;
	@ElementList
	private List<WavePath> Paths;

	/**
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 
	 * @return
	 */
	public List<WavePath> getPaths() {
		return Paths;
	}
}