package net.rstyles.lab.apps.gae.brownout.servlet.tepco.epsd.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rstyles.lab.apps.gae.brownout.dto.AvailabilityLine;
import net.rstyles.lab.apps.gae.brownout.dto.ProspectiveLine;
import net.rstyles.lab.apps.gae.brownout.dto.SupplyAndDemand;
import net.rstyles.lab.apps.gae.brownout.dto.TableDisplay;
import net.rstyles.lab.apps.gae.brownout.dto.TableDisplayCurrentOnly;
import net.rstyles.lab.apps.gae.brownout.dto.TepcoEpUseResultCsv;
import net.rstyles.lab.apps.gae.brownout.util.ConvertUtils;
import net.rstyles.lab.apps.gae.brownout.util.DatastoreUtils;
import net.rstyles.lab.apps.gae.brownout.util.MemcachedUtils;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class Analyzer extends AbstractDefaultTaskServlet {

	private static final Logger LOGGER = Logger.getLogger(Analyzer.class.getName());	
	
	@Override
	public void doRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
		final Calendar cal = Calendar.getInstance(DatastoreUtils.TIMEZONE);
		TepcoEpUseResultCsv csv = MemcachedUtils.get(DatastoreUtils.getKey(TepcoEpUseResultCsv.class, cal));
		if (csv == null) {
			LOGGER.log(Level.WARNING, "[CSV][Data not found] for " + cal.getTime());
			cal.add(Calendar.DATE, -1);
			csv = MemcachedUtils.get(DatastoreUtils.getKey(TepcoEpUseResultCsv.class, cal));
			if (csv == null) {
				LOGGER.log(Level.WARNING, "[CSV][Data not found] none.");
				return;
			}
		}
		final AvailabilityLine availability = this.getAvailability(csv);
		if (availability == null) {return;}
		final ProspectiveLine prospective = this.getProspective(csv);
		if (prospective == null) {return;}
		final List<SupplyAndDemand> results = this.getResults(csv, availability);
		try {
			final TableDisplay analyzed = this.getAnalyzed(TableDisplay.class, csv, availability, prospective);
			analyzed.setResults(results);
			Key key = DatastoreUtils.getKey(TableDisplay.class, cal);
			MemcachedUtils.put(key, analyzed);
			LOGGER.log(Level.INFO, "[Memcached Putted] key: " + key.getName() + "(" + key + ")");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "[ERROR] " + e.getMessage());
		}
		try {
			final TableDisplayCurrentOnly analyzed = this.getAnalyzed(TableDisplayCurrentOnly.class, csv, availability, prospective);
			final List<SupplyAndDemand> minResults = new ArrayList<SupplyAndDemand>();
			minResults.add(this.getCurrent(results));
			analyzed.setResults(minResults);
			Key key = DatastoreUtils.getKey(TableDisplayCurrentOnly.class, cal);
			MemcachedUtils.put(key, analyzed);
			LOGGER.log(Level.INFO, "[Memcached Putted] key: " + key.getName() + "(" + key + ")");
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "[ERROR] " + e.getMessage());
		}
	}
	
	protected AvailabilityLine getAvailability(TepcoEpUseResultCsv csv) {
		final List<String> lines = csv.getAvailabilityLines();
		if (lines == null || lines.isEmpty()) {return null;}
		lines.remove(0);
		final AvailabilityLine availability = new AvailabilityLine();
		for (String line : lines) {
			String[] columns = line.split(",");
			availability.setAvailability(Long.parseLong(columns[0]));
			availability.setCurrentTime(columns[1]);
			availability.setUpdatedDate(columns[2]);
			availability.setUpdatedTime(columns[3]);
		}
		return availability;
	}
	
	protected ProspectiveLine getProspective(TepcoEpUseResultCsv csv) {
		final List<String> lines = csv.getProspectiveLines();
		if (lines == null || lines.isEmpty()) {return null;}
		lines.remove(0);
		final ProspectiveLine prospective = new ProspectiveLine();
		for (String line : lines) {
			String[] columns = line.split(",");
			prospective.setAmount(Long.parseLong(columns[0]));
			prospective.setTime(columns[1]);
			prospective.setUpdatedDate(columns[2]);
			prospective.setUpdatedTime(columns[3]);
		}
		return prospective;
	}
	
	protected <T extends TableDisplay> T getAnalyzed(Class<T> clazz, TepcoEpUseResultCsv csv, AvailabilityLine availability, ProspectiveLine prospective) throws InstantiationException, IllegalAccessException {
		final T dto = clazz.newInstance();
		dto.setUpdated(csv.getHeading());
		dto.setAvailability(Long.toString(availability.getAvailability()));
		dto.setProspectiveAmount(Long.toString(prospective.getAmount()));
		dto.setProspectiveTime(prospective.getTime());
		return dto;
	}
	
	protected List<SupplyAndDemand> getResults(TepcoEpUseResultCsv csv, AvailabilityLine availability) {
		final List<String> lines = csv.getResultLines();
		if (lines == null || lines.isEmpty()) {return Collections.emptyList();}
		lines.remove(0);
		List<SupplyAndDemand> results = new ArrayList<SupplyAndDemand>();
		for (String line : lines) {
			String[] columns = line.split(",");
			final SupplyAndDemand result = new SupplyAndDemand();
			result.setAmount(Long.parseLong(columns[2]));
//			result.setYesterday(Long.parseLong(columns[3]));
			result.setPrediction(Long.parseLong(columns[3]));
			result.setPercentage(ConvertUtils.convertPercentBigDecimal(result.getAmount(), availability.getAvailability()));
//			result.setComparison(ConvertUtils.convertPercentBigDecimal(result.getAmount(), result.getYesterday()));
			result.setComparison(ConvertUtils.convertPercentBigDecimal(result.getAmount(), result.getPrediction()));
			result.setOverhundred(result.getComparison().doubleValue() >= 100.0);
			final String time = columns[1];
			result.setPeaktime(time.equals(availability.getCurrentTime()));
			result.setTime(time.substring(0, time.indexOf(":")));
			results.add(result);
		}
		return results;
	}
	
	protected SupplyAndDemand getCurrent(List<SupplyAndDemand> supplyAndDemands) {
		if (supplyAndDemands == null || supplyAndDemands.isEmpty()) {return null;}
		SupplyAndDemand current = null;
		for (SupplyAndDemand dto : supplyAndDemands) {
			if (dto.getPercentage().intValue() == 0) {break;}
			current = dto;
		}
		return current;
	}
}
