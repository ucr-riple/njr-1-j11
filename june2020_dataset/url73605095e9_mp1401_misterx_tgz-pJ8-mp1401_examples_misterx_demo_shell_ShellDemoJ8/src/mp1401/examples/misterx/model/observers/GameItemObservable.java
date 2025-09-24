package mp1401.examples.misterx.model.observers;

public interface GameItemObservable {
	
	public void addGameItemObserver(GameItemObserver observer);
	public void removeGameItemObserver(GameItemObserver observer);
	public void notifyGameItemObservers();

}
