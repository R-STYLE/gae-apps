package net.rstyles.lab.apps.gae.brownout.servlet.tepco.epsd;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ForwardRender extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(ForwardRender.class.getName());
	
	public void doRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			final String pageName = req.getRequestURI() + ".html";
			LOGGER.log(Level.INFO, "[FOWARD TO: " + pageName + "]");
			getServletContext().getRequestDispatcher(pageName).forward(req,res);
		} catch (ServletException e) {
			LOGGER.log(Level.WARNING, "[SERVLET ERROR]: " + e.getMessage());
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		this.doRequest(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		this.doRequest(req, res);
	}

}
