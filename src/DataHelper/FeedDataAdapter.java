package DataHelper;

import java.util.ArrayList;
import java.util.List;



import Entities.RSSFeed;
import Entities.RSSItem;
import Entities.Website;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FeedDataAdapter {
	private Context context;
	private SQLiteDatabase db;
	private DataHelper dataHelper;
	private String tableName = "Feed";
	private String[] FIELDS = new String[]{
			"_id",
			"websiteId",
			"description",
			"feedUrl"
	};
	
	public FeedDataAdapter(Context context){
		this.context = context;
		dataHelper = new DataHelper(context);
	}
	public List<RSSFeed> GetFeedsByWebsiteId(int id){
		String whereClause = "websiteId = ? and isHomePage = ? ";
		String[] whereArgs = new String[] {
		    String.valueOf(id),
		    "1"
		};
		return GetList(whereClause, whereArgs);
	}
	public RSSFeed GetFeedById(int id){
		String whereClause = "_id = ?";
		String[] whereArgs = new String[] {
				String.valueOf(id)
		};
		WebsiteDataAdapter website = new WebsiteDataAdapter(context);
		website.open();
		Cursor c = db.query(tableName, FIELDS, whereClause, whereArgs, null, null, null);
		if(c!=null && c.getCount()>0){
			c.moveToFirst();
			return new RSSFeed(c.getInt(c.getColumnIndex("_id")),
					website.GetWebsiteById(c.getInt(c.getColumnIndex("websiteId"))),
					c.getString(c.getColumnIndex("description")),
					c.getString(c.getColumnIndex("feedUrl")));
		}
		return null;
	}
	public List<RSSFeed> GetFeedsByCategoryId(int id){
		String whereClause = "categoryId = ?";
		String[] whereArgs = new String[] {
		    String.valueOf(id)
		};
		return GetList(whereClause, whereArgs);
	}
	public List<RSSFeed> GetFeatureFeed(){
		String whereClause = "isFeature = ?";
		String[] whereArgs = new String[] {
		    "1"
		};
		return GetList(whereClause, whereArgs);
	}
	public List<RSSFeed> GetList(String whereClause,String[] whereArgs){
		WebsiteDataAdapter website = new WebsiteDataAdapter(context);
		website.open();
		List<RSSFeed> feeds = new ArrayList<RSSFeed>(); 
		Cursor c = db.query(tableName, FIELDS, whereClause, whereArgs, null, null, null);
		c.moveToFirst();
		while(!c.isAfterLast()){
			if(website.GetWebsiteById(c.getInt(c.getColumnIndex("websiteId")))!=null){
				RSSFeed feed = new RSSFeed(c.getInt(c.getColumnIndex("_id")),
						website.GetWebsiteById(c.getInt(c.getColumnIndex("websiteId"))),
						c.getString(c.getColumnIndex("description")),
						c.getString(c.getColumnIndex("feedUrl")));
				feeds.add(feed);
			}
			c.moveToNext();
		}
		return feeds;
	}
	public Cursor GetAll() {
		return db.query(tableName, FIELDS, null, null, null, null, null);
	}
	public Boolean Insert(RSSFeed feed)
	{
		try
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put("isHomePage", 1);
			initialValues.put("isFeature", 0);
			initialValues.put("websiteId", feed.getWebsite().getId());
			initialValues.put("categoryId", -1);
			initialValues.put("feedUrl", feed.getLink());
			db.insert(tableName, null, initialValues);
		}
		catch(Exception ex)
		{
			return false;
		}
		return true;
	}
	public FeedDataAdapter open(){
		dataHelper = new DataHelper(context);
		db = dataHelper.getWritableDatabase();
		return this;
	}
}
