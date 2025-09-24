package map;

import java.awt.Graphics2D;
import java.util.ArrayList;

import achiever.Achiever;
import attribute.Attribute;
import attribute.AttributeMapModifiable;
import frame.TBComponent;

/**
 * Class representing abstract idea of game map.  Contains a grid of Tiles with various getters/setters.
 * 
 * @author bryanyang, Matthew Tse
 * 
 */

public class LevelMap implements TBComponent, java.io.Serializable, Achiever {


    public final static int DEFAULT_TILE_SIZE = 50;
    
    
    private static int DEFAULT_MAP_BYTE_WIDTH = 500;
    private static int DEFAULT_MAP_BYTE_HEIGHT = 500;
    private static int DEFAULT_MAP_BYTE_AREA = DEFAULT_MAP_BYTE_WIDTH * DEFAULT_MAP_BYTE_HEIGHT;
    
    private int tileDimX;
    private int tileDimY;

    private Tile[][] myTiles;
    private ArrayList<Tile> myTilesList;
    private int dimX, dimY;
    private int tileSize;
    private int xLoc, yLoc;
    
    private AttributeMapModifiable mapModifiable;
    private boolean useDeafult;

    /**
     * Default Map Initialization
     */
    public LevelMap(){
    	this(10, 10);
    }
    
    /**
     * Creates map with dimensions t by t
     * @param t
     */
    public LevelMap(int t){
    	this(t,t);
    }
    
    /**
     * Constructor for rectangular maps
     * 
     * @param x
     * @param y
     */
    public LevelMap(int x, int y) {
        myTiles = new Tile[x][y];
        dimX = x;
        dimY = y;
        if (tileDimX == 0 || tileDimY == 0) calculateTileSize();
        initialize();
    }

    /**
     * Constructor for rectangular map with tile size
     * @param x
     * @param y
     * @param t
     */
    public LevelMap(int x, int y, int t) {
        myTiles = new Tile[x][y];
        dimX = x;
        dimY = y;
        tileDimX = t;
        tileDimY = t;
        initialize();
    }
    

    
    public LevelMap(int x, int y, int t, int xlocation, int ylocation){
    	myTiles = new Tile[x][y];
    	dimX = x;
    	dimY = y;
    	tileDimX = t;
    	tileDimY = t;
    	xLoc = xlocation;
    	yLoc = ylocation;
    	initialize();
    	
    }
    

    
    public void calculateTileSize () {
        int tileByteArea = DEFAULT_MAP_BYTE_AREA / (dimX * dimY);
        tileDimX = (int) Math.ceil(Math.sqrt((double) tileByteArea));
        tileDimY = (int) Math.floor(Math.sqrt((double) tileByteArea));
    }
    
    public int getTileDimX () {
        return new Integer(tileDimX);
    }

    public int getTileDimY () {
        return new Integer(tileDimY);
    }


    public int getRowIndex(int y) {
        return y / tileDimY;
    }

    public int getColumnIndex(int x) {
        return x / tileDimX;
    }

    public Tile getTileByCoords(int x, int y) {
        return myTiles[x][y];
    }

    public Tile getTileByPixels(int x, int y) {
        int xDiv;
        int yDiv;
        x -= xLoc;
        y -= yLoc;
        if (x <= tileDimX * dimX && y <= tileDimY * dimY) {
            xDiv = getRowIndex(x);
            yDiv = getColumnIndex(y);
            return getTileByCoords(xDiv, yDiv);
        }

        return null;
    }

    private void initialize() {
        myTilesList = new ArrayList<Tile>();
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                Tile newtile = new Tile(i * tileDimX+xLoc, j * tileDimY+yLoc);
                myTiles[i][j] = newtile;
                myTilesList.add(newtile);
            }
        }
        this.mapModifiable = new AttributeMapModifiable(this);
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
				myTiles[i][j].render(g);
            }
        }
    }
    
    public ArrayList<Tile> getTilesList(){
        return myTilesList;
    }
    
    public int getDimX () {
        return new Integer(dimX);
    }
    
    public int getDimY () {
        return new Integer(dimY);
    }
    
    
    //***************************HIGHLIGHTING***************************//
    
    
    public void unhighlightRange(int tileRank) {
        Tile[][] tiles = this.getTiles();
        for (int r = 0; r < this.getDimY(); ++r) {
            for (int c = 0; c < this.getDimX(); ++c) {
                if (tiles[r][c].getTileRankeList().contains(tileRank)) {
                    tiles[r][c].removeBackground(tileRank);
                }
            }
        }
    }

    public void unHighlightEverything(){
    Tile[][] tiles = this.getTiles();
    for (int r = 0; r < this.getDimY(); ++r) {
        for (int c = 0; c < this.getDimX(); ++c) {
            tiles[r][c].clearSelection();
            }
        }
    }
    //*************************************************************************//

    public void setLocation(int x, int y) {
		xLoc = x;
		yLoc = y;
		
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
            	myTiles[i][j].setLocation(i * tileDimX+xLoc, j * tileDimY+yLoc);
            }
        }
    }


    public int getHeight() {
	    return dimY * tileDimY;
    }

    public int getWidth() {
	    return dimX * tileDimX;
    }

	@Override
    public int getXLocation() {
		return xLoc;
	}

	@Override
    public int getYLocation() {
		return yLoc;
    }

	public Tile[][] getTiles() {
		return myTiles;
	}
	
	public void setTiles(Tile[][] myTiles) {
		this.myTiles = myTiles;
	}

	public void clearTiles() {
		for(Tile[] t:this.myTiles) {
			for(Tile t2:t) {
				t2.clearUnitAndEnvironment();
			}
		}
	}
	
	@Override
    public void resize(int dx, int dy) {
		tileDimX = dx/dimX;
		tileDimY = dy/dimY;
		
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
            	myTiles[i][j].resize(tileDimX, tileDimY);
            	myTiles[i][j].setLocation(i * tileDimX+xLoc, j * tileDimY+yLoc);
            }
        }
    }

    public void modify() {
        
    }

    public void addAchievementMessage(String message) {
        
    }

    public Attribute getAttribute(String name) {
        if (name.equals(Attribute.MAP_MODIFIABLE)) {
            return this.mapModifiable;
        }
        return null;
    }

    @Override
    public void addAttribute(Attribute attribute) {        
    }

    @Override
    public void removeAttribute(Attribute attribute) {        
    }

}
