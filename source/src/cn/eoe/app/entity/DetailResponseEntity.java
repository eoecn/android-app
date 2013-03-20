package cn.eoe.app.entity;

public class DetailResponseEntity {
	
	private String content;
	private String share_url;
	private int comment_num;
	private DetailBarEntity bar;
	
	
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
