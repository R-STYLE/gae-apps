package net.rstyles.lab.apps.gae.brownout.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.*;

import net.rstyles.lab.apps.gae.brownout.dto.SupplyAndDemand;
import net.rstyles.lab.apps.gae.brownout.dto.entity.Availability;
import net.rstyles.lab.apps.gae.brownout.dto.entity.EpUseResult;
import net.rstyles.lab.apps.gae.brownout.dto.entity.Status;

public class ConvertUtils {

	private ConvertUtils() {}
	
	public static final BigDecimal convertPercentBigDecimal(long val1, long val2) {
		if (val2 == 0) return new BigDecimal(0);
		return new BigDecimal((double) val1 / (double) val2 * 100.0).setScale(1, BigDecimal.ROUND_HALF_UP);
	}
	
	public static final List<SupplyAndDemand> convertForDisplay(Status status) {
		final Availability avail = status.getAvailability();
		final List<SupplyAndDemand> sd = new ArrayList<SupplyAndDemand>();
		for (EpUseResult result : status.getResults()) {
			sd.add(convertForDisplay(avail, result));
		}
		
		isEmpty("");
		
		return sd;
	}
	
	public static final SupplyAndDemand convertForDisplay(Availability avail, EpUseResult result) {
		final SupplyAndDemand ret = new SupplyAndDemand();
		ret.setAmount(result.getToday());
		ret.setYesterday(result.getYesterday());
		ret.setPercentage(convertPercentBigDecimal(result.getToday(), avail.getAvailability()));
		ret.setComparison(convertPercentBigDecimal(result.getToday(), result.getYesterday()));
		ret.setOverhundred(ret.getComparison().doubleValue() > 100.0);
		final String time = result.getTime();
		ret.setPeaktime(time.equals(avail.getCurrentTime()));
		ret.setTime(time.substring(0, time.indexOf(":")));
		return ret;
	}
}
