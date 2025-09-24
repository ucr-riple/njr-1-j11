package karlney.tetris.ai;

import karlney.tetris.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AIPlayer extends Player {

    private static Logger log = LoggerFactory.getLogger(Player.class);

    private AIThread aiThread = new AIThread();
    private PiecePlacer placer;
    private int moveDelay;
    private BoardEvaluator evaluator;
    private PathFinder pathFinder;

    private Piece destinationPiece = null;

    public AIPlayer(Board board, PieceGenerator generator, PiecePlacer piecePlacer, BoardEvaluator boardEvaluator, PathFinder pathFinder, int level, int moveDelay) {
        super(board, generator, level);
        this.evaluator = boardEvaluator;
        this.pathFinder = pathFinder;
        this.placer = piecePlacer;
        this.moveDelay = moveDelay;
    }

    public void start(int delay){
        super.start(delay);
        aiThread.start();
    }

    public void stop(){
        super.stop();
        aiThread.stop();
    }

    public PiecePlacer getPlacer() {
        return placer;
    }

    public void setPlacer(PiecePlacer placer) {
        this.placer = placer;
    }

    public BoardEvaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(BoardEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public void setPathFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }



    public synchronized void processInput(PlayerInput input){
        super.processInput(input);
        if (input == PlayerInput.INSTANT_MOVE){
            currentPiece = destinationPiece;
            clearDestination();
            try {
                commitCurrentPieceToBoard();
            } catch (UnableToPlacePieceException e) {
                log.info(e.getMessage());
                stop();
            }
        }
    }


    public void clearDestination(){
        destinationPiece = null;
    }

    public void setDestination(Piece destinationPiece) {
        this.destinationPiece = destinationPiece;
    }

    public Piece getDestination() {
        return destinationPiece;
    }

    public boolean hasDestination() {
        return destinationPiece!=null;
    }


    /**
     * This class executes the AI action loop
     *
     */
    public class AIThread implements Runnable {

        private Thread t = new Thread(this);

        public void run(){

            try {
                while (isRunning()){

                    //If destination is unknown then find it
                    if (!hasDestination()){
                        //Find the best placement of the piece according the placer - given the board, the current and the next piece
                        Piece p = placer.bestPlacement(getEvaluator(), getBoard(), getCurrentPiece(), getNextPiece());
                        //Set the current destination for the player
                        setDestination(p);

                        if (getCurrentPiece().getType()== PieceType.I){
                            getCurrentPiece().getType();
                        }
                    }

                    //Calculate the path (do this in every iteration to account for 'slow AI reaction') - the piece may have moved down since last time
                    List<PlayerInput> path = pathFinder.findPath(getBoard(),getCurrentPiece(),getDestination());

                    //Sleep to avoid AI 'cheating' of doing things too quickly
                    if (moveDelay>0){
                        Thread.sleep(moveDelay);
                    }

                    //Execute the first action in the path
                    if (path.size()>0){
                        processInput(path.get(0));
                    }
                }
            }catch (InterruptedException e){
                //Do nothing..
            }catch(Exception	e)	{
                log.error("AI thread crashed.", e);
            }
        }

        public void start() {
            t.start();
            log.info("AI Thread started.");
        }

        public void stop(){
            t.interrupt();
            log.info("AI Thread stopped.");
        }

    }


}