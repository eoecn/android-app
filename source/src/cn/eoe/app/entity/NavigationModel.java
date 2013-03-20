package cn.eoe.app.entity;

public class NavigationModel {
	
	private String name;
	//作为唯一标识符 news blog wiki 方便于每个页面请求相对应的地址
	private String tags;
	
	public  NavigationModel(String name1,String tags1){
		this.name = name1;
		this.tags = tags1;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
	

}
