package chess.piece.impl;

import chess.ChessCoord;
import chess.ChessRules;
import chess.PieceType;
import chess.Position;
import chess.impl.Coords;
import chess.piece.ChessColor;
import chess.piece.Piece;
import chess.piece.PieceFactory;

public class DefaultPieceFactory implements PieceFactory {

    @Override
    public Piece createPiece(Position pos, PieceType pieceType,
	    final ChessColor color, ChessCoord initialCoord) {
	Piece result;
	switch (pieceType) {
	case PAWN:
	    result = new Pawn(pos, color, initialCoord);
	    break;
	case ROOK:
	    /*
	     * At the moment we assign kingside rooks to be the one on the right
	     * half of the starting positions
	     */
	    /*
	     * IMPROVE This is specific to initial setups where the rooks are
	     * actually on the respective sides of the king. To support any rook
	     * positioning, we have to redo this
	     */
	    result = new Rook(pos, color, initialCoord,
		    initialCoord.getFile() > 3);
	    break;
	case KNIGHT:
	    result = new Knight(pos, color, initialCoord);
	    break;
	case BISHOP:
	    result = new Bishop(pos, color, initialCoord);
	    break;
	case QUEEN:
	    result = new Queen(pos, color, initialCoord);
	    break;
	case KING:
	    result = new King(pos, color, initialCoord);
	    break;
	default:
	    throw new UnsupportedOperationException(
		    "The passed PieceType is not supported");
	}
	return result;
    }

    @Override
    public void createDefaultPieceSets(final Position pos) {
	// Set white pieces
	int file = 0;
	pos.addPiece(this.createPiece(pos, PieceType.ROOK, ChessColor.WHITE,
		Coords.coord(file++, 0)));
	pos.addPiece(this.createPiece(pos, PieceType.KNIGHT, ChessColor.WHITE,
		Coords.coord(file++, 0)));
	pos.addPiece(this.createPiece(pos, PieceType.BISHOP, ChessColor.WHITE,
		Coords.coord(file++, 0)));
	pos.addPiece(this.createPiece(pos, PieceType.QUEEN, ChessColor.WHITE,
		Coords.coord(file++, 0)));
	pos.addPiece(this.createPiece(pos, PieceType.KING, ChessColor.WHITE,
		Coords.coord(file++, 0)));
	pos.addPiece(this.createPiece(pos, PieceType.BISHOP, ChessColor.WHITE,
		Coords.coord(file++, 0)));
	pos.addPiece(this.createPiece(pos, PieceType.KNIGHT, ChessColor.WHITE,
		Coords.coord(file++, 0)));
	pos.addPiece(this.createPiece(pos, PieceType.ROOK, ChessColor.WHITE,
		Coords.coord(file++, 0)));
	// Set white Pawns
	for (int i = 0; i <= ChessRules.MAX_FILE; ++i) {
	    pos.addPiece(this.createPiece(pos, PieceType.PAWN,
		    ChessColor.WHITE, Coords.coord(i, 1)));
	}

	// Set black pieces
	file = 0;
	pos.addPiece(this.createPiece(pos, PieceType.ROOK, ChessColor.BLACK,
		Coords.coord(file++, 7)));
	pos.addPiece(this.createPiece(pos, PieceType.KNIGHT, ChessColor.BLACK,
		Coords.coord(file++, 7)));
	pos.addPiece(this.createPiece(pos, PieceType.BISHOP, ChessColor.BLACK,
		Coords.coord(file++, 7)));
	pos.addPiece(this.createPiece(pos, PieceType.QUEEN, ChessColor.BLACK,
		Coords.coord(file++, 7)));
	pos.addPiece(this.createPiece(pos, PieceType.KING, ChessColor.BLACK,
		Coords.coord(file++, 7)));
	pos.addPiece(this.createPiece(pos, PieceType.BISHOP, ChessColor.BLACK,
		Coords.coord(file++, 7)));
	pos.addPiece(this.createPiece(pos, PieceType.KNIGHT, ChessColor.BLACK,
		Coords.coord(file++, 7)));
	pos.addPiece(this.createPiece(pos, PieceType.ROOK, ChessColor.BLACK,
		Coords.coord(file++, 7)));
	// Set black Pawns
	for (int i = 0; i <= ChessRules.MAX_FILE; ++i) {
	    pos.addPiece(this.createPiece(pos, PieceType.PAWN,
		    ChessColor.BLACK, Coords.coord(i, 6)));
	}
    }
}
