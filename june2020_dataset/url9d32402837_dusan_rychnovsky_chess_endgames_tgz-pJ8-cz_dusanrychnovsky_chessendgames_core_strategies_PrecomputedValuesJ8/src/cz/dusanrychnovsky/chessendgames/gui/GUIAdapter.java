package cz.dusanrychnovsky.chessendgames.gui;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import cz.dusanrychnovsky.chessendgames.core.Game;
import cz.dusanrychnovsky.chessendgames.core.King;
import cz.dusanrychnovsky.chessendgames.core.Move;
import cz.dusanrychnovsky.chessendgames.core.Player;
import cz.dusanrychnovsky.chessendgames.core.Position;
import cz.dusanrychnovsky.chessendgames.core.Rook;
import cz.dusanrychnovsky.chessendgames.core.Situation;
import cz.dusanrychnovsky.chessendgames.core.Player.Color;
import cz.dusanrychnovsky.chessendgames.core.strategies.PrecomputedValues;
import cz.dusanrychnovsky.chessendgames.core.strategies.Strategy;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class GUIAdapter implements MouseEventListener
{
	private final Window window;
	
	private final Player whitePlayer = Player.get(Color.WHITE);
	private final King whiteKing = new King(whitePlayer);
	
	private final Player blackPlayer = Player.get(Color.BLACK);
	private final King blackKing = new King(blackPlayer);
	private final Rook blackRook = new Rook(blackPlayer);
	
	private final Strategy strategy;
	private Game game;
	
	private GameState currentState;
	
	/**
	 * 
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException
	{
		new GUIAdapter();
	}
	
	/**
	 * 
	 * @param strategy
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public GUIAdapter() throws ClassNotFoundException, IOException 
	{
		SplashScreen splashScreen = new SplashScreen();
		
		InputStream is = PrecomputedValues.class.getResourceAsStream("strategy.dat");
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(is));
		
		strategy = PrecomputedValues.load(in);
		in.close();
		
		splashScreen.close();
		
		window = new Window(this);
		initGame();
	}
	
	/**
	 * 
	 */
	private void initGame()
	{		
		currentState = new PlaceBlackKing();
		
		window.setSituation(currentState.getSituation());
		window.setStatus(currentState.getStatusMessage());
	}
	
	@Override
	public void onMouseClicked(Position position)
	{
		currentState = currentState.handle(position);
		
		Situation currentSituation = currentState.getSituation();
		window.setSituation(currentSituation);
		
		String statusMessage = currentState.getStatusMessage();
		window.setStatus(statusMessage);
		
		window.repaint();
	}
	
	/**
	 * 
	 * @author Dušan Rychnovský
	 *
	 */
	private abstract class GameState
	{
		/**
		 * 
		 * @param position
		 * @return
		 */
		public abstract GameState handle(Position position);
		
		/**
		 * 
		 * @return
		 */
		public abstract Situation getSituation();
		
		/**
		 * 
		 * @return
		 */
		public abstract String getStatusMessage();
	}
	
	/**
	 * 
	 * @author Dušan Rychnovský
	 *
	 */
	private class PlaceBlackKing extends GameState
	{
		private final Situation currSituation = new Situation();
		
		@Override
		public GameState handle(Position position) 
		{
			Situation newSituation = new Situation(currSituation);
			newSituation.addPiece(blackKing, position);
			
			return new PlaceBlackRook(newSituation);
		}

		@Override
		public Situation getSituation() {
			return currSituation;
		}

		@Override
		public String getStatusMessage() {
			return "Click on a square to place the opponent's king.";
		}
	}
	
	/**
	 * 
	 * @author Dušan Rychnovský
	 *
	 */
	private class PlaceBlackRook extends GameState
	{
		private final Situation currSituation;
		private boolean inError = false;
		
		/**
		 * 
		 * @param currSituation
		 */
		public PlaceBlackRook(Situation currSituation) {
			this.currSituation = currSituation;
		}

		@Override
		public GameState handle(Position position)
		{
			if (currSituation.isOccupied(position))
			{
				inError = true;
				return this;
			}
			
			inError = false;
			
			Situation newSituation = new Situation(currSituation);
			newSituation.addPiece(blackRook, position);
			
			return new PlaceWhiteKing(newSituation);
		}

		@Override
		public Situation getSituation() {
			return currSituation;
		}

		@Override
		public String getStatusMessage()
		{
			String message = "Click on a square to place the opponent's rook.";
			if (inError) {
				message = "Invalid position. " + message;
			}
			
			return message;
		}
	}
	
	/**
	 * 
	 * @author Dušan Rychnovský
	 *
	 */
	private class PlaceWhiteKing extends GameState
	{
		private final Situation currSituation;
		private boolean inError = false;
		
		/**
		 * 
		 * @param currSituation
		 */
		public PlaceWhiteKing(Situation currSituation) {
			this.currSituation = currSituation;
		}

		@Override
		public GameState handle(Position position)
		{
			if (!isValid(position))
			{
				inError = true;
				return this;
			}
			
			inError = false;
			
			Situation newSituation = new Situation(currSituation);
			newSituation.addPiece(whiteKing, position);
			
			return new MakeMove(newSituation);
		}
		
		/**
		 * 
		 * @param position
		 * @return
		 */
		private boolean isValid(Position position)
		{
			if (currSituation.isOccupied(position)) {
				return false;
			}
			
			if (position.isNeighbour(currSituation.getPosition(blackKing))) {
				return false;
			}
			
			// TODO: don't allow to place the king into a check
			// (situation.isValid() ?)
			
			return true;
		}

		@Override
		public Situation getSituation() {
			return currSituation;
		}

		@Override
		public String getStatusMessage()
		{
			String message = "Click on a square to place your own king.";
			if (inError) {
				message = "Invalid position. " + message;
			}
			
			return message;
		}
	}
	
	/**
	 * 
	 * @author Dušan Rychnovský
	 *
	 */
	private class MakeMove extends GameState
	{
		private boolean inError = false;
		
		/**
		 * 
		 * @param situation
		 */
		public MakeMove(Situation situation) {
			game = new Game(strategy, situation, blackPlayer);
		}
		
		@Override
		public GameState handle(Position position)
		{
			Situation currSituation = game.getCurrentSituation();
			
			Position from = currSituation.getPosition(whiteKing);
			Move move;
			
			try {
				move = new Move(whiteKing, from, position);
				
				if (!move.isValid(currSituation)) {
					throw new IllegalArgumentException();
				}
			}
			catch (IllegalArgumentException ex)
			{
				inError = true;
				return this;
			}
			
			inError = false;
			
			Situation nextSituation = game.doMove(move);
			if (nextSituation.isFinal()) {
				return new GameFinished();
			}
			
			nextSituation = game.playMove();
			if (nextSituation.isFinal()) {
				return new GameFinished();
			}
			
			return this;
		}

		@Override
		public Situation getSituation() {
			return game.getCurrentSituation();
		}

		@Override
		public String getStatusMessage()
		{
			String message = "Click on a square to make your move.";
			if (inError) {
				message = "Invalid move. " + message;
			}
			
			return message;
		}
	}
	
	/**
	 * 
	 * @author Dušan Rychnovský
	 *
	 */
	private class GameFinished extends GameState
	{
		@Override
		public GameState handle(Position position) {
			return this;
		}

		@Override
		public Situation getSituation() {
			return game.getCurrentSituation();
		}

		@Override
		public String getStatusMessage() {
			return "Game finished - " + game.getCurrentSituation().getResult();
		}		
	}
}
