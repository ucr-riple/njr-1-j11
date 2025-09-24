package solver;

import core.Resource;


public interface PLSATSolver {
	
	Resource ATOM = Resource.of("ATOM");
	Resource NOT  = Resource.of("NOT");
	Resource AND  = Resource.of("AND");
	Resource OR   = Resource.of("OR");
	Resource _0   = Resource.of("0");
	Resource _1   = Resource.of("1");
	
	enum Satisfiability { YES, NO };
	
	Satisfiability check(String exp);

}
