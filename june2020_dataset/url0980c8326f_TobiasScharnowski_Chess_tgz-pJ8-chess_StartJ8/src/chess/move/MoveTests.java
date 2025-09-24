package chess.move;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import chess.move.impl.PGNParserTest;
import chess.move.impl.SimpleMoveTest;

@RunWith(Suite.class)
@SuiteClasses({ SimpleMoveTest.class, PGNParserTest.class })
public class MoveTests {

}
