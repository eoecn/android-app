package cn.eoe.app.entity;

import java.util.List;

public class DetailsDiscussResponse {
	private String more_url;
	private List<DetailsDiscussItem> items;
	public String getMore_url() {
		return more_url;
	}
	public void setMore_url(String more_url) {
		this.more_url = more_url;
	}
	public List<DetailsDiscussItem> getItems() {
		return items;
	}
	public void setItems(List<DetailsDiscussItem> items) {
		this.items = items;
	}
	
	
}
