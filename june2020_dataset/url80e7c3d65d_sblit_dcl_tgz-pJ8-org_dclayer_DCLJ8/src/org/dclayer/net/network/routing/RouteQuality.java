package org.dclayer.net.network.routing;

public class RouteQuality {

	public final boolean critical;
	public final double quality;
	
	public RouteQuality(boolean critical, double quality) {
		this.critical = critical;
		this.quality = quality;
	}
	
	@Override
	public String toString() {
		return String.format("%s%.8f", critical ? "critical/" : "", quality);
	}
	
}
