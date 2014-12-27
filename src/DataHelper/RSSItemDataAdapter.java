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

public class RSSItemDataAdapter {
	private Context context;
	private SQLiteDatabase db;
	private DataHelper dataHelper;
	private String tableName = "Article";
	private String[] FIELDS = new String[]{
			"_id",
			"title",
			"image",
			"link",
			"websiteId",
			"content",
			"pubdate"
	};
	public RSSItemDataAdapter(Context context){
		this.context = context;
		dataHelper = new DataHelper(context);
	}
	public RSSItemDataAdapter open(){
		dataHelper = new DataHelper(context);
		db = dataHelper.getWritableDatabase();
		return this;
	}
	
	public List<RSSItem> GetAllList(){
		return GetList(null, null);
	}
		
	public Cursor getbyLink(String link) {
		String whereClause = "link = ?";
		String[] whereArgs = new String[] {
		    link
		};
		Cursor mCursor = null;
		mCursor = db.query(tableName, FIELDS, whereClause, whereArgs, null, null, null);
		return mCursor;
	}
	
	public List<RSSItem> GetList(String whereClause,String[] whereArgs){
		WebsiteDataAdapter website = new WebsiteDataAdapter(context);
		website.open();
		List<RSSItem> items = new ArrayList<RSSItem>(); 
		Cursor c = db.query(tableName, FIELDS, whereClause, whereArgs, null, null, null);
		c.moveToFirst();
		while(!c.isAfterLast()){
			Website w = new Website();
			w=website.GetWebsiteById(c.getInt(c.getColumnIndex("websiteId")));
			if(w!=null){
				RSSItem item = new RSSItem(c.getInt(c.getColumnIndex("_id")), 
						c.getString(c.getColumnIndex("title")), 
						c.getString(c.getColumnIndex("image")),
						c.getString(c.getColumnIndex("link")),
						c.getString(c.getColumnIndex("content")),
						c.getString(c.getColumnIndex("pubdate")),
						"") ;
				item.setWebsite(w);
				items.add(item);
			}
			c.moveToNext();
		}
		return items;
	}
	
	public Boolean Insert(RSSItem item)
	{
		try
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put("websiteId", item.getWebsite().getId());
			initialValues.put("title", item.getTitle());
			initialValues.put("image", item.getImage());
			initialValues.put("link", item.getLink());
			initialValues.put("pubdate", item.getPubdate().toString());
			initialValues.put("content", item.getDescription());
			db.insert(tableName, null, initialValues);
		}
		catch(Exception ex)
		{
			return false;
		}
		return true;
	}
	public boolean Delete(int Id) {
		return db.delete(tableName,"_id ="+ Id,null)>0;
	}
}
