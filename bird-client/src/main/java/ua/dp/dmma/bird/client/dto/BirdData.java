package ua.dp.dmma.bird.client.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BirdData
{
    private String name;
    private String color;
    private Double weight;
    private Double height;

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
    public String toString()
    {
        return "BirdData [name=" + name + ", color=" + color + ", weight=" + weight + ", height=" + height + "]";
    }

}
