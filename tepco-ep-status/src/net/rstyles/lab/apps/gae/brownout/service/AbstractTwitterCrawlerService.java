package net.rstyles.lab.apps.gae.brownout.service;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public abstract class AbstractTwitterCrawlerService {

	public void execute() {
		final Twitter twitter = new TwitterFactory().getInstance();
		try {
			this.doSuccess(twitter.getUserTimeline(this.getTargetScreenName()));
		} catch (TwitterException e) {
			this.doFailed(e);
		}
	}

	protected abstract void doSuccess(ResponseList<Status> responseList);
	
	protected abstract void doFailed(TwitterException e);

	protected abstract String getTargetScreenName();

}
