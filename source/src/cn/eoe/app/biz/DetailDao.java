package cn.eoe.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import cn.eoe.app.config.Constants;
import cn.eoe.app.entity.DetailJson;
import cn.eoe.app.entity.DetailResponseEntity;
import cn.eoe.app.utils.RequestCacheUtil;

public class DetailDao extends BaseDao {
	
	private String mUrl;
	
	public DetailDao(Context context,String url)
	{
		super(context);
		mUrl=url;
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
			String result = RequestCacheUtil.getRequestContent(mContext,
					mUrl, Constants.WebSourceType.Json,
					Constants.DBContentType.Content_content, useCache);
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
