package cn.eoe.app.entity;
import cn.eoe.app.entity.base.*;
/**
 * 资讯详情item实体类
 * @author wangxin
 *
 */
public class NewsContentItem extends BaseContentItem{
	
	private String 	title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	private String	thumbnail_url;
	
	public String getThumbnail_url() {
		return thumbnail_url;
	}
	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}
	
}
