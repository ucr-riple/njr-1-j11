/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nextgen.generator;

import nextgen.dao.FileManager;
import nextgen.model.Element;
import nextgen.model.Project;

/**
 *
 * @author castillobg
 */
public class AngularClient extends Generator {

    private Project project;
    private String directory;

    @Override
    public void generate(Project project, String directory) throws Exception {
        this.project = project;
        this.directory = directory;
        createFolders();
        createCrudRequests();
    }

    private void createFolders() {
        FileManager.copyDir("template_files/public", directory + "/public");
    }

    private void createCrudRequests() {
        String fileContent = "";
        for (Element e : project.getElements()) {
            fileContent += generateCrudContent(e);
        }
        fileContent += "// </editor-fold>";
        FileManager.generateFile(directory + "/public/js/crudRequests.js", fileContent);
    }

    private String generateCrudContent(Element e) {
        String content = "// <editor-fold desc=\"" + e.getName() + "\" defaultstate=\"collapse\">\n";
        String get = "function get" + capitalize(e.getName()) + "($http, filter, callback) {\n"
                + "\t$http.get(url + '" + e.getName().toLowerCase() + "/' + JSON.stringify(filter)).success(function(data) {\n"
                + "\t\tcallback(data);\n"
                + "\t});\n"
                + "}\n\n";
        String post = "function create" + capitalize(e.getName()) + "($http, obj, callback) {\n"
                + "\t$http.post(url + '" + e.getName().toLowerCase() + "/',obj).success(function(result) {\n"
                + "\t\tcallback(result);\n"
                + "\t});\n"
                + "}\n\n";
        String put = "function update" + capitalize(e.getName()) + "($http, obj, callback) {\n"
                + "\t$http.put(url + '" + e.getName().toLowerCase() + "/',obj).success(function(result) {\n"
                + "\t\tcallback(result);\n"
                + "\t});\n"
                + "}\n\n";
        String delete = "function delete" + capitalize(e.getName()) + "($http, obj, callback) {\n"
                + "\t$http.delete(url + '" + e.getName().toLowerCase() + "/',obj).success(function(result) {\n"
                + "\t\tcallback(result);\n"
                + "\t});\n"
                + "}\n\n";
        content += get + post + put + delete;
        return content;
    }

    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
