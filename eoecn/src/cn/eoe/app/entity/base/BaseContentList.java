package cn.eoe.app.entity.base;

public abstract class BaseContentList {
	 
	private String name;		//list_name
	private String more_url;	//相对应的加载更多 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMore_url() {
		return more_url;
	}
	public void setMore_url(String more_url) {
		this.more_url = more_url;
	}
	
	

}
