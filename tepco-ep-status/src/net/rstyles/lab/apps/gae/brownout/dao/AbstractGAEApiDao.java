package net.rstyles.lab.apps.gae.brownout.dao;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


public abstract class AbstractGAEApiDao implements GenericDao<Entity, Key> {

	private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	@Override
	public void delete(Entity entity) {
		datastore.delete(entity.getKey());
	}

	@Override
	public Entity find(Key id) {
		try {
			return datastore.get(id);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	@Override
	public List<Entity> findAll() {
		Query query = new Query(this.getKind());
		PreparedQuery prepared = datastore.prepare(query);
		return prepared.asList(FetchOptions.Builder.withDefaults());
	}

	@Override
	public void store(Entity entity) {
		datastore.put(entity);
	}

	@Override
	public void update(Entity entity) {
		datastore.put(entity);
	}
	
	protected abstract String getKind();

}
