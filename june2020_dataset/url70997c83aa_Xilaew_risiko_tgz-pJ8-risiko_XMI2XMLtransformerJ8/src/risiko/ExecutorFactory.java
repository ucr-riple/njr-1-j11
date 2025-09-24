package risiko;

import risiko.board.Board;
import risiko.gamestate.State;

public final class ExecutorFactory {
	
	public static ActionExecutor getExecutor(Board board, State state){
		return new ActionExecutor(board,state);		
	};

}
