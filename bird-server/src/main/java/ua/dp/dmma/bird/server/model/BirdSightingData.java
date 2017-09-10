package ua.dp.dmma.bird.server.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;

/**
 * 
 * @author dmma
 *
 */
public class BirdSightingData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6172047460667925791L;
	@JsonbProperty
	private String name;
	@JsonbProperty
	private String location;
	@JsonbProperty
	private String date;
	@JsonbProperty
	private String time;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
