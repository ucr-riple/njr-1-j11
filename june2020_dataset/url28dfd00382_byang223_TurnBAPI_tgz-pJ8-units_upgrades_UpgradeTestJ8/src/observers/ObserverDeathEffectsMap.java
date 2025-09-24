package observers;

import map.LevelMap;
import environment.Portal;

public class ObserverDeathEffectsMap extends GameObserver<LevelMap> {
    
    @Override
    void notifyObserverTemplate(LevelMap achiever) {
        ((Portal)achiever.getTileByCoords(0, 1).getEnvironment().get(0)).setActive(true);
        
    }

}
