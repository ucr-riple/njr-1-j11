package units.reactions;

import units.Unit;
import units.upgrades.MoveModification;

public class HyperSpeedReaction extends Reaction{

	@Override
    public void apply(Unit u) {
		new MoveModification (u, 0, 5).modify();
    }

}
