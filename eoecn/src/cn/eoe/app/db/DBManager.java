package cn.eoe.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private static DBManager mDBManagerInstance = null;
	private Context mContext = null;
	private DBHelper dbHelper = null;
	private SQLiteDatabase mSQLiteDB = null;
	
	private DBManager (Context ctx){
		mContext = ctx;
		if(dbHelper == null){
			dbHelper = DBHelper.getInstance(ctx);
			mSQLiteDB = dbHelper.getReadableDatabase();
		}
	}
	
	public static DBManager getDBManager(Context ctx){
		if(mDBManagerInstance == null ){
			mDBManagerInstance = new DBManager(ctx);
		}
		return mDBManagerInstance;
	}
	
	

}
