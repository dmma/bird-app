package ua.dp.dmma.bird.server.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbProperty;

/**
 * @author dmma
 */
public class BirdData implements Serializable, Comparable<BirdData> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2996648682884920749L;

	@JsonbProperty
	private String name;
	@JsonbProperty
	private String color;
	@JsonbProperty
	private BigDecimal weight;
	@JsonbProperty
	private BigDecimal height;

	public BirdData() {
		
	}
	
	public BirdData(String name) {
		this.name = name;
	}

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

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BirdData other = (BirdData) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(BirdData o) {
		return this.name.compareTo(o.getName());
	}

}
