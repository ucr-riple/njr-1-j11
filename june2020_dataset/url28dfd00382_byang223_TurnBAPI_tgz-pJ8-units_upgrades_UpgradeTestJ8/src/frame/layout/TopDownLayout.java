package frame.layout;

import java.util.ArrayList;
import java.util.HashMap;

import frame.TBComponent;
import frame.layout.container.LayoutContainer;
import frame.layout.container.VerticalLayoutContainer;

/**
 * Top Down layout
 * 
 * @author bryanyang
 *
 */
public class TopDownLayout extends Layout{
	
	private int horizontalDivider;
	
	public HashMap<String, LayoutContainer> containers;
	
	public TopDownLayout(){
		super();
		super.getMap().put("TOP", new ArrayList<TBComponent>());
		super.getMap().put("BOTTOM", new ArrayList<TBComponent>());
		horizontalDivider = getYDim()/2;
		containers = new HashMap<String, LayoutContainer>();

	}
	
	/**
	 * First checks for maximum. Let's just assume things fit
	 */
    public void update() {
    	
    	if(containers.isEmpty()) return;
    	
    	updateContainers();
    	containers.get("TOP").addAll((ArrayList) super.getMap().get("TOP"));
    	containers.get("BOTTOM").addAll((ArrayList) super.getMap().get("BOTTOM"));
    	
    }

    public void initialize() {

		containers.put("TOP", new VerticalLayoutContainer(getXLoc(), getYLoc(), getXDim(), getYDim()/2));
		containers.put("BOTTOM", new VerticalLayoutContainer(getXLoc(), getYLoc()+getYDim()/2, getXDim(), getYDim()/2));
	    
    }

    public void updateContainers() {
    	containers.clear();
		containers.put("TOP", new VerticalLayoutContainer(getXLoc(), getYLoc(), getXDim(), getYDim()/2));
		containers.put("BOTTOM", new VerticalLayoutContainer(getXLoc(), getYLoc()+getYDim()/2, getXDim(), getYDim()/2));	    
    }

}
