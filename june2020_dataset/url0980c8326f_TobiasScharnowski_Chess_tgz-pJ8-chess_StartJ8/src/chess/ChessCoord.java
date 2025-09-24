package chess;

public interface ChessCoord {
    public int getRank();

    public int getFile();

    public boolean sameAs(final ChessCoord other);
}