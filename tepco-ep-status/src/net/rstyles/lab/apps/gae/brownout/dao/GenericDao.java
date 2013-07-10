package net.rstyles.lab.apps.gae.brownout.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<ENTITY, PK extends Serializable> {
	
	public ENTITY find(PK id);
	
	public List<ENTITY> findAll();
	
	public void store(ENTITY entity);
	
	public void update(ENTITY entity);

	public void delete(ENTITY entity);

}
