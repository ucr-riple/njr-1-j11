package karlney.tetris.swing;

import karlney.tetris.ai.*;
import karlney.tetris.core.*;
import karlney.tetris.test.BoardBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * This is the main Game class
 * It uses Java swing for drawing the game frame, menues etc.
 */
public class TetrisGameSwing extends JPanel implements Runnable,KeyListener,ActionListener{

    //Size in pixels of graphical elements
    private final static int
            XSIZE=200+180,
            YSIZE=400+90,
            LOCATION_X = 200,
            LOCATION_Y = 100;

    private static final int DEFAULT_START_LEVEL = 1;
    private static final int SQUARE_SIZE = 20;
    private static final int ROWS = 20;
    private static final int COLS = 10;

    //Swing Graphics
    private JPanel obs=new	JPanel();
    private JFrame jf= new	JFrame("Tetris");
    private JMenuItem
            exitCmd,
            newGameCmd,
            pauseCmd,
            changeLevelCmd,
            showHighscoreCmd,
            nextLvlCmd,
            prevLvlCmd;


    public TetrisGame game;
    public PieceGenerator generator = new RandomPieceGenerator();


    /**
     * Tis method starts a new game
     */
    private void newGame() {
        int level = DEFAULT_START_LEVEL;
        if (game!=null){
            game.stop();
            level = game.getLevel();
        }

        Board board = BoardBuilder.createBoard(10, 20, new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0},
                {1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0},
                {1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });

        //Player player = new Player(new Board(),generator,level);
        Player player = AIPlayerBuilder.getInstantMoveOnePieceAIPlayer(board, generator, new ElAshiTetrisBoardEvaluator(), level, 0);

