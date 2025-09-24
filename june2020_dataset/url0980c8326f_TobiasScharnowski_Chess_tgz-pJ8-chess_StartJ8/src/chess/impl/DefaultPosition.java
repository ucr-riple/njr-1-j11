package chess.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import chess.CastlingAvailability;
import chess.ChessCoord;
import chess.ChessRules;
import chess.PieceType;
import chess.Position;
import chess.move.Move;
import chess.piece.ChessColor;
import chess.piece.Piece;
import chess.piece.impl.DefaultPieceFactory;

public class DefaultPosition implements Position {
    private final int INITIAL_PIECE_SET_LENGTH = 20;
    private Map<ChessColor, List<Piece>> pieceSets;
    private final Piece[][] pieces;
    private List<Move> moves;
    private ChessColor activeColor;
    private int halveMoveClock;
    private int halveMoves;
    private int enPassantFile;
    private CastlingAvailability castlingAvailability;

    @SuppressWarnings("unused")
    private DefaultPosition() {
        pieces = null;
    }

    {
        pieceSets = new HashMap<ChessColor, List<Piece>>();
        pieceSets.put(ChessColor.WHITE, new ArrayList<Piece>(
                INITIAL_PIECE_SET_LENGTH));
        pieceSets.put(ChessColor.BLACK, new ArrayList<Piece>(
                INITIAL_PIECE_SET_LENGTH));
    }

    public DefaultPosition(final boolean setDefaultPieces) {
        this(new Piece[ChessRules.MAX_FILE + 1][ChessRules.MAX_RANK + 1],
                new LinkedList<Move>(), ChessColor.WHITE, 0, 0, -1,
                new DefaultCastlingAvailability());
        if (setDefaultPieces) {
            this.generateDefaultPieces();
        }
    }

    public DefaultPosition(Piece[][] pieces, List<Move> moves,
            ChessColor activeColor, int halfMoves, int fullMoves,
            int enPassantFile, CastlingAvailability castlingAvailability) {
        this.pieces = pieces;
        this.moves = moves;
        this.activeColor = activeColor;
        this.halveMoveClock = halfMoves;
        this.halveMoves = fullMoves;
        this.enPassantFile = enPassantFile;
        this.castlingAvailability = castlingAvailability;
    }

    // IMPROVE Copying: For copying a position we need to do more than set the
    // pieces and we got no use case for that at the moment.
    /*
     * @Override public Position getCopy() { DefaultPosition copy = new
     * DefaultPosition(false); PieceFactory factory = new DefaultPieceFactory();
     * for (int file = 0; file <= DefaultChessCoord.MAX_FILE; ++file) { for (int
     * rank = 0; rank <= DefaultChessCoord.MAX_RANK; ++rank) { Piece currPiece =
     * this.getPieceAt(file, rank); if(currPiece!=null) {
     * factory.createPiece(copy, currPiece.getType(), currPiece.getColor(), new
     * DefaultChessCoord(currPiece.getCoord())); } } } return copy; }
     */

    @Override
    public Piece getPieceAt(ChessCoord coord) {
        return pieces[coord.getFile()][coord.getRank()];
    }

    @Override
    public Piece getPieceAt(int file, int rank) {
        return pieces[file][rank];
    }

    @Override
    public void setPieceAt(final Piece piece, final ChessCoord coord) {
        if (piece != null) {
            piece.setCoord(coord);
        }
        pieces[coord.getFile()][coord.getRank()] = piece;
    }

    @Override
    public int getEnpassantFile() {
        return this.enPassantFile;
    }

    @Override
    public void setEnpassantFile(int enpassantFile) {
        this.enPassantFile = enpassantFile;
    }

    @Override
    public int getHalveMoveClock() {
        return this.halveMoveClock;
    }

    @Override
    public void advanceHalveMoveClock() {
        ++this.halveMoveClock;
    }

    @Override
    public void revertHalveMoveClock() {
        --this.halveMoveClock;
    }

    @Override
    public boolean isCastlingAvailable(boolean kingside, ChessColor color) {
        return this.castlingAvailability.isCastlingAvailable(kingside, color);
    }

