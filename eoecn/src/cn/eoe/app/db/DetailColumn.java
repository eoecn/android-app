package cn.eoe.app.db;

import java.util.HashMap;
import java.util.Map;

import android.net.Uri;

public class DetailColumn extends DatabaseColumn {

	public static final String TABLE_NAME = "detailRecord";
	// public static final String KEY_WORD = "key_word";
	// public static final String CONTENT_ID = "contentID";
	public static final String URL = "url";
	public static final String KEY = "key";
	public static final String GOOD = "good";
	public static final String BAD = "bad";
	public static final String COLLECT = "collect";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	private static final Map<String, String> mColumnMap = new HashMap<String, String>();
	static {
		mColumnMap.put(_ID, "integer primary key autoincrement");
		mColumnMap.put(URL, "text");
		mColumnMap.put(KEY, "text");
		mColumnMap.put(GOOD, "integer");
		mColumnMap.put(BAD, "integer");
		mColumnMap.put(COLLECT, "integer");
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	@Override
	public Uri getTableContent() {
		// TODO Auto-generated method stub
		return CONTENT_URI;
	}

	@Override
	protected Map<String, String> getTableMap() {
		// TODO Auto-generated method stub
		return mColumnMap;
	}

}
