package ua.dp.dmma.bird.client.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BirdSightingData {
	private String name;
	private String location;
	private String date;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
