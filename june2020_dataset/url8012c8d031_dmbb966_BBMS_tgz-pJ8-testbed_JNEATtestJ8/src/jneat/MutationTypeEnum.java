package jneat;

// A "Cold Gaussian" means that the mutated weight depends only on the random number generated,
// as opposed to modifying the existing weight by a random number.

public enum MutationTypeEnum {
	GAUSSIAN, COLD_GAUSSIAN
}
