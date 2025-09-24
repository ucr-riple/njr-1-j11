package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2013-04-22
 * @modified 2013-05-08
 * @version 1.0.1
 **/

public class HTTPDRuntimeStatistics {

    private long systemStartedTime;

    //private int reportedErrorCount;

    private int reportedSevereCount;
    private int reportedWarningCount;
    private int reportedInfoCount;
    private int reportedConfigCount;
    private int reportedFineCount;
    private int reportedFinerCount;
    private int reportedFinestCount;
    
    private int httpRequestCount;

    public HTTPDRuntimeStatistics( long systemStartedTime ) {
	super();
	
	this.systemStartedTime = systemStartedTime;
    }


    public long getSystemStartedTime() {
	return this.systemStartedTime;
    }

    public long getUptime_ms() {
	return System.currentTimeMillis() - this.systemStartedTime;
    }

    /**
     * Increases the internal SEVERE counter by one.
     **/
    public void reportSevere() {
	this.reportedSevereCount++;
    }

    /**
     * Get the number of reported SEVEREs.
     **/
    public int getReportedSevereCount() {
	return this.reportedSevereCount;
    }


    /**
     * Increases the internal WARNING counter by one.
     **/
    public void reportWarning() {
	this.reportedWarningCount++;
    }

    /**
     * Get the number of reported WARNINGs.
     **/
    public int getReportedWarningCount() {
	return this.reportedWarningCount;
    }


    /**
     * Increases the internal INFO counter by one.
     **/
    public void reportInfo() {
	this.reportedInfoCount++;
    }

    /**
     * Get the number of reported INFOs.
     **/
    public int getReportedInfoCount() {
	return this.reportedInfoCount;
    }


    /**
     * Increases the internal CONFIG counter by one.
     **/
    public void reportConfig() {
	this.reportedConfigCount++;
    }

    /**
     * Get the number of reported CONFIGs.
     **/
    public int getReportedConfigCount() {
	return this.reportedConfigCount;
    }


    /**
     * Increases the internal FINE counter by one.
     **/
    public void reportFine() {
	this.reportedFineCount++;
    }

    /**
     * Get the number of reported FINEs.
     **/
    public int getReportedFineCount() {
	return this.reportedFineCount;
    }



    /**
     * Increases the internal FINER counter by one.
     **/
    public void reportFiner() {
	this.reportedFinerCount++;
    }

    /**
     * Get the number of reported FINERs.
     **/
    public int getReportedFinerCount() {
	return this.reportedFinerCount;
    }


    /**
     * Increases the internal FINEST counter by one.
     **/
    public void reportFinest() {
	this.reportedFinestCount++;
    }

    /**
     * Get the number of reported FINESTs.
     **/
    public int getReportedFinestCount() {
	return this.reportedFinestCount;
    }


    
    /**
     * Increases the internal httpRequestCount counter by one.
     **/
    public void reportHTTPRequest() {
	this.httpRequestCount++;
    }

    /**
     * Get the number of reported HTTP requests.
     **/
    public int getHTTPRequestCount() {
	return this.httpRequestCount;
    }
    
    

    /*public int getReportedErrorCount() {
	return -1;
    }
    */


}