package DataHelper;

import java.util.ArrayList;
import java.util.List;

import Entities.Website;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WebsiteDataAdapter {
	private Context context;
	private SQLiteDatabase db;
	private DataHelper dataHelper;
	private String tableName = "Website";
	private String[] FIELDS = new String[]{
			"_id",
			"name",
			"link",
			"icon",
			"description",
			"className",
			"isDefault"
	};
	
	public WebsiteDataAdapter(Context context){
		this.context = context;
		dataHelper = new DataHelper(context);
	}
	
	public WebsiteDataAdapter open(){
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
	
	public List<Website> GetAllList(){
		return GetList(null, null);
	}
	
	public List<Website> GetWebsitesByType(int type){
		
		String whereClause = "isDefault = ?";
		String[] whereArgs = new String[] {
				String.valueOf(type)
		};
		if(type == 0){
			whereClause = "isDefault != ?";
			whereArgs = new String[] {
					"1"
			};
		}
		
		return GetList(whereClause, whereArgs);
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
	public List<Website> GetList(String whereClause,String[] whereArgs){
		List<Website> list = new ArrayList<Website>();
		Cursor c = db.query(tableName, FIELDS, whereClause, whereArgs, null, null, null);
		c.moveToFirst();
		while(!c.isAfterLast()) {
			list.add(new Website(c.getInt(c.getColumnIndex("_id")), 
					c.getString(c.getColumnIndex("name")), 
					c.getString(c.getColumnIndex("icon")), 
					c.getString(c.getColumnIndex("link")), 
					c.getString(c.getColumnIndex("description")),
					c.getString(c.getColumnIndex("className")),
					c.getInt(c.getColumnIndex("isDefault")))); //add the item
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
	public Boolean Insert(Website website)
	{
		try
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put("name", website.getName());
			initialValues.put("description", website.getDescription());
			initialValues.put("link", website.getLink());
			initialValues.put("isDefault", 0);
			initialValues.put("icon", website.getIcon());
			db.insert(tableName, null, initialValues);
		}
		catch(Exception ex)
		{
			return false;
		}
		return true;
	}
	public boolean Delete(int Id) {
		db.delete(tableName, "_id =" + Id, null);
		return db.delete("Feed","websiteId ="+ Id,null)>0;
	}
}
