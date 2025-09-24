package cz.dusanrychnovsky.chessendgames.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import cz.dusanrychnovsky.chessendgames.core.Column;
import cz.dusanrychnovsky.chessendgames.core.King;
import cz.dusanrychnovsky.chessendgames.core.Piece;
import cz.dusanrychnovsky.chessendgames.core.Player;
import cz.dusanrychnovsky.chessendgames.core.Position;
import cz.dusanrychnovsky.chessendgames.core.Rook;
import cz.dusanrychnovsky.chessendgames.core.Row;
import cz.dusanrychnovsky.chessendgames.core.Situation;
import cz.dusanrychnovsky.chessendgames.core.Player.Color;

/**
 * 
 * @author Dušan Rychnovský
 *
 */
public class Board extends JPanel implements MouseListener
{
	private static final long serialVersionUID = 1L;
	
	private static final int SQUARE_WIDTH = 53;
	private static final int SQUARE_HEIGHT = 53;
	
	private final Image board;
	private final Image blackKing;
	private final Image blackRook;
	private final Image whiteKing;
	
	private final MouseEventListener listener;
	
	private Situation currentSituation = null;
	
	/**
	 * 
	 * @param initialSituation
	 * @param listener
	 */
	public Board(MouseEventListener listener) 
	{
		this.listener = listener;
		
		initPanel();
		
		board = loadImage("empty-board.png");
		blackKing = loadImage("black-king.png");
		blackRook = loadImage("black-rook.png");
		whiteKing = loadImage("white-king.png");
    }

	/**
	 * 
	 */
	private void initPanel()
	{
		setPreferredSize(new Dimension(424, 424));
		addMouseListener(this);
	}
	
	/**
	 * 
	 * @param imagePath
	 * @return
	 */
	private Image loadImage(String imagePath) 
	{
        ImageIcon boardIcon = new ImageIcon(this.getClass().getResource(imagePath));
        return boardIcon.getImage();
	}
	
	/**
	 * 
	 * @param situation
	 */
	public void setSituation(Situation situation) {
		this.currentSituation = situation;
	}
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		paintBoard(g2d);
		
		if (currentSituation != null) {
			paintSituation(g2d, currentSituation);
		}
	}
	
	/**
	 * 
	 * @param g2d
	 */
	private void paintBoard(Graphics2D g2d) {
		g2d.drawImage(board, 0, 0, null);
	}
	
	/**
	 * 
	 * @param g2d
	 * @param situation
	 */
	private void paintSituation(Graphics2D g2d, Situation situation)
	{
		for (Entry<Piece, Position> entry : situation) {
			paintPiece(g2d, entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * 
	 * @param g2d
	 * @param piece
	 * @param position
	 */
	private void paintPiece(Graphics2D g2d, Piece piece, Position position)
	{
		int posX = getPosX(position.getColumn());
		int posY = getPosY(position.getRow());
		
		Image image = getImage(piece);
		
		g2d.drawImage(image, posX, posY, null);
	}
	
	/**
	 * 
	 * @param piece
	 * @return
	 */
	private Image getImage(Piece piece) 
	{
		if (piece instanceof Rook) {
			return blackRook;
		}
		
		if (piece instanceof King)
		{
			Player player = piece.getPlayer();
			
			if (player.equals(Player.get(Color.BLACK))) {
				return blackKing;
			}
			
			if (player.equals(Player.get(Color.WHITE))) {
				return whiteKing;
			}
		}
		
		throw new IllegalArgumentException(
			"Unknown piece [" + piece + "]."
		);
	}

	/**
	 * 
	 * @param row
	 * @return
	 */
	private int getPosY(Row row) {
		return row.ordinal() * SQUARE_HEIGHT;
	}
	
	/**
	 * 
	 * @param column
	 * @return
	 */
	private int getPosX(Column column) {
		return column.ordinal() * SQUARE_HEIGHT;
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		Point point = e.getPoint();
		
		double posX = point.getX();
		Column column = getColumn(posX);
		
		double posY = point.getY();
		Row row = getRow(posY);
		
		Position position = Position.get(column, row);
		listener.onMouseClicked(position);
	}
	
	/**
	 * 
	 * @param posX
	 * @return
	 */
	public Column getColumn(double posX)
	{
		int ordinal = (int) posX / SQUARE_WIDTH;
		return Column.get(ordinal);
	}
	
	/**
	 * 
	 * @param posY
	 * @return
	 */
	public Row getRow(double posY)
	{
		int ordinal = (int) posY / SQUARE_HEIGHT;
		return Row.get(ordinal);
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {	
	}

	@Override
	public void mouseExited(MouseEvent arg0) {	
	}

	@Override
	public void mousePressed(MouseEvent arg0) {	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {	
	}
}
