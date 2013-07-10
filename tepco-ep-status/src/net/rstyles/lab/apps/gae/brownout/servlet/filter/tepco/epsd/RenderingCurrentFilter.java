package net.rstyles.lab.apps.gae.brownout.servlet.filter.tepco.epsd;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.rstyles.lab.apps.gae.brownout.dto.TableDisplayCurrentOnly;

public class RenderingCurrentFilter extends AbstractRenderingFilter {

	private static final Logger LOGGER = Logger.getLogger(RenderingCurrentFilter.class.getName());

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		try {
			LOGGER.log(Level.INFO, "[doFilter]: " + RenderingCurrentFilter.class.getSimpleName());
			this.setDisplayData(req);
		} catch (Throwable e) {
			LOGGER.log(Level.WARNING, "[ERROR][MAIN CONTENTS]: " + e.getMessage());
		}
		chain.doFilter(req, res);
	}
	
	protected void setDisplayData(ServletRequest req) {
		final TableDisplayCurrentOnly displayDto = this.getLatestData(TableDisplayCurrentOnly.class);
		if (displayDto == null) {return;}
		req.setAttribute("availability", displayDto.getAvailability());
		req.setAttribute("prospectiveAmount", displayDto.getProspectiveAmount());
		req.setAttribute("prospectiveTime", displayDto.getProspectiveTime());
		req.setAttribute("updated", displayDto.getUpdated());
		req.setAttribute("results", displayDto.getResults());
	}

}
