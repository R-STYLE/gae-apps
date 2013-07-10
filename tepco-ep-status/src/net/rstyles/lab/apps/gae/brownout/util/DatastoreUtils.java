package net.rstyles.lab.apps.gae.brownout.util;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.time.FastDateFormat;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DatastoreUtils {
	
	public static final TimeZone TIMEZONE = TimeZone.getTimeZone("JST");
	
	public static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd", TIMEZONE, Locale.JAPAN);

	public static final Key getKey(Class<?> clazz, Calendar cal) {
		return KeyFactory.createKey(clazz.getSimpleName(), DATE_FORMAT.format(cal));
	}
	
}
