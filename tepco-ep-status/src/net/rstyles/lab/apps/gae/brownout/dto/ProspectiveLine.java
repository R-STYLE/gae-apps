package net.rstyles.lab.apps.gae.brownout.dto;

import java.io.Serializable;

public class ProspectiveLine implements Serializable {

	private static final long serialVersionUID = -4663980462094396870L;

	private Long amount;

	private String time;

	private String updatedDate;

	private String updatedTime;
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	@Override
	public String toString() {
		return "Prospective: " + this.amount;
	}
	
}