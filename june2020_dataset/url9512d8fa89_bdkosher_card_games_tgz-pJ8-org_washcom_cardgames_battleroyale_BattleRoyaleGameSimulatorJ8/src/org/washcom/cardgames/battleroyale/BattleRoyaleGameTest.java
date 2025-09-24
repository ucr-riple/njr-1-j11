package org.washcom.cardgames.battleroyale;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.washcom.cardgames.core.DeckBuilder;

/**
 *
 * @author Joe
 */
public class BattleRoyaleGameTest {

    private static abstract class FixedFeeAssessor implements BattleAssessor {

        private final int fixedFee;

        public FixedFeeAssessor() {
            this(0);
        }

        public FixedFeeAssessor(int fixedFee) {
            this.fixedFee = fixedFee;
        }

        @Override
        public Map<Player, Integer> determineFees(Battle battle) {
            return new HashMap<Player, Integer>() {

                {
                    put(new Player("1"), fixedFee);
                    put(new Player("2"), fixedFee);
                    put(new Player("3"), fixedFee);
                }
            };
        }
    }

    private static class AlwaysWinsAssessor extends FixedFeeAssessor {

        private final int winnerIndex;

        public AlwaysWinsAssessor(int winnerIndex) {
            this.winnerIndex = winnerIndex;
        }

        @Override
        public BattleCard pickWinner(Battle battle) {
            return battle.getBattleCards().get(winnerIndex);
        }
    }
    
    @Test
    public void testHookup() {
        assertTrue(true);
    }

    @Test
    public void testFirstPlayerAlwaysWins() {
        BattleRoyaleGame game = new BattleRoyaleGame(new AlwaysWinsAssessor(0));
        game.play(DeckBuilder.build52CardDeck(), new Player("1"), new Player("2"), new Player("3"));
        assertEquals(new Player("1"), game.getWinner());
    }
    
    @Test
    public void testSecondPlayerAlwaysWins() {
        BattleRoyaleGame game = new BattleRoyaleGame(new AlwaysWinsAssessor(1));
        game.play(DeckBuilder.build52CardDeck(), new Player("1"), new Player("2"), new Player("3"));
        assertEquals(new Player("2"), game.getWinner());
    }
    
    @Test
    public void testThirdPlayerAlwaysWins() {
        BattleRoyaleGame game = new BattleRoyaleGame(new AlwaysWinsAssessor(2));
        game.play(DeckBuilder.build52CardDeck(), new Player("1"), new Player("2"), new Player("3"));
        assertEquals(new Player("3"), game.getWinner());
    }

    @Test
    public void testFirstPlayerWinsOnFirstContinuationNoFees() {
        BattleRoyaleGame game = new BattleRoyaleGame(new BattleAssessor() {

            @Override
            public BattleCard pickWinner(Battle battle) {
                if (battle.getContinuations() == 0) {
                    return null;
                }
                return battle.getBattleCards().get(0);
            }

            @Override
            public Map<Player, Integer> determineFees(Battle battle) {
                return new HashMap<Player, Integer>() {

                    {
                        put(new Player("1"), 0);
                        put(new Player("2"), 0);
                        put(new Player("3"), 0);
                    }
                };
            }
        });
        game.play(DeckBuilder.build52CardDeck(), new Player("1"), new Player("2"), new Player("3"));
        assertEquals(new Player("1"), game.getWinner());
    }

    @Test
    public void testFirstPlayerWinsOnFirstContinuationFeeOf1() {
        BattleRoyaleGame game = new BattleRoyaleGame(new BattleAssessor() {

            @Override
            public BattleCard pickWinner(Battle battle) {
                if (battle.getContinuations() == 0) {
                    return null;
                }
                return battle.getBattleCards().get(0);
            }

            @Override
            public Map<Player, Integer> determineFees(Battle battle) {
                return new HashMap<Player, Integer>() {

                    {
                        put(new Player("1"), 1);
                        put(new Player("2"), 1);
                        put(new Player("3"), 1);
                    }
                };
            }
        });
        game.play(DeckBuilder.build52CardDeck(), new Player("1"), new Player("2"), new Player("3"));
        assertEquals(new Player("1"), game.getWinner());
    }

    @Test
    public void testFirstPlayerWinsOnFirstContinuationFeeOf3() {
        BattleRoyaleGame game = new BattleRoyaleGame(new BattleAssessor() {

            @Override
            public BattleCard pickWinner(Battle battle) {
                if (battle.getContinuations() == 0) {
                    return null;
                }
                return battle.getBattleCards().get(0);
            }

            @Override
            public Map<Player, Integer> determineFees(Battle battle) {
                return new HashMap<Player, Integer>() {

                    {
                        put(new Player("1"), 3);
                        put(new Player("2"), 3);
                        put(new Player("3"), 3);
                    }
                };
            }
        });
        game.play(DeckBuilder.build52CardDeck(), new Player("1"), new Player("2"), new Player("3"));
        assertEquals(new Player("1"), game.getWinner());
    }

}
