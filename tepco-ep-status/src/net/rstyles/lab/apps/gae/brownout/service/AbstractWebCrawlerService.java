package net.rstyles.lab.apps.gae.brownout.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractWebCrawlerService {

	public void execute() {
		InputStream in = null;
		try {
			in = getResourceUrl().openStream();
			doSuccess(in);
		} catch (IOException e) {
			doFailed(e);
		} finally {
			closeQuietly(in);
		}
	}
	
	protected abstract void doSuccess(InputStream in) throws IOException;
	
	protected abstract void doFailed(Exception e);
	
	protected abstract URL getResourceUrl() throws MalformedURLException;
	
	protected void closeQuietly(InputStream in) {
		if (in == null) {return;}
		try {
			in.close();
		} catch (IOException e) {
			// do nothing.
		}
	}
	
	protected void closeQuietly(Reader reader) {
		if (reader == null) {return;}
		try {
			reader.close();
		} catch (IOException e) {
			// do nothing.
		}
	}
	
}
