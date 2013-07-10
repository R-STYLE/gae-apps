package net.rstyles.lab.apps.gae.brownout.servlet.filter.tepco.epsd;

import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import net.rstyles.lab.apps.gae.brownout.util.DatastoreUtils;
import net.rstyles.lab.apps.gae.brownout.util.MemcachedUtils;

import com.google.appengine.api.datastore.Key;

public abstract class AbstractRenderingFilter implements Filter {
	
	@Override
	public void destroy() {
		// do nothing.
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// do nothing.
	}
	
	protected <T> T getLatestData(Class<T> clazz) {
		final Calendar cal = Calendar.getInstance(DatastoreUtils.TIMEZONE);
		final Key key = DatastoreUtils.getKey(clazz, cal);
		final T data = MemcachedUtils.get(key);
		if (data != null) {return data;}
		cal.add(Calendar.DATE, -1);
		return MemcachedUtils.get(DatastoreUtils.getKey(clazz, cal));
	}

}
