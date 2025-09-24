package units.interactions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import map.Tile;
import modes.GameMode;
import modes.models.GameModel;
import serialization.SBufferedImage;
import units.Unit;
import units.upgrades.UnitUpgradable;
import achiever.Achiever;
import attribute.Attribute;
import attribute.AttributeProduction;

/**
 * Basic unit command to produce a new unit. Must pass in the unit that will be
 * produced into the constructor of this button. When clicked, it will highlight
 * the range of production, then one must click a spot within range to place the
 * new instance of that unit on the selected tile. Uses AttributeProduction to
 * determine range of production.
 * 
 * @author Matthew
 * 
 */
public class UnitButtonProduce extends ButtonTargetRequired {

    private String imageFilepath;
    private Unit myUnit;

    public UnitButtonProduce(Unit unit) {
        this.imageFilepath = unit.addButton();
        myUnit = unit;
    }

    @Override
    public SBufferedImage buttonImage() {
        SBufferedImage imgS = new SBufferedImage();
        try {
            BufferedImage img = ImageIO.read(new File(imageFilepath));
            imgS.setImage(img);
        } catch (IOException e) {
        }
        return imgS;
    }

    public void performButton(GameModel myModel) {
        if(!myModel.getSelectedUnit().hasAttribute("Produce")){
            return;
        }

        myModel.getMap().unhighlightRange(targetTileBackground());
        if(!isTargetable(myModel, myModel.getSelectedDestination())) return;
        AttributeProduction produce = (AttributeProduction) myModel
                .getSelectedUnit().getAttribute("Produce");

            Unit newUnit;
            try {
                newUnit = myUnit.getClass().newInstance();
                Tile dest = myModel.getSelectedDestination();
                dest.setUnit((UnitUpgradable) newUnit);
                newUnit.setLoc(dest.getTileCoordinateX(), dest.getTileCoordinateY());
                newUnit.setOwner((Achiever) myModel.getCurrentPlayer());
                myModel.getCurrentPlayer()
                        .getAttribute(Attribute.UNIT_GROUP)
                        .augmentData(newUnit);

            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    @Override
    public String toString() {
        return "Produce";
    }

    @Override
    protected int targetTileBackground() {
        return Tile.IN_RANGE_TILE_BACKGROUND_RANK;
    }

    @Override
    protected boolean isTargetable(GameModel g, Tile tile) {
        Tile selectedTile = g.getSelectedTile();
        if(tile.getUnit()!=null) return false;
        if(!selectedTile.getUnit().hasAttribute("Produce")) return false;
        AttributeProduction att = (AttributeProduction) g.getSelectedUnit().getAttribute("Produce");
        int produceRange = att.getProductionRange();
        return (tile.getDistance(selectedTile) <= produceRange);
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        
    }

}
