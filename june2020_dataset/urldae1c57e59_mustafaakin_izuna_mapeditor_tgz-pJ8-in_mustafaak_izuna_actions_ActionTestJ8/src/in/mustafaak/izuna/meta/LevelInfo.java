package in.mustafaak.izuna.meta;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * 
 * @author Mustafa
 */
@Root(name = "Level")
public class LevelInfo {
	@Element
	private String Name;
	@Attribute
	private int no;
	@Element
	private String Password;
	@ElementList()
	private List<WaveInfo> Waves;

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 
	 * @return
	 */
	public int getNo() {
		return no;
	}

	public String getPassword() {
		return Password;
	}

	/**
	 * 
	 * @return
	 */
	public List<WaveInfo> getWaves() {
		return Waves;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public void setWaves(List<WaveInfo> waves) {
		Waves = waves;
	}

}
