package Entities;

public class RSSFeed {
	int _id;
	String link;
	Website website;
	String description;
	boolean isChecked;
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Website getWebsite() {
		return website;
	}
	public void setWebsite(Website website) {
		this.website = website;
	}
	public RSSFeed(){
		
	}
	public RSSFeed(int _id, Website website,String description, String link) {
		super();
		this._id = _id;
		this.website = website;
		this.link = link;
		this.description = description;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
