package Adapter;



import DataHelper.RSSItemDataAdapter;
import Entities.RSSItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RssItemDataAdapter {
	public static final String KEY_ROWID = "_id";
	public static final String[] FLICKR_PHOTO_FIELDS = new String[] {
		KEY_ROWID,    
		"item_id", // flickr id of the photo
		"title",
		"pubdate", 
		"description",
		"websiteId"	
	};
	private DatabaseHelper mDbHelper;
	public SQLiteDatabase mDb;
	private static final String DATABASE_CREATE =
			"create table rssitem (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "item_id not null,"
			+ "title text,"
			+ "pubdate text,"		
			+ "description text,"
			+ "websietId INTEGER"
			+");";
	
	private final Context context;
	public static String TAG = RssItemAdapter.class.getSimpleName();
	 static final String DATABASE_NAME = "RSSReaderDB";
	 public static final String DATABASE_TABLE = "rssitem";
	private static final int DATABASE_VERSION = 1;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG , "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS rssitem");
			onCreate(db);
		}
	}
	
	public RssItemDataAdapter(Context context) {
		this.context = context;
	}
	
	public RssItemDataAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(context);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	public void close() {
		if(mDbHelper!=null){
			mDbHelper.close();
		}
	}
	
	public Cursor fetchItems() {

		return mDb.query(DATABASE_TABLE, FLICKR_PHOTO_FIELDS, null, null, null, null, null);
	}
	public Cursor fetchByItemId(String itemId) throws SQLException {
		Cursor mCursor =
			mDb.query(true, DATABASE_TABLE, FLICKR_PHOTO_FIELDS, "item_id" + "='" + itemId+"'", null,
					null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public void upgrade() throws SQLException {
		mDbHelper = new DatabaseHelper(context); //open
		mDb = mDbHelper.getWritableDatabase();
		mDbHelper.onUpgrade(mDb, 1, 0);
	}
	
	public long createItem(RSSItem item) {
		ContentValues initialValues = new ContentValues();
		if (item.getGuid()!= null)
			initialValues.put("item_id", item.getGuid());	
		if (item.getTitle()!=null)
			initialValues.put("title", item.getTitle());	
		if (item.getPubdate()!=null)
			initialValues.put("pubdate", item.getPubdate().toString());	
		if (item.getDescription()!=null)
			initialValues.put("description", item.getDescription());	
		if (item.getWebsite().getId() != 0)
			initialValues.put("websiteId", item.getWebsite().getId());	
				
		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean updateItem(String id, RSSItem item) {
		ContentValues initialValues = new ContentValues();
		if (item.getTitle()!=null)
			initialValues.put("title", item.getTitle());	
		if (item.getPubdate()!=null)
			initialValues.put("pubdate", item.getPubdate().toString());	
		if (item.getDescription()!=null)
			initialValues.put("description", item.getDescription());	
		if (item.getWebsite().getId() != 0)
			initialValues.put("websiteId", item.getWebsite().getId());	
		return mDb.update(DATABASE_TABLE, initialValues, "item_id" + "=" + id, null) > 0;
	}
	public boolean deleteItem(long rowId) {
		return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public boolean deleteItem(String item_id) {
		return mDb.delete(DATABASE_TABLE, "item_id" + "=" + item_id, null) > 0;
	}
}