        game = new TetrisGame(level,Arrays.asList(player));
        game.start();
    }

    public TetrisGameSwing(){
        initGraphics();
        new Thread(this).start();
        newGame();
    }

    private void initGraphics() {
        jf.setResizable(false);
        jf.setSize(new Dimension(XSIZE,YSIZE));
        jf.setLocation(LOCATION_X,LOCATION_Y);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.addKeyListener(this);
        Container c=jf.getContentPane();
        setMinimumSize(new Dimension(XSIZE,YSIZE));
        c.add(this);
        buildMenu(jf);
        jf.setVisible(true);
    }


    public void buildMenu(JFrame jf){
        jf.setJMenuBar(new JMenuBar());
        jf.getJMenuBar().setBorderPainted(false);

        JMenu	m1= new JMenu("Game");
        m1.setBackground(new Color(150,150,250));

        newGameCmd=m1.add(new JMenuItem("New Game		(Ctrl+N)"));
        newGameCmd.addActionListener(this);

        pauseCmd=m1.add(new JMenuItem("Pause/Unpause	(P)"));
        pauseCmd.addActionListener(this);

        prevLvlCmd=m1.add(new JMenuItem("Decrease	 Level	  (1)"));
        prevLvlCmd.addActionListener(this);

        nextLvlCmd=m1.add(new JMenuItem("Increase	 Level	  (2)"));
        nextLvlCmd.addActionListener(this);

        exitCmd=m1.add(new JMenuItem("Exit	  (Ctrl+Q)"));
        exitCmd.addActionListener(this);

        jf.getJMenuBar().add(m1);
        jf.getJMenuBar().setBackground(new Color(0,0,0));

        newGame();
    }


    /**
     * This methods shuts down the JVM
     */
    public void	quitGame(){
        System.exit(0);
    }

    // User	Input
    public void	keyTyped(KeyEvent	e){}
    public void	keyReleased(KeyEvent	e){}

    /**
     * This method is called when a key is pressed
     *
     * @param e2 key pressed event
     */
    public void	keyPressed(KeyEvent e2){
        if (!game.isPaused())  processKeyInput(e2);

        if(e2.getKeyCode() == KeyEvent.VK_1){
            game.decreaseLevel();
        }
        if(e2.getKeyCode() == KeyEvent.VK_2){
            game.increaseLevel();
        }

        if	(e2.getKeyCode() == KeyEvent.VK_P){
            game.togglePause();
        }
        if	((e2.getKeyCode()	==	KeyEvent.VK_Q)	&&	(e2.isControlDown() || e2.isMetaDown()) ){
            quitGame();
        }
        if	((e2.getKeyCode()	==	KeyEvent.VK_N)	&&	(e2.isControlDown() || e2.isMetaDown()) ){
            newGame();
        }
    }

    public void processKeyInput(KeyEvent e2){
        if((e2.getKeyCode() == KeyEvent.VK_UP || e2.getKeyCode()	==	KeyEvent.VK_W))
            game.getPlayers().get(0).processInput(PlayerInput.ROTATE);

        if((e2.getKeyCode() == KeyEvent.VK_DOWN || e2.getKeyCode() == KeyEvent.VK_S))
            game.getPlayers().get(0).processInput(PlayerInput.DOWN);


        if((e2.getKeyCode() == KeyEvent.VK_LEFT || e2.getKeyCode() == KeyEvent.VK_A))
            game.getPlayers().get(0).processInput(PlayerInput.LEFT);

        if((e2.getKeyCode() == KeyEvent.VK_RIGHT || e2.getKeyCode()	==	KeyEvent.VK_D))
            game.getPlayers().get(0).processInput(PlayerInput.RIGHT);

        if(e2.getKeyCode() == KeyEvent.VK_SPACE ){
            game.getPlayers().get(0).processInput(PlayerInput.DROP);
        }
    }


    /**
     * This method is called when the menu is used (or hot keys)
     *
     * @param e action event
     */
    public void	actionPerformed(ActionEvent e){
        if	(e.getSource()==exitCmd){
            quitGame();
        }

        if	(e.getSource()==pauseCmd){
            game.togglePause();
        }
        if(e.getSource()==newGameCmd){
            newGame();
        }

        if(e.getSource()==nextLvlCmd){
            game.increaseLevel();
        }

        if(e.getSource()==prevLvlCmd){
            game.decreaseLevel();
        }

        if(e.getSource()==showHighscoreCmd){
            Highscore.showHighscore();
        }
    }


    /**
     * This method is called when the game window needs to be re-painted
     *
     * @param g the graphics handle
     */
    public void	paintComponent(Graphics	g){
        try{
            g.setColor(new	Color(0,0,0));
            g.fillRect(0,0,XSIZE,YSIZE);

            for (Player player: game.getPlayers()) {
                drawBoard(g, player.getBoard());
                drawPiece(player.getCurrentPiece(), g, 0, 0);

                g.setColor(Color.RED);
                g.drawString("Level: "+game.getLevel(), XSIZE-100,20);
                g.drawString("Score: "+player.getScore(),XSIZE-100,50);
                g.drawString("Lines: "+player.getLines(),	XSIZE-100,110);
                g.drawString("Pieces: "+player.getNumberOfPieces(), XSIZE-100,170);

                drawNextPiece(g, player.getNextPiece());

                if (player instanceof AIPlayer){
                    drawDestination(g, ((AIPlayer) player).getDestination());
                }


                if(game.hasEnded()){	//If games is quit then display GAME OVER
                    Font f=g.getFont();
                    g.setFont(new Font("Georgia",	Font.BOLD, 50));
                    g.setColor(Color.WHITE);
                    g.drawString("GAME OVER",20,150);

                    g.setFont(new Font("Georgia",	Font.BOLD, 30));
                    //g.setColor(Color.YELLOW);
                    g.drawString("Final score",100,200);
                    g.drawString(""+player.getScore(),130,250);

                    g.setFont(f);
                }

                if(game.isPaused()){	//	//If games is paused then display Paused
                    Font f=g.getFont();
                    g.setFont(new Font("Arial", Font.BOLD,	30));
                    g.setColor(Color.WHITE);
                    g.drawString("Paused",60,150);
                    g.setFont(f);
                }
            }
        }
        catch(Exception e){}

    }

    private void drawDestination(Graphics g, Piece piece) {
        for(int j=0; j<piece.getSize(); j++){
            for(int i=0; i<piece.getSize(); i++){
                drawColoredSquare(piece.getSquare(i,j),g,(piece.getX()+i)*SQUARE_SIZE,(piece.getY()+j)*SQUARE_SIZE,Color.GRAY);
            }
        }
    }

    private void drawPiece(Piece piece, Graphics g, int Xoff, int Yoff) {
        for(int j=0; j<piece.getSize(); j++){
            for(int i=0; i<piece.getSize(); i++){
                drawSquare(piece.getSquare(i,j),g,Xoff+(piece.getX()+i)*SQUARE_SIZE,Yoff+(piece.getY()+j)*SQUARE_SIZE);
            }
        }
    }

    private void drawNextPiece(Piece piece, Graphics g, int Xoff, int Yoff) {
        for(int j=0; j<piece.getSize(); j++){
            for(int i=0; i<piece.getSize(); i++){
                drawSquare(piece.getSquare(i,j),g,Xoff+i*SQUARE_SIZE,Yoff+j*SQUARE_SIZE);
            }
        }
    }

    private void drawBoard(Graphics g, Board board) {
        for(int j=0; j<22; j++){
            for(int i=0; i<12; i++){
                drawSquare(board.getSquare(i, j),g,SQUARE_SIZE*i,SQUARE_SIZE*j);
            }
        }
    }

    private void drawNextPiece(Graphics g, Piece nextPiece) {
        if(nextPiece instanceof PieceO){
            drawNextPiece(nextPiece, g, XSIZE - 80, 250);
        } else {
            drawNextPiece(nextPiece, g, XSIZE - 100, 250);
        }
    }

    public static final Color
            c1=new Color(250,250,0),//Yellow
            c2=new Color(0,200,0), //Green
            c3=new Color(250,150,150), //Teal
            c4=new Color(0,150,220), // Pink
            c6=new Color(250,0,0), //Red
            c5=new Color(250,250,250), //White
            c7=new Color(0,120,90), //Dark green
            c8=new Color(100,0,100), // Purple
            c9=new Color(0,250,250); //Blue

    public void drawColoredSquare(Square square, Graphics g,int x, int y, Color c){
        if(square.isFilled()){
            g.setColor(getColors(square.getType().getValue())[0]);
            g.fillRect(x,y,SQUARE_SIZE,SQUARE_SIZE);
            g.setColor(c);
            g.fillRect(x+2,y+2,SQUARE_SIZE-4,SQUARE_SIZE-4);

        }
    }

    public void drawSquare(Square square, Graphics g,int x, int y){
        if(square.isFilled()){
            g.setColor(getColors(square.getType().getValue())[0]);
            g.fillRect(x,y,SQUARE_SIZE,SQUARE_SIZE);
            g.setColor(getColors(square.getType().getValue())[1]);
            g.fillRect(x+2,y+2,SQUARE_SIZE-4,SQUARE_SIZE-4);

        } else if (square.getType() == PieceType.BOARD){
            g.setColor(Color.BLACK);
            g.fillRect(x,y,SQUARE_SIZE,SQUARE_SIZE);
        }
        /*else if (square.getType() == PieceType.BOARD){
            g.setColor(Color.WHITE);
            g.fillRect(x,y,SQUARE_SIZE,SQUARE_SIZE);
            g.setColor(Color.BLACK);
            g.fillRect(x+1,y+1,SQUARE_SIZE-2,SQUARE_SIZE-2);
        } */
    }

    public Color[] getColors(int nr){
        Color[] cOut= new Color[2];
        if(nr==1) {
            cOut[0]=c7;
            cOut[1]=c3;
        }
        if(nr==2) {
            cOut[0]=c7;
            cOut[1]=c6;
        }
        if(nr==3) {
            cOut[0]=c7;
            cOut[1]=c1;
        }
        if(nr==4) {
            cOut[0]=c7;
            cOut[1]=c9;
        }
        if(nr==5) {
            cOut[0]=c7;
            cOut[1]=c5;
        }
        if(nr==6){
            cOut[0]=c7;
            cOut[1]=c8;
        }
        if(nr==7) {
            cOut[0]=c7; //Frame
            cOut[1]=c2; //Internal
        }
        if(nr==10) { //Game frame
            cOut[0]=c4;
            cOut[1]=c4;
        }
        return cOut;
    }



    //Infinite rendering loop, can only be stopped by system exit
    public void	run(){
        try {
            //noinspection InfiniteLoopStatement
            while(true){
                if (jf.isShowing())
                    repaint();
                Thread.sleep(11);
            }

        }
        catch(InterruptedException	e)	{}
    }



    public static void main(String[] argv){
        new TetrisGameSwing();
    }
}
