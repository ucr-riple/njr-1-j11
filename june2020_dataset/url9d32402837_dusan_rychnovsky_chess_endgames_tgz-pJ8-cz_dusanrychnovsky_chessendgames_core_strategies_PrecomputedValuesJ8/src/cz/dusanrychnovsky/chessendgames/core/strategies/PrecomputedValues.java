package cz.dusanrychnovsky.chessendgames.core.strategies;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import cz.dusanrychnovsky.chessendgames.core.King;
import cz.dusanrychnovsky.chessendgames.core.Move;
import cz.dusanrychnovsky.chessendgames.core.Player;
import cz.dusanrychnovsky.chessendgames.core.Player.Color;
import cz.dusanrychnovsky.chessendgames.core.Position;
import cz.dusanrychnovsky.chessendgames.core.Rook;
import cz.dusanrychnovsky.chessendgames.core.Situation;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class PrecomputedValues extends Strategy implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final Map<Pair<Situation, Player>, Integer> cache = new HashMap<Pair<Situation, Player>, Integer>();
	private final Map<Pair<Situation, Player>, Move> moves = new HashMap<Pair<Situation, Player>, Move>();
	
	private final Player whitePlayer;
	private final King whiteKing;
	
	private final Player blackPlayer;
	private final King blackKing;
	private final Rook blackRook;
	
	public static void main(String[] args) throws IOException
	{
        Player whitePlayer = Player.get(Color.WHITE);
        King whiteKing = new King(whitePlayer);
        
        Player blackPlayer = Player.get(Color.BLACK);
        King blackKing = new King(blackPlayer);
        Rook blackRook = new Rook(blackPlayer);
        
        PrecomputedValues strategy = PrecomputedValues.get(whiteKing, blackKing, blackRook);
        strategy.save(new File("D:/strategy.dat"));
        
        System.out.println("DONE");
	}
	
	/**
	 * 
	 * @param whiteKing
	 * @param blackKing
	 * @param blackRook
	 * @return
	 */
	public static PrecomputedValues get(King whiteKing, King blackKing, Rook blackRook)
	{
		PrecomputedValues result = new PrecomputedValues(whiteKing, blackKing, blackRook);
		
		int i = 0, count;
		do 
		{
			System.out.println("Walkthrough no. " + (i + 1));
			i++;
			
			long startTime = System.currentTimeMillis();
			count = result.walkthrough();
			long finishTime = System.currentTimeMillis();
			
			long duration = finishTime - startTime; 
			System.out.println(duration + " ms");
			
			System.out.println(count + " new records");
		}
		while (count != 0);
		
		// the cache won't be needed anymore - allow to free the occupied memory
		result.cache.clear();
		
		return result;
	}
	
	/**
	 * 
	 * @param whiteKing
	 * @param blackKing
	 * @param blackRook
	 */
	private PrecomputedValues(King whiteKing, King blackKing, Rook blackRook)
	{
		this.whitePlayer = whiteKing.getPlayer();
		this.whiteKing = whiteKing;
		
		this.blackPlayer = blackKing.getPlayer();
		this.blackKing = blackKing;
		this.blackRook = blackRook;
	}
	
	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void save(File file) throws IOException
	{
		ObjectOutputStream out = null;
		
		try {
			
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			save(out);
		}		
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 
	 * @param out
	 * @throws IOException
	 */
	public void save(ObjectOutput out) throws IOException {
		out.writeObject(this);
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static PrecomputedValues load(File file) throws IOException, ClassNotFoundException
	{
		ObjectInputStream in = null;
		
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			return load(in);
		}
		finally {
			if (in != null) {
				in.close();
			}
		}
	}
	
	/**
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static PrecomputedValues load(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		PrecomputedValues result = (PrecomputedValues) in.readObject();	
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	private int walkthrough()
	{
		int result = 0;
		
		for (Position whiteKingPos : Position.getAllPositions())
		{
			for (Position blackKingPos : Position.getAllPositions())
			{
				if (whiteKingPos == blackKingPos) {
					continue;
				}
				
				for (Position blackRookPos : Position.getAllPositions())
				{
					if (blackRookPos == blackKingPos || blackRookPos == whiteKingPos) {
						continue;
					}
					
					Situation situation = new Situation();
					situation.addPiece(whiteKing, whiteKingPos);
					situation.addPiece(blackKing, blackKingPos);
					situation.addPiece(blackRook, blackRookPos);
					
					if (evaluate(situation, blackPlayer)) {
						result++;
					}
					
					if (evaluate(situation, whitePlayer)) {
						result++;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param situation
	 * @param player
	 * @return
	 */
	public boolean evaluate(Situation situation, Player player)
	{
		Pair<Situation, Player> key = Pair.of(situation, player);
		if (cache.containsKey(key)) {
			return false;
		}
		
		if (situation.isCheckmate(whiteKing)) {
			cache.put(key, 0);
			return true;
		}
		
		if (situation.isStalemate(whiteKing)) {
			cache.put(key, Integer.MAX_VALUE);
			return true;
		}
		
		Player opponent = player.getOpponent();
		
		if (player.equals(whitePlayer))
		{
			int max = Integer.MIN_VALUE;
			Move result = null;
			
			for (Move move : situation.generateValidMoves(player))
			{
				Situation successor = Situation.get(situation, move);
				Pair<Situation, Player> tmpKey = Pair.of(successor, opponent);
				if (!cache.containsKey(tmpKey)) {
					return false;
				}
				
				int tmpValue = cache.get(tmpKey);
				if (tmpValue > max) 
				{
					result = move;
					max = tmpValue;
				}
			}
			
			cache.put(key, max);
			moves.put(key, result);
			
			return true;
		}
		else if (player.equals(blackPlayer))
		{
			int min = Integer.MAX_VALUE;
			Move result = null;
			
			for (Move move : situation.generateValidMoves(player))
			{
				Situation successor = Situation.get(situation, move);
				Pair<Situation, Player> tmpKey = Pair.of(successor, opponent);
				
				if (cache.containsKey(tmpKey))
				{
					int tmpValue = cache.get(tmpKey);
					
					// intentionally <, not <= here - we don't want to take
					// into account moves that lead to draws here
					if (tmpValue < min)
					{
						result = move;
						min = tmpValue;
					}
				}	
			}
			
			if (result != null)
			{
				int value = inc(min);
				
				cache.put(key, value);
				moves.put(key, result);
				
				return true;
			}
			else {
				return false;
			}
		}
		else {
			throw new IllegalArgumentException(
				"Unknown player [" + player + "]."
			);
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	private int inc(int value)
	{
		if (value == Integer.MAX_VALUE) {
			return value;
		}
		else {
			return value + 1;
		}
	}

	@Override
	public Move chooseMove(Situation situation, Player player)
	{
		Pair<Situation, Player> key = Pair.of(situation, player);
		if (!moves.containsKey(key)) {
			throw new IllegalArgumentException(
				"Unknown move for situation [" + situation + "]."
			);
		}
		
		return moves.get(key);
	}
}
