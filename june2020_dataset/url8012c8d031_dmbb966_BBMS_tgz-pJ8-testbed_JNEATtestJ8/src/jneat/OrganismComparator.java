package jneat;

public class OrganismComparator implements java.util.Comparator<Organism> {
	public OrganismComparator() {
		
	}
	
	public int compare(Organism o1, Organism o2) {				
		if (o1.fitness < o2.fitness) return +1;
		if (o1.fitness > o2.fitness) return -1;
		
		return 0;
	}
}
