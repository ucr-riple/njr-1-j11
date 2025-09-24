package karlney.tetris.core;

import java.util.Random;

/**
 * This is the PieceGenerator class. It is responsible of generating new blocks
 */
public abstract class AbstractPieceGenerator implements PieceGenerator {

    private static boolean[][][] PIECE_SHAPES ={
            {{true,true},{true,true}},                                     // O = 1
            {{false,true,false,false},{false,true,false,false},{false,true,false,false},{false,true,false,false}}, // I = 2
            {{false,false,true},  {false,true,true}, {false,true,false}},  // S = 3
            {{false,true,false},  {false,true,true}, {false,false,true}},  // Z = 4
            {{false,true,true},  {false,true,false}, {false,true,false}},  // L = 5
            {{false,true,false},  {false,true,false}, {false,true,true}},  // J = 6
            {{false,true,false},  {false,true,true}, {false,true,false}}}; // T = 7

    protected final static int NUMBER_OF_BLOCKS = 7;

    private Square[][] buildShape(PieceType type) {
        boolean[][] bools = PIECE_SHAPES[type.getValue()-1];
        Square[][] out = new Square[bools.length][bools.length];
        for (int i=0;i<bools.length;i++){
            for (int j=0;j<bools[i].length;j++){
                out[i][j] = new Square(type,bools[i][j]);
            }
        }
        return out;
    }

    protected Piece getPiece(PieceType type, Board board){
        switch (type){
            case O  : return new PieceO(getStartX(board, type),getStartY(type),board,buildShape(type));
            case I  : return new PieceI(getStartX(board, type),getStartY(type),board,buildShape(type));
            case S  : return new PieceS(getStartX(board, type),getStartY(type),board,buildShape(type));
            case Z  : return new PieceZ(getStartX(board, type),getStartY(type),board,buildShape(type));
            default : return new PieceJLT(getStartX(board, type),getStartY(type),board,buildShape(type));
        }
    }

    private int getStartY(PieceType type) {
        switch (type){
            case O  : return 1;
            default : return 0;
        }
    }

    private int getStartX(Board board, PieceType type) {
        int middle = board.getCols()/2;
        switch (type){
            case I  : return middle-1;
            default : return middle;
        }
    }

}
