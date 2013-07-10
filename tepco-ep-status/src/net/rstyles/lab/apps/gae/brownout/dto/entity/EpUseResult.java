package net.rstyles.lab.apps.gae.brownout.dto.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class EpUseResult implements Entity {
	
	private static final long serialVersionUID = 5968676244583877765L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private Status parent;
	
	@Persistent
	private String date;
	
	@Persistent
	private String time;
	
	@Persistent
	private long today;
	
	@Persistent
	private long yesterday;

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
	public long getToday() {
		return today;
	}
	public void setToday(long today) {
		this.today = today;
	}
	public long getYesterday() {
		return yesterday;
	}
	public void setYesterday(long yesterday) {
		this.yesterday = yesterday;
	}
	
	@Override
	public String toString() {
		return "Result: " + this.time + ", " + this.today;
	}

}