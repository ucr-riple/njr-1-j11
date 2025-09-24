
package ar.fiuba.tecnicas.framework.JTest;


import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class XmlPrinter implements TestListener {
    private List<String> namesTestSuites;
    private String nameCase;
    private String time;
    private Throwable throwable;
    private FileWriter fileWriter;
    private PrintWriter printer;

    public XmlPrinter() {
        namesTestSuites = new ArrayList<String>();
        inicializar();
    }
    private void inicializar() {
        try {
            fileWriter = new FileWriter("ReporteXsd.xml");
            printer = new PrintWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setSuiteRaiz(String name) {
        namesTestSuites.add(name);
    }

    @Override
    public void addFailure(Test test, String time, Throwable throwable) {
        printTestCaseWithError(test, time, throwable,"failure");
    }

    @Override
    public void addError(Test test, String time, Throwable throwable) {
        printTestCaseWithError(test, time, throwable,"error");
    }

    private void printTestCaseWithError(Test test, String time, Throwable throwable, String type) {
        nameCase = test.toString();
        this.time = time;
        this.throwable = throwable;

        printHeaderTestCase();
        printContentException(type);
        printAtribuesTestCase();
    }

    @Override
    public void addSuccess(TestCase test, String time) {
        nameCase = test.toString();
        this.time = time;
        printHeaderTestCase();
        printAtribuesTestCase();
    }

    private void printContentException(String type) {
        printer.println("\t\t\t\t\t\t\t\t<xs:sequence>\n\t<xs:element name=\"" + type + "\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<xs:complexType mixed=\"true\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<xs:attribute name=\"" + throwable.toString() + "\" type=\"xs:string\" use=\"optional\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<xs:attribute name=\"" + throwable.getMessage() + "\" type=\"xs:string\" use=\"optional\"/>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</xs:complexType>\n" +
                "\t\t\t\t\t\t\t\t\t</xs:element> \t\t\t\t\t\t\t\t</xs:sequence>");
    }

    private void printHeaderTestCase() {
        printer.println("\t\t\t\t<xs:element name=\"testcase\">\n" +
                "\t\t\t\t\t<xs:complexType>");
    }

    private void printAtribuesTestCase() {
        printer.print("\t\t\t\t\t\t<xs:attribute name=\"" + nameCase + "\" type=\"xs:string\" use=\"required\"/>\n" +
                "\t\t\t\t\t\t<xs:attribute name=\"" + time + "\" type=\"xs:string\" use=\"optional\"/>" +
                "\n\t\t\t\t\t</xs:complexType>\n" +
                "\t\t\t\t</xs:element>\n");
    }

    @Override
    public void print(String messsage) {
        if(!messsage.contains(":")) {
            printHeader();
            printXsdTestSuiteMain();
            setSuiteRaiz(messsage);
        } else {
            String last = namesTestSuites.get(namesTestSuites.size() - 1);
            namesTestSuites.remove(namesTestSuites.size() - 1);
            imprimirCloseTestSuite(last);
        }

    }

    private void imprimirCloseTestSuite(String name) {
        printer.print("\t\t\t<xs:attribute name=\"" + name + "\" type=\"xs:string\" use=\"optional\"/>" +
                "\n\t\t</xs:complexType>\n  \t</xs:element>");
        if(namesTestSuites.size() > 1) {
            printer.print("</xs:sequence>");
        }
        if(namesTestSuites.size() == 0) {
            printer.print("\n</xs:schema>");
            printer.close();
        }
    }
    @Override
    public void insertHSeparator() {

    }

    private void printHeader() {
        printer.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n" +
                "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n");
    }

    private void printXsdTestSuiteMain() {
        printer.print("<xs:element name=\"testsuite\"");
        if(namesTestSuites.isEmpty()) {
            System.out.print(" xmlns:xsi=\"http://www.w3c.org/2001/XMLSchema-instance\"");
        }
        printer.println(">\n\t<xs:complexType>\n" +
                "\t\t<xs:sequence>");
    }
}
