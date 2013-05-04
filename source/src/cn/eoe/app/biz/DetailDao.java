package cn.eoe.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import android.app.Activity;
import android.util.Log;
import cn.eoe.app.config.Constants;
import cn.eoe.app.entity.DetailJson;
import cn.eoe.app.entity.DetailResponseEntity;
import cn.eoe.app.utils.RequestCacheUtil;
import cn.eoe.app.utils.Utility;

public class DetailDao extends BaseDao {
	
	private String mUrl;
	
	public DetailDao(Activity activity,String url)
	{
		super(activity);
		mUrl=url+ Utility.getScreenParams(mActivity);
	}
	
	private DetailResponseEntity mDetailResponseEntity;

	public DetailResponseEntity getmDetailResponseEntity() {
		return mDetailResponseEntity;
	}

	public void setmDetailResponseEntity(DetailResponseEntity mDetailResponseEntity) {
		this.mDetailResponseEntity = mDetailResponseEntity;
	}
	
	public DetailResponseEntity mapperJson(boolean useCache){
		try {
			String result = RequestCacheUtil.getRequestContent(mActivity,
					mUrl, Constants.WebSourceType.Json,
					Constants.DBContentType.Content_content, useCache);
			Log.i("info",mUrl);
			DetailJson detailJson = mObjectMapper.readValue(result, new TypeReference<DetailJson>() {});
			this.mDetailResponseEntity = detailJson.getResponse();
			return this.mDetailResponseEntity;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
