package tools.wala.driver;


import java.io.FileWriter;  
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Arrays;

import com.ibm.wala.ipa.callgraph.*;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;

import com.ibm.wala.classLoader.*;
import com.ibm.wala.types.*;
import com.ibm.wala.util.*;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.io.CommandLine;

import com.ibm.wala.ipa.callgraph.AnalysisOptions.ReflectionOptions;


public class ReachableMethods {
  public static void main(String[] args) throws WalaException, IllegalArgumentException, CancelException, IOException {
    Properties p = CommandLine.parse(args);
    String classpath = p.getProperty("classpath");
    String mainclass = p.getProperty("mainclass");

    AnalysisScope scope = AnalysisScopeReader.makeJavaBinaryAnalysisScope(classpath, null);
    ClassHierarchy cha = ClassHierarchyFactory.make(scope);

    Iterable<Entrypoint> entrypoints = Util.makeMainEntrypoints(scope, cha, "L" + mainclass.replaceAll("\\.","/"));
    AnalysisOptions options = new AnalysisOptions(scope, entrypoints);
    options.setReflectionOptions(ReflectionOptions.NONE);

    CallGraphBuilder builder = Util.makeZeroCFABuilder(Language.JAVA, options, new AnalysisCacheImpl(), cha, scope);
    CallGraph graph = builder.makeCallGraph(options, null);

    for(Iterator<CGNode> it = graph.iterator(); it.hasNext(); ) {
        CGNode cgnode = it.next();
        System.out.println(cgnode.getMethod().getSignature());
    }
  }
}
