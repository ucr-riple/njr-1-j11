package nextgen.model;

import java.util.HashMap;

/**
 *
 * @author Rodrigo
 */
public class Entity {

    private String name;
    private String description;

    public Entity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // <editor-fold defaultstate="collapsed" desc="Get and Set">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // </editor-fold>
    @Override
    public String toString() {
        return getName();
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> entity = new HashMap<>();
        entity.put("name", name);
        entity.put("description", description);
        return entity;
    }
}
