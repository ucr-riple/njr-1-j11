package karlney.tetris.cmd;

import karlney.tetris.ai.AIPlayerBuilder;
import karlney.tetris.ai.ElAshiTetrisBoardEvaluator;
import karlney.tetris.core.*;

import java.util.Arrays;

/**
 * TODO javadoc
 * <p/>
 * Date: 2012-11-05
 * Time: 15:06
 *
 * @author karl.neyvaldt
 */
public class TetrisGameRunner {

    /*
    21:06:47.152 [Thread-1] INFO  karlney.tetris.core.Player - The next piece T {x=5 y=0 h=0}  could not be placed on the board. This means GAME OVER.
21:06:47.152 [Thread-1] INFO  karlney.tetris.core.Player - Player thread stopped with: Lines cleared 834047. Pieces played 2085143
21:06:47.152 [Thread-1] INFO  karlney.tetris.core.Player - AI Thread stopped.
     */


    public static void main(String[] args) throws InterruptedException {
        TetrisGame game;
        PieceGenerator generator = new RandomPieceGenerator(1);
        int level = 1;
        Player player = AIPlayerBuilder.getInstantMoveOnePieceAIPlayer(new Board(), generator, new ElAshiTetrisBoardEvaluator(), level, 0);
        game = new TetrisGame(level, Arrays.asList(player));
        game.start();

        while (!game.hasEnded()){
            Thread.sleep(100);
        }
        System.exit(0);
    }
}
