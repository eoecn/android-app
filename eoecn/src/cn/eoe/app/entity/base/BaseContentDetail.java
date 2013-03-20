package cn.eoe.app.entity.base;

import cn.eoe.app.entity.DetailBarEntity;

public abstract class BaseContentDetail {
	
	private String 		content;		//内容
	private String		share_url;		//分享
	private	int			comment_num;	//评论数量
	private	DetailBarEntity	bar;		//动态管理操作栏
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getShare_url() {
		return share_url;
	}
	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public DetailBarEntity getBar() {
		return bar;
	}
	public void setBar(DetailBarEntity bar) {
		this.bar = bar;
	}
	
	

}
