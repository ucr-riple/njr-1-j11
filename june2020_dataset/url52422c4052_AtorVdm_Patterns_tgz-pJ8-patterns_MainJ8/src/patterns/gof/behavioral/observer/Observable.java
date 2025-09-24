package patterns.gof.behavioral.observer;

public interface Observable {
	void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}