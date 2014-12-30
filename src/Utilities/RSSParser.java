package Utilities;
import org.apache.commons.lang.StringEscapeUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import Entities.RSSItem;
import android.app.ListFragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Node;




public class RSSParser {
	private static String TAG_CHANNEL = "channel";
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_DESRIPTION = "description";
    private static String TAG_LANGUAGE = "language";
    private static String TAG_ITEM = "item";
    private static String TAG_PUB_DATE = "pubDate";
    private static String TAG_GUID = "guid";
    private static String TAG_IMG = "image";
    private static Bitmap bitmap = null;
	
	public List<RSSItem> getRSSItems(String Xml){
		String t = Xml;
		List<RSSItem> itemsList = new ArrayList<RSSItem>();
		if(Xml != null){
			Document doc = this.getDomElement(Xml);
			NodeList nodeList = doc.getElementsByTagName(TAG_CHANNEL);
			Element e = (Element) nodeList.item(0);
			
			NodeList items = e.getElementsByTagName(TAG_ITEM);
            // looping through each item
            for(int i = 0; i < items.getLength(); i++){
                Element e1 = (Element) items.item(i);
                String title = this.getValue(e1, TAG_TITLE);
                String link = this.getValue(e1, TAG_LINK);
                String description = this.getValue(e1, TAG_DESRIPTION);
                String pubdate = this.getValue(e1, TAG_PUB_DATE);
                String guid = this.getValue(e1, TAG_GUID);
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher m = p.matcher(description);
                String image= null;
                if (m.find()) {
                	  image= m.group(1);
                }
               
                //new getBitmapFromURL().execute(image);
                RSSItem rssItem = new RSSItem(i,title,image, link, description, pubdate, guid);
                 
                // adding item to list
                itemsList.add(rssItem);
            }

		}
		return itemsList;
	}
	public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child
                        .getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE || ( child.getNodeType() == Node.CDATA_SECTION_NODE)) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
	public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }
	public String getRSSLinkFromURL(String url) { 
		String rssUrl="";
		org.jsoup.nodes.Document doc = null;
		//try{
			try {
				doc = Jsoup.connect(url).get();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return rssUrl;
			}
		//}
		//catch(Exception ex){
			//return rssUrl;
		//}
		// finding rss links which are having link[type=application/rss+xml]
		org.jsoup.select.Elements links = doc.select("link[type=application/rss+xml]");
		if(links.size()>0){
			rssUrl = links.get(0).attr("href").toString();
		}
		else{
			org.jsoup.select.Elements links1 = doc
		            .select("link[type=application/atom+xml]");
		    if(links1.size() > 0){
		        rssUrl = links1.get(0).attr("href").toString();    
		    }
		}
		Log.d("Rss link: ", rssUrl);
		return rssUrl;
	}
	
	public String getXmlFromUrl(String rssUrl){
		String Xml = null;
		try {
			if(rssUrl != null){
				System.setProperty("http.keepAlive", "false");
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(rssUrl);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				Xml=EntityUtils.toString(httpEntity,HTTP.UTF_8).trim();
				
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.d("error", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("error", e.getMessage());
			e.printStackTrace();
		}
		return Xml;
	}
	
	public Document getDomElement(String Xml){
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			//InputSource is = new InputSource(new ByteArrayInputStream(
				    //xml.getBytes("UTF-8")));
			//is.setEncoding("UTF-8");
			try {
				is.setEncoding("UTF-8");
				StringEscapeUtils.unescapeHtml(Xml);
				Xml.replaceAll("\u00A0","");
				Log.d("xml",Xml);
				//Xml.replaceAll(" \"", "\"");
				//Charset.forName("UTF-8").encode(Xml);
				is.setCharacterStream(new StringReader(Xml));
				doc = (Document)builder.parse(is);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("error", e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("error", e.getMessage());
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("error", e.getMessage());
		}
		return doc;
	}
	
	
}
