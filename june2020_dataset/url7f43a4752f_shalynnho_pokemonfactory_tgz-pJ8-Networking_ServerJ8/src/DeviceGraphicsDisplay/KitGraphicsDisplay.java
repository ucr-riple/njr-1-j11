package DeviceGraphicsDisplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Networking.Client;
import Networking.Request;
import Utils.Constants;
import Utils.Location;
import factory.KitConfig;
import factory.PartType;

public class KitGraphicsDisplay extends DeviceGraphicsDisplay  {

	public int getRemoveTimer() {
		return removeTimer;
	}

	public void setRemoveTimer(int removeTimer) {
		this.removeTimer = removeTimer;
	}

	private static final int MAX_PARTS = 8;
	private int currentI;
	private Location kitLocation;
	

	private int position;
	private int removeTimer;
	private boolean  startRemove;
	
	private ArrayList<PartType> partTypes = new ArrayList<PartType>();
	private ArrayList<PartGraphicsDisplay> parts = new ArrayList<PartGraphicsDisplay>(8);
	ImageIcon kitImage;
	int velocity ;
	int yOffset;
	
	private Client kitClient;
	
	private KitConfig kitConfig;
	
	public ImageIcon getKitImage() {
		return kitImage;
	}

	public void setKitImage(Image kitImage) {
		this.kitImage.setImage( kitImage);
	}

	public KitGraphicsDisplay(Client kitClient){
		kitLocation = Constants.KIT_LOC;
		position = 0;
		kitImage = new ImageIcon( Constants.KIT_IMAGE );
		this.kitClient = kitClient;
		velocity = 0;
		startRemove = false;
		removeTimer = 0;
	}
	
	public KitGraphicsDisplay() {
		kitLocation = Constants.KIT_LOC;
		position = 0;
		velocity =0;
		kitImage = new ImageIcon( Constants.KIT_IMAGE );
		currentI = 0;
		kitConfig = new KitConfig("DummyqwerKit");
		yOffset = 10;
		startRemove = false;
		removeTimer = 0;
	}
	public KitGraphicsDisplay(KitConfig kitConfig)
	{
		this();
		this.kitConfig= kitConfig;
		currentI = kitConfig.getParts().size(); 
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setLocation(Location newLocation) {
		kitLocation = newLocation;
	}

	public Location getLocation() {
		return kitLocation;
	}
	
	public void draw(JComponent c, Graphics2D g) {
		
	}
	
	public void drawKit(JComponent c, Graphics2D g) {
		if(startRemove)
		{
			removeTimer--;
		}
		drawWithOffset(c, g, 0);
		setLocation(new Location(kitLocation.getX()+velocity, kitLocation.getY()));
	}

	public void drawWithOffset(JComponent c, Graphics2D g, int offset) {
		g.drawImage(kitImage.getImage(), kitLocation.getX() + offset,
				kitLocation.getY()+yOffset, c);

		convertPartTypesToDisplay();
		//TODO fix so that it draws the actual parts
		for(int i =0; i<parts.size();  i++) {
			int gap =0;
			if(i==2 || i ==3 ||i ==6  || i==7)
			{
				gap = 20;
			}
			if(i<4)
				parts.get(i).setLocation(new Location(kitLocation.getX() + offset-29 + i%4*23 + gap, kitLocation.getY()-48 +yOffset) );
			else
				parts.get(i).setLocation(new Location(kitLocation.getX() + offset-29 + i%4*23 +gap, kitLocation.getY() -48 + 25+yOffset));
			
			if(!parts.get(i).getPartType().isInvisible())
			{	
				parts.get(i).drawPokeball(0,parts.get(i).getLocation(),c, g);
			}
		}

	}
	
	public void convertPartTypesToDisplay(){
	
		partTypes = kitConfig.getParts();
		parts = new ArrayList<PartGraphicsDisplay>(8);
		for(int i =0; i<currentI; i++)
		{
			PartGraphicsDisplay tempPartDisplay = new PartGraphicsDisplay(partTypes.get(i));
			parts.add(i,tempPartDisplay);
		}
	}

	public void receiveData(Request req) {
		if (req.getCommand().equals(Constants.KIT_UPDATE_PARTS_LIST_COMMAND)) {
			PartType type = (PartType) req.getData();
			receivePart(new PartGraphicsDisplay(type));
		} else if(req.getCommand().equals(Constants.CONVEYOR_RECEIVE_KIT_COMMAND)){
			moveAway();
		}
	}
	
	public int getPartsSize(){
		return parts.size();
	}

	public void receivePart(PartGraphicsDisplay pgd) { 
		
		if(currentI<MAX_PARTS)
		{
			kitConfig.addItem(pgd.partType,currentI);
			currentI++;
		}	
		
	}
	
	public void moveAway(){
		velocity = -5;
	}

	public KitConfig getKitConfig() {
		return kitConfig;
	}

	public void setKitConfig(KitConfig kitConfig) {
		this.kitConfig = kitConfig;
	}
	
	public void beginRemoval(){
		startRemove = true;
	}

}
