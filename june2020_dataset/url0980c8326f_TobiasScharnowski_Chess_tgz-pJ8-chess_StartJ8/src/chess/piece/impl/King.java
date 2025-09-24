package chess.piece.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import chess.ChessCoord;
import chess.ChessRules;
import chess.PieceType;
import chess.Position;
import chess.impl.Coords;
import chess.move.Move;
import chess.move.impl.CastlingMove;
import chess.piece.ChessColor;
import chess.piece.Piece;

/**
 * Class representing the default Chess King piece
 * 
 * @author Tobias Scharnowski
 * 
 */
public class King extends AbstractPiece {

    public King(Position pos, ChessColor cc, ChessCoord initialCoord) {
        super(pos, PieceType.KING, cc, initialCoord);
    }

    @Override
    protected List<Move> _generateMoves() {
        // IMPROVE Depending on the protocol we can get rid of this check in
        // case checking for the piece's activity is the caller's responsibility
        if (!this.isActive()) {
            return Collections.emptyList();
        }
        final List<Move> result = new LinkedList<Move>();

        int leftBoundary = this.getCoord().getFile() == ChessRules.MIN_FILE ? ChessRules.MIN_FILE
                : this.getCoord().getFile() - 1;
        int rightBoundary = this.getCoord().getFile() == ChessRules.MAX_FILE ? ChessRules.MAX_FILE
                : this.getCoord().getFile() + 1;
        int bottomBoundary = this.getCoord().getRank() == ChessRules.MIN_RANK ? ChessRules.MIN_RANK
                : this.getCoord().getRank() - 1;
        int topBoundary = this.getCoord().getRank() == ChessRules.MAX_RANK ? ChessRules.MAX_RANK
                : this.getCoord().getRank() + 1;

        // Check for regular Moves first
        for (int file = leftBoundary; file <= rightBoundary; ++file) {
            for (int rank = bottomBoundary; rank <= topBoundary; ++rank) {
                // We assume having valid coordinates only!
                // Skip the field the king is standing on at the moment
                if (!(file == this.getCoord().getFile() && rank == this
                        .getCoord().getRank())) {
                    // IMPROVE We can inject a coordinate factory bean to get
                    // rid of
                    // the implementation specifics here
                    final ChessCoord destCoord = Coords.coord(file, rank);
                    final Piece destCoordPiece = this.getPos().getPieceAt(
                            destCoord);
                    if ((!(destCoordPiece != null && destCoordPiece.getColor()
                            .equals(this.getColor())))
                            && !this.getPos().isControlled(destCoord)) {
                        // Now we know we can actually go there and let our
                        // helper do the work. We are not interested in its
                        // return value though as we can only pursue the
                        // direction in a distance of one around the king.
                        final Move possibleMove = defaultCorrectMoveTypeForValidCoord(destCoord);
                        if (possibleMove != null) {
                            result.add(possibleMove);
                        }
                    }
                }
            }
        }

        // Check for castling now (only if the king is not in check at the
        // moment)
        if (!this.getPos().isControlled(this.coord)) {
            // Kingside
            if (castlingPossible(true)) {
                final ChessCoord rookSrc = Coords.coord(
                        pos.getCastlingRookFile(true, color), coord.getRank());
                final ChessCoord targetCoord = ChessRules
                        .getCastlingTargetCoord(true, color);
                result.add(new CastlingMove(getCoord(), targetCoord, rookSrc));
            }
            // Queenside
            if (castlingPossible(false)) {
                final ChessCoord rookSrc = Coords.coord(
                        pos.getCastlingRookFile(false, color), coord.getRank());
                final ChessCoord targetCoord = ChessRules
                        .getCastlingTargetCoord(false, color);
                result.add(new CastlingMove(coord, targetCoord, rookSrc));
            }
        }

        return result;
    }

    @Override
    public boolean controls(ChessCoord coord) {
        /*
         * In this case we have an easy time as the king just controls all
         * fields around himself.
         */
        return this.isActive()
                && Math.abs(coord.getFile() - this.getCoord().getFile()) <= 1
                && Math.abs(coord.getRank() - this.getCoord().getRank()) <= 1;
    }

    private boolean castlingPossible(final boolean kingside) {
        // First check whether castling is generally allowed in this position.
        boolean result = this.getPos().isCastlingAvailable(kingside,
                this.getColor());
        if (result) {
            // Castling to the specified side is generally allowed, we are good
            // to go on checking.

            // Prepare the file index limit (left or right corner)
            int fileBorder = kingside ? ChessRules.MAX_FILE
                    : ChessRules.MIN_FILE;
            // Decide whether we have to go left (-1) or right (+1)
            int step = kingside ? 1 : -1;
            /*
             * Make sure no piece is blocking the castling and no field between
             * the rook and the king is controlled.
             */
            /*
             * IMPROVE Castling in Fischer Random Chess: there can be the
             * situation where the rook is actually on the wrong side of the
             * king with castling being a legal move. In this case we have to
             * check the fields between the king and his target coordinate as
             * well as the range between the king and the rook. The calculation
             * is not correct in this case at the moment as we would check the
             * whole range from the king to the border because the index moves
             * away from the rook position.
             */
            for (int i = this.getCoord().getFile(); i != this.getPos()
                    .getCastlingRookFile(true, this.getColor())
                    && i != fileBorder; i += step) {
                // Get target coordinate and piece at coordinate
                final ChessCoord tarCoord = Coords.coord(i, this.getCoord()
                        .getRank());
                final Piece tarPiece = this.getPos().getPieceAt(tarCoord);
                // Make sure our target coordinate is not blocked or
                // controlled
                if (this.getPos().isControlled(tarCoord) || (tarPiece != null)) {
                    // It is blocked, so we cannot castle. Break the loop and
                    // return.
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

}
