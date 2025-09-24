package ch2.msg;

import java.util.HashMap;
import java.util.Map;

class EntityManager {
    private static final EntityManager instance = new EntityManager();

    public static EntityManager instance() {
        return instance;
    }

    private Map<String, BaseGameEntity> entities = new HashMap<String, BaseGameEntity>();

    public void register(BaseGameEntity entity) {
        entities.put(entity.getId(), entity);
    }

    public BaseGameEntity get(String id) {
        return entities.get(id);
    }

    public void remote(BaseGameEntity entity) {
        entities.remove(entity.getId());
    }
}
