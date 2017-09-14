package ua.dp.dmma.bird.client.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BirdSightingData {
	private String name;
	private String location;
	private Long dateTime;

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

	public Long getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(Long dateTime)
	{
		this.dateTime = dateTime;
	}
}
