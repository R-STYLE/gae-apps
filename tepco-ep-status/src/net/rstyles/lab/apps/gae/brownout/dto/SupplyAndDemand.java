package net.rstyles.lab.apps.gae.brownout.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplyAndDemand implements Serializable {

	private static final long serialVersionUID = 3510698093668591829L;

	private boolean peaktime;
	
	private boolean overhundred;
	
	private String time;
	
	private long amount;
	
	private long prediction;
	
	private long yesterday;
	
	private BigDecimal percentage;
	
	private BigDecimal comparison;

	public boolean isPeaktime() {
		return peaktime;
	}

	public void setPeaktime(boolean peaktime) {
		this.peaktime = peaktime;
	}

	public boolean isOverhundred() {
		return overhundred;
	}

	public void setOverhundred(boolean overhundred) {
		this.overhundred = overhundred;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getPrediction() {
		return prediction;
	}

	public void setPrediction(long prediction) {
		this.prediction = prediction;
	}

	public long getYesterday() {
		return yesterday;
	}

	public void setYesterday(long yesterday) {
		this.yesterday = yesterday;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public BigDecimal getComparison() {
		return comparison;
	}

	public void setComparison(BigDecimal comparison) {
		this.comparison = comparison;
	}

}
