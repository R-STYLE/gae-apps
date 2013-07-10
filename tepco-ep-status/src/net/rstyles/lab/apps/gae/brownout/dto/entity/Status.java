package net.rstyles.lab.apps.gae.brownout.dto.entity;

import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Status implements Entity {
	
	private static final long serialVersionUID = 1140081559761600396L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String updateDate;
	
	@Persistent
	private String updateTime;
	
	@Persistent(serialized = "true", defaultFetchGroup = "true")
	private Availability availability;
	
	@Persistent(mappedBy = "parent", serialized = "true", defaultFetchGroup = "true")
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="time asc"))
	private List<EpUseResult> results;

	public Key getId() {
		return id;
	}
	public void setId(Key id) {
		this.id = id;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Availability getAvailability() {
		return availability;
	}
	public void setAvailability(Availability availability) {
		this.availability = availability;
	}
	public void setResults(List<EpUseResult> results) {
		this.results = results;
	}
	public List<EpUseResult> getResults() {
		return results;
	}

}