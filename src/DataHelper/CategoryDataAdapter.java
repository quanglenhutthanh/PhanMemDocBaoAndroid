package DataHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.phanmemdocbao.AddWebsiteActivity;

import Entities.Category;
import Entities.Content;
import Entities.RSSFeed;
import Entities.Website;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CategoryDataAdapter {
	private Context context;
	private SQLiteDatabase db;
	private DataHelper dataHelper;
	private String tableName = "Category";
	private String[] FIELDS = new String[]{
			"_id",
			"name",
			"description",
			"type"
	};
	
	public CategoryDataAdapter(Context context){
		this.context = context;
		dataHelper = new DataHelper(context);
	}
	
	public CategoryDataAdapter open(){
		dataHelper = new DataHelper(context);
		db = dataHelper.getWritableDatabase();
		return this;
	}
	
	public List<Category> GetUserList(){
		String whereClause = "type = ?";
		String[] whereArgs = new String[] {
		    "1"
		};
		return GetList(whereClause, whereArgs);
	}
	
	public List<Category> GetAllList(){
		return GetList(null, null);
	}
	
	public List<Category> GetList(String whereClause,String[] whereArgs){
		List<Category> list = new ArrayList<Category>();
		Cursor c = db.query(tableName, FIELDS, whereClause, whereArgs, null, null, null);
		c.moveToFirst();
		while(!c.isAfterLast()) {
			list.add(new Category(c.getInt(c.getColumnIndex("_id")), 
					c.getString(c.getColumnIndex("name")), 
					c.getString(c.getColumnIndex("description")),null)); //add the item
		     c.moveToNext();
		}
		return list;
	}
	public Boolean Insert(Category category)
	{
		try
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put("name", category.getName());
			initialValues.put("description", category.getDescription());
			initialValues.put("type", 1);
			db.insert(tableName, null, initialValues);
			for(RSSFeed f:category.getFeeds()){
				FeedDataAdapter feedAdapter  = new FeedDataAdapter(context);
				feedAdapter.open();
				feedAdapter.Insert(f);
			}
		}
		catch(Exception ex)
		{
			return false;
		}
		return true;
	}
	 public Cursor GetAll() {
			return db.query(tableName, FIELDS, null, null, null, null, null);
		}
}
