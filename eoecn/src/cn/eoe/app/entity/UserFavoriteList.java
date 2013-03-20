package cn.eoe.app.entity;

import java.util.List;

public class UserFavoriteList {
	private String name;
	private List<UserCollectionItem> lists;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserCollectionItem> getLists() {
		return lists;
	}

	public void setLists(List<UserCollectionItem> lists) {
		this.lists = lists;
	}

}

