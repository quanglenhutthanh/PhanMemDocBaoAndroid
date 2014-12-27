package Utilities;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import android.util.Log;

public abstract class BaseFeedParser implements FeedParser {

	// names of the XML tags
	static final String CHANNEL = "channel";
	static final String PUB_DATE = "pubDate";
	static final  String DESCRIPTION = "description";
	static final  String LINK = "link";
	static final  String TITLE = "title";
	static final  String ITEM = "item";
	
	private final URL feedUrl;

	protected BaseFeedParser(String feedUrl){
		try {
			this.feedUrl = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	protected InputStream getInputStream() {
		try {
			//URLConnection con = feedUrl.openConnection();
			//InputStream in = new InputStreamReader(con.getInputStream());
			//String inputStreamString = new Scanner(input,"UTF-8").useDelimiter("\\A").next();
			//Log.d("xml parse",inputStreamString);
			//return input;
			return feedUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}