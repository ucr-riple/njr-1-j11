package ar.fiuba.tecnicas.framework;

public class Usage {
    private static final String usage="Usage:\n" +
            "\tOptions:\n" +
            "\t\t"+ArgumentValidator.regexptestcaseopt+" test case regular expression" +
            "\n\t\t"+ArgumentValidator.regexptestsuiteopt+" test suite regular expression" +
            "\n\t\t"+ArgumentValidator.tagstestcaseopt+" test case 's tags list\n";

    @Override
    public String toString() {
        return usage;
    }
}
