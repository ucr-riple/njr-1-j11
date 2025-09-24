package units.interactions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import map.Tile;
import modes.GameMode;
import modes.models.GameModel;
import serialization.SBufferedImage;
import units.Unit;
import attribute.AttributeMove;
import attribute.AttributeReactable;
import environment.Environment;
/**
 * Basic movement button.  Uses AttributeMove's moves left to determine how far it can move each turn.
 * @author Matthew
 *
 */
public class UnitButtonMove extends ButtonTargetRequired{

    
    @Override
    public SBufferedImage buttonImage() {
		SBufferedImage imgS = new SBufferedImage();
		try {
			BufferedImage img = ImageIO.read(new File("resources/buttons/movebutton.png"));
			imgS.setImage(img);
		} catch (IOException e) {
		}
		return imgS;
	}
        
    @Override
	public void performButton(GameModel myModel) {
        if(!myModel.getSelectedUnit().hasAttribute("Move")){
            return;
        }
         myModel.getSelectedTile().removeBackground(targetTileBackground());
         myModel.getMap().unhighlightRange(targetTileBackground());

         
         AttributeMove move = (AttributeMove) myModel.getSelectedUnit().getAttribute("Move");
         int distance = myModel.getSelectedTile().getDistance(
                 myModel.getSelectedDestination());
         
         if (move.getMovesLeft() >= distance
                 && isValidMove(myModel.getSelectedUnit(), myModel.getSelectedDestination())) {
        	 myModel.getSelectedTile().removeUnit();
             updateDestinationTile(myModel.getSelectedUnit(),myModel.getSelectedDestination(), myModel.getSelectedTile(), myModel);
             updateSourceUnit(myModel.getSelectedUnit(), myModel.getSelectedDestination(), distance, myModel);
             myModel.getSelectedDestination().updateEnvironmentalEffects(myModel.getSelectedUnit());
         } else {
             myModel.getSelectedTile().pushBackground(Tile.SELECTED_TILE_BACKGROUND_IMAGE_RANK);
         }
	}
    private void updateDestinationTile(Unit source, Tile destination, Tile sourceTile, GameModel myModel) {
        destination.setUnit(source);
        sourceTile.clearSelection();
        destination.pushBackground(Tile.SELECTED_TILE_BACKGROUND_IMAGE_RANK);
        myModel.setSelectedTile(destination);
    }
    private void updateSourceUnit(Unit source, Tile destination, int distance, GameModel myModel) {
        ((AttributeMove) source.getAttribute("Move")).decrementMoveCount(distance);
        source.setLoc(destination.getTileCoordinateX(), destination.getTileCoordinateY());
        myModel.setSelectedUnit(source);
    }
    private boolean isValidMove(Unit u, Tile destination){
        return (destination.getUnit() == null || !destination.getUnit().isActive()) 
        		&& validMoveEnviro(u, destination.getEnvironment());
    }

    private boolean validMoveEnviro(Unit u, ArrayList<Environment> enviroList){
    	for (Environment e: enviroList){
    		if (u.hasAttribute("Reactable") && !((AttributeReactable) u.getAttribute("Reactable")).isValidMove(u, e)){
    			return false;
    		}
    	}
    	return true;
    }
    
    @Override
    public String toString() {
        return "Move";
    }


    @Override
    protected int targetTileBackground() {
        return Tile.IN_RANGE_TILE_BACKGROUND_RANK;
    }

    @Override
    protected boolean isTargetable(GameModel g, Tile tile) {
        Tile selectedTile = g.getSelectedTile();
        AttributeMove att = (AttributeMove) g.getSelectedUnit().getAttribute("Move");
        int moveRange = att.getMovesLeft();
        if (moveRange <= 0)
        	GameMode.setCurrState(GameMode.SELECTED);
        return (tile.getDistance(selectedTile)<=moveRange&&tile.getUnit()==null && isValidMove(g.getSelectedUnit(), tile));
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
    }



}
