package net.rstyles.lab.apps.gae.brownout.dto;

import java.io.Serializable;

public class AvailabilityLine implements Serializable {

	private static final long serialVersionUID = -4663980462094396870L;

	private Long availability;

	private String updatedDate;
	
	private String updatedTime;
	
	private String currentTime;

	public Long getAvailability() {
		return availability;
	}
	public void setAvailability(Long availability) {
		this.availability = availability;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	
	@Override
	public String toString() {
		return "Avairability: " + this.availability;
	}
	
}