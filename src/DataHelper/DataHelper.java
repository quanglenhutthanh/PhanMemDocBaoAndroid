package DataHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
	private static String DB_PATH = "";  
	private static String DB_NAME ="RssReaderDB";// Database name 
	
	//private final Context mContext;
	private final Context mContext; 
	SQLiteDatabase mDb;
	public DataHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.mContext=context;
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		if(!checkDataBase()){
			try {
				createDataBase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Toast.makeText(this, "database exist", Toast.LENGTH_LONG).show();
		}
	}
		
	public void createDataBase() throws IOException 
	{ 
		
	    //If database not exists copy it from the assets 
		boolean mDataBaseExist = checkDataBase(); 
	    if(!mDataBaseExist) 
	    { 
	        this.getReadableDatabase(); 
	        this.close(); 
	        try  
	        { 
	            //Copy the database from assests 
	            copyDataBase(); 
	            Log.e("TAG", "createDatabase database created"); 
	        }  
	        catch (IOException mIOException)  
	        { 
	            throw new Error("ErrorCopyingDataBase"); 
	        } 
	    } 
	} 
	private void copyDataBase() throws IOException 
    { 
		
        InputStream mInput = mContext.getAssets().open(DB_NAME); 
       
        String outFileName = DB_PATH + DB_NAME; 
        OutputStream mOutput = new FileOutputStream(outFileName); 
        byte[] mBuffer = new byte[1024]; 
        int mLength; 
        while ((mLength = mInput.read(mBuffer))>0) 
        { 
            mOutput.write(mBuffer, 0, mLength); 
        } 
        mOutput.flush(); 
        mOutput.close(); 
        mInput.close(); 
    } 
	public boolean checkDataBase() 
    { 
        File dbFile = new File(DB_PATH + DB_NAME); 
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists()); 
        return dbFile.exists(); 
    } 
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	} 
}
