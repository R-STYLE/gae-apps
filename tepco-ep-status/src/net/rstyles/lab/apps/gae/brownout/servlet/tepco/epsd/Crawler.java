package net.rstyles.lab.apps.gae.brownout.servlet.tepco.epsd;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rstyles.lab.apps.gae.brownout.service.impl.TepcoEpUseResultCsvCrawlerService;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@SuppressWarnings("serial")
public class Crawler extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		new TepcoEpUseResultCsvCrawlerService().execute();
		final Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/tepco/task/sd-analyzer").method(Method.GET));
	}
}
