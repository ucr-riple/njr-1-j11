package com.sjl.health;


public interface HealthService {

	/**
     * @param aNotYetMonitored - the object to be instrumented for health monitoring
     * @param aConfiguration - the state configuration for this component
     * @return A polymorphically equivalent T which has been instrumented
     */
    public <T> T monitor(T aNotYetMonitored, Configuration aConfig);
    
    /**
     * @param aMaybeMonitored - the object whose health is being enquired about
     * @return the HealthInfo for that object, if it is monitored, null otherwise
     */
    public HealthInfo get(Object aMaybeMonitored);
    
    /**
     * @param aMaybeMonitored - the object whose health should be reset to the
     * original state
     */
    public void reset(Object aMaybeMonitored);
}
