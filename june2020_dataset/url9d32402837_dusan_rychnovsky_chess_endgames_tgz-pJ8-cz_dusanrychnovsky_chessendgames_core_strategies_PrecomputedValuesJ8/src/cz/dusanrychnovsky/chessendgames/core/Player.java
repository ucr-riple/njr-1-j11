package cz.dusanrychnovsky.chessendgames.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class Player implements Serializable
{
	private static final long serialVersionUID = 1L;

	public enum Color { BLACK, WHITE }
	
	private static final Player whitePlayer = new Player(Color.WHITE);
	private static final Player blackPlayer = new Player(Color.BLACK);
	
	static {
		whitePlayer.setOpponent(blackPlayer);
		blackPlayer.setOpponent(whitePlayer);
	}
	
	private final Color color;
	
	private final List<Piece> pieces = new ArrayList<Piece>();
	private King king = null;
	
	private Player opponent = null;
	
	/**
	 * 
	 * @param color
	 * @return
	 */
	public static Player get(Color color)
	{
		if (color.equals(Color.WHITE)) {
			return whitePlayer;
		}
		
		if (color.equals(Color.BLACK)) {
			return blackPlayer;
		}
		
		throw new IllegalArgumentException(
			"Unexpected player color [" + color + "]."
		);
	}
	
	/**
	 * 
	 * @param color
	 */
	private Player(Color color) {
		this.color = color;
	}

	/**
	 * 
	 * @param opponent
	 */
	private void setOpponent(Player opponent)
	{
		if (this.opponent != null) {
			throw new IllegalArgumentException("Opponent has already been set.");
		}
		
		this.opponent = opponent;
	}
	
	/**
	 * 
	 * @param piece
	 */
	public void addPiece(Piece piece)
	{
		if (this != piece.getPlayer()) {
			throw new IllegalArgumentException("Cannot add another player's piece.");
		}
		
		if (piece instanceof King)
		{
			if (king != null) {
				throw new IllegalArgumentException("Cannot add more than one king.");
			}
			
			king = (King) piece;
		}
		
		pieces.add(piece);
	}
	
	/**
	 * 
	 */
	public void removeAllPieces()
	{
		pieces.clear();
		king = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Piece> getPieces() {
		return Collections.unmodifiableList(pieces);
	}
	
	/**
	 * 
	 * @return
	 */
	public King getKing() {
		return king;
	}
	
	/**
	 * 
	 * @return
	 */
	public Player getOpponent() {
		return opponent;
	}
	
	@Override
	public int hashCode() {
		return color.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Player)) {
			return false;
		}
		
		Player other = (Player) obj;
		return color.equals(other.color);
	}
	
	@Override
	public String toString() {
		return color.toString();
	}
}
