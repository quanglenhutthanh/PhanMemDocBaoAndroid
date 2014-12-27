package Entities;

import java.util.List;

public class Content {
	int _id;
	String name;
	String description;
	List<RSSFeed> feed;
	public Content() {
		super();
	}
	public Content(int _id, String name, String description, List<RSSFeed> feed) {
		super();
		this._id = _id;
		this.name = name;
		this.description = description;
		this.feed = feed;
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
	public List<RSSFeed> getFeed() {
		return feed;
	}
	public void setFeed(List<RSSFeed> feed) {
		this.feed = feed;
	}
}
