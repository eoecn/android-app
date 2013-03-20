package cn.eoe.app.entity;

import java.util.List;

public class UserResponse {
	private UserInfoItem info;
	private List<UserFavoriteList> favorite;

	
	
	public UserInfoItem getInfo() {
		return info;
	}

	public void setInfo(UserInfoItem info) {
		this.info = info;
	}

	public List<UserFavoriteList> getFavorite() {
		return favorite;
	}

	public void setFavorite(List<UserFavoriteList> favorite) {
		this.favorite = favorite;
	}

	

}
