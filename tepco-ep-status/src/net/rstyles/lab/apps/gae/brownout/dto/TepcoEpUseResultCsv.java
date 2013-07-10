package net.rstyles.lab.apps.gae.brownout.dto;

import java.io.Serializable;
import java.util.List;

public class TepcoEpUseResultCsv implements Serializable {

	private static final long serialVersionUID = 3668066865021712799L;

	private String heading;
	
	private List<String> availabilityLines;
	
	private List<String> prospectiveLines;

	private List<String> resultLines;

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public List<String> getAvailabilityLines() {
		return availabilityLines;
	}

	public void setAvailabilityLines(List<String> availabilityLines) {
		this.availabilityLines = availabilityLines;
	}

	public void setProspectiveLines(List<String> prospectiveLines) {
		this.prospectiveLines = prospectiveLines;
	}

	public List<String> getProspectiveLines() {
		return prospectiveLines;
	}

	public List<String> getResultLines() {
		return resultLines;
	}

	public void setResultLines(List<String> resultLines) {
		this.resultLines = resultLines;
	}
	
}
