package cn.eoe.app.biz;

import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;

public  class BaseDao {
	ObjectMapper mObjectMapper = new ObjectMapper();

	protected Context mContext;
	
	public BaseDao(){};
	
	public BaseDao(Context context)
	{
		mContext=context;
	}

	
}
