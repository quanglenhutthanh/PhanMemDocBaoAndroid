package Entities;

import java.util.List;

public class Category {
	int _id;
	String name;
	String description;
	boolean isChecked;
	List<RSSFeed> feeds;
	public List<RSSFeed> getFeeds() {
		return feeds;
	}
	public void setFeeds(List<RSSFeed> feeds) {
		this.feeds = feeds;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public Category(int _id, String name, String description, List<RSSFeed> feeds) {
		super();
		this._id = _id;
		this.name = name;
		this.description = description;
		this.feeds = feeds;
	}
	public Category(){
		
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