    @Override
    public void setCastlingAvailable(boolean kingside, ChessColor color,
            boolean b) {
        this.castlingAvailability.setCastlingAvailability(kingside, color, b);
    }

    @Override
    public int getMoveNumber() {
        return halveMoves;
    }

    @Override
    public void advanceMoveNumber() {
        ++halveMoves;
    }

    @Override
    public void revertMoveNumber() {
        --this.halveMoves;
    }

    @Override
    public ChessColor getActiveColor() {
        return this.activeColor;
    }

    @Override
    public void flipActiveColor() {
        this.setActiveColor(ChessColor.oppositeColor(this.getActiveColor()));
    }

    private void generateDefaultPieces() {
        DefaultPieceFactory pieceFac = new DefaultPieceFactory();
        pieceFac.createDefaultPieceSets(this);
    }

    private void setActiveColor(final ChessColor chessColor) {
        this.activeColor = chessColor;
    }

    @Override
    public void addPiece(final Piece piece) {
        this.pieceSets.get(piece.getColor()).add(piece);
        this.setPieceAt(piece, piece.getCoord());
    }

    @Override
    public void removePiece(final Piece piece) {
        /*
         * We have to check for this being null because of the PawnPromotionMove
         * implementation as a SimpleTakingMove
         */
        if (piece != null) {
            this.setPieceAt(null, piece.getCoord());
            this.pieceSets.get(piece.getType()).remove(piece);
        }
    }

    @Override
    public int getCastlingRookFile(final boolean kingside,
            final ChessColor color) {
        return this.castlingAvailability.getCastlingRookFile(kingside, color);
    }

    @Override
    public boolean isControlled(final ChessCoord coord) {
        final List<Piece> opponentsPieces = this.pieceSets.get(ChessColor
                .oppositeColor(getActiveColor()));
        for (Piece p : opponentsPieces) {
            if (p.isActive() && p.controls(coord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean processMove(Move move) {
        move.doMove(this);
        if (this.isLegalPosition()) {
            this.moves.add(move);
            return true;
        } else {
            move.undoMove(this);
            return false;
        }
    }

    @Override
    public void undoLastMove() {
        if (!this.moves.isEmpty()) {
            this.moves.get(moves.size() - 1).undoMove(this);
            moves.remove(moves.size() - 1);
        }
    }

    @Override
    public List<Move> possibleMoves() {
        List<Move> result = new LinkedList<>();
        for (Piece piece : this.pieceSets.get(this.getActiveColor())) {
            result.addAll(piece.getPossibleMoves());
        }
        return result;
    }

    /**
     * Determines whether this {@link chess.Position Position} is legal.
     *
     * @return True in case the position is legal, false if not.
     */
    private boolean isLegalPosition() {
        return !isControlled(activeKingCoord());
    }

    @Override
    public ChessCoord activeKingCoord() {
        final List<Piece> king = getPiecesBy(PieceType.KING,
                this.getActiveColor());
        if (king.size() != 1) {
            throw new IllegalStateException(
                    "There is supposed to be exactly one King for each side.");
        } else {
            return king.get(0).getCoord();
        }
    }

    private List<Piece> getPiecesBy(final PieceType pt, final ChessColor color) {
        final List<Piece> result = new ArrayList<Piece>();
        for (Piece piece : this.pieceSets.get(color)) {
            if (piece.getType().equals(pt)) {
                result.add(piece);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Piece piece;
        for (int row = pieces.length - 1; row >= 0; --row) {
            for (int file = 0; file < pieces.length; ++file) {
                piece = pieces[file][row];
                char shortCut = piece == null ? ' ' : piece.shortCut();
                sb.append('[');
                sb.append(shortCut);
                sb.append(']');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    @Override
    public List<Move> possibleMovesToCoordForType(PieceType pt,
            ChessCoord targetCoord) {
        final List<Move> result = new LinkedList<>();
        for (Piece piece : this.getPiecesBy(pt, getActiveColor())) {
            final List<Move> possibleMove = piece
                    .generatePossibleMovesToCoord(targetCoord);
            if (possibleMove != null) {
                result.addAll(possibleMove);
            }
        }
        return result;
    }

}
