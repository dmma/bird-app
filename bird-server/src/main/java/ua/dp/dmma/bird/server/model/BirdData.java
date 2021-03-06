package ua.dp.dmma.bird.server.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;

public class BirdData implements Serializable, Comparable<BirdData>
{
    private static final long serialVersionUID = -8044206813456597310L;

    @JsonbProperty
    private String name;
    @JsonbProperty
    private String color;
    @JsonbProperty
    private Double weight;
    @JsonbProperty
    private Double height;

    public BirdData()
    {

    }

    public BirdData(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public Double getWeight()
    {
        return weight;
    }

    public void setWeight(Double weight)
    {
        this.weight = weight;
    }

    public Double getHeight()
    {
        return height;
    }

    public void setHeight(Double height)
    {
        this.height = height;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BirdData other = (BirdData) obj;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int compareTo(BirdData o)
    {
        return this.name.compareTo(o.getName());
    }

}
