package br.com.bit.ideias.reflection.scanner;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.com.bit.ideias.reflection.test.artefacts.forPackageTest.AnnotationParaPackageTest;
import br.com.bit.ideias.reflection.test.artefacts.forPackageTest.Class2ParaPackageTest;
import br.com.bit.ideias.reflection.test.artefacts.forPackageTest.ClasseParaPackageTest;
import br.com.bit.ideias.reflection.test.artefacts.forPackageTest.EnumParaPackageTest;


/**
 * @author Leonardo Campos
 * @date 16/08/2009
 */
public class PackageScannerTest {
    @Test
    public void scannOnArtefactsPackageShouldReturnAllClasses() throws Exception {
        PackageScanner scanner = PackageScanner.forPackage("br.com.bit.ideias.reflection.test.artefacts.forPackageTest");
        
        ScannerResult result = scanner.scan();
        Set<Class<?>> classes = result.getClasses();
        
        Set<Class<?>> expected = new HashSet<Class<?>>();
        expected.add(AnnotationParaPackageTest.class);
        expected.add(ClasseParaPackageTest.class);
        expected.add(Class2ParaPackageTest.class);
        expected.add(EnumParaPackageTest.class);
//        expected.add(ClasseDoSubPacote.class);
        
        assertEquals(expected, classes);
    }
}
