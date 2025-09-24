/*
 * AdjustmentVO.java
 *
 * Created on October 6, 2005, 9:58 AM
 */

package com.usps.evs.vo;

/**
 *
 * @author qy71q0
 */
public class AdjustmentInfo
{
    private String corpDuns;
    private String month;
    private String year;
    private int type;
    private int status;
    private int systemType;
    
	//TBP flag
	private String tpbDuns;
    
    /** Creates a new instance of AdjustmentVO */
    public AdjustmentInfo()
    {
    }

    public AdjustmentInfo(String corpDuns, String month, String year,
        int type, int systemType, int status)
    {
        this.corpDuns = corpDuns;
        this.month = month;
        this.year = year;
        this.type = type;
        this.systemType = systemType;
        this.status = status;
    }

    public String getCorpDuns()
    {
        return corpDuns;
    }

    public void setCorpDuns(String corpDuns)
    {
        this.corpDuns = corpDuns;
    }

    public String getMonth()
    {
        return month;
    }

    public void setMonth(String month)
    {
        this.month = month;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getSystemType()
    {
        return systemType;
    }

    public void setSystemType(int systemType)
    {
        this.systemType = systemType;
    }

	public String getTpbDuns() {
		return tpbDuns;
	}

	public void setTpbDuns(String tpbDuns) {
		this.tpbDuns = tpbDuns;
	}
    
}
