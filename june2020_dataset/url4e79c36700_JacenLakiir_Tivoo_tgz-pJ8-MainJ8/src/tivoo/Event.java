package tivoo;

import java.util.Calendar;
import java.util.HashMap;


public class Event extends HashMap<String, String>
{
    private String title;
    private String description;
    private Calendar startTime;
    private Calendar endTime;
    private String location;


    public String getTitle ()
    {
        return title;
    }


    public String getDescription ()
    {
        return description;
    }


    public Calendar getStartTime ()
    {
        return startTime;
    }


    public Calendar getEndTime ()
    {
        return endTime;
    }


    public String getLocation ()
    {
        return location;
    }


    public void setTitle (String title)
    {
        this.title = title;
    }


    public void setDescription (String description)
    {
        this.description = description;
    }


    public void setStartTime (Calendar startTime)
    {
        this.startTime = startTime;
    }


    public void setEndTime (Calendar endTime)
    {
        this.endTime = endTime;
    }


    public void setLocation (String location)
    {
        this.location = location;
    }
}
