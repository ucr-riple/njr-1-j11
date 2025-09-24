package chess;

import java.util.List;

import chess.impl.DefaultPosition;
import chess.move.Move;

public class Start {

    /**
     * @param args
     */
    public static void main(String[] args) {
	Position pos = new DefaultPosition(true);
	List<Move> moves;
	while (true) {
	    moves = pos.possibleMoves();
	    if (moves.size() == 0) {
		System.out.println("No possible moves left!");
		break;
	    }
	    System.out.println(moves);
	    Move move = moves.get((int) (Math.random() * moves.size()));
	    System.out.println("Picked move: " + move);
	    pos.processMove(move);
	    System.out.println(pos);
	}
    }

}
