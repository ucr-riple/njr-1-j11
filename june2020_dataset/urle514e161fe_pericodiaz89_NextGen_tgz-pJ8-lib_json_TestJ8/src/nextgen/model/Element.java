/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nextgen.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Rodrigo
 */
public class Element extends Entity {

    private Element parent;
    private String tableName;
    private nextgen.model.Package package1;
    private ArrayList<Attribute> attributes;
    private ArrayList<Key> keys;

    public Element(String name, String description, String tableName, nextgen.model.Package package1, Element parent, ArrayList<Attribute> attributes, ArrayList<Key> keys) {
        super(name, description);
        this.parent = parent;
        this.tableName = tableName;
        this.package1 = package1;
        this.attributes = attributes;
        this.keys = keys;
    }

    public Element(String name, String description, String tableName) {
        super(name, description);
        parent = null;
        this.tableName = tableName;
        this.package1 = new Package("", "");
        attributes = new ArrayList<>();
        keys = new ArrayList<>();
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = super.toHashMap();
        if (parent != null) {
            map.put("parent", parent.getName());
        }

        map.put("tablename", tableName);
        if (package1 != null) {
            map.put("package", package1.toHashMap());
        }

        ArrayList<HashMap<String, Object>> attributeList = new ArrayList<>();
        for (Attribute a : this.attributes) {
            attributeList.add(a.toHashMap());
        }
        map.put("attributes", attributeList);

        ArrayList<HashMap<String, Object>> keyList = new ArrayList<>();
        for (Key a : this.keys) {
            keyList.add(a.toHashMap());
        }
        map.put("keys", keyList);

        return map;
    }

    // <editor-fold defaultstate="collapsed" desc="Get and Set">
    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<Key> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<Key> keys) {
        this.keys = keys;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Package getPackage1() {
        return package1;
    }

    public void setPackage1(nextgen.model.Package package1) {
        this.package1 = package1;
    }
    // </editor-fold>
}
