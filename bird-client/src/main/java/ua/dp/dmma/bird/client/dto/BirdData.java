package ua.dp.dmma.bird.client.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BirdData {
	private String name;
	private String color;
	private String weight;
	private String height;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "BirdData [name=" + name + ", color=" + color + ", weight=" + weight + ", height=" + height + "]";
	}

	
	
}
