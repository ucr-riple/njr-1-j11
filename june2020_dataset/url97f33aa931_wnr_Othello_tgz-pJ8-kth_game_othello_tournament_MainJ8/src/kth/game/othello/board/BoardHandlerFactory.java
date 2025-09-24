package kth.game.othello.board;

/**
 * Responsible of creating BoardHandler instances.
 */

public class BoardHandlerFactory {
	/**
	 * Creates an BoardHandler instance given a Board instance.
	 *
	 * @param board The board instance to handle.
	 * @return An BoardHandler instance to handle the given board.
	 */
	public BoardHandler createOthelloBoardHandler(BoardImpl board) {
		BoardHistoryHandler boardHistoryHandler = new BoardHistoryHandler(board);
		return new BoardHandler(board, boardHistoryHandler);
	}
}
