package frame.layout;

import java.util.ArrayList;
import java.util.HashMap;

import frame.TBComponent;
import frame.layout.container.LayoutContainer;
import frame.layout.container.VerticalLayoutContainer;

/**
 * Side to side layout
 * 
 * @author bryanyang
 *
 */
public class LeftRightLayout extends Layout{
	
	
	public HashMap<String, LayoutContainer> containers;
	
	public LeftRightLayout(){
		super();
		super.getMap().put("LEFT", new ArrayList<TBComponent>());
		super.getMap().put("RIGHT", new ArrayList<TBComponent>());
		containers = new HashMap<String, LayoutContainer>();

	}
	
	/**
	 * First checks for maximum. Let's just assume things fit
	 */
    public void update() {
    	
    	if(containers.isEmpty()) return;
    	
    	updateContainers();
    	containers.get("LEFT").addAll((ArrayList) super.getMap().get("LEFT"));
    	containers.get("RIGHT").addAll((ArrayList) super.getMap().get("RIGHT"));
    	
    }

    public void initialize() {

		containers.put("LEFT", new VerticalLayoutContainer(super.getXLoc(), super.getYLoc(), super.getXDim()/2, super.getYDim()));
		containers.put("RIGHT", new VerticalLayoutContainer(super.getXLoc() + super.getXDim()/2, super.getYLoc(), super.getXDim()/2, super.getYDim()));
	    
    }

    public void updateContainers() {
    	containers.clear();
		containers.put("LEFT", new VerticalLayoutContainer(super.getXLoc(), super.getYLoc(), super.getXDim()/2, super.getYDim()));
		containers.put("RIGHT", new VerticalLayoutContainer(super.getXLoc() + super.getXDim()/2, super.getYLoc(), super.getXDim()/2, super.getYDim()));
    }

}
