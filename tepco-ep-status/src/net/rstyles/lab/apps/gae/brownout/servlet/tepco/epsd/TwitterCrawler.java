package net.rstyles.lab.apps.gae.brownout.servlet.tepco.epsd;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rstyles.lab.apps.gae.brownout.service.impl.TepcoTwitterCrawlerService;

@SuppressWarnings("serial")
public class TwitterCrawler extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		new TepcoTwitterCrawlerService().execute();
	}
}
