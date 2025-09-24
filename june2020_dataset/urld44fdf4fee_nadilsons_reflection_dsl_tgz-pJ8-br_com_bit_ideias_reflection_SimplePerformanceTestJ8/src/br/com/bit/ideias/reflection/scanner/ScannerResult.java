package br.com.bit.ideias.reflection.scanner;

import java.util.Collections;
import java.util.Set;

/**
 * @author Leonardo Campos
 * @date 16/08/2009
 */
public class ScannerResult {
	private Set<Class<?>> classes;
	
    public ScannerResult(Set<Class<?>> classes) {
		this.classes = classes;
	}

	public Set<Class<?>> getClasses() {
        return Collections.unmodifiableSet(classes);
    }
}
