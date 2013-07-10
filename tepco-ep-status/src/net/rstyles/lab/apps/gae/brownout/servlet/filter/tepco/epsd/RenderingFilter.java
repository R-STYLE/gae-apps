package net.rstyles.lab.apps.gae.brownout.servlet.filter.tepco.epsd;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.rstyles.lab.apps.gae.brownout.dto.TableDisplay;
import twitter4j.Status;

public class RenderingFilter extends AbstractRenderingFilter {

	private static final Logger LOGGER = Logger.getLogger(RenderingFilter.class.getName());

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		try {
			LOGGER.log(Level.INFO, "[doFilter]: " + RenderingFilter.class.getSimpleName());
			this.setDisplayData(req);
		} catch (Throwable e) {
			LOGGER.log(Level.WARNING, "[ERROR][MAIN CONTENTS]: " + e.getMessage());
		}
		try {
			this.setTwitterStatus(req);
		} catch (Throwable e) {
			LOGGER.log(Level.WARNING, "[ERROR][TWITTER AREA]: " + e.getMessage());
		}
		chain.doFilter(req, res);
	}
	
	protected void setDisplayData(ServletRequest req) {
		final TableDisplay displayDto = this.getLatestData(TableDisplay.class);
		if (displayDto == null) {return;}
		req.setAttribute("availability", displayDto.getAvailability());
		req.setAttribute("prospectiveAmount", displayDto.getProspectiveAmount());
		req.setAttribute("prospectiveTime", displayDto.getProspectiveTime());
		req.setAttribute("updated", displayDto.getUpdated());
		req.setAttribute("results", displayDto.getResults());
	}

	protected void setTwitterStatus(ServletRequest req) {
		final Status latest = this.getLatestData(Status.class);
		if (latest == null) {return;}
		req.setAttribute("twitterText", latest.getText());
		req.setAttribute("twitterUser", latest.getUser().getScreenName());
		req.setAttribute("twitterParmanentId", latest.getId());
	}

}
