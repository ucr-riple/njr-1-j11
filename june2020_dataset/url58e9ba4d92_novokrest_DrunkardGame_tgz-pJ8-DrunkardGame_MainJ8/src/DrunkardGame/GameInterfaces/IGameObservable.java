package DrunkardGame.GameInterfaces;

import DrunkardGame.GameObjects.CommonObjects.GameMovingObject;

/**
 * Created by novokrest on 3/4/14.
 */
public interface IGameObservable {
    public void registerGameObject(GameMovingObject gameObject);
    public void notifyAboutStep();
}
