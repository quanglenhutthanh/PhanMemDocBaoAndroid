package Entities;

import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import com.ocpsoft.pretty.time.PrettyTime;

import android.net.ParseException;
import android.util.Log;

public class RSSItem implements Comparable<RSSItem> {
	static SimpleDateFormat FORMATTER = 
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
	public static List<RSSItem> currentList;
	int _id;
	String title;
	String image;
	Website website;
	String guid;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Website getWebsite() {
		return website;
	}
	public void setWebsite(Website website) {
		this.website = website;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	String link;
    String description;
    Date pubdate;
    
	public RSSItem(){
		
	}
	public RSSItem(int id,String title,String image, String link, String description, String pubdate, String guid){
        this.title = title;
        this.image = image;
        this.link = link;
        this.description = description;
        setPubdate(pubdate);
        this._id=id;
        //this.guid = guid;
    }
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFormatPubdate() {
		PrettyTime ptime = new PrettyTime();
		return ptime.format(this.pubdate);
		//return FORMATTER.format(this.pubdate);
		//return pubdate.toString();
	}
	public Date getPubdate(){
		return pubdate;
	}
	public void setPubdate(String date) {
		// pad the date if necessary
		/*while (!date.endsWith("00")){
			date += "0";
		}*/
		//FORMATTER.setTimeZone(TimeZone.getTimeZone("UTC+07:00"));
		try {
			try {
				Date dt;
				try{
					dt = new Date(date);
				}catch(Exception ex)
				{
					dt = new Date();
				}
				this.pubdate = (Date) FORMATTER.parse(FORMATTER.format(dt));
				
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				this.pubdate = new Date();
				e.printStackTrace();
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	 @Override
	  public int compareTo(RSSItem o) {
	    if (getPubdate()== null || o.getPubdate() == null)
	      return 0;
	    return o.getPubdate().compareTo(getPubdate());
	  }
    
}
