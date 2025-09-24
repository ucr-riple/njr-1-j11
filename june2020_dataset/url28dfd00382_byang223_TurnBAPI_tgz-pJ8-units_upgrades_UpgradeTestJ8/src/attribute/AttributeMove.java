package attribute;

import java.util.ArrayList;

import units.Unit;
import environment.Environment;
import map.Tile;

/**
 * Attribute that contains functionality for movement, must pass in maximum
 * number of moves. It has functionality to keep track of moves left.
 * 
 * @author Matthew
 * 
 */
public class AttributeMove extends Attribute<Integer,Integer> {
    
    public int maxMoves;
    public int movesLeft;
    
    public AttributeMove(Unit unit,int moves){
        super(unit);
        super.setData(moves);
        maxMoves = moves;
        refresh();
    }

    public void setMaxMoves(int moves) {
        maxMoves = moves;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public int getMaxMoves() {
        return maxMoves;
    }

    public void decrementMoveCount(int used) {
        movesLeft -= used;
    }

  
    @Override
    public void refresh() {
        movesLeft = maxMoves;

    }

    @Override
    public String name() {
        return "Move";
    }
    @Override
    public void augmentDataTemplate(Integer dataElement) {
        super.setData(super.getData()+dataElement);
    }

}
