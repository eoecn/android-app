package cn.eoe.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import cn.eoe.app.config.Constants;
import cn.eoe.app.config.Urls;
import cn.eoe.app.entity.BlogsJson;
import cn.eoe.app.entity.BlogsResponseEntity;
import cn.eoe.app.utils.RequestCacheUtil;

public class BlogsDao extends BaseDao {

	private BlogsResponseEntity _blogsResponse;

	public BlogsDao(Context context) {
		super(context);
	}

	public BlogsResponseEntity getBlogsResponse() {
		return _blogsResponse;
	}

	public void setBlogsResponse(BlogsResponseEntity blogsResponse) {
		this._blogsResponse = blogsResponse;
	}

	public BlogsResponseEntity mapperJson(boolean useCache) {
		BlogsJson blogsJson_;
		try {
			String result = RequestCacheUtil.getRequestContent(mContext,
					Urls.BLOGS_LIST, Constants.WebSourceType.Json,
					Constants.DBContentType.Content_list, useCache);
			blogsJson_ = mObjectMapper.readValue(result
			/* HttpUtils.getByHttpClient(mContext, Urls.BLOGS_LIST) */,
					new TypeReference<BlogsJson>() {
					});
			if (blogsJson_ == null) {
				return null;
			}
			_blogsResponse = blogsJson_.getResponse();
			return _blogsResponse;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
