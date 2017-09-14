package ua.dp.dmma.bird.server.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;

public class BirdSightingData implements Serializable
{
    private static final long serialVersionUID = 7435474605560847900L;

    @JsonbProperty
    private String name;
    @JsonbProperty
    private String location;
    @JsonbProperty
    private Long dateTime;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public Long getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(Long dateTime)
    {
        this.dateTime = dateTime;
    }
}
