package Entities;

import java.io.Serializable;

public class Website implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int _id;
	String name;
	String icon;
	String link;
	String description;
	String className;
	int isDefault;
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Website(){
		
	}
	public Website(int id, String name, String icon, String link,
			String description,String className,int isDefault) {
		super();
		this._id = id;
		this.name = name;
		this.icon = icon;
		this.link = link;
		this.description = description;
		this.className = className;
		this.isDefault = isDefault;
	}
	
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
	
}
