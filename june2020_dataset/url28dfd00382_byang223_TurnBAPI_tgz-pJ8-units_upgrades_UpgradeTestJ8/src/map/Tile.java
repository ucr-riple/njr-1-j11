package map;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import units.Unit;
import units.upgrades.UnitUpgradable;
import attribute.AttributeReactable;

import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.util.ImageUtil;

import environment.Environment;

/**
 * Individual Tiles that make up the levelmap. Each tile has an ArrayList of
 * integer ranks, each integer represents a certain color to display on the Tile
 * when rendered. The tile will render only the color with the highest rank.
 * Thus when one wishes to highlight a tile, he simply pushes on the Integer
 * representing highlighting to this Tile's arraylist.
 * 
 * @author Matthew
 * 
 */
public class Tile implements java.io.Serializable {

    public final static int ORIGINAL_BACKGROUND_RANK = 1;
    public final static int CURRENT_PLAYER_OWNED_BACKGROUND_RANK = 2;
    public final static int SELECTED_TILE_BACKGROUND_IMAGE_RANK = 6;
    public final static int IN_RANGE_TILE_BACKGROUND_RANK = 4;
    public final static int ATTACK_RANGE_TILE_BACKGROUND_RANK = 5;

    private Unit myUnit;
    private Background myBackground;
    private int x;
    private int y;
    private int tileDimX;
    private int tileDimY;
    private int tileCoordinateX;
    private int tileCoordinateY;
    private boolean renderable;
    private ArrayList<Environment> myEnvironment;
    private ArrayList<Integer> tileRankList;
    private Map<Integer, String> rankMap;

    public ArrayList<Integer> getTileRankeList() {
        return tileRankList;
    }

