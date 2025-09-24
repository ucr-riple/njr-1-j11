package units.interactions;

import map.Tile;
import modes.models.GameModel;

/**
 * Specific type of UnitButton that requires a target after the command is
 * clicked. e.g., click a move button, then click a space on the map to move to.
 * 
 * @author Matthew
 * 
 */
public abstract class ButtonTargetRequired extends InteractionUnitButton implements
        java.io.Serializable {
    /**
     * returns the tiletype(mapped by an public static final integers in Tile)
     * that this button will change in range tiles to when highlighting the
     * range of this interaction.
     * 
     * @return
     */
    protected abstract int targetTileBackground();

    /**
     * Highlights target-able tiles for this interaction. Uses (boolean
     * isTargetable) to determine whether a tile should be highlighted.
     * 
     * @param g
     */
    public void highlightTargetableTiles(GameModel g) {
        Tile[][] tiles = g.getMap().getTiles();
        for (int r = 0; r < g.getMap().getDimY(); ++r) {
            for (int c = 0; c < g.getMap().getDimX(); ++c) {
                if (isTargetable(g, tiles[r][c]))
                    tiles[r][c].pushBackground(targetTileBackground());
            }
        }
    }

    /**
     * returns the boolean for whether a particular tile is target-able by this
     * interaction.
     * 
     * @param g
     * @param tile
     * @return
     */
    protected abstract boolean isTargetable(GameModel g, Tile tile);

}
