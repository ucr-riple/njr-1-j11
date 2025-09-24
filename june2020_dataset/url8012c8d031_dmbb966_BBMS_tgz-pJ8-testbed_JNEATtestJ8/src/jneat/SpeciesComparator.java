package jneat;

public class SpeciesComparator implements java.util.Comparator<Species> {
	public SpeciesComparator() {
		
	}
	
	public int compare(Species s1, Species s2) {	
		
		Organism o1 = s1.organisms.firstElement();
		Organism o2 = s2.organisms.firstElement();
		
		if (o1.orig_fitness < o2.orig_fitness) return +1;
		if (o1.orig_fitness > o2.orig_fitness) return -1;
		
		return 0;
	}
}
