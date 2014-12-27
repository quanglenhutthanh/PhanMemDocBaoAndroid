package DataHelper;

import java.util.ArrayList;
import java.util.List;

import Entities.Content;
import Entities.RSSFeed;
import Entities.Website;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ContentDataAdapter {
	private Context context;
	private SQLiteDatabase db;
	private DataHelper dataHelper;
	private String tableName = "Content";
	private String[] FIELDS = new String[]{
			"_id",
			"name",
			"description"
	};
	
	public ContentDataAdapter(Context context){
		this.context = context;
		dataHelper = new DataHelper(context);
	}
	
	public ContentDataAdapter open(){
		dataHelper = new DataHelper(context);
		db = dataHelper.getWritableDatabase();
		return this;
	}
	public Website GetWebsiteById(int id){
		String whereClause = "_id = ?";
		String[] whereArgs = new String[] {
				String.valueOf(id)
		};
		Cursor c = db.query(tableName, FIELDS, whereClause, whereArgs, null, null, null);
		if(c!=null && c.getCount()>0){
			c.moveToFirst();
			return new Website(c.getInt(c.getColumnIndex("_id")),
					c.getString(c.getColumnIndex("name")), 
					c.getString(c.getColumnIndex("icon")), 
					c.getString(c.getColumnIndex("link")), 
					c.getString(c.getColumnIndex("description")),
					c.getString(c.getColumnIndex("className")),
					c.getInt(c.getColumnIndex("isDefault"))
					);
		}
		return null;
	}
	public List<Content> GetAllList(){
		return GetList(null, null);
	}
	
	public String GetWebsiteName(int id){
		String whereClause = "_id = ?";
		String[] whereArgs = new String[] {
				String.valueOf(id)
		};
		Cursor c = db.query(tableName, FIELDS, whereClause, whereArgs, null, null, null);
		c.moveToFirst();
		return c.getString(c.getColumnIndex("name"));
	}
	public List<Content> GetList(String whereClause,String[] whereArgs){
		List<Content> list = new ArrayList<Content>();
		FeedDataAdapter feedDataAdapter = new FeedDataAdapter(context);
		feedDataAdapter.open();
		Cursor c = db.query(tableName, FIELDS, whereClause, whereArgs, null, null, null);
		c.moveToFirst();
		while(!c.isAfterLast()) {
			List<RSSFeed> feeds = new ArrayList<RSSFeed>();
			List<Integer> ids = new ArrayList<Integer>();
			ids = GetListFeedId(c.getInt(c.getColumnIndex("_id")));
			for(int id:ids){
				feeds.add(feedDataAdapter.GetFeedById(id));
			}
			list.add(new Content(c.getInt(c.getColumnIndex("_id")), 
					c.getString(c.getColumnIndex("name")), 
					c.getString(c.getColumnIndex("description")),
					feeds
					));
			c.moveToNext();
		}
		return list;
	}
	public List<Integer> GetListFeedId(int contentId){
		List<Integer> list = new ArrayList<Integer>();
		String[] FIELDS1 = new String[]{
				"_id",
				"contentId",
				"feedId"
		};
		String whereClause = "contentId = ?";
		String[] whereArgs = new String[] {
				String.valueOf(contentId)
		};
		Cursor c = db.query("ContentFeed", FIELDS1, whereClause, whereArgs, null, null, null);
		c.moveToFirst();
		while(!c.isAfterLast()) {
			list.add(c.getInt(c.getColumnIndex("feedId")));
			c.moveToNext();
		}
		return list;
	}
	public Cursor GetAll() {
		return db.query(tableName, FIELDS, null, null, null, null, null);
	}
	public int getMaxID() {
        int id = 0;
        final String MY_QUERY = "SELECT MAX(_id) AS _id FROM "+tableName;
        Cursor mCursor = db.rawQuery(MY_QUERY, null);  

              if (mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                id = mCursor.getInt(mCursor.getColumnIndex("_id"));
              }

         return id;
     }
	
	public Boolean Insert(Content content)
	{
		try
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put("name", content.getName());
			initialValues.put("description", content.getDescription());
			db.insert(tableName, null, initialValues);
			for(RSSFeed f:content.getFeed()){
				ContentValues initialValues1 = new ContentValues();
				initialValues1.put("contentId", getMaxID());
				initialValues1.put("feedId", f.get_id());
				Log.d("insert",String.valueOf(getMaxID()));
				db.insert("ContentFeed", null, initialValues1);
			}
		}
		catch(Exception ex)
		{
			return false;
		}
		return true;
	}
	public boolean Delete(int Id) {
		db.delete(tableName, "_id =" + Id, null);
		return db.delete("ContentFeed","contentId ="+ Id,null)>0;
	}
}
