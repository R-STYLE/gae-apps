package net.rstyles.lab.apps.gae.brownout.dto.entity;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

public interface Entity extends Serializable {

	public Key getId();
	
}
