package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.*;
import kth.game.othello.rules.Rules;

/**
 * A strategy that always tries to pick the move with the most top-left coordinates
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class TopLeftStrategy implements MoveStrategy {

    @Override
    public String getName() {
        return "TopLeft";
    }

    @Override
    public Node move(String playerId, Rules rules, Board board) {
        List<Node> validMoves = rules.getValidMoves(playerId);

        Node highestNode = null;

        for (Node n : validMoves) {
            if (highestNode == null ||
                    highestNode.getYCoordinate() > n.getYCoordinate() ||
                    highestNode.getYCoordinate() == n.getYCoordinate() && highestNode.getXCoordinate() > n.getXCoordinate()) {
                highestNode = n;
            }
        }

        return highestNode;
    }
}
