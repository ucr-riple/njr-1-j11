package assignments.farmer;


public interface Farmer {

	public void plow();
	public void plant();
	public void weedControl();
	public void harvest();
	public void market();
	
	
	
	public void setSeasonState(SeasonState seasonState);
}
