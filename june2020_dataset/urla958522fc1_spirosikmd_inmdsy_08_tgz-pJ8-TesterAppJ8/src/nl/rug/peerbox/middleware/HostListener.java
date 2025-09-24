package nl.rug.peerbox.middleware;

public interface HostListener {
	
	void detected(int hostID);
	void removed(int hostID);

}
