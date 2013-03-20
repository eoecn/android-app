package cn.eoe.app.db;

import java.util.HashMap;
import java.util.Map;

import android.net.Uri;

public class RequestCacheColumn extends DatabaseColumn {

	public static final String TABLE_NAME = "request_cache";
	public static final String URL = "url";
	public static final String SOURCE_TYPE = "source_type";
	public static final String Content_type = "content_type";
	public static final String Timestamp = "timestamp";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);

	private static final Map<String, String> mColumnMap = new HashMap<String, String>();
	static {
		mColumnMap.put(_ID, "integer primary key autoincrement");
		mColumnMap.put(URL, "text");
		mColumnMap.put(SOURCE_TYPE, "text");
		mColumnMap.put(Content_type, "text");
		mColumnMap.put(Timestamp, "integer");
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
