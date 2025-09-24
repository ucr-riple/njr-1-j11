package karlney.tetris.core;

import java.util.Random;

/**
 * This is the PieceGenerator class. It is responsible of generating new blocks
 */
public class RandomPieceGenerator extends AbstractPieceGenerator {

    //Random generator for next blocks
    private Random generator;


    public RandomPieceGenerator() {
        generator = new Random();
    }

    public RandomPieceGenerator(int seed) {
        generator = new Random(seed);
    }

    @Override
    public Piece getNextPiece(Board board){
        PieceType type = PieceType.valueOf(generator.nextInt(NUMBER_OF_BLOCKS));
        return getPiece(type,board);
    }

}
