package net.rstyles.lab.apps.gae.brownout.servlet.tepco.epsd.task;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

@SuppressWarnings("serial")
public abstract class AbstractDefaultTaskServlet extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(AbstractDefaultTaskServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			this.doRequest(req, res);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "[ERROR] " + e.getClass().getCanonicalName());
			LOGGER.log(Level.WARNING, "[ERROR] " + e.getMessage());
		} finally {
			final Queue queue = QueueFactory.getDefaultQueue();
			queue.purge();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			this.doRequest(req, res);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "[ERROR] " + e.getClass().getCanonicalName());
			LOGGER.log(Level.WARNING, "[ERROR] " + e.getMessage());
		} finally {
			final Queue queue = QueueFactory.getDefaultQueue();
			queue.purge();
		}
	}
	
	public abstract void doRequest(HttpServletRequest req, HttpServletResponse res) throws IOException;
	
}
