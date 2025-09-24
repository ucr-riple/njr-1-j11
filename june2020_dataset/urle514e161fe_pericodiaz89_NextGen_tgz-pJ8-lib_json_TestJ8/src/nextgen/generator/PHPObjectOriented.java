package nextgen.generator;

import java.util.ArrayList;
import java.util.Set;
import nextgen.model.Element;
import nextgen.model.Project;
import nextgen.dao.FileManager;
import nextgen.model.Attribute;
import nextgen.model.Key;
import nextgen.model.enums.KeyType;

/**
 *
 * @author Mateo
 */
public class PHPObjectOriented extends Generator {

    private Project project;
    private String directory;

    @Override
    public void generate(Project project, String directory) throws Exception {
        this.project = project;
        this.directory = directory;
        if (createPackages()) {
            createDao();
            createHelpers();
            createModel();
            createServices();
        }
    }

    public boolean createPackages() throws Exception {
        if (FileManager.generateFolder(directory) || FileManager.checkFolder(directory)) {
            FileManager.generateFolder(directory + "/model");
            ArrayList<Element> elements = project.getElements();
            for (Element e : elements) {
                if (e.getPackage1() != null && !FileManager.checkFolder(directory + "/model/" + e.getPackage1().getName())) {
                    FileManager.generateFolder(directory + "/model/" + e.getPackage1().getName());
                    FileManager.generateFile(directory + "/model/" + e.getPackage1().getName() + "/description.txt", e.getPackage1().getDescription());
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public void createDao() {
        FileManager.copyDir("template_files/dao", directory + "/dao");
    }

    public void createHelpers() {
        FileManager.copyDir("template_files/helpers", directory + "/helpers");
    }

    public void createModel() {
        for (Element e : project.getElements()) {
            String php;
            php = String.format("<?php "
                    + "\n class %s {"
                    + "\n", e.getName());
            String parameters = "";
            String init = "";
            String retGet = "";
            String getsNSets = "";
            String sqlComp = "";
            //generate all attributes , get/sets, parameters for _construct, content of _construct and the return of the get
            for (Attribute a : e.getAttributes()) {

                String name = a.getName();
                php = String.format(php + "\n\t private $%s;", name);

                //puts a "," if its already another parameter
                if (!parameters.equals("")) {
                    parameters += ", ";
                    retGet += ", ";
                }
                parameters = String.format(parameters + "$%s", name);
                retGet = String.format(retGet + "$object->%s", name);
                init = String.format(init + "\n\t\t $this->%s=$%s;", name, name);
                getsNSets = String.format(getsNSets + "\n\n\t public function get%s() {"
                        + "\n\t\t return $this->%s;"
                        + "\n\t }"
                        + "\n\n\t public function set%s($%s){"
                        + "\n\t\t$this->%s = $%s;"
                        + "\n\t}", capitalize(name), name, capitalize(name), name, name, name);
                sqlComp = String.format(sqlComp + "\n\t\t $%s = $mysql->checkVariable($%s->get%s());", name, e.getName(), capitalize(name));
            }

            php = String.format(php + "\n\n function __construct(%s){"
                    + "%s"
                    + "\n\t}"
                    + "\n "
                    + "\n\tpublic static function get($object){"
                    + "\n\t\tif(property_exists($object, \"%s\")){"
                    + "\n\t\t\t$object = $object->%s;"
                    + "\n\t\t}"
                    + "\n\t\treturn new %s (%s);"
                    + "\n\t}"
                    + "\n"
                    + "\n\t// <editor-fold defaultstate=\"collapsed\" desc=\"Get and Set\">"
                    + "%s"
                    + "\n\t// </editor-fold>"
                    + "\n\n // <editor-fold defaultstate=\"collapsed\" desc=\"CRUD\">", parameters, init, e.getName(), e.getName(), e.getName(), retGet, getsNSets);
            Key primary = null;
            for (Key k : e.getKeys()) {
                if (k.getType() == KeyType.Primary) {
                    primary = k;
                }
            }
            boolean first = true;
            String where = "";
            for (Attribute a : primary.getAttributes()) {
                if (!first) {
                    where += " AND ";
                } else {
                    first = false;
                }
                where = String.format(where+"`%s` = '$%s'", a.getName(),a.getName());
            }
            php = String.format(php + "\n\n\t public static function create($%s){"
                    + "\n\t\t$mysql = MysqlDBC::getInstance();"
                    + "\n\t\t%s"
                    + "\n\t\treturn $mysql->insert("
                    + "\n\t\t\t\t \" INSERT INTO `%s` (", e.getName(), sqlComp, e.getTableName());
            first = true;
            for (Attribute a : e.getAttributes()) {
                if (first) {
                    php = String.format(php + "`%s`", a.getName());
                    first = false;
                } else {
                    php = String.format(php + ",`%s`", a.getName());
                }
            }
            first = true;
            for (Attribute a : e.getAttributes()) {
                if (first) {
                    php = String.format(php + ") VALUES ($%s", a.getName());
                    first = false;
                } else {
                    php = String.format(php + ",$%s", a.getName());
                }
            }
            php = String.format(php + ")\""
                    + "\n\t\t);"
                    + "\n\t}"
                    + "\n\n\t public static function modify($%s){"
                    + "\n\t\t$mysql = MysqlDBC::getInstance();"
                    + "\n\t\t%s"
                    + "\n\t\t return $mysql->update("
                    + "\n\t\t\t\t\"UPDATE `%s` SET", e.getName(), sqlComp, e.getTableName());
            first = true;
            for (Attribute a : e.getAttributes()) {
                if (!where.matches(".*"+a.getName()+".*")) {
                    if (first) {
                        php = String.format(php + "`%s`=$%s", a.getName(), a.getName());
                        first = false;
                    } else {
                        php = String.format(php + ",`%s`=$%s", a.getName(), a.getName());
                    }
                }
            }
            php = String.format(php + " WHERE %s \" "
                    + "\n\t\t);"
                    + "\n\t}"
                    + "\n\n\tpublic static function delete($%s){"
                    + "\n\t\t$mysql = MysqlDBC::getInstance();"
                    + "\n\t\t%s"
                    + "\n\t\t return $mysql->delete("
                    + "\"DELETE FROM `%s` WHERE %s LIMIT 1\""
                    + "\n\t\t);"
                    + "\n\t}"
                    + "\n\n\tpublic static function getList($page, $count, $filters) {"
                    + "\n\t\t// <editor-fold defaultstate=\"collapsed\" desc=\"Limit\">"
                    + "\n\t\t$limit = \"\";"
                    + "\n\t\tif ($count > 0 && $page >= 0) {"
                    + "\n\t\t\t$lowerLimit = $page * $count;"
                    + "\n\t\t\t$limit = \" LIMIT $lowerLimit, $count\";"
                    + "\n\t\t}"
                    + "\n\t\t// </editor-fold>"
                    + "\n\t\t// <editor-fold defaultstate=\"collapsed\" desc=\"Where\">"
                    + "\n\t\t$where = \"\";"
                    + "\n\t\tif (is_object($filters)) {"
                    + "\n\t\t\t$filters = get_object_vars($filters);"
                    + "\n\t\t\tif (is_array($filters) && count($filters) > 0) {"
                    + "\n\t\t\t\t$where = \" WHERE \";"
                    + "\n\t\t\t\t$keys = array_keys($filters);"
                    + "\n\t\t\t\tfor ($i = 0; $i < count($keys); $i++) {"
                    + "\n\t\t\t\t\t$where .= \"%s.\" . $keys[$i] . \" = '\" . $filters[$keys[$i]] . \"'\";"
                    + "\n\t\t\t\t\tif ($i < count($keys) - 1) {"
                    + "\n\t\t\t\t\t\t$where .= \" AND \";"
                    + "\n\t\t\t\t\t}"
                    + "\n\t\t\t\t}"
                    + "\n\t\t\t}"
                    + "\n\t\t}"
                    + "\n\t\t$result = MysqlDBC::getInstance()->getResult(\"SELECT * FROM `%s` $where $limit\");"
                    + "\n\t\t$list = array();"
                    + "\n\t\twhile ($row = $result->fetch_object()) {"
                    + "\n\t\t\t$Entity = %s::get($row);"
                    + "\n\t\t\tarray_push($list, $Entity);"
                    + "\n\t\t}"
                    + "\n\t\treturn $list;"
                    + "\n\t }"
                    + "\n\n\t// </editor-fold>"
                    + "\n\n\tpublic function toArray() {"
                    + "\n\t\treturn array(", where, e.getName(), sqlComp, e.getTableName(), where, e.getTableName(), e.getTableName(), e.getName());
            first = true;
            for (Attribute a : e.getAttributes()) {
                if (!first) {
                    php = php + ",";
                } else {
                    first = false;
                }
                php = String.format(php + "\n\t\t\t'%s' => $this->get%s()", a.getName(), capitalize(a.getName()));
            }
            php = String.format(php + "\n\t\t );"
                    + "\n\t}"
                    + "\n}"
                    + "\n\n?>");
            if (e.getPackage1() == null) {
                FileManager.generateFile(directory + "/model/" + e.getName() + ".php", php);
            } else {
                FileManager.generateFile(directory + "/model/" + e.getPackage1().getName() +"/"+ e.getName() + ".php", php);
            }

        }
    }

    public void createServices() {
        FileManager.copyDir("template_files/services", directory + "/services");
        for (Element e : project.getElements()){
            String dir ;
            if(e.getPackage1() != null){
                dir = e.getPackage1().getName() + e.getName()+".php";
            }else{
                dir = e.getName()+".php";
            }
            String php = String.format("<?php"
                    + "\n include ('Service.php');"
                    + "\n define('%s', '%s');"
                    + "\n class %sService extends Service {"
                    + "\n\n\t public function includeSpecificFiles() {"
                    + "\n\t\t include('../model/%s');"
                    + "\n\t }"
                    + "\n\n\t public function create() {"
                    + "\n\t\t if (checkParam(%s)) {"
                    + "\n\t\t\t  $%s = $this->get%s();"
                    + "\n\t\t\t  return %s::create($%s);"
                    + "\n\t\t}else{"
                    + "\n\t\t\t return getErrorArray(03, \"Parameters missing (%s)\");"
                    + "\n\t\t }"
                    + "\n\t }"
                    + "\n\n\t public function modify() {"
                    + "\n\t\t if (checkParam(%s)) {"
                    + "\n\t\t\t $%s = $this->get%s();"
                    + "\n\t\t\t return %s::modify($%s);"
                    + "\n\t\t }else{"
                    + "\n\t\t\t return getErrorArray(03, \"Parameters missing (%s)\");"
                    + "\n\t\t }"
                    + "\n\t }"
                    + "\n\n\t public function delete() {"
                    + "\n\t\t if (checkParam(%s)){"
                    + "\n\t\t\t $%s = $this->get%s();"
                    + "\n\t\t\t return %s::delete($%s);"
                    + "\n\t\t }else{"
                    + "\n\t\t\t return getErrorArray(03, \"Parameters missing (%s)\");"
                    + "\n\t\t }"
                    + "\n\t }"
                    + "\n\n\t public function get() {"
                    + "\n\t\t $filters = array();"
                    + "\n\t\t if (checkParam('filters')) {"
                    + "\n\t\t\t  $filters = json_decode($_REQUEST['filters']);"
                    + "\n\t\t }"
                    + "\n\t\t if (checkParams('page', 'count')) {"
                    + "\n\t\t\t  $A = (%s::getList($_REQUEST['page'], $_REQUEST['count'], $filters));"
                    + "\n\t\t\t return ArrayHelper::toArray($A);"
                    + "\n\t\t } else {"
                    + "\n\t\t\t return getErrorArray(03, \"Parameters missing (page, count)\");"
                    + "\n\t\t }"
                    + "\n\t }"
                    + "\n\n\t public function get%s() {"
                    + "\n\t\t return %s::get(json_decode($_REQUEST[%s]));"
                    + "\n\t}"
                    + "\n }"
                    + "\n\n new %sService();"
                    + "\n ?>",
                    e.getName(),e.getName(),e.getName(),dir,e.getName(),e.getName(),e.getName(),e.getName(),e.getName(),
                    e.getName(),e.getName(),e.getName(),e.getName(),e.getName(),e.getName(),e.getName(),e.getName(),
                    e.getName(), e.getName(), e.getName(), e.getName(), e.getName(), e.getName(), e.getName(), e.getName(), e.getName(), e.getName());
            if (e.getPackage1() == null) {
                FileManager.generateFile(directory+"/services/"+e.getName()+"Service.php", php);
            }else{
                FileManager.generateFile(directory+"/services/"+e.getPackage1()+"/"+e.getName()+"Service.php", php);
            }
        }
    }

    public static String capitalize(String text){
        return text.substring(0,1).toUpperCase() + text.substring(1);
    }
}
