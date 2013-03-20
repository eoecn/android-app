package cn.eoe.app.db;

import java.util.HashMap;
import java.util.Map;

import android.net.Uri;
/**
 * news
 * @author wangxin
 *
 */
public class NewsColumn extends DatabaseColumn {
	
	public static final String TABLE_NAME			= 	"news";
	public static final String 	UPDATE_TIME			=	"update_time";
	public static final String JSON_PATH			=	"json_path";
	
	public static final Uri		CONTENT_URI			=	Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
	private static final Map<String,String> mColumnMap = new HashMap<String,String>();
	 static {
		 
		 mColumnMap.put(_ID, "integer primary key autoincrement");
		 mColumnMap.put(UPDATE_TIME, "timestamp");
		 mColumnMap.put(JSON_PATH, "text not null");
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
