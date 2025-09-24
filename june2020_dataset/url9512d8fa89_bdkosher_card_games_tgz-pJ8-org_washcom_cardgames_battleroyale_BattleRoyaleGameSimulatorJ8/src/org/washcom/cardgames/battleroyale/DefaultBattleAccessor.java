package org.washcom.cardgames.battleroyale;

import java.util.Collections;
import java.util.Map;

public class DefaultBattleAccessor implements BattleAssessor {

    private final TwoBattlerAssessor twoBattlerAssessor;
    private final ThreeBattlerAssessor threeBattlerAssessor;

    public DefaultBattleAccessor() {
        twoBattlerAssessor = new TwoBattlerAssessor();
        threeBattlerAssessor = new ThreeBattlerAssessor();
    }

    @Override
    public BattleCard pickWinner(Battle battle) {
        switch (battle.getBattlers().size()) {
            case 3:
                return threeBattlerAssessor.pickWinner(battle);
            case 2:
                return twoBattlerAssessor.pickWinner(battle);
            case 1:
                return battle.getBattleCards().get(0);
            default:
                throw new IllegalStateException("Number of battlers must be 2 or 3.");
        }
    }

    @Override
    public Map<Player, Integer> determineFees(Battle battle) {
        switch (battle.getBattlers().size()) {
            case 3:
                return threeBattlerAssessor.determineFees(battle);
            case 2:
                return twoBattlerAssessor.determineFees(battle);
            default:
                return Collections.emptyMap();
        }
    }
}
