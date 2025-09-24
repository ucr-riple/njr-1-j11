package in.mustafaak.izuna.meta;

import in.mustafaak.izuna.actions.Action;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * 
 * @author Mustafa
 */
@Root(name = "Wave")
public class WaveInfo {

	@ElementList
	private List<WaveEnemy> Enemies = new ArrayList<WaveEnemy>();

	/**
	 * 
	 * @return
	 */
	public List<WaveEnemy> getEnemies() {
		return Enemies;
	}
	
	public void setEnemies(List<WaveEnemy> enemies) {
		Enemies = enemies;
	}
	
	public void putEnemy(String ship, Action... actions) {
		WaveEnemy waveEnemy = new WaveEnemy();
		waveEnemy.setKey(ship);
		waveEnemy.setPaths(Action.construct(null, actions));
		getEnemies().add(waveEnemy);
	}
	
	public static WaveInfo combine(WaveInfo... waveInfos) {
		WaveInfo waveInfo = new WaveInfo();
		waveInfo.setEnemies(new ArrayList<WaveEnemy>());
		for (WaveInfo w : waveInfos) {
			waveInfo.getEnemies().addAll(w.getEnemies());
		}
		return waveInfo;
	}
}
