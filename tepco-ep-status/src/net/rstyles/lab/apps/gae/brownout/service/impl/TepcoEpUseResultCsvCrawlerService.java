package net.rstyles.lab.apps.gae.brownout.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rstyles.lab.apps.gae.brownout.dto.TepcoEpUseResultCsv;
import net.rstyles.lab.apps.gae.brownout.service.AbstractWebCrawlerService;
import net.rstyles.lab.apps.gae.brownout.util.DatastoreUtils;
import net.rstyles.lab.apps.gae.brownout.util.MemcachedUtils;

import com.google.appengine.api.datastore.Key;

public class TepcoEpUseResultCsvCrawlerService extends AbstractWebCrawlerService {

	private static final Logger LOGGER = Logger.getLogger(TepcoEpUseResultCsvCrawlerService.class.getName());
	
	private static final String CSV_FILEPATH = "http://www.tepco.co.jp/forecast/html/images/juyo-j.csv";
	
	@Override
	protected void doFailed(Exception e) {
		LOGGER.log(Level.WARNING, e.getMessage());
	}

	@Override
	protected void doSuccess(InputStream in) throws IOException {
		BufferedReader reader = null;
		Key key = DatastoreUtils.getKey(TepcoEpUseResultCsv.class, Calendar.getInstance(DatastoreUtils.TIMEZONE));
		TepcoEpUseResultCsv stored = MemcachedUtils.get(key);
		final TepcoEpUseResultCsv csv = new TepcoEpUseResultCsv();
		try {
			reader = new BufferedReader(new InputStreamReader(in, "MS932"));
			String line = reader.readLine();
			if (line.indexOf("UPDATE") > 0) {
				String[] updates = line.split(" ");
				csv.setHeading(updates[0] + " " + updates[1]);
			} else {
				LOGGER.log(Level.WARNING, "[Memcached][UPDATE]: invalid format.");
				return;
			}
			if (stored != null && csv.getHeading().equals(stored.getHeading())) {
				LOGGER.log(Level.INFO, "[Memcached][CURRENT]: " + csv.getHeading());
				return;
			}
			final List<String> head = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				if ("".equals(line)) {break;}
				head.add(line);
			}
			csv.setAvailabilityLines(head);
			final List<String> prospective = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				if ("".equals(line)) {break;}
				prospective.add(line);
			}
			csv.setProspectiveLines(prospective);
			final List<String> rest = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				if ("".equals(line)) {break;}
				rest.add(line);
			}
			csv.setResultLines(rest);
		} finally {
			closeQuietly(reader);
		}
		MemcachedUtils.put(key, csv);
		LOGGER.log(Level.INFO, "[Memcached][PUTDATA]: " + csv);
	}

	@Override
	protected URL getResourceUrl() throws MalformedURLException {
		return new URL(CSV_FILEPATH);
	}

}
