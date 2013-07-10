package net.rstyles.lab.apps.gae.brownout.servlet.tepco.epsd;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.tools.view.VelocityLayoutServlet;

@SuppressWarnings("serial")
public class VelocityRender extends VelocityLayoutServlet {

	private static final Logger LOGGER = Logger.getLogger(VelocityRender.class.getName());

	private static final String ATTR_CALLBACK = "callback";
	
	enum ContentType {
		HTML(".html", "text/html; charset=utf-8"),
		XML(".xml", "text/xml; charset=utf-8"),
		JSON(".json", "application/json; charset=utf-8"),
		JSONP(".jsonp", "text/javascript; charset=utf-8");
		private String ext;
		private String type;
		private ContentType(String ext, String type) {
			this.ext = ext;
			this.type = type;
		}
		public String getExt() {return ext;}
		public void setExts(String ext) {this.ext = ext;}
		public String getType() {return type;}
		public void setType(String type) {this.type = type;}
	}

	@Override
	protected void doRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
		this.setContentType(req, res);
		LOGGER.log(Level.INFO, getServletConfig().getInitParameter("org.apache.velocity.properties"));
		super.doRequest(req, res);
	}
	
	protected void setContentType(HttpServletRequest req, HttpServletResponse res) {
		super.setContentType(req, res);
		final String uri = req.getRequestURI();
		for (ContentType type : ContentType.values()) {
			if (!StringUtils.endsWith(uri, type.getExt())) {continue;}
			if (StringUtils.endsWith(uri, ContentType.JSON.getExt())) {
				if (StringUtils.isNotEmpty(req.getParameter(ATTR_CALLBACK))) {
					req.setAttribute(ATTR_CALLBACK, req.getParameter(ATTR_CALLBACK));
					res.setContentType(ContentType.JSONP.getType());
					return;
				}
			}
			res.setContentType(type.getType());
		}
	}

	@Override
	protected String findLayout(HttpServletRequest req) {
		final String uri = req.getRequestURI();
		for (ContentType type : ContentType.values()) {
			if (!StringUtils.endsWith(uri, type.getExt())) {continue;}
			switch (type) {
			case HTML: req.setAttribute(KEY_LAYOUT, "html.vm"); break;
			case XML: req.setAttribute(KEY_LAYOUT, "xml.vm"); break;
			case JSON: req.setAttribute(KEY_LAYOUT, "json.vm"); break;
			}
		}
		return super.findLayout(req);
	}

}
