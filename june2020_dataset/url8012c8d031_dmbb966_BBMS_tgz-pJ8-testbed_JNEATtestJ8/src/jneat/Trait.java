package jneat;

/** A trait is a group of parameters that can be expressed as a group more than once.
 * Traits save a genetic algorithm from having to search large parameter landscapes
 * on every node.  Instead, each node can simply point to a trait and those traits
 * can evolve on their own.
 */

public class Trait {
	
	int id;
	
	double[] params;
	
	public Trait() {
		id = JNEATGlobal.NewTraitID();
		params = new double[JNEATGlobal.numTraitParams];
		for (int j = 0; j < JNEATGlobal.numTraitParams; j++) {
			params[j] = 0.0;
		}
	}
	
	/** Clones a trait */
	public Trait(Trait t) {
		id = t.id;
		
		this.params = new double[JNEATGlobal.numTraitParams];
		for (int j = 0; j < JNEATGlobal.numTraitParams; j++) {
			params[j] = t.params[j];
		}
	}
	
	/** Averages the two parent traits into a new trait.  If one trait passed in is a null,
	 * will simply take the other one whole. */
	public Trait(Trait t1, Trait t2) {
		id = t1.id;
		
		if (t1 == null) t1 = t2;
		else if (t2 == null) t2 = t1;
		
		this.params = new double[JNEATGlobal.numTraitParams];
		for (int j = 0; j < JNEATGlobal.numTraitParams; j++) {
			params[j] = (t1.params[j] + t2.params[j]) / 2.0;			
		}		
	}
	
	/** Sets a parameter of this trait to the specified value.
	 * If paramNum is out of range, will return an error.
	 * 
	 * @param paramNum
	 * @param paramVal
	 */
	public void setTraitParam(int paramNum, double paramVal) {
		if (paramNum > JNEATGlobal.numTraitParams) {
			System.out.println("ERROR!  Param selection out of range.");
			return;
		}
		
		this.params[paramNum] = paramVal;
	}
	
	
	/** Mutates all params in this trait if the probability check passes. 
	 * Params will not be reduced below 0.0. */
	public void Mutate() {
		for (int i = 0; i < JNEATGlobal.numTraitParams; i++) {
			if (bbms.GlobalFuncs.randFloat() <= JNEATGlobal.traitParamMutProb) {
				params[i] += bbms.GlobalFuncs.randPosNeg() * bbms.GlobalFuncs.randFloat() * JNEATGlobal.traitMutationPower;
				if (params[i] < 0) params[i] = 0;
			}
		}
	}
	
	public String PrintTrait() {
		String ret = "Trait #" + id + ": \t";
		
		for (int i = 0; i < JNEATGlobal.numTraitParams; i++) {
			ret = ret + params[i] + " | ";
		}
				
		return ret;
	}
}
