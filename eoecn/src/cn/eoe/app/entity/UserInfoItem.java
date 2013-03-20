package cn.eoe.app.entity;

import java.util.List;

public class UserInfoItem {
	private String name;
	private String level;
	private String points;
	private String eoe_p;
	private String eoe_m;
	private String reg_at;
	private String head_image_url;
	List<UserIcon> icon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getEoe_p() {
		return eoe_p;
	}

	public void setEoe_p(String eoe_p) {
		this.eoe_p = eoe_p;
	}

	public String getEoe_m() {
		return eoe_m;
	}

	public void setEoe_m(String eoe_m) {
		this.eoe_m = eoe_m;
	}

	public String getReg_at() {
		return reg_at;
	}

	public void setReg_at(String reg_at) {
		this.reg_at = reg_at;
	}

	public List<UserIcon> getIcon() {
		return icon;
	}

	public void setIcon(List<UserIcon> icon) {
		this.icon = icon;
	}

	public String getHead_image_url() {
		return head_image_url;
	}

	public void setHead_image_url(String head_image_url) {
		this.head_image_url = head_image_url;
	}

	
}


