package coursescheduleapp.model;

public interface Preference {
	public double getFitness(Schedule sched);
	public void addPref(String pref);
	public String toString();
}
