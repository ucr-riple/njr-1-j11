package ar.fiuba.tecnicas.framework;

// Responsabilidad: Valida que los argumentos ingresados sean expresiones regulares o tags
// y se los envia a testrunner

import ar.fiuba.tecnicas.framework.JTest.TestRunner;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;

public class ArgumentValidator {
    private TestRunner testrunner;
    private List<String> args;
    public static final String regexptestcaseopt="-tcregexp";
    public static final String regexptestsuiteopt="-tsregexp";
    public static final String tagstestcaseopt="-tctags";
    public ArgumentValidator(TestRunner testrunner, String[] args) {
        this.testrunner = testrunner;
        this.args = Arrays.asList(args);
    }
    private void setArgumentToRunner(){
        int regexptestcaseidx=args.indexOf(regexptestcaseopt);
        int regexptestsuiteidx=args.indexOf(regexptestsuiteopt);
        int tagsidx=args.indexOf(tagstestcaseopt);
        if (regexptestcaseidx!=-1) testrunner.setRegexpTestcase(args.get(regexptestcaseidx + 1));
        if (regexptestsuiteidx!=-1) testrunner.setRegexpTestsuite(args.get(regexptestsuiteidx+1));
        if (tagsidx!=-1) testrunner.setArgtags(args.subList(tagsidx+1,args.size()));
    }
    private  boolean badOption(){
        return (isNotRegExpOption())&&(!args.contains(tagstestcaseopt));
    }
    private boolean isNotRegExpOption(){
        return ((!args.contains(regexptestcaseopt))&&(!args.contains(regexptestsuiteopt)));
    }
    public void start() throws InvalidArgumentException{
        if (args.size()>0){
            if (badOption() || badArgAmount()){
                throw new InvalidArgumentException(new String[]{"Bad options"});
            }
            setArgumentToRunner();
        }
    }

    private boolean badArgAmount() {
        return (args.size()%2!=0);
    }
}
