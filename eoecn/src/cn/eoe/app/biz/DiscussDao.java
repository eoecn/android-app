package cn.eoe.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import cn.eoe.app.config.Constants;
import cn.eoe.app.config.Urls;
import cn.eoe.app.entity.DetailsOwnDiscussJson;
import cn.eoe.app.entity.NewsJson;
import cn.eoe.app.entity.NewsResponseEntity;
import cn.eoe.app.https.HttpUtils;
import cn.eoe.app.ui.DetailsDiscussActivity;
import cn.eoe.app.utils.RequestCacheUtil;

public class DiscussDao extends BaseDao {

	public DiscussDao(Context context) {
		super(context);
	}

	private NewsResponseEntity _newsResponse;

	public NewsResponseEntity get_newsResponse() {
		return _newsResponse;
	}

	public void set_newsResponse(NewsResponseEntity _newsResponse) {
		this._newsResponse = _newsResponse;
	}

	public DetailsOwnDiscussJson mapperJson(String url, boolean useCache) {
		// TODO Auto-generated method stub
		DetailsOwnDiscussJson json = null;
		try {
			String result = RequestCacheUtil.getRequestContent(mContext, url,
					Constants.WebSourceType.Json,
					Constants.DBContentType.Discuss, useCache);
			json = mObjectMapper.readValue(result,
					new TypeReference<DetailsOwnDiscussJson>() {
					});
			return json;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
