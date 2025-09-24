package org.washcom.cardgames.battleroyale;

import java.util.Arrays;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.washcom.cardgames.core.Card;
import org.washcom.cardgames.core.Denomination;
import org.washcom.cardgames.core.Suit;

public class DefaultBattleAccessorTest {

    DefaultBattleAccessor accessor = new DefaultBattleAccessor();

    @Test
    public void testJackEight() {
        Battle battle = Mockito.mock(Battle.class);
        Mockito.when(battle.getBattlers()).thenReturn(Arrays.asList(Mockito.mock(Player.class), Mockito.mock(Player.class)));
        final boolean[] swapOccurred = { false };
        BattleRoyaleGame game = new BattleRoyaleGame() {

            @Override
            void swapHands(Player one, Player other) {
                swapOccurred[0] = true;
            }
            
        };
        Mockito.when(battle.getGame()).thenReturn(game);
        BattleCard card1 = new BattleCard(new Card(Denomination.JACK,
                Suit.CLUBS), Mockito.mock(Player.class));
        BattleCard card2 = new BattleCard(new Card(Denomination.EIGHT,
                Suit.CLUBS), Mockito.mock(Player.class));
        Mockito.when(battle.getBattleCards()).thenReturn(
                Arrays.asList(card1, card2));
        Assert.assertEquals(card1, accessor.pickWinner(battle));
        Assert.assertTrue(swapOccurred[0]);
    }
    
    @Test
    public void testThreeFour() {
        Battle battle = Mockito.mock(Battle.class);
        Mockito.when(battle.getBattlers()).thenReturn(Arrays.asList(Mockito.mock(Player.class), Mockito.mock(Player.class)));
        BattleCard card1 = new BattleCard(new Card(Denomination.THREE,
                Suit.CLUBS), Mockito.mock(Player.class));
        BattleCard card2 = new BattleCard(new Card(Denomination.FOUR,
                Suit.CLUBS), Mockito.mock(Player.class));
        Mockito.when(battle.getBattleCards()).thenReturn(
                Arrays.asList(card1, card2));
        Assert.assertNull(accessor.pickWinner(battle));
    }
    
    @Test
    public void testThreeFive() {
        Battle battle = Mockito.mock(Battle.class);
        Mockito.when(battle.getBattlers()).thenReturn(Arrays.asList(Mockito.mock(Player.class), Mockito.mock(Player.class)));
        BattleCard card1 = new BattleCard(new Card(Denomination.THREE,
                Suit.CLUBS), Mockito.mock(Player.class));
        BattleCard card2 = new BattleCard(new Card(Denomination.FIVE,
                Suit.CLUBS), Mockito.mock(Player.class));
        Mockito.when(battle.getBattleCards()).thenReturn(
                Arrays.asList(card1, card2));
        Assert.assertNull(accessor.pickWinner(battle));
    }
    
    @Test
    public void testJackAceFive() {
        Battle battle = Mockito.mock(Battle.class);
        Mockito.when(battle.getBattlers()).thenReturn(Arrays.asList(Mockito.mock(Player.class), Mockito.mock(Player.class)));
        BattleCard card1 = new BattleCard(new Card(Denomination.JACK,
                Suit.CLUBS), Mockito.mock(Player.class));
        BattleCard card2 = new BattleCard(new Card(Denomination.ACE,
                Suit.CLUBS), Mockito.mock(Player.class));
        BattleCard card3 = new BattleCard(new Card(Denomination.FIVE,
                Suit.CLUBS), Mockito.mock(Player.class));
        Mockito.when(battle.getBattleCards()).thenReturn(
                Arrays.asList(card1, card2, card3));
        Assert.assertNull(accessor.pickWinner(battle));
    }
    

}
