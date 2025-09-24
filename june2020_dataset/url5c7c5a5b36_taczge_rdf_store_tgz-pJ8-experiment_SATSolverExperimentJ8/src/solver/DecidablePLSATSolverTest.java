package solver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import solver.PLSATSolver.Satisfiability;

public class DecidablePLSATSolverTest {
	
	private static PLSATSolver sut;
	
	@Before
	public void setUp() {
		sut = new DecidablePLSATSolver();
	}
	
	@Test
	public void check_0() {
		assertThat(
				sut.check("(and (and p1 q2) (not (or q1 (or q2 (not (not q3))))))"),
				is(Satisfiability.NO));
	}

	@Test
	public void check_1() {
		assertThat(sut.check("(and p q)"), is(Satisfiability.YES));
	}

	@Test
	public void check_2() {
		assertThat(sut.check("(or p q)"), is(Satisfiability.YES));
	}

	@Test
	public void check_3() {
		assertThat(sut.check("(and p (not p)"), is(Satisfiability.NO));
	}

	@Test
	public void check_4() {
		assertThat(sut.check("(or p (not p)"), is(Satisfiability.YES));
	}
	
}
