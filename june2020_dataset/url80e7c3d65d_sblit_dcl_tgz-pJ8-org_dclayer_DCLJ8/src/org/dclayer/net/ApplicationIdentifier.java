package org.dclayer.net;

/**
 * an identifier used to distinct applications
 */
public class ApplicationIdentifier {
	/**
	 * the application identifier as String
	 */
	private String applicationIdentifier;
	
	/**
	 * creates a new {@link ApplicationIdentifier} using the given String
	 * @param applicationIdentifier the application identifier
	 */
	public ApplicationIdentifier(String applicationIdentifier) {
		this.applicationIdentifier = applicationIdentifier;
	}
	
	/**
	 * returns the application identifier String
	 * @return the application identifier String
	 */
	public String getApplicationIdentifier() {
		return applicationIdentifier;
	}
	
	@Override
	public String toString() {
		return getApplicationIdentifier();
	}
	
	/**
	 * compares this {@link ApplicationIdentifier} to another {@link ApplicationIdentifier}
	 * @param applicationIdentifier the {@link ApplicationIdentifier} to compare this {@link ApplicationIdentifier} to
	 * @return true if this {@link ApplicationIdentifier} equals the given {@link ApplicationIdentifier}
	 */
	public boolean equals(ApplicationIdentifier applicationIdentifier) {
		return this.applicationIdentifier.equals(applicationIdentifier.getApplicationIdentifier());
	}
}
