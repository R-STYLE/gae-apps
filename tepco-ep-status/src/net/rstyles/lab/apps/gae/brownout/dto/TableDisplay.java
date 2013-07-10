package net.rstyles.lab.apps.gae.brownout.dto;

import java.io.Serializable;
import java.util.List;

public class TableDisplay implements Serializable {

	private static final long serialVersionUID = 3220468777610772278L;

	private String updated;
	
	private String availability;
	
	private String prospectiveAmount;
	
	private String prospectiveTime;

	private List<SupplyAndDemand> results;

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getProspectiveAmount() {
		return prospectiveAmount;
	}

	public void setProspectiveAmount(String prospectiveAmount) {
		this.prospectiveAmount = prospectiveAmount;
	}

	public String getProspectiveTime() {
		return prospectiveTime;
	}

	public void setProspectiveTime(String prospectiveTime) {
		this.prospectiveTime = prospectiveTime;
	}

	public List<SupplyAndDemand> getResults() {
		return results;
	}

	public void setResults(List<SupplyAndDemand> results) {
		this.results = results;
	}
	
}
