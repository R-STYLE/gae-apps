package net.rstyles.lab.apps.gae.brownout.dto.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Availability implements Entity {

	private static final long serialVersionUID = 8931705651388101669L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent(mappedBy = "availability")
	private Status parent;
	
	@Persistent
	private Long availability;

	@Persistent
	private String updatedDate;
	
	@Persistent
	private String updatedTime;
	
	@Persistent
	private String currentTime;

	public Key getId() {
		return id;
	}
	public void setId(Key id) {
		this.id = id;
	}
	public void setParent(Status parent) {
		this.parent = parent;
	}
	public Status getParent() {
		return parent;
	}
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