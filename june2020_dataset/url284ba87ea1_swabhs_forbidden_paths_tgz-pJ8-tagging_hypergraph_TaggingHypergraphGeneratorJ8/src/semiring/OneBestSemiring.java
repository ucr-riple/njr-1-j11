package semiring;

import java.util.List;

public class OneBestSemiring implements Semiring<Derivation> {

	/** */
	@Override
	public Derivation multiply(List<Derivation> derivations) {
		double product = 0.0;
		for (Derivation d : derivations) {
			product = product + d.getScore();
		}
		Derivation result = new Derivation(null, product, derivations);
		return result;
	}
	
	/** Compares the scores of two derivations and returns the maximum */
	@Override
	public Derivation add(Derivation element1, Derivation element2) {
		return (element1.getScore() >= element2.getScore()) ? element1 : element2;
	}

}
