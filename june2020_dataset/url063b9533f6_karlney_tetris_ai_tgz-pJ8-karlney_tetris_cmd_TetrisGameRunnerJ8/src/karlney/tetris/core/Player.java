package karlney.tetris.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

public class Player implements Runnable{

    private static Logger log = LoggerFactory.getLogger(Player.class);

    private static final int FALLING_DELAY = 3;

    private final Board board;
    private final PieceGenerator generator;
    private final Thread t = new Thread(this);

    protected Piece currentPiece;
    protected Piece nextPiece;

    private long score = 0;
    private int lines = 0;
    private int freeFallIterations = 0;
    private Map<PieceType,Integer> pieceStatistics;

    private int delay;
    private int level;
    private boolean running;

    public Player(Board board, PieceGenerator generator, int level) {
        this.board = board;
        this.generator = generator;
        this.level = level;
        score = 0;
        lines = 0;
        freeFallIterations = 0;
        currentPiece = generator.getNextPiece(board);
        nextPiece = generator.getNextPiece(board);
        running = false;
        pieceStatistics = new TreeMap<PieceType, Integer>();
        for (PieceType t: PieceType.values()){
            pieceStatistics.put(t,0);
        }
        updateDistribution(currentPiece.getSquare(0, 0).getType());
    }

    public void start(int delay){
        if (!running){
            running = true;
            this.delay = delay;
            t.start();
            log.info("Player thread "+t.getName()+" started.");
        } else {
            throw new IllegalStateException("Thread already running, can't start twice");
        }
    }

    public void stop(){
        running=false;
        this.delay=0;
        t.interrupt();
        log.info("Player thread stopped with: Lines cleared "+lines+". Pieces played "+getNumberOfPieces());
    }

    public synchronized void moveDownOrLand(){
        boolean pieceIsLanded = currentPiece.moveDown(); //TODO there can be things that happens in between here..
        if (pieceIsLanded){
            try {
                log.debug("Placing piece on board "+currentPiece);
                commitCurrentPieceToBoard();                  //.. and here that makes the pieceIsLanded state to have changed
            } catch (UnableToPlacePieceException e) {
                log.error(e.getMessage());
                stop();
            }
        }
    }

    public synchronized void setDelay(int delay){
        this.delay = delay;
        t.interrupt();
    }

    public synchronized void setLevel(int level) {
        this.level = level;
    }


    public synchronized void updatePlaceScore(int removedRows){
        score+= ((21+(3*level))-freeFallIterations+1);

        if	(removedRows==1)
            score=score+(level)*50;
        if	(removedRows==2)
            score=score+(level)*150;
        if	(removedRows==3)
            score=score+(level)*300;
        if	(removedRows==4)
            score=score+(level)*1000;

        lines +=removedRows;

        if (lines>0 && removedRows>0 && (lines)%5000==0){
            log.info("Lines cleared "+lines+". Pieces played "+getNumberOfPieces()+" Pieces/Lines "+(double)getNumberOfPieces()/(lines));
        }
    }

    protected synchronized void commitCurrentPieceToBoard() throws UnableToPlacePieceException {
        board.placePieceOnBoard(currentPiece);
        int rows= board.removeFullRows();
        updatePlaceScore(rows);
        currentPiece = nextPiece;
        nextPiece    = generator.getNextPiece(board);
        freeFallIterations = 0;
        updateDistribution(currentPiece.getSquare(0, 0).getType());
        //Make sure we have not reached end of game
        if (!board.allowedPlacement(currentPiece)){
            throw new UnableToPlacePieceException("The next piece "+currentPiece+" could not be placed on the board. This means GAME OVER.",null);
        }
    }

    private synchronized void updateDistribution(PieceType type) {
        if (pieceStatistics.containsKey(type)){
            pieceStatistics.put(type,pieceStatistics.get(type)+1);
        }
    }

    public void processInput(PlayerInput input){
        switch (input){
            case ROTATE: currentPiece.rotateIfPossible(); break;
            case DOWN: t.interrupt(); break;
            case LEFT: currentPiece.moveSideWays(input); break;
            case RIGHT: currentPiece.moveSideWays(input); break;
            case DROP: currentPiece.drop(); t.interrupt(); break;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void run(){
        while (running){
            try {
                if (currentPiece.isDropped()){
                    Thread.sleep(FALLING_DELAY);
                }else{
                    freeFallIterations++;
                    Thread.sleep(delay);
                }
            }catch(InterruptedException e) {}
            moveDownOrLand();
        }
    }


    public Piece getNextPiece() {
        return nextPiece;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public Board getBoard() {
        return board;
    }

    public long getScore() {
        return score;
    }

    public int getLines() {
        return lines;
    }

    public int getNumberOfPieces() {
        int out = 0;
        for (Integer i: pieceStatistics.values()){
            out+=i;
        }
        return out;
    }

}