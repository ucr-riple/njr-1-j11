package nextgen.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.json.JSONArray;
import lib.json.JSONObject;
import nextgen.model.Attribute;
import nextgen.model.Element;
import nextgen.model.Entity;
import nextgen.model.Key;
import nextgen.model.Project;
import nextgen.model.Package;
import nextgen.model.enums.Cardinality;
import nextgen.model.enums.KeyType;

/**
 *
 * @author Rodrigo
 */
public class DAO {

    private FileManager fileManager;
    private HashMap<Integer, Attribute> attributeMap;
    private HashMap<String, Entity> entityMap;
    private HashMap<Entity, ArrayList<Attribute>> entityUsed;

    public DAO() {
        fileManager = new FileManager();
    }

    public Project getProject(String dir) throws Exception {
        entityMap = new HashMap<>();
        entityUsed = new HashMap<>();
        //Read JSON
        JSONObject obj;
        obj = fileManager.loadData(dir);

        //Capture name and description project
        String nameProject = obj.getString("name");
        String descriptionProject = obj.getString("description");

        //Capture elements project
        ArrayList<Element> elementList = new ArrayList<>();
        JSONArray elements = obj.getJSONArray("elements");

        //Cycle for each element
        for (int i = 0; i < elements.length(); i++) {
            attributeMap = new HashMap<>();
            JSONObject elem = elements.getJSONObject(i);
            //Capture information element
            String descriptionElement = elem.getString("description");
            String tableNameElement = elem.getString("tablename");
            String nameElement = elem.getString("name");

            //Attributes
            JSONArray attributes = elem.getJSONArray("attributes");
            ArrayList<Attribute> attributeList = getAttributes(attributes);

            // <editor-fold defaultstate="collapsed" desc="Key">
            //Capture keys element
            JSONArray keys = elem.getJSONArray("keys");

            ArrayList<Key> keysList = new ArrayList<>();
            if (keys != null) {
                //Cycle for each key
                for (int j = 0; j < keys.length(); j++) {
                    JSONObject eachKey = keys.getJSONObject(j);

                    //Capture key information
                    String keyName = eachKey.getString("name");
                    String k = eachKey.getString("type");
                    KeyType keyType = (k.equals("Primary")) ? KeyType.Primary : KeyType.Unique;

                    Key key = new Key(keyName, keyType);
                    //Attributes
                    JSONArray attributesArray = eachKey.getJSONArray("attributes");

                    for (int l = 0; l < attributesArray.length(); l++) {
                        int id = attributesArray.getInt(l);
                        if (attributeMap.containsKey(id)) {
                            key.getAttributes().add(attributeMap.get(id));
                        }
                    }
                    keysList.add(key);

                }
            }
            // </editor-fold>

            Package packageElement = null;
            //Package
            if (elem.has("package")) {
                JSONObject packageElem = elem.getJSONObject("package");
                String descriptionPackage = packageElem.getString("description");
                String namePackage = packageElem.getString("name");
                packageElement = new Package(namePackage, descriptionPackage);
            }

            //Add to element list
            elementList.add(new Element(nameElement, descriptionElement, tableNameElement, packageElement, null, attributeList, keysList));
        }
        return new Project(nameProject, descriptionProject, elementList);
    }

    private ArrayList<Attribute> getAttributes(JSONArray attributes) throws Exception {
        ArrayList<Attribute> attributeList = new ArrayList<>();
        if (attributes != null) {

            for (int k = 0; k < attributes.length(); k++) {
                //Capture Attribute information
                JSONObject attr = attributes.getJSONObject(k);
                String autoIncrement = attr.getString("autoincrement");
                String nameAttr = attr.getString("name");
                int id = attr.getInt("id");

                //Entity
                JSONObject entity = attr.getJSONObject("entity");
                String descriptionEntity = entity.getString("description");
                String nameEntity = entity.getString("name");
                Entity entityModel;
                if (entityMap.containsKey(nameEntity)) {
                    entityModel = entityMap.get(nameEntity);
                } else {
                    entityModel = new Entity(nameEntity, descriptionEntity);
                    entityMap.put(nameEntity, entityModel);
                }
                String commonTable = attr.getString("commonTable");
                String required = attr.getString("required");
                String comment = attr.getString("comment");
                String defaultValue = attr.getString("defaultValue");

                String cardinality = attr.getJSONObject("cardinality").toString();
                Cardinality c = (cardinality.equals("Multiple")) ? Cardinality.Multiple : Cardinality.Single;
                Attribute attribute = new Attribute(id, nameAttr, entityModel, c, Boolean.parseBoolean(required), comment, defaultValue, Boolean.parseBoolean(autoIncrement), commonTable);
                attributeMap.put(attribute.getId(), attribute);
                //Add attribute List
                attributeList.add(attribute);
            }
        } else {
            return null;
        }
        return attributeList;
    }

    public void saveProject(Project project, String path) throws Exception {
        JSONObject obj = new JSONObject(project.toHashMap());
        if (!path.matches(".*\\.ng")) {
            path += ".ng";
        }
        fileManager.saveData(obj, path);
    }

    public static void main(String[] args) {
        DAO dao = new DAO();

        try {
            Project p = dao.getProject("src/nextgen/files/test.ng");
            System.out.println(p.toHashMap().toString());
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
