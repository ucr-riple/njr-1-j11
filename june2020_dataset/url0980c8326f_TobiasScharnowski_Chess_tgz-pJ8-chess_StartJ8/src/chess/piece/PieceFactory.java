package chess.piece;

import chess.ChessCoord;
import chess.PieceType;
import chess.Position;

public interface PieceFactory {
    public Piece createPiece(final Position pos, final PieceType pieceType,
	    final ChessColor color, final ChessCoord initialCoord);

    public void createDefaultPieceSets(final Position pos);
}