    public Tile(int xCor, int yCor) {
        tileRankList = new ArrayList<Integer>();
        fillRankMap();
        myEnvironment = new ArrayList<Environment>();
        tileDimX = 50;
        tileDimY = 50;
        try {
            myBackground = new ImageBackground(ImageUtil.resize(ImageIO
                    .read(new File(rankMap.get(ORIGINAL_BACKGROUND_RANK))),
                    tileDimX, tileDimY), tileDimX, tileDimY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        x = xCor;
        y = yCor;
        tileCoordinateX = x / tileDimX;
        tileCoordinateY = y / tileDimY;

        renderable = true;

    }
    /**
     * fills the map with Integer rank keys and location strings for the background.
     */
    private void fillRankMap() {
        rankMap = new HashMap<Integer, String>();
        rankMap.put(ORIGINAL_BACKGROUND_RANK, "resources/tiles/tile.png");
        rankMap.put(CURRENT_PLAYER_OWNED_BACKGROUND_RANK,
                "resources/tiles/tile2.png");
        rankMap.put(SELECTED_TILE_BACKGROUND_IMAGE_RANK,
                "resources/tiles/tile3.png");
        rankMap.put(IN_RANGE_TILE_BACKGROUND_RANK, "resources/tiles/tile4.png");
        rankMap.put(ATTACK_RANGE_TILE_BACKGROUND_RANK,
                "resources/tiles/tile5.png");
    }
    /**
     * Returns the maximum ranked Background stored in tileRankList.
     * 
     * 
     */
    private Background maxRankedImage() {
        if (tileRankList.size() == 0)
            try {
                return new ImageBackground(ImageUtil.resize(ImageIO
                        .read(new File(rankMap.get(ORIGINAL_BACKGROUND_RANK))),
                        tileDimX, tileDimY), tileDimX, tileDimY);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        int key = Collections.max(tileRankList);
        String fileLocation = rankMap.get(key);
        Background retbackground = null;
        try {
            retbackground = new ImageBackground(ImageUtil.resize(
                    ImageIO.read(new File(fileLocation)), tileDimX, tileDimY),
                    tileDimX, tileDimY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retbackground;

    }

    public Unit getUnit() {
        return myUnit;
    }

    public ArrayList<Environment> getEnvironment() {
        return myEnvironment;
    }

    public void setUnit(UnitUpgradable upgradable) {
        myUnit = (Unit) upgradable;
        if (myUnit != null) {
            myUnit.setLocation((double) x, (double) y);
            myUnit.setBackground(myBackground);
            myUnit.setLoc(x / tileDimX, y / tileDimY);
            // myUnit.setTile(this);
            myBackground.setToCenter(myUnit);
        }
    }

    public void setEnvironment(Environment e) {
        if (e != null) {
            e.setLocation((double) x, (double) y);
            if (myEnvironment == null) {
                myEnvironment = new ArrayList<Environment>();
            }
            myEnvironment.add(e);
        }
    }

    public void removeUnit() {
        myUnit = null;
    }

    public void removeEnviroment() {
        myEnvironment = null;
    }

    public int getDistance(Tile otherTile) {
        int distX = Math.abs(this.x / tileDimX - otherTile.x / tileDimX);
        int distY = Math.abs(this.y / tileDimX - otherTile.y / tileDimY);
        return distX + distY;
    }

    public int getTileCoordinateX() {
        return new Integer(tileCoordinateX);
    }

    public int getTileCoordinateY() {
        return new Integer(tileCoordinateY);
    }

    public int getTileDimX() {
        return tileDimX;
    }

    public int getTileDimY() {
        return tileDimY;
    }

    public void render(Graphics2D g) {
        if (renderable) {
            Background backgroundToRender = maxRankedImage();
            backgroundToRender.render(g, 0, 0, x, y, tileDimX, tileDimY);

            // render environment images
            if (myEnvironment != null) {
                displayMultipleEnviron(g, x, y);
            }

            // render unit images
            if (myUnit != null) {
                if (myUnit.isActive()) {
                    myUnit.resize(tileDimX, tileDimY);
                    myUnit.setLocation(x, y);
                    myUnit.render(g);
                } else
                    myUnit = null;
            }
            int setx = x;
            int sety = y;

        }
    }

    private void displayMultipleEnviron(Graphics2D g, int setx, int sety) {
        for (Environment e : myEnvironment) {
            if (e.isActive()) {
                e.resize(tileDimX, tileDimY);
                e.setLocation(setx, sety);
                e.render(g);

                setx += e.getDimX();
                if (isWithinBoundary(setx, e)) {
                    setx = x;
                    sety += e.getDimY();
                }
            }
        }
    }

    public boolean isWithinBoundary(int setx, Environment e) {
        return (setx + e.getDimX() > x + tileDimX);
    }

    public void toggleRenderable() {
        if (renderable)
            renderable = false;
        else
            renderable = true;
    }

    public boolean isRenderable() {
        return new Boolean(renderable);
    }

    // **************************************HIGHLIGHTING*****************************************//
    // *******************************************************************************//
    /**
     * Push an Integer rank onto this tile's ArrayList of backgrounds
     * 
     * @param backgroundRank
     */
    public void pushBackground(int backgroundRank) {
        tileRankList.add(backgroundRank);
    }

    /**
     * remove a specific tile rank from this Tile's list of ranks.
     * 
     * @param backgroundRank
     */
    public void removeBackground(int backgroundRank) {
        if (tileRankList.contains(backgroundRank)) {
            int index = tileRankList.indexOf(new Integer(backgroundRank));
            tileRankList.remove(index);
        }
    }

    /**
     * clear all tile ranks from this tile's list of ranks. This leaves the tile
     * displaying the original background color.
     */
    public void clearSelection() {
        tileRankList.clear();
    }

    public void updateEnvironmentalEffects(Unit source) {
        for (Environment e : myEnvironment) {
             ((AttributeReactable)
             source.getAttribute("Reactable")).reactToEnvironment(source, e);
        }
    }

    public void clearUnitAndEnvironment() {
        myEnvironment = null;
        myUnit = null;
    }

    public void setLocation(int xnew, int ynew) {
        x = xnew;
        y = ynew;
    }

    public void resize(int dx, int dy) {
        tileDimX = dx;
        tileDimY = dy;
    }

    // FOR DEBUGGING PURPOSES

    public String toString() {
        return "Tile [x=" + x + ", y=" + y + ", tileCoordinateX="
                + tileCoordinateX + ", tileCoordinateY=" + tileCoordinateY
                + ", myUnit=" + myUnit + ", myEnvironment=" + myEnvironment
                + "]";
    }

    public void print() {
    }

    public int getHighestRank() {
        if (tileRankList.size() == 0)
            return 1;
        return Collections.max(tileRankList);
    }

}