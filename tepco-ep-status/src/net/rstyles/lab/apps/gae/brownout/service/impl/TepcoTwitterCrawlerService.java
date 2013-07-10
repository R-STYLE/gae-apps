package net.rstyles.lab.apps.gae.brownout.service.impl;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rstyles.lab.apps.gae.brownout.service.AbstractTwitterCrawlerService;
import net.rstyles.lab.apps.gae.brownout.util.DatastoreUtils;
import net.rstyles.lab.apps.gae.brownout.util.MemcachedUtils;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;

import com.google.appengine.api.datastore.Key;

public class TepcoTwitterCrawlerService extends AbstractTwitterCrawlerService {

	private static final Logger LOGGER = Logger.getLogger(TepcoTwitterCrawlerService.class.getName());
	
	private static final String TEPCO_TWITTER = "OfficialTEPCO";

	@Override
	protected void doFailed(TwitterException e) {
		LOGGER.log(Level.WARNING, e.getMessage());
	}

	@Override
	protected void doSuccess(ResponseList<Status> responses) {
		Status latest = this.getLatestStatus(responses);
		if (latest == null) {return;}
		Calendar cal = Calendar.getInstance(DatastoreUtils.TIMEZONE);
		Key key = DatastoreUtils.getKey(Status.class, cal);
		Status stored = MemcachedUtils.get(key);
		if (stored == null || stored.getCreatedAt().before(latest.getCreatedAt())) {
			MemcachedUtils.put(key, latest);
			LOGGER.log(Level.INFO, "[Memcached][PUTDATA][Twitter] " + latest.getSource());
		}
	}
	
	private Status getLatestStatus(ResponseList<Status> responses) {
		if (responses.isEmpty()) {return null;}
		for (Status status : responses) {
			if (status.getText().indexOf("■■計画停電■■") < 0) {continue;}
			return status;
		}
		return null;
	}

	@Override
	protected String getTargetScreenName() {
		return TEPCO_TWITTER;
	}


}
