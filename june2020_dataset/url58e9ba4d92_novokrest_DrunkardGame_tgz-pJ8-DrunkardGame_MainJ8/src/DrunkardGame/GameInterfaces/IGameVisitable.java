package DrunkardGame.GameInterfaces;

import DrunkardGame.GameObjects.CommonObjects.Field;

/**
 * Created by novokrest on 3/3/14.
 */
public interface IGameVisitable {
    public void accept(IGameVisitor visitor, Field field);
}
