package ru.spbau.tinydb.grammar;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import ru.spbau.tinydb.queries.IQuery;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author adkozlov
 */
public class GrammarTester {

    public static final String TESTS_PATH = "testData/grammar";
    public static final String FILTER = "*.sql";

    private static List<String> testsList(String directory) {
        List<String> result = new ArrayList<>();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory), FILTER)) {
            for (Path path : directoryStream) {
                result.add(path.toString());
            }
        } catch (IOException e) {
            handleException(e);
        }

        return result;
    }

    private static void handleException(Exception e) {
        System.err.println(e.getMessage());
    }

    public static void main(String[] args) {
        for (String testName : testsList(TESTS_PATH)) {
            System.out.println(testName);

            try {
                SQLGrammarParser parser = new SQLGrammarParser(new CommonTokenStream(
                        new SQLGrammarLexer(new ANTLRInputStream(new FileInputStream(testName)))));

                for (IQuery query : parser.script().result) {
                    System.out.println(query);
                }
            } catch (IOException e) {
                handleException(e);
            }

            System.out.println();
        }
    }
}
