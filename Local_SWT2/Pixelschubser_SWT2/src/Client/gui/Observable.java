package Client.gui;


public interface Observable {
	public void registerObserver(Observer observer);
	public void notifyObservers();
	public void removeObserver();
}
