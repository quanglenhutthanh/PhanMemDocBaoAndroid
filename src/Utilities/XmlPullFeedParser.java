package Utilities;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import Entities.RSSItem;
import Entities.Website;
import android.util.Log;

public class XmlPullFeedParser{
	String Xml;
	Website website;

	String url;
	public XmlPullFeedParser(String xml,Website website) {
		//super(feedUrl);
		this.Xml = xml;
		this.website = website;
		
	}
	String CHANNEL = "channel";
	static final String PUB_DATE = "pubDate";
	static final  String DESCRIPTION = "description";
	static final  String LINK = "link";
	static final  String TITLE = "title";
	static final  String ITEM = "item";
	public List<RSSItem> parse() {
		List<RSSItem> messages = new ArrayList<RSSItem>();
		
		try {
			// auto-detect the encoding from the stream
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser parser = factory.newPullParser();
	        InputStream is = new ByteArrayInputStream(Xml.getBytes());
	        if(is!=null)
	        {
	        	StringEscapeUtils.unescapeHtml(Xml);
				parser.setInput(is,null);
				int eventType = parser.getEventType();
				RSSItem currentItem = null;
				boolean done = false;
				int count = 0;
				
				while (eventType != XmlPullParser.END_DOCUMENT && !done && count<=25){
					String name = null;
					switch (eventType){
						case XmlPullParser.START_DOCUMENT:
							messages = new ArrayList<RSSItem>();
							break;
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if (name.equalsIgnoreCase(ITEM)){
								currentItem = new RSSItem();
								currentItem.setWebsite(website);
							} else if (currentItem != null){
								if (name.equalsIgnoreCase(DESCRIPTION)){
									String desc = parser.nextText();
									currentItem.setDescription(desc);
									
									Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
					                Matcher m = p.matcher(desc);
					                String image= "null";
					                if(m.find()){
					                	image= m.group(1);
					                }
					                currentItem.setImage(image);
					            } else if (name.equalsIgnoreCase(PUB_DATE)){
									String date  = parser.nextText().replaceAll("GMT", "\\+0700");
									date = date.replaceAll("\\+0000", "\\+0700");
									currentItem.setPubdate(date);
								} else if (name.equalsIgnoreCase(TITLE)){
									currentItem.setTitle(parser.nextText());
									count++;
								}	else if (name.equalsIgnoreCase(LINK)){
									String url = parser.nextText();
									currentItem.setGuid(url);
									if(currentItem.getLink() == null){
										currentItem.setLink(url);
									}
								} else if (name.equalsIgnoreCase("image")){
									currentItem.setImage(parser.nextText());
								}else if (name.equalsIgnoreCase("thumb")){
									currentItem.setImage(parser.nextText());
								}
							}
							break;
						case XmlPullParser.END_TAG:
							name = parser.getName();
							if (name.equalsIgnoreCase(ITEM) && currentItem != null){
								messages.add(currentItem);
							} else if (name.equalsIgnoreCase(CHANNEL)){
								done = true;
							}
							break;
					}
					eventType = parser.next();
				}
	        }
	        
		} catch (Exception e) {
			Log.e("AndroidNews::PullFeedParser", e.getMessage(), e);
			//throw new RuntimeException(e);
		}
		return messages;
	}
}
