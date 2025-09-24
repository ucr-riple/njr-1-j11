package pl.cc.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.log4j.Logger;
/**
 * Lista dostępnych pauz
 *
 * @since 2009-06-02
 */
public class PauseTypeList extends ArrayList<PauseType> {
	private static final long serialVersionUID = 602473443032192111L;
	static final Logger log = Logger.getLogger(PauseTypeList.class);
	
	public PauseType findByValue(String value){
		for (PauseType pauseType : this){
			if (pauseType.getName().equalsIgnoreCase(value)) return pauseType;
		}
		return null;
	}

	public PauseType findById(int id){
		for (PauseType pauseType : this){
			if (pauseType.getId()==id) return pauseType;
		}
		return null;
	}

	
	public PauseType getDefault(){
		for (PauseType pauseType : this){
			if (pauseType.isDefault()) return pauseType;
		}
		log.warn("getDefault - not found");
		return null;
	}
	
	public ArrayList<PauseType> getNoAdministrative(){
		PauseTypeList list = new PauseTypeList();
		for (PauseType pauseType : this){
			if (! pauseType.isAdministrative()){
				list.add(pauseType);
			}
		}
		return list;
	}
	
	public void sort(){
		class Com implements Comparator<PauseType> {

			@Override
			public int compare(PauseType o1, PauseType o2) {
				return new Integer(o2.getId()).compareTo(o1.getId());
			} 
		
		}
		Collections.sort(this, new Com());
	}

	public PauseType getAutoPauseDef() {
		for (PauseType pauseType : this){
			if (pauseType.isAuto()){
				return pauseType;
			}
		}
		log.error("AutoPause def not found");
		return null;
	}
	
}
